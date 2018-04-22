package com.mexc.dao.model.vcoin;

import java.util.Date;

public class MexcAddressLib {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 类别
     */
    private String type;

    /**
     * 地址
     */
    private String address;

    /**
     * 密码
     */
    private String pwd;

    /**
     * iv向量
     */
    private String iv;

    /**
     * 短语
     */
    private String phrase;

    /**
     * 文件地址
     */
    private String filepath;

    private Date createTime;

    private Integer isuse;

    /**
     * 用户ID
     */
    private String memberId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv == null ? null : iv.trim();
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase == null ? null : phrase.trim();
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath == null ? null : filepath.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsuse() {
        return isuse;
    }

    public void setIsuse(Integer isuse) {
        this.isuse = isuse;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }
}