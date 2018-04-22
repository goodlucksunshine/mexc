package com.mexc.dao.model.member;

import java.io.Serializable;
import java.util.Date;

public class MexcMember implements Serializable {
    /**
     * 会员编号
     */
    private String id;

    private String account;

    private String accountPwd;

    /**
     * 二次认证密码
     */
    private String secondPwd;

    /**
     * 交易密码
     */
    private String transPwd;

    /**
     * 二次认证类型：0-无，1-手机，2-谷歌
     */
    private Integer secondAuthType;

    /**
     * 认证号码
     */
    private String authAcc;

    /**
     * 认证等级：1-普通，2-中级，3-高级，4-特级
     */
    private Integer authLevel;

    /**
     * 状态：0-新建，1-正常，2-暂停，3-注销，9-异常
     */
    private Integer status;

    /**
     * 推荐人
     */
    private String recommender;

    private String note;

    private String lastLoginIp;

    private Date lastLoginTime;

    private String updateBy;

    private String updareByName;

    private Date updateTime;

    private String createBy;

    private String createByName;

    private Date createTime;
    /**
     * 用户序列号
     */
    private Integer memberSeq;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getAccountPwd() {
        return accountPwd;
    }

    public void setAccountPwd(String accountPwd) {
        this.accountPwd = accountPwd == null ? null : accountPwd.trim();
    }

    public String getSecondPwd() {
        return secondPwd;
    }

    public void setSecondPwd(String secondPwd) {
        this.secondPwd = secondPwd == null ? null : secondPwd.trim();
    }

    public String getTransPwd() {
        return transPwd;
    }

    public void setTransPwd(String transPwd) {
        this.transPwd = transPwd == null ? null : transPwd.trim();
    }

    public Integer getSecondAuthType() {
        return secondAuthType;
    }

    public void setSecondAuthType(Integer secondAuthType) {
        this.secondAuthType = secondAuthType;
    }

    public String getAuthAcc() {
        return authAcc;
    }

    public void setAuthAcc(String authAcc) {
        this.authAcc = authAcc == null ? null : authAcc.trim();
    }

    public Integer getAuthLevel() {
        return authLevel;
    }

    public void setAuthLevel(Integer authLevel) {
        this.authLevel = authLevel;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRecommender() {
        return recommender;
    }

    public void setRecommender(String recommender) {
        this.recommender = recommender == null ? null : recommender.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp == null ? null : lastLoginIp.trim();
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public String getUpdareByName() {
        return updareByName;
    }

    public void setUpdareByName(String updareByName) {
        this.updareByName = updareByName == null ? null : updareByName.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public String getCreateByName() {
        return createByName;
    }

    public void setCreateByName(String createByName) {
        this.createByName = createByName == null ? null : createByName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getMemberSeq() {
        return memberSeq;
    }

    public void setMemberSeq(Integer memberSeq) {
        this.memberSeq = memberSeq;
    }
}