package com.mexc.dao.vo.wallet;

public class MemberAssetCheckVo {
    //用户id
    private String memberId;
    //币种id
    private String vcoinId;
    //币种名
    private String vcoinName;
    //充值总额
    private Integer rechargeAmount;
    //提现总额
    private Integer cashAmount;
    //账户资产余额
    private Integer assetBalanceAmount;

    public String getVcoinName() {
        return vcoinName;
    }

    public void setVcoinName(String vcoinName) {
        this.vcoinName = vcoinName;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getVcoinId() {
        return vcoinId;
    }

    public void setVcoinId(String vcoinId) {
        this.vcoinId = vcoinId;
    }

    public Integer getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(int rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public Integer getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(int cashAmount) {
        this.cashAmount = cashAmount;
    }

    public Integer getAssetBalanceAmount() {
        return assetBalanceAmount;
    }

    public void setAssetBalanceAmount(int assetBalanceAmount) {
        this.assetBalanceAmount = assetBalanceAmount;
    }
}
