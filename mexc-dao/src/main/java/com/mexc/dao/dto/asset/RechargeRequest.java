package com.mexc.dao.dto.asset;

import java.util.Date;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2018/1/11
 * Time: 下午10:58
 */
public class RechargeRequest {

    private String assetId;

    private String rechargeValue;

    private String createBy;

    private Date createTime;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getRechargeValue() {
        return rechargeValue;
    }

    public void setRechargeValue(String rechargeValue) {
        this.rechargeValue = rechargeValue;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
