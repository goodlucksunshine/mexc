package com.mexc.dao.dto.activity;

import com.mexc.dao.model.activity.MexcActivityCandies;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2018/2/7
 * Time: 下午8:16
 */
public class ActivityRuleDto extends MexcActivityCandies {


    private String sourceName;

    private String presentName;

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getPresentName() {
        return presentName;
    }

    public void setPresentName(String presentName) {
        this.presentName = presentName;
    }
}
