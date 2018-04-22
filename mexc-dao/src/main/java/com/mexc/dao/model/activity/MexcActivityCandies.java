package com.mexc.dao.model.activity;

import java.math.BigDecimal;
import java.util.Date;

public class MexcActivityCandies {
    private Integer id;

    /**
     * 活动ID
     */
    private String activityId;

    /**
     * 规则名称
     */
    private String ruleName;

    /**
     * 条件资产ID
     */
    private String sourceId;

    /**
     * 赠送资产ID
     */
    private String percentId;

    /**
     * 赠送百分比
     */
    private BigDecimal percent;

    /**
     * 赠送开始时间
     */
    private Date ruleStartTime;

    /**
     * 赠送结束时间
     */
    private Date ruleEndTime;

    /**
     * 赠送类型 1:按比例 2:按固定个数
     */
    private Integer type;

    /**
     * 固定个数赠送
     */
    private Integer fix;

    /**
     * 赠送条件 一般为达到多少个按照类型(固定个数|比例)赠送(固定个数|百分比)
     */
    private Integer ruleCondition;

    /**
     * 执行状态 0:初始化 1:执行中 2:执行完成 -1:执行异常
     */
    private Integer execStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId == null ? null : activityId.trim();
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName == null ? null : ruleName.trim();
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId == null ? null : sourceId.trim();
    }

    public String getPercentId() {
        return percentId;
    }

    public void setPercentId(String percentId) {
        this.percentId = percentId == null ? null : percentId.trim();
    }

    public BigDecimal getPercent() {
        return percent;
    }

    public void setPercent(BigDecimal percent) {
        this.percent = percent;
    }

    public Date getRuleStartTime() {
        return ruleStartTime;
    }

    public void setRuleStartTime(Date ruleStartTime) {
        this.ruleStartTime = ruleStartTime;
    }

    public Date getRuleEndTime() {
        return ruleEndTime;
    }

    public void setRuleEndTime(Date ruleEndTime) {
        this.ruleEndTime = ruleEndTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getFix() {
        return fix;
    }

    public void setFix(Integer fix) {
        this.fix = fix;
    }

    public Integer getRuleCondition() {
        return ruleCondition;
    }

    public void setRuleCondition(Integer ruleCondition) {
        this.ruleCondition = ruleCondition;
    }

    public Integer getExecStatus() {
        return execStatus;
    }

    public void setExecStatus(Integer execStatus) {
        this.execStatus = execStatus;
    }
}