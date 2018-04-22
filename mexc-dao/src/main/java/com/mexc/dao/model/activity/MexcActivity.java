package com.mexc.dao.model.activity;

import java.util.Date;

public class MexcActivity {
    private String id;

    private String activityTitle;

    private Date activityStartTime;

    private Date activityEndTime;

    /**
     * 1:糖果赠送
     */
    private Integer activityType;

    private String activityMemo;

    private String createBy;

    private Date createDate;

    /**
     * 1:开启 0:新建  -1:结束
     */
    private Integer activityStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getActivityTitle() {
        return activityTitle;
    }

    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle == null ? null : activityTitle.trim();
    }

    public Date getActivityStartTime() {
        return activityStartTime;
    }

    public void setActivityStartTime(Date activityStartTime) {
        this.activityStartTime = activityStartTime;
    }

    public Date getActivityEndTime() {
        return activityEndTime;
    }

    public void setActivityEndTime(Date activityEndTime) {
        this.activityEndTime = activityEndTime;
    }

    public Integer getActivityType() {
        return activityType;
    }

    public void setActivityType(Integer activityType) {
        this.activityType = activityType;
    }

    public String getActivityMemo() {
        return activityMemo;
    }

    public void setActivityMemo(String activityMemo) {
        this.activityMemo = activityMemo == null ? null : activityMemo.trim();
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(Integer activityStatus) {
        this.activityStatus = activityStatus;
    }
}