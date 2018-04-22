package com.mexc.web.core.service.member;

import com.mexc.dao.model.member.MexcMember;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2018/1/12
 * Time: 下午9:21
 */
public interface IMemberService {

    MexcMember queryMember(String memberId);

    MexcMember queryMemberByAccount(String account);
}
