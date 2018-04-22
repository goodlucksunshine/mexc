package com.mexc.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.laile.esf.common.exception.BusinessException;
import com.laile.esf.common.exception.ResultCode;
import com.laile.esf.common.util.StringUtil;
import com.mexc.api.service.ITradeGroupService;
import com.mexc.api.vo.BaseResponse;
import com.mexc.api.vo.trade.CollectRequest;
import com.mexc.api.vo.trade.TradeGroupInfo;
import com.mexc.api.vo.trade.TradeGroupRequest;
import com.mexc.api.vo.trade.TradeGroupResponse;
import com.mexc.common.constant.CommonConstant;
import com.mexc.common.util.LogUtil;
import com.mexc.common.util.jedis.RedisUtil;
import com.mexc.dao.delegate.market.MarketDelegate;
import com.mexc.dao.delegate.market.MarketVCoinCollectDelegate;
import com.mexc.dao.delegate.market.MarketVCoinDelegate;
import com.mexc.dao.delegate.member.MemberDelegate;
import com.mexc.dao.dto.vcoin.BtcValueDto;
import com.mexc.dao.model.market.MexcMarket;
import com.mexc.dao.model.market.MexcMarketVCoin;
import com.mexc.dao.model.market.MexcMarketVcoinCollect;
import com.mexc.dao.model.member.MexcMember;
import com.mexc.dao.vo.vcoin.trade.TradeGroupInfoVo;
import com.mexc.match.engine.util.QueueKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by huangxinguang on 2018/2/5 下午7:28.
 * 交易对
 */
@Service
public class TradeGroupService implements ITradeGroupService {
    private static Logger logger = LoggerFactory.getLogger(TradeGroupService.class);

    @Autowired
    private MemberDelegate memberDelegate;

    @Autowired
    private MarketDelegate marketDelegate;

    @Autowired
    private MarketVCoinDelegate marketVCoinDelegate;

    @Autowired
    private MarketVCoinCollectDelegate marketVCoinCollectDelegate;

    /**
     * 查询所有币种的交易对
     * @param tradeGroupRequest
     * @return
     */
    public TradeGroupResponse getTradeGroup(TradeGroupRequest tradeGroupRequest) {
        TradeGroupResponse response = new TradeGroupResponse();
        try {
            /** 从redis获取所有交易对信息 */
            List<TradeGroupInfo> tradeGroupList = new ArrayList<>();
            List<MexcMarket> marketList = marketDelegate.queryMarketList();
            if(!CollectionUtils.isEmpty(marketList)) {
                for (MexcMarket market : marketList) {
                    List<MexcMarketVCoin> vcoinList = marketDelegate.queryMarketVcoinList(market.getId());
                    if(!CollectionUtils.isEmpty(vcoinList)) {
                        for (MexcMarketVCoin vcoin : vcoinList) {
                            String tradeGroupKey = QueueKeyUtil.getKey(market.getId(), vcoin.getVcoinId());
                            String tradeGroupInfoJson = RedisUtil.get(CommonConstant.TRADE_GROUP + tradeGroupKey);
                            TradeGroupInfoVo tradeGroupInfo = JSONObject.parseObject(tradeGroupInfoJson, TradeGroupInfoVo.class);
                            TradeGroupInfo tradeGroup = buildTradeGroup(tradeGroupInfo);
                            tradeGroupList.add(tradeGroup);
                        }
                    }
                }
            }

            response.setResultCode(com.mexc.api.constant.ResultCode.MEXC_API_00000.getCode());
            response.setMessage(com.mexc.api.constant.ResultCode.MEXC_API_00000.getMsg());
        }catch (Exception e) {
            logger.error("获取交易对异常",e);
            response.setResultCode(com.mexc.api.constant.ResultCode.MEXC_API_99999.getCode());
            response.setMessage(com.mexc.api.constant.ResultCode.MEXC_API_99999.getMsg());
            return response;
        }

        return response;
    }

