package com.mexc.dao.delegate.member;

import com.mexc.dao.dao.member.MexcMemberAssetDAO;
import com.mexc.dao.delegate.AbstractDelegate;
import com.mexc.dao.vo.wallet.MemberAssetCheckVo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class MemberAssetCheckDelegate extends AbstractDelegate<MemberAssetCheckVo,String>{

    @Resource
    private MexcMemberAssetDAO mexcMemberAssetDAO;

    /**
     * 返回异常用户列表
     * @return
     */
    public List<MemberAssetCheckVo> memberAssetCheck(){
        List<MemberAssetCheckVo> memberAssetCheckVos = mexcMemberAssetDAO.queryMemberAssetCheck();
        List<MemberAssetCheckVo> memberException = new ArrayList<>();
        if (memberAssetCheckVos.size() == 0 || memberAssetCheckVos == null){
            return memberException;
        }
        for (MemberAssetCheckVo memberAssetCheckVo : memberAssetCheckVos) {
            if (memberAssetCheckVo == null){
                continue;
            }
            int rechargeAmount = memberAssetCheckVo.getRechargeAmount();
            int cashAmount = memberAssetCheckVo.getCashAmount();
            int assetBalanceAmount = memberAssetCheckVo.getAssetBalanceAmount();
            if (rechargeAmount < (cashAmount + assetBalanceAmount)){
                memberException.add(memberAssetCheckVo);
            }
        }
        return memberException;
    }
}
