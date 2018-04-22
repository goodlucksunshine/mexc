package com.mexc.dao.delegate.activity;

import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.dao.activity.MexcActivityDAO;
import com.mexc.dao.delegate.AbstractDelegate;
import com.mexc.dao.model.activity.MexcActivity;
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
public class ActivityDelegate extends AbstractDelegate<MexcActivity, String> {

    @Autowired
    MexcActivityDAO mexcActivityDAO;

    @Autowired
    @Override
    public void setBaseMapper(IBaseDAO<MexcActivity, String> baseMapper) {
        super.setBaseMapper(baseMapper);
    }
}
