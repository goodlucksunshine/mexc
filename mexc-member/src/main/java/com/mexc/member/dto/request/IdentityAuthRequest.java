package com.mexc.member.dto.request;

/**
 * Created by huangxinguang on 2017/12/14 下午3:20.
 * 身份认证
 */
public class IdentityAuthRequest {
    /**
     * 账户
     */
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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
        this.areaName = areaName;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public String getAuthSurname() {
        return authSurname;
    }

    public void setAuthSurname(String authSurname) {
        this.authSurname = authSurname;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardHome() {
        return cardHome;
    }

    public void setCardHome(String cardHome) {
        this.cardHome = cardHome;
    }

    public String getCardBack() {
        return cardBack;
    }

    public void setCardBack(String cardBack) {
        this.cardBack = cardBack;
    }

    public String getCardHand() {
        return cardHand;
    }

    public void setCardHand(String cardHand) {
        this.cardHand = cardHand;
    }
}
