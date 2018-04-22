package com.mexc.dao.delegate.member;

import com.alibaba.fastjson.JSON;
import com.laile.esf.common.exception.BusinessException;
import com.laile.esf.common.exception.ResultCode;
import com.laile.esf.common.util.Page;
import com.mexc.common.plugin.SqlCondition;
import com.mexc.common.util.UUIDUtil;
import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.dao.member.MexcAssetTransDAO;
import com.mexc.dao.dao.member.MexcMemberAssetDAO;
import com.mexc.dao.dao.vcoin.MexcAddressLibDAO;
import com.mexc.dao.dao.wallet.MexcAssetCashDAO;
import com.mexc.dao.delegate.AbstractDelegate;
import com.mexc.dao.delegate.wallet.MexcAssetRechargeDelegate;
import com.mexc.dao.dto.asset.AssetBalanceCheckDto;
import com.mexc.dao.dto.asset.AssetDto;
import com.mexc.dao.dto.asset.AssetQueryDto;
import com.mexc.dao.dto.asset.RechargeRequest;
import com.mexc.dao.model.member.MexcAssetTrans;
import com.mexc.dao.model.member.MexcMemberAsset;
import com.mexc.dao.model.wallet.MexcAssetRecharge;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 17/12/14
 * Time: 下午5:43
 */
@Component
@Transactional
public class MemberAssetDelegate extends AbstractDelegate<MexcMemberAsset, String> {
    @Resource
    MexcMemberAssetDAO mexcMemberAssetDAO;
    @Resource
    MexcAssetTransDAO mexcAssetTransDAO;
    @Resource
    MexcAssetCashDAO mexcAssetCashDAO;
    @Resource
    MexcAddressLibDAO mexcAddressLibDAO;
    @Resource
    MexcAssetRechargeDelegate assetRechargeDelegate;

    @Autowired
    @Override
    public void setBaseMapper(IBaseDAO<MexcMemberAsset, String> baseMapper) {
        super.setBaseMapper(baseMapper);
    }


    public List<AssetDto> queryMermberAsset(AssetQueryDto queryDto) {
        return mexcMemberAssetDAO.queryMemberAsset(queryDto);
    }


    public Page<AssetDto> queryMermberAssetPage(Integer currentPage, Integer showCount, AssetQueryDto queryDto) {
        Page<AssetDto> page = new Page<>(currentPage, showCount);
        mexcMemberAssetDAO.queryMemberAssetPage(page, queryDto);
        return page;
    }

    public MexcMemberAsset queryMemberAsset(String memberId, String vcoinId) {
        SqlCondition condition = new SqlCondition();
        condition.put(" where member_id=", memberId);
        condition.put(" and vcoin_id=", vcoinId);
        List<MexcMemberAsset> list = mexcMemberAssetDAO.selectByCondition(condition);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    public void assetIncome(MexcAssetTrans trans) {
        mexcAssetTransDAO.insertSelective(trans);
        assetIncomeing(trans.getAssetId(), trans.getTransAmount());
    }

    public void recharge(MexcAssetTrans trans, MexcAssetRecharge recharge) {
        assetRechargeDelegate.insertSelective(recharge);
        assetIncome(trans);
    }


    public void candiesSend(String assetId, BigDecimal value, String ruleId) {
        MexcAssetTrans trans = new MexcAssetTrans();
        trans.setTransAmount(value);
        trans.setTradeType("6");
        trans.setTransType(1);
        trans.setTransTime(new Date());
        trans.setAssetId(assetId);
        trans.setId(UUIDUtil.get32UUID());
        trans.setTransNo(ruleId);
        assetIncome(trans);
    }


    public MexcMemberAsset queryAsset(String address, String vcoinid) {
        SqlCondition condition = new SqlCondition();
        condition.put(" where wallet_address=", address);
        condition.put(" and vcoin_id=", vcoinid);
        List<MexcMemberAsset> assets = mexcMemberAssetDAO.selectByCondition(condition);
        if (CollectionUtils.isNotEmpty(assets)) {
            return assets.get(0);
        } else {
            return null;
        }
    }


    public MexcMemberAsset checkAddress(String address, String token) {
        SqlCondition condition = new SqlCondition();
        condition.put(" where wallet_address=", address);
        condition.put(" and token=", token);
        List<MexcMemberAsset> list = mexcMemberAssetDAO.selectByCondition(condition);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        } else {
            return null;
        }
    }


