package com.mexc.dao.delegate.wallet;

import com.laile.esf.common.exception.BusinessException;
import com.laile.esf.common.exception.ResultCode;
import com.laile.esf.common.util.Page;
import com.laile.esf.common.util.StringUtil;
import com.mexc.common.plugin.SqlCondition;
import com.mexc.common.util.UUIDUtil;
import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.dao.member.MexcAssetTransDAO;
import com.mexc.dao.dao.member.MexcMemberAssetDAO;
import com.mexc.dao.dao.wallet.MexcAssetCashDAO;
import com.mexc.dao.delegate.AbstractDelegate;
import com.mexc.dao.delegate.plat.PlatAssetDelegate;
import com.mexc.dao.delegate.plat.PlatAssetDetailDelegate;
import com.mexc.dao.dto.asset.AssetCashDto;
import com.mexc.dao.model.member.MexcAssetTrans;
import com.mexc.dao.model.member.MexcMemberAsset;
import com.mexc.dao.model.plat.MexcPlatAsset;
import com.mexc.dao.model.plat.MexcPlatAssetDetail;
import com.mexc.dao.model.wallet.MexcAssetCash;
import com.mexc.dao.model.wallet.MexcAssetCashVcoin;
import com.mexc.dao.vo.asset.AssetCashHistoryVo;
import com.mexc.dao.vo.asset.AssetCashVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2017/12/30
 * Time: 下午10:47
 */
@Transactional
@Component
public class MexcAssetCashDelegate extends AbstractDelegate<MexcAssetCash, String> {

    @Autowired
    MexcAssetCashDAO mexcAssetCashDAO;
    @Autowired
    MexcAssetTransDAO assetTransDAO;
    @Autowired
    MexcMemberAssetDAO memberAssetDAO;
    @Autowired
    MexcAssetCashDAO assetCashDAO;
    @Autowired
    PlatAssetDelegate platAssetDelegate;
    @Autowired
    PlatAssetDetailDelegate platAssetDetailDelegate;


    @Autowired
    @Override
    public void setBaseMapper(IBaseDAO<MexcAssetCash, String> baseMapper) {
        super.setBaseMapper(baseMapper);
    }


    public boolean localUserAsset(MexcAssetCash cash, List<MexcAssetTrans> transList) {
        //冻结资金
        BigDecimal txAmount = new BigDecimal(cash.getTxAmount());
        int result = memberAssetDAO.frozenAmount(cash.getAssetId(), txAmount);
        if (result < 1) {
            throw new BusinessException(ResultCode.COMMON_ERROR, "用户余额不足");
        }
        //插入提现单
        mexcAssetCashDAO.insertSelective(cash);
        //插入资金明细
//        assetTransDAO.insertBatch(transList);
        return true;
    }

    public void cashSuccess(MexcAssetCash cash) {
        Date now = new Date();
        BigDecimal txAmount = new BigDecimal(cash.getTxAmount());
        BigDecimal fee = cash.getCashFee();
        int result = memberAssetDAO.cashSuccess(cash.getAssetId(), txAmount);
        int result1 = assetCashDAO.cashSuccess(cash.getId());
        List<MexcAssetTrans> transList = new ArrayList<>();
        MexcAssetTrans cashTrans = new MexcAssetTrans();
        cashTrans.setId(UUIDUtil.get32UUID());
        cashTrans.setAssetId(cash.getAssetId());
        //-1:提现 1:冲值 2:委托 3:手续费
        cashTrans.setTradeType("-1");
        cashTrans.setTransAmount(new BigDecimal(cash.getTxAmount()));
        cashTrans.setTransTime(now);
        cashTrans.setTransNo(cash.getId());
        transList.add(cashTrans);
        MexcAssetTrans feeTrans = new MexcAssetTrans();
        feeTrans.setId(UUIDUtil.get32UUID());
        feeTrans.setAssetId(cash.getAssetId());
        //-1:提现 1:冲值 2:委托 3:手续费 7:提现手续费
        feeTrans.setTradeType("7");
        feeTrans.setTransAmount(cash.getCashFee());
        feeTrans.setTransTime(now);
        feeTrans.setTransNo(cash.getId());
        transList.add(feeTrans);
        assetTransDAO.insertBatch(transList);
        //手续如入平台资产及平台资产明细表
        MexcMemberAsset mexcMemberAsset = memberAssetDAO.selectByPrimaryKey(cash.getAssetId());
        MexcPlatAsset platAsset = platAssetDelegate.queryPlatAsset(mexcMemberAsset.getVcoinId());
        MexcPlatAssetDetail platAssetDetail = new MexcPlatAssetDetail();
        platAssetDetail.setId(UUIDUtil.get32UUID());
        platAssetDetail.setPlatId(platAsset.getId());
        platAssetDetail.setAmount(fee);
        platAssetDetail.setOptTime(new Date());
        platAssetDetail.setOptType(1);
        platAssetDetail.setTradeFee(fee);
        platAssetDetail.setBalance(BigDecimal.ZERO);
        platAssetDetailDelegate.insertSelective(platAssetDetail);
        int result2 = platAssetDelegate.assetIncome(platAsset.getId(), cash.getCashFee());
        if (result < 1 || result1 < 1 || result2 < 1) {
            logger.error("提现入账失败:memberasset:" + result + ",cashresult:" + result1 + ",platasset:" + result2);
            throw new BusinessException(ResultCode.COMMON_ERROR, "提现入账失败");
        }
    }

