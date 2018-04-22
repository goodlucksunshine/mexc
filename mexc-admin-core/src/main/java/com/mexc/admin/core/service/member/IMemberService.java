package com.mexc.admin.core.service.member;

import com.laile.esf.common.util.Page;
import com.mexc.admin.core.service.IBaseService;
import com.mexc.dao.dto.asset.AssetDto;
import com.mexc.dao.dto.asset.AssetQueryDto;
import com.mexc.dao.dto.asset.RechargeRequest;
import com.mexc.dao.dto.member.MemberQueryDto;
import com.mexc.dao.model.member.MexcMember;
import com.mexc.dao.model.member.MexcMemberAuth;
import com.mexc.dao.vo.wallet.MemberAssetCheckVo;

import java.util.List;

/**
 * Created by huangxinguang on 2017/11/24 下午2:37.
 */
public interface IMemberService extends IBaseService<MexcMember, String> {

    Page<MexcMember> queryMemberListPage(MemberQueryDto queryDto);
    List<MemberAssetCheckVo> queryExceptionUser();


    MexcMemberAuth queryAuth(String authId);

    Page<MexcMemberAuth> queryAuthPage(Integer currentPage, Integer pageSize,Integer status, String searchKey);

    int auditAUth(String authId,Integer status);

    Page<AssetDto> queryAssetPage(AssetQueryDto queryDto);

    boolean assetRecharge(RechargeRequest rechargeRequest);

}
