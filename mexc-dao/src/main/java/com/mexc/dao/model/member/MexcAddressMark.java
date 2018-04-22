package com.mexc.dao.model.member;

import java.util.Date;

public class MexcAddressMark {
    private String id;

    private String assetId;

    /**
     * 地址标签
     */
    private String addressTab;

    private String address;

    private Date createTime;

    private String memberId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId == null ? null : assetId.trim();
    }

    public String getAddressTab() {
        return addressTab;
    }

    public void setAddressTab(String addressTab) {
        this.addressTab = addressTab == null ? null : addressTab.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }
}