    /**
     * 提现失败，需要人工处理
     *
     * @param cash
     */
    public boolean cashAutoFailed(MexcAssetCash cash) {
        int result = assetCashDAO.cashAutoFailed(cash.getId());
        if (result < 1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean cashConfirm(String cashId) {
        int result = assetCashDAO.cashConfirm(cashId);
        if (result < 1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean cashFailture(MexcAssetCash cash) {
        BigDecimal txAmount = new BigDecimal(cash.getTxAmount());
        BigDecimal fee = cash.getCashFee();
        BigDecimal total = txAmount.add(fee);
        int result = memberAssetDAO.cashFail(cash.getAssetId(), total);
        int result1 = assetCashDAO.cashFail(cash.getId());
        if (result < 1 || result1 < 1) {
            return false;
        } else {
            return true;
        }
    }

    public List<MexcAssetCash> queryAssetCash(String memberId) {
        return assetCashDAO.queryAssetCash(memberId);
    }

    public List<MexcAssetCashVcoin> queryAssetCashVcoin(String memberId) {
        return assetCashDAO.queryAssetCashVcoin(memberId);
    }

    public List<AssetCashHistoryVo> checkLevelLimit(String memberId) {
        return mexcAssetCashDAO.checkLevelCashLimit(memberId);
    }

    public Page<AssetCashVo> queryAssetCashPage(AssetCashDto assetCashDto) {
        Page<AssetCashVo> page = new Page<>(assetCashDto.getCurrentPage(), assetCashDto.getShowCount());
        List<AssetCashVo> list = mexcAssetCashDAO.queryAssetCashPage(page, assetCashDto);
        page.setResultList(list);
        return page;
    }

    public Page<AssetCashVo> queryAssetCashPageByCondition(AssetCashDto assetCashDto) {
        Page<AssetCashVo> page = new Page<>(assetCashDto.getCurrentPage(), assetCashDto.getShowCount());
        List<AssetCashVo> list = mexcAssetCashDAO.queryAssetCashPageByCondition(page, assetCashDto);
        if (!StringUtil.isEmpty(assetCashDto.getAccount())) {
            if (null != list && list.size() > 0) {
                for (AssetCashVo a : list) {
                    a.setAccount(assetCashDto.getAccount());
                }
            }
        }
        page.setResultList(list);
        return page;
    }

    public int cashResend(String cashId) {
        return mexcAssetCashDAO.resendCash(cashId);
    }

    public int updateCashHash(String cashId, String txid) {
        return mexcAssetCashDAO.updateCashHash(cashId, txid);
    }


    public List<MexcAssetCash> queryProcessingCashList(int limit) {
        SqlCondition sqlCondition = new SqlCondition();
        sqlCondition.put(" where tx_hash!='' and cash_Status=2 limit " + limit);
        return mexcAssetCashDAO.selectByCondition(sqlCondition);
    }
}
