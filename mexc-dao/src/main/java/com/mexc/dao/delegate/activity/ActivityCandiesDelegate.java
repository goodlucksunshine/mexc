package com.mexc.dao.delegate.activity;

import com.laile.esf.common.util.Page;
import com.mexc.common.plugin.SqlCondition;
import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.dao.activity.MexcActivityCandiesDAO;
import com.mexc.dao.dao.activity.MexcActivityDAO;
import com.mexc.dao.delegate.AbstractDelegate;
import com.mexc.dao.dto.activity.ActivityRuleDto;
import com.mexc.dao.dto.activity.ActivityRuleSearchDto;
import com.mexc.dao.model.activity.MexcActivityCandies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2018/2/7
 * Time: 上午11:50
 */
@Component
public class ActivityCandiesDelegate extends AbstractDelegate<MexcActivityCandies, Integer> {
    @Autowired
    MexcActivityDAO mexcActivityDAO;

    @Autowired
    MexcActivityCandiesDAO activityCandiesDAO;

    @Autowired
    @Override
    public void setBaseMapper(IBaseDAO<MexcActivityCandies, Integer> baseMapper) {
        super.setBaseMapper(baseMapper);
    }


    public Page<ActivityRuleDto> queryByActivityId(Page<ActivityRuleDto> page, ActivityRuleSearchDto searchDto) {
        activityCandiesDAO.queryRulePage(page, searchDto);
        return page;
    }
}