    public List<AssetBalanceCheckDto> balanceCheckList(Integer memberSeq, Integer limit) {
        return mexcMemberAssetDAO.queryAssetFromMemberSeqLimit(memberSeq, limit);
    }

    public void assetOutcomeing(String assetId, BigDecimal incomeValue) {
        int result = mexcMemberAssetDAO.assetOutcome(assetId, incomeValue);
        if (result < 1) {
            throw new BusinessException(ResultCode.COMMON_ERROR, "减少用户资产失败");
        }
    }

    public void assetIncomeing(String assetId, BigDecimal incomeValue) {
        int result = mexcMemberAssetDAO.assetIncome(assetId, incomeValue);
        if (result < 1) {
            throw new BusinessException(ResultCode.COMMON_ERROR, "增加用户资产失败");
        }
    }

    public void frozenAmount(String assetId, BigDecimal frozenAmount) {
        int result = mexcMemberAssetDAO.frozenAmount(assetId, frozenAmount);
        if (result < 1) {
            throw new BusinessException(ResultCode.COMMON_ERROR, "冻结用户资产失败");
        }
    }

    public void unfrozenAmount(String assetId, BigDecimal frozenAmount) {
        int result = mexcMemberAssetDAO.unfrozenAmount(assetId, frozenAmount);
        if (result < 1) {
            throw new BusinessException(ResultCode.COMMON_ERROR, "解冻用户资产失败");
        }
    }


    public MexcMemberAsset selectMemberAsset(String memberId, String assetId) {
        SqlCondition sqlCondition = new SqlCondition();
        sqlCondition.put(" where member_id=", memberId);
        sqlCondition.put(" and asset_id=", assetId);
        List<MexcMemberAsset> assets = selectByCondition(sqlCondition);
        if (CollectionUtils.isNotEmpty(assets)) {
            return assets.get(0);
        } else {
            return null;
        }
    }


    public MexcMemberAsset selectMemberAssetByVcoin(String vcoin, String memberId) {
        SqlCondition sqlCondition = new SqlCondition();
        sqlCondition.put(" where vcoin_id=", vcoin);
        sqlCondition.put(" and member_id=", memberId);
        List<MexcMemberAsset> assets = selectByCondition(sqlCondition);
        if (CollectionUtils.isNotEmpty(assets)) {
            return assets.get(0);
        } else {
            return null;
        }
    }


    public int insertMemberAsset(List<MexcMemberAsset> assets, String memberId) {
        List<String> addressList = new ArrayList<>();
        for (MexcMemberAsset asset : assets) {
            addressList.add(asset.getWalletAddress());
        }
        mexcMemberAssetDAO.insertBatch(assets);
        logger.info("更新资产库状态：{}", JSON.toJSONString(addressList));
        return mexcAddressLibDAO.updateAssetStatus(addressList, memberId);
    }


    public MexcMemberAsset selectMemberAssetByToken(String token, String memberId) {
        SqlCondition sqlCondition = new SqlCondition();
        sqlCondition.put(" where token=", token);
        sqlCondition.put(" and member_id =", memberId);
        List<MexcMemberAsset> assets = selectByCondition(sqlCondition);
        if (CollectionUtils.isNotEmpty(assets)) {
            return assets.get(0);
        } else {
            return null;
        }
    }

    public void assetRecharge(RechargeRequest rechargeRequest) {
        Date now = new Date();
        MexcAssetTrans trans = new MexcAssetTrans();
        trans.setId(UUIDUtil.get32UUID());
        trans.setAssetId(rechargeRequest.getAssetId());
        trans.setTransTime(now);
        trans.setTransNo("");
        trans.setTransReceipt("");
        trans.setTransType(1);
        trans.setTradeType("5");
        trans.setTransAmount(new BigDecimal(rechargeRequest.getRechargeValue()));
        assetIncome(trans);
    }





}
