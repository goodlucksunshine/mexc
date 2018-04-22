package com.mexc.admin.core.service.asset.impl;

import com.alibaba.fastjson.JSON;
import com.laile.esf.common.exception.BusinessException;
import com.laile.esf.common.exception.ResultCode;
import com.laile.esf.common.util.Page;
import com.mexc.admin.core.service.asset.IEntrustOrderService;
import com.mexc.common.constant.CommonConstant;
import com.mexc.common.util.jedis.RedisUtil;
import com.mexc.dao.delegate.market.MarketDelegate;
import com.mexc.dao.delegate.member.MemberAssetDelegate;
import com.mexc.dao.delegate.member.MemberDelegate;
import com.mexc.dao.delegate.vcoin.MexcEnTrustDelegate;
import com.mexc.dao.delegate.vcoin.MexcTradeDetailDelegate;
import com.mexc.dao.dto.order.CancelEntrustTradeDto;
import com.mexc.dao.dto.order.OrderQueryDto;
import com.mexc.dao.javaenum.EntrustOrderEnum;
import com.mexc.dao.model.market.MexcMarket;
import com.mexc.dao.model.member.MexcMember;
import com.mexc.dao.model.member.MexcMemberAsset;
import com.mexc.dao.model.vcoin.MexcEnTrust;
import com.mexc.dao.model.vcoin.MexcTradeDetail;
import com.mexc.match.engine.service.IExchangeMatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by huangxinguang on 2018/1/23 上午10:06.
 */
@Service
public class EntrustOrderService implements IEntrustOrderService {
    private static final Logger logger = LoggerFactory.getLogger(EntrustOrderService.class);

    @Autowired
    private MemberDelegate memberDelegate;

    @Autowired
    private MarketDelegate marketDelegate;

    @Autowired
    private MexcEnTrustDelegate mexcEnTrustDelegate;

    @Autowired
    private MexcTradeDetailDelegate mexcTradeDetailDelegate;

    @Autowired
    private IExchangeMatchService exchangeMatchService;

    @Autowired
    private MemberAssetDelegate memberAssetDelegate;

    @Override
    public Page<MexcEnTrust> queryEntrustOrderPage(OrderQueryDto queryDto) {
        return mexcEnTrustDelegate.queryEntrustOrderPage(queryDto);
    }

    /**
     * 撤单
     * @param cancelEntrustTradeDto
     */
    @Transactional
    public void cancelEntrustOrder(CancelEntrustTradeDto cancelEntrustTradeDto) {
        MexcMember member = memberDelegate.queryMermberByAccount(cancelEntrustTradeDto.getAccount());
        MexcEnTrust enTrust = mexcEnTrustDelegate.queryEntrust(cancelEntrustTradeDto.getTradeNo());
        if(member == null) {
            logger.error("mexc-admin撤单：撤单用户不存在,entrust:{}", JSON.toJSONString(enTrust));
            throw new BusinessException(ResultCode.COMMON_ERROR, "撤单用户不存在.");
        }

        /**更新委托状态:全撤 状态改为已撤 、部撤状态改为部分成交**/
        if (enTrust.getTradeRemainNumber().compareTo(enTrust.getTradeNumber()) < 0) {
            enTrust.setStatus(EntrustOrderEnum.PART_CANCEL.getStatus());
        } else {
            enTrust.setStatus(EntrustOrderEnum.CANCEL.getStatus());
        }
        int rows = mexcEnTrustDelegate.updateEntrustToCancel(enTrust);
        if (rows < 1) {
            logger.error("mexc-admin撤单：修改委托状态失败,有可能状态已发生更改,entrust:{}", JSON.toJSONString(enTrust));
            throw new BusinessException(ResultCode.COMMON_ERROR, "修改委托状态失败,请检查委托状态是否已经更改.");
        }

        try {
            /**增加撤销明细**/
            MexcTradeDetail cancelTradeDetail = new MexcTradeDetail();
            cancelTradeDetail.setMarketId(enTrust.getMarketId());
            cancelTradeDetail.setCreateBy(member.getId());
            cancelTradeDetail.setCreateByName(member.getAccount());
            cancelTradeDetail.setCreateTime(new Date());
            cancelTradeDetail.setTradeNumber(enTrust.getTradeRemainNumber());
            cancelTradeDetail.setMemberId(member.getId());
            cancelTradeDetail.setTradePrice(enTrust.getTradePrice());
            cancelTradeDetail.setTransNo(enTrust.getTradeNo());
            cancelTradeDetail.setTradeVcoinId(enTrust.getTradeVcoinId());
            cancelTradeDetail.setTradeTotalAmount(enTrust.getTradeRemainAmount());
            cancelTradeDetail.setTradeNo(enTrust.getTradeNo());
            cancelTradeDetail.setTradeRate(new BigDecimal(0));
            cancelTradeDetail.setTradeFee(new BigDecimal(0));
            cancelTradeDetail.setType(2);
            mexcTradeDetailDelegate.insert(cancelTradeDetail);
            logger.info("mexc-admin撤单：增加的撤单明细 {}", JSON.toJSONString(cancelTradeDetail));


            /** 撤单：解冻用户资产**/
            try {
                MexcMarket market = marketDelegate.selectByPrimaryKey(enTrust.getMarketId());
                MexcMemberAsset mainVcoinAsset = memberAssetDelegate.queryMemberAsset(member.getId(), market.getVcoinId());//市场主币资产
                MexcMemberAsset currentVcoinAsset = memberAssetDelegate.queryMemberAsset(member.getId(), enTrust.getTradeVcoinId());//当前币交易资产

                if (enTrust.getTradeType().intValue() == CommonConstant.BUY.intValue()) {//买：解冻主币资产
                    BigDecimal unfrozenAmount = enTrust.getTradeRemainNumber().multiply(enTrust.getTradePrice());
                    memberAssetDelegate.unfrozenAmount(mainVcoinAsset.getId(), unfrozenAmount);
                } else if (enTrust.getTradeType().intValue() == CommonConstant.SELL.intValue()) {//卖：解冻副币资产
                    BigDecimal unfrozenAmount = enTrust.getTradeRemainNumber();
                    memberAssetDelegate.unfrozenAmount(currentVcoinAsset.getId(), unfrozenAmount);
                }
            }catch (Exception e) {
                logger.error("撤单：解冻资产异常",e);
                throw new BusinessException(ResultCode.COMMON_ERROR, "撤单：解冻资产异常。");
            }

            /**删除用户当前委托缓存*/
            String userCurrentEntrustKey = CommonConstant.USER_CURRENT_ENTRUST + enTrust.getMarketId() + enTrust.getTradeVcoinId() + member.getAccount();
            try {
                //从用户委托单列表中删除 reids
                RedisUtil.hdel(userCurrentEntrustKey, enTrust.getTradeNo());
                logger.info("MEXC-ADMIN : redis缓存：用户当前委托列表中删除，key: {} field:{}", userCurrentEntrustKey, enTrust.getTradeNo());
            }catch (Exception e) {
                logger.error("MEXC-ADMIN : 从删除当前用户委托缓存异常,key {}",userCurrentEntrustKey);
            }

        } catch (Exception e) {
            logger.error("mexc-admin撤单：更新委托明细删除委托缓存异常,entrust:{}", JSON.toJSONString(enTrust));
            throw new BusinessException(ResultCode.COMMON_ERROR, "mexc-admin更新委托明细删除委托缓存异常.");
        }
    }
}
