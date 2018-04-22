package com.mexc.web.core.service.member.impl;

import com.mexc.dao.delegate.member.MemberDelegate;
import com.mexc.dao.model.member.MexcMember;
import com.mexc.web.core.service.member.IMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2018/1/12
 * Time: 下午9:21
 */
@Service
public class MemberServiceImpl implements IMemberService {

    private static Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

    @Resource
    MemberDelegate memberDelegate;

    @Override
    public MexcMember queryMember(String memberId) {
        return memberDelegate.selectByPrimaryKey(memberId);
    }

    @Override
    public MexcMember queryMemberByAccount(String account) {
        return memberDelegate.queryMermberByAccount(account);
    }


}
