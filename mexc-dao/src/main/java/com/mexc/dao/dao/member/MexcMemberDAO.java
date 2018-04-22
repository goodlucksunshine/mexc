package com.mexc.dao.dao.member;

import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.model.member.MexcMember;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by huangxinguang on 2017/11/22 下午4:55.
 */
@Repository
public interface MexcMemberDAO extends IBaseDAO<MexcMember, String> {
    /**
     * 认证等级增加
     *
     * @param account
     *
     * @return
     */
    int authLevelIncrease(String account);


    List<MexcMember> queryMemberByAssetCondition(@Param("vcoinId") String vcoinId, @Param("value") BigDecimal value);
}
