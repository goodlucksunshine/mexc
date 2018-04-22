package com.mexc.dao.model.wallet;

import java.util.Date;

public class MexcWallet {
    private String id;

    /**
     * 钱包名称
     */
    private String name;

    /**
     * 钱包类型
     */
    private String type;

    /**
     * 钱包地址
     */
    private String url;

    private String rpcPort;

    private String socketPort;

    private Date createTime;

    private String createBy;

    private String createByName;

    private Date updateTime;

    private String updateBy;

    private String updateByName;

    /**
     * 钱包状态 -1 离线 1 在线
     */
    private Integer status;

    /**
     * 热钱包地址
     */
    private String hotAddress;

    /**
     * 热钱包密码
     */
    private String hotPwd;

    /**
     * 热钱包秘钥文件
     */
    private String hotFile;

    /**
     * 冷钱包地址
     */
    private String coldAddress;

    private String lastSyncTransHash;

    private String blockSync;

    private String walletUser;

    private String walletPwd;

    private String note;

    /**
     * 钱包余额
     */
    private String balance;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getRpcPort() {
        return rpcPort;
    }

    public void setRpcPort(String rpcPort) {
        this.rpcPort = rpcPort == null ? null : rpcPort.trim();
    }

    public String getSocketPort() {
        return socketPort;
    }

    public void setSocketPort(String socketPort) {
        this.socketPort = socketPort == null ? null : socketPort.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public String getUpdateByName() {
        return updateByName;
    }

    public void setUpdateByName(String updateByName) {
        this.updateByName = updateByName == null ? null : updateByName.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getHotAddress() {
        return hotAddress;
    }

    public void setHotAddress(String hotAddress) {
        this.hotAddress = hotAddress == null ? null : hotAddress.trim();
    }

    public String getHotPwd() {
        return hotPwd;
    }

    public void setHotPwd(String hotPwd) {
        this.hotPwd = hotPwd == null ? null : hotPwd.trim();
    }

    public String getHotFile() {
        return hotFile;
    }

    public void setHotFile(String hotFile) {
        this.hotFile = hotFile == null ? null : hotFile.trim();
    }

    public String getColdAddress() {
        return coldAddress;
    }

    public void setColdAddress(String coldAddress) {
        this.coldAddress = coldAddress == null ? null : coldAddress.trim();
    }

    public String getLastSyncTransHash() {
        return lastSyncTransHash;
    }

    public void setLastSyncTransHash(String lastSyncTransHash) {
        this.lastSyncTransHash = lastSyncTransHash == null ? null : lastSyncTransHash.trim();
    }

    public String getBlockSync() {
        return blockSync;
    }

    public void setBlockSync(String blockSync) {
        this.blockSync = blockSync == null ? null : blockSync.trim();
    }

    public String getWalletUser() {
        return walletUser;
    }

    public void setWalletUser(String walletUser) {
        this.walletUser = walletUser == null ? null : walletUser.trim();
    }

    public String getWalletPwd() {
        return walletPwd;
    }

    public void setWalletPwd(String walletPwd) {
        this.walletPwd = walletPwd == null ? null : walletPwd.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}