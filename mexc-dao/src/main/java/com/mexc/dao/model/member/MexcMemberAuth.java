package com.mexc.dao.model.member;

import java.io.Serializable;
import java.util.Date;

public class MexcMemberAuth implements Serializable {
    private String id;

    private String memberId;

    private String account;

    /**
     * 会员地区类别：1-中华人民共和国，2-其它国家或地区
     */
    private Integer areaType;

    /**
     * 地区名称
     */
    private String areaName;

    /**
     * 认证名称
     */
    private String authName;

    /**
     * 认证姓氏
     */
    private String authSurname;

    /**
     * 身份证号码
     */
    private String cardNo;

    /**
     * 证件封面
     */
    private String cardHome;

    /**
     * 证件背面
     */
    private String cardBack;

    /**
     * 手持身份证
     */
    private String cardHand;

    /**
     * 认证状态:0-待审核，1-通过，2-审核失败，9-其它
     */
    private Integer status;

    private String note;

    private String updateBy;

    private String updareByName;

    private Date updateTime;

    private String createBy;

    private String createByName;

    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public Integer getAreaType() {
        return areaType;
    }

    public void setAreaType(Integer areaType) {
        this.areaType = areaType;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName == null ? null : areaName.trim();
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName == null ? null : authName.trim();
    }

    public String getAuthSurname() {
        return authSurname;
    }

    public void setAuthSurname(String authSurname) {
        this.authSurname = authSurname == null ? null : authSurname.trim();
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo == null ? null : cardNo.trim();
    }

    public String getCardHome() {
        return cardHome;
    }

    public void setCardHome(String cardHome) {
        this.cardHome = cardHome == null ? null : cardHome.trim();
    }

    public String getCardBack() {
        return cardBack;
    }

    public void setCardBack(String cardBack) {
        this.cardBack = cardBack == null ? null : cardBack.trim();
    }

    public String getCardHand() {
        return cardHand;
    }

    public void setCardHand(String cardHand) {
        this.cardHand = cardHand == null ? null : cardHand.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
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
}