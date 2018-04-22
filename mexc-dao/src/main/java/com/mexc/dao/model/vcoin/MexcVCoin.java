package com.mexc.dao.model.vcoin;

import java.util.Date;

public class MexcVCoin extends MexcVcoinConfig{

    /**
     * 虚拟币代码
     */
    private String vcoinToken;

    private String vcoinName;

    private String vcoinNameEn;

    private String vcoinNameFull;

    private String icon;

    /**
     * 系统账户
     */
    private String sysAccount;

    /**
     * 交易key
     */
    private String tradeKey;

    /**
     * 状态：0-新建，1-正常，2-暂停，3-关闭，9-其它
     */
    private Integer status;

    private Integer sort;

    private String updateBy;

    private String updareByName;

    private Date updateTime;

    private String createBy;

    private String createByName;

    private Date createTime;

    /**
     * 主币或者代币
     */
    private Integer mainCoin;

    /**
     * 合约地址
     */
    private String contractAddress;

    /**
     * 是否可以冲值
     */
    private Integer canCash;

    /**
     * 是否可以提现
     */
    private Integer canRecharge;

    /**
     * 小数位
     */
    private Integer scale;

    private String note;


    public String getVcoinToken() {
        return vcoinToken;
    }

    public void setVcoinToken(String vcoinToken) {
        this.vcoinToken = vcoinToken == null ? null : vcoinToken.trim();
    }

    public String getVcoinName() {
        return vcoinName;
    }

    public void setVcoinName(String vcoinName) {
        this.vcoinName = vcoinName == null ? null : vcoinName.trim();
    }

    public String getVcoinNameEn() {
        return vcoinNameEn;
    }

    public void setVcoinNameEn(String vcoinNameEn) {
        this.vcoinNameEn = vcoinNameEn == null ? null : vcoinNameEn.trim();
    }

    public String getVcoinNameFull() {
        return vcoinNameFull;
    }

    public void setVcoinNameFull(String vcoinNameFull) {
        this.vcoinNameFull = vcoinNameFull == null ? null : vcoinNameFull.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getSysAccount() {
        return sysAccount;
    }

    public void setSysAccount(String sysAccount) {
        this.sysAccount = sysAccount == null ? null : sysAccount.trim();
    }

    public String getTradeKey() {
        return tradeKey;
    }

    public void setTradeKey(String tradeKey) {
        this.tradeKey = tradeKey == null ? null : tradeKey.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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

    public Integer getMainCoin() {
        return mainCoin;
    }

    public void setMainCoin(Integer mainCoin) {
        this.mainCoin = mainCoin;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress == null ? null : contractAddress.trim();
    }

    public Integer getCanCash() {
        return canCash;
    }

    public void setCanCash(Integer canCash) {
        this.canCash = canCash;
    }

    public Integer getCanRecharge() {
        return canRecharge;
    }

    public void setCanRecharge(Integer canRecharge) {
        this.canRecharge = canRecharge;
    }

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }
}