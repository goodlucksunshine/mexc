package com.mexc.admin.core.service.activity;

import com.laile.esf.common.util.Page;
import com.mexc.dao.dto.activity.ActivityRuleDto;
import com.mexc.dao.dto.activity.ActivityRuleSearchDto;
import com.mexc.dao.dto.activity.ActivitySearchDto;
import com.mexc.dao.model.activity.MexcActivity;
import com.mexc.dao.model.activity.MexcActivityCandies;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2018/2/7
 * Time: 下午4:53
 */
public interface IActivityService {


    Page<MexcActivity> queryActivityPage(ActivitySearchDto searchDto);

    MexcActivity queryActivityDetail(String activityId);


    void saveOrUpdate(MexcActivity activity);

    Page<ActivityRuleDto> queryRulePage(ActivityRuleSearchDto searchDto);


    void saveOrUpdateRule(MexcActivityCandies candies);

    MexcActivityCandies queryRuleDetail(Integer ruleId);

    boolean execActivityRule(Integer ruleId);

}
