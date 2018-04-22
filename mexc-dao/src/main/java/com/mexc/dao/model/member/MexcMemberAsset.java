package com.mexc.dao.model.member;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MexcMemberAsset implements Serializable {
    private String id;

    private String memberId;

    private String account;

    private String vcoinId;

    /**
     * 币种
     */
    private String token;

    /**
     * 总额
     */
    private BigDecimal totalAmount;

    /**
     * 可用余额
     */
    private BigDecimal balanceAmount;

    /**
     * 冻结金额
     */
    private BigDecimal frozenAmount;

    /**
     * BTC估值
     */
    private BigDecimal btcAmount;

    /**
     * 状态：0-新建，1-正常，2-删除
     */
    private Integer status;

    private String walletAddress;
    /**
     * 账户密码
     */
    private String accountPwd;
    /**
     * 账户短语
     */
    private String accountPhrase;
    /**
     * 向量
     */
    private String iv;

    private String note;

    private String updateBy;

    private String updareByName;

    private Date updateTime;

    private String createBy;

    private String createByName;

    private Date createTime;
    /**
     * 钱包地址
     */
    private String walletId;
    /**
     * 钱包文件地址
     */
    private String filePath;

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

    public String getVcoinId() {
        return vcoinId;
    }

    public void setVcoinId(String vcoinId) {
        this.vcoinId = vcoinId == null ? null : vcoinId.trim();
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public BigDecimal getFrozenAmount() {
        return frozenAmount;
    }

    public void setFrozenAmount(BigDecimal frozenAmount) {
        this.frozenAmount = frozenAmount;
    }

    public BigDecimal getBtcAmount() {
        return btcAmount;
    }

    public void setBtcAmount(BigDecimal btcAmount) {
        this.btcAmount = btcAmount;
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

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public String getAccountPwd() {
        return accountPwd;
    }

    public void setAccountPwd(String accountPwd) {
        this.accountPwd = accountPwd;
    }

    public String getAccountPhrase() {
        return accountPhrase;
    }

    public void setAccountPhrase(String accountPhrase) {
        this.accountPhrase = accountPhrase;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}