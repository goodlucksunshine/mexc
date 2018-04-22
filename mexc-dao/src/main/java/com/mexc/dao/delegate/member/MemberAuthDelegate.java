package com.mexc.dao.delegate.member;

import com.mexc.common.plugin.SqlCondition;
import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.dao.member.MexcMemberAuthDAO;
import com.mexc.dao.delegate.AbstractDelegate;
import com.mexc.dao.model.member.MexcMemberAuth;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by huangxinguang on 2017/12/14 下午3:52.
 */
@Transactional
@Component
public class MemberAuthDelegate extends AbstractDelegate<MexcMemberAuth, String> {

    @Resource
    MexcMemberAuthDAO memberAuthDAO;

    @Autowired
    @Override
    public void setBaseMapper(IBaseDAO<MexcMemberAuth, String> baseMapper) {
        super.setBaseMapper(baseMapper);
    }


    public MexcMemberAuth queryIdenAuthByMemberId(String memberId) {
        SqlCondition sqlCondition = new SqlCondition();
        sqlCondition.put("where member_id=", memberId);
        sqlCondition.put("order by id desc limit 1");
        List<MexcMemberAuth> authList = memberAuthDAO.selectByCondition(sqlCondition);
        if (CollectionUtils.isNotEmpty(authList)) {
            return authList.get(0);
        } else {
            return null;
        }
    }
}
