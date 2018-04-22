package com.mexc.dao.model.quartz;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JobConfig implements Serializable {
    public static final String STATUS_FAIL = "FAIL";// 上次失败

    public static final String STATUS_ERROR = "ERROR";// 调度任务错误

    public static final String STATUS_BLOCKED = "BLOCKED";// 线程堵塞

    public static final String STATUS_NONE = "NONE";// 未开启

    public static final String STATUS_NORMAL = "NORMAL";// 正常状态

    public static final String STATUS_PAUSED = "PAUSED";// 暂停

    public static final String STATUS_RUNNING = "RUNNING";// 运行中

    public static final String STATUS_COMPLETE = "COMPLETE";// 上次执行完成

    public static final Integer ISCONCURRENT_TRUE = 1;

    public static final Integer ISCONCURRENT_FALSE = 0;

    public static final Map<String, String> enmuMap = new HashMap<String, String>();
    static {
        enmuMap.put(STATUS_FAIL, "上次失败");
        enmuMap.put(STATUS_ERROR, "调度任务错误");
        enmuMap.put(STATUS_BLOCKED, "线程堵塞");
        enmuMap.put(STATUS_NONE, "未开启");
        enmuMap.put(STATUS_NORMAL, "正常状态");
        enmuMap.put(STATUS_PAUSED, "暂停中");
        enmuMap.put(STATUS_RUNNING, "运行中");
        enmuMap.put(STATUS_COMPLETE, "上次执行成功");
    }

    public static final Map<Integer, Long> executeDate = new HashMap<Integer, Long>();
    static {
        executeDate.put(1, 0L);
        executeDate.put(2, 5000L);// 5秒
        executeDate.put(3, 15000L);// 15秒
        executeDate.put(4, 60000L);// 1分钟
        executeDate.put(5, 600000L);// 10分钟
        executeDate.put(6, 3000000L);// 半小时
        executeDate.put(7, 6000000L);// 1小时
        executeDate.put(8, 12000000L);// 2小时
        /*
         * executeDate.put(2,5000L);//5秒 executeDate.put(3,15000L);//15秒
         * executeDate.put(4,60000L);//1分钟 executeDate.put(5,600000L);//10分钟
         * executeDate.put(6,1800000L);//半小时 executeDate.put(7,7200000L);//2小时
         * executeDate.put(8,18000000L);//5小时
         */
    }

    private Integer id;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务分组
     */
    private String jobGroup;

    /**
     * 当前任务状态
     */
    private String status;

    /**
     * 时间表达式
     */
    private String cronExpression;

    /**
     * 任务描述
     */
    private String description;

    /**
     * 执行类的全限定名
     */
    private String className;

    /**
     * 执行的方法
     */
    private String methodName;

    /**
     * 参数
     */
    private String arguments;

    /**
     * 地址
     */
    private String address;

    /**
     * spring容器的ID 若有则优先使用注入
     */
    private String springId;

    /**
     * 是否加入到了调度任务中
     */
    private Integer isActive;

    /**
     * 是否并发执行
     */
    private Integer isConcurrent;

    /**
     * 创建人
     */
    private String creater;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后更新时间
     */
    private Date lastUpdateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 已执行次数
     */
    private Integer count;

    /**
     * 任务名+任务组的组合
     */
    private String keyName;

    /**
     * 任务类型
     */
    private String jobType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getArguments() {
        return arguments;
    }

    public void setArguments(String arguments) {
        this.arguments = arguments;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSpringId() {
        return springId;
    }

    public void setSpringId(String springId) {
        this.springId = springId;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getIsConcurrent() {
        return isConcurrent;
    }

    public void setIsConcurrent(Integer isConcurrent) {
        this.isConcurrent = isConcurrent;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    @Override
    public String toString() {
        return "JobConfig{" + "id=" + id + ", jobName='" + jobName + '\'' + ", jobGroup='" + jobGroup + '\''
                + ", status='" + status + '\'' + ", cronExpression='" + cronExpression + '\'' + ", description='"
                + description + '\'' + ", className='" + className + '\'' + ", methodName='" + methodName + '\''
                + ", arguments='" + arguments + '\'' + ", address='" + address + '\'' + ", springId='" + springId + '\''
                + ", isActive=" + isActive + ", isConcurrent=" + isConcurrent + ", creater='" + creater + '\''
                + ", createTime=" + createTime + ", lastUpdateTime=" + lastUpdateTime + ", remark='" + remark + '\''
                + ", count=" + count + ", keyName='" + keyName + '\'' + ",jobType='" + jobType + '\'' + '}';
    }
}