package com.mexc.dao.vo.asset;

import com.mexc.common.util.ThressDescUtil;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * Created by huangxinguang on 2018/1/22 下午6:11.
 * 资金转入Vo
 */
public class AssetRechargeVo {
    /**
     * 账户
     */
    private String account;
    /**
     * 币种
     */
    private String txToken;
    /**
     * 时间
     */
    private Date syncTime;
    /**
     * 充值地址
     */
    private String rechargeAddress;
    /**
     * 充值金额
     */
    private String rechargeValue;
    /**
     * 状态
     */
    private String status;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getTxToken() {
        return txToken;
    }

    public void setTxToken(String txToken) {
        this.txToken = txToken;
    }

    public Date getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(Date syncTime) {
        this.syncTime = syncTime;
    }

    public String getRechargeAddress() {
        if(!StringUtils.isEmpty(rechargeAddress)) {
            return ThressDescUtil.decodeAssetAddress(rechargeAddress);
        }
        return rechargeAddress;
    }

    public void setRechargeAddress(String rechargeAddress) {
        this.rechargeAddress = rechargeAddress;
    }

    public String getRechargeValue() {
        return rechargeValue;
    }

    public void setRechargeValue(String rechargeValue) {
        this.rechargeValue = rechargeValue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
