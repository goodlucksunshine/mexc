package com.mexc.dao.dao.activity;

import com.laile.esf.common.util.Page;
import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.dto.activity.ActivityRuleDto;
import com.mexc.dao.dto.activity.ActivityRuleSearchDto;
import com.mexc.dao.model.activity.MexcActivityCandies;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MexcActivityCandiesDAO extends IBaseDAO<MexcActivityCandies, Integer> {

    List<ActivityRuleDto> queryRulePage(Page<ActivityRuleDto> page, @Param("search") ActivityRuleSearchDto searchDto);
}