    /**
     * 添加或删除自选
     * @param collectRequest
     * @return
     */
    public BaseResponse addOrCancelCollect(CollectRequest collectRequest) {
        BaseResponse response = new BaseResponse();
        try {
            MexcMember member = memberDelegate.selectByPrimaryKey(collectRequest.getUserId());

            MexcMarketVCoin marketVCoin = marketVCoinDelegate.selectByPrimaryKey(collectRequest.getBargainId());
            if (marketVCoin == null) {
                logger.info(LogUtil.msg("TradeGroupService:addOrCancelCollect", "交易对[" + collectRequest.getBargainId() + "]不存在"));
                response.setResultCode(com.mexc.api.constant.ResultCode.MEXC_API_00009.getCode());
                response.setMessage(com.mexc.api.constant.ResultCode.MEXC_API_00009.getMsg());
                return response;
            }

            if (collectRequest.getIsCollected().intValue() == 1) {
                Boolean flag = marketVCoinCollectDelegate.checkCollect(marketVCoin.getMarketId(), marketVCoin.getVcoinId(), member.getAccount());
                if (flag) {
                    logger.info(LogUtil.msg("TradeGroupService:addOrCancelCollect", "交易对[" + collectRequest.getBargainId() + "]已经收藏过"));
                    response.setResultCode(com.mexc.api.constant.ResultCode.MEXC_API_00010.getCode());
                    response.setMessage(com.mexc.api.constant.ResultCode.MEXC_API_00010.getMsg());
                    return response;
                }
                /** 添加收藏 */
                MexcMarketVcoinCollect collect = new MexcMarketVcoinCollect();
                collect.setCreateByName(member.getAccount());
                collect.setCreateBy(member.getId());
                collect.setMarketId(marketVCoin.getMarketId());
                collect.setVcoinId(marketVCoin.getVcoinId());
                collect.setCreateTime(new Date());
                collect.setStatus(1);
                marketVCoinCollectDelegate.insert(collect);
                /** 放到redis */
                String userGroupKey = QueueKeyUtil.getUserGroupKey(marketVCoin.getMarketId(), marketVCoin.getVcoinId(), member.getAccount());
                RedisUtil.set(userGroupKey, "1");

            } else if(collectRequest.getIsCollected().intValue() == 0){
                /** 删除收藏 */
                MexcMarketVcoinCollect collect = new MexcMarketVcoinCollect();
                collect.setMarketId(marketVCoin.getMarketId());
                collect.setVcoinId(marketVCoin.getVcoinId());
                collect.setCreateBy(member.getId());
                marketVCoinCollectDelegate.uncollect(collect);
                /** 修改redis标志 */
                String userGroupKey = QueueKeyUtil.getUserGroupKey(marketVCoin.getMarketId(), marketVCoin.getVcoinId(), member.getAccount());
                RedisUtil.set(userGroupKey, "0");

            }

            response.setResultCode(com.mexc.api.constant.ResultCode.MEXC_API_00000.getCode());
            response.setMessage(com.mexc.api.constant.ResultCode.MEXC_API_00000.getMsg());

        }catch (Exception e) {
            logger.info(LogUtil.msg("TradeGroupService:addOrCancelCollect", "操作异常"));
            response.setResultCode(com.mexc.api.constant.ResultCode.MEXC_API_99999.getCode());
            response.setMessage(com.mexc.api.constant.ResultCode.MEXC_API_99999.getMsg());
            return response;
        }

        return response;
    }


    private TradeGroupInfo buildTradeGroup(TradeGroupInfoVo tradeGroupInfoVo) {
        TradeGroupInfo tradeGroup = new TradeGroupInfo();
        if(tradeGroupInfoVo == null) {
            return tradeGroup;
        }

        /** 从redis获取币种美元估值 */
        String key = tradeGroupInfoVo.getVcoinNameEn()+"-"+tradeGroupInfoVo.getVcoinId();
        String btcValueJson = RedisUtil.get(key);
        BtcValueDto btcValue = JSON.parseObject(btcValueJson, BtcValueDto.class);
        if(btcValue == null) {
            tradeGroup.setCurrencyPrice("0");
        }else {
            BigDecimal usdPrice = new BigDecimal(tradeGroupInfoVo.getLatestTradePrice())
                                                    .multiply(new BigDecimal(btcValue.getPriceBtc()))
                                                    .multiply(new BigDecimal(btcValue.getPriceUsb()));
            tradeGroup.setCurrencyPrice(usdPrice.toPlainString());
        }

        /**查询交易对ID*/
        MexcMarketVCoin marketVCoin = marketVCoinDelegate.queryMarketVCoin(tradeGroupInfoVo.getMarketId(),tradeGroupInfoVo.getVcoinId());
        tradeGroup.setBargainId(marketVCoin.getId());

        /** 判断涨跌 */
        if(new BigDecimal(tradeGroupInfoVo.getUpOrDownValue()).compareTo(new BigDecimal("0")) < 0) {
            tradeGroup.setUpOrDownTag(-1);
        }else if(new BigDecimal(tradeGroupInfoVo.getUpOrDownValue()).compareTo(new BigDecimal("0")) > 0) {
            tradeGroup.setUpOrDownTag(1);
        }else if(new BigDecimal(tradeGroupInfoVo.getUpOrDownValue()).compareTo(new BigDecimal("0")) == 0){
            tradeGroup.setUpOrDownTag(0);
        }

        /** 填充交易对行情信息 */
        tradeGroup.setMainBargain(tradeGroupInfoVo.getMarketName());
        tradeGroup.setExchangeBargain(tradeGroupInfoVo.getVcoinNameEn());
        tradeGroup.setExchangePrice(tradeGroupInfoVo.getLatestTradePrice());
        tradeGroup.setUpOrDownRange(tradeGroupInfoVo.getUpOrDownRateValue());
        tradeGroup.setTradingVolume(tradeGroupInfoVo.getSumTradePrice());
        tradeGroup.setIsCollected(tradeGroupInfoVo.getCollect());
        tradeGroup.setTradingVolume(tradeGroupInfoVo.getSumTradePrice());

        return tradeGroup;
    }
}
