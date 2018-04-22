package com.mexc.admin.core.service.member.impl;

import com.alibaba.fastjson.JSON;
import com.laile.esf.common.util.Page;
import com.mexc.admin.core.service.impl.AbstractService;
import com.mexc.admin.core.service.member.IMemberService;
import com.mexc.common.util.ThressDescUtil;
import com.mexc.dao.delegate.AbstractDelegate;
import com.mexc.dao.delegate.member.MemberAssetCheckDelegate;
import com.mexc.dao.delegate.member.MemberAssetDelegate;
import com.mexc.dao.delegate.member.MemberDelegate;
import com.mexc.dao.dto.asset.AssetDto;
import com.mexc.dao.dto.asset.AssetQueryDto;
import com.mexc.dao.dto.asset.RechargeRequest;
import com.mexc.dao.dto.member.MemberQueryDto;
import com.mexc.dao.model.member.MexcMember;
import com.mexc.dao.model.member.MexcMemberAuth;
import com.mexc.dao.vo.wallet.MemberAssetCheckVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangxinguang on 2017/11/24 下午2:38.
 */
@Service
public class MemberService extends AbstractService<MexcMember, String> implements IMemberService {

    @Autowired
    private MemberDelegate memberDelegate;

    @Autowired
    private MemberAssetDelegate memberAssetDelegate;

    @Autowired
    private MemberAssetCheckDelegate memberAssetCheckDelegate;

    @Autowired
    @Override
    public void setBaseDelegate(AbstractDelegate<MexcMember, String> baseDelegate) {
        super.setBaseDelegate(baseDelegate);
    }

    @Override
    public List<MemberAssetCheckVo> queryExceptionUser() {
         return memberAssetCheckDelegate.memberAssetCheck();
    }

    @Override
    public Page<MexcMember> queryMemberListPage(MemberQueryDto queryDto) {
        return memberDelegate.queryMemberListPage(queryDto);
    }

    @Override
    public MexcMemberAuth queryAuth(String authId) {
        return memberDelegate.queryMemberAuth(authId);
    }

    @Override
    public Page<MexcMemberAuth> queryAuthPage(Integer currentPage, Integer pageSize,Integer status, String searchKey) {
        return memberDelegate.queryMemberAuthPage(currentPage, pageSize,status, searchKey);
    }

    @Override
    public int auditAUth(String authId, Integer status) {
        return memberDelegate.auditAuth(authId, status);
    }

    @Override
    public Page<AssetDto> queryAssetPage(AssetQueryDto queryDto) {
        Page<AssetDto> page = memberAssetDelegate.queryMermberAssetPage(queryDto.getCurrentPage(), queryDto.getShowCount(), queryDto);
        for (AssetDto assetDto : page.getResultList()) {
            assetDto.setWalletAddress(ThressDescUtil.decodeAssetAddress(assetDto.getWalletAddress()));
        }
        return page;
    }

    @Override
    public boolean assetRecharge(RechargeRequest rechargeRequest) {
        try {
            memberAssetDelegate.assetRecharge(rechargeRequest);
            return true;
        } catch (Exception e) {
            logger.error("用户资产充值失败assetId:" + rechargeRequest.getAssetId(), e);
            return false;
        }

    }
}
