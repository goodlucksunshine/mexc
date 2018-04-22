package com.mexc.vcoin;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2018/1/6
 * Time: 下午2:58
 */
public class WalletInfo {
    /**
     * 服务器IP
     */
    private String ip;
    /**
     * 钱包ID
     */
    private String id;
    /**
     * 端口
     */
    private String port;
    /**
     * socket端口
     */
    private String socketPort;
    /**
     * 用户
     */
    private String user;
    /**
     * 密码
     */
    private String pwd;
    /**
     * 上次同步到最后的区块
     */
    private String lastSync;
    /**
     * 比特币上次同步交易地址
     */
    private String lastTransOffset;

    /**
     * 冷钱包地址
     */
    private String coldAddress;
    /**
     * 热钱包地址
     */
    private String hotAddress;
    /**
     * 热钱包密码
     */
    private String hotPwd;
    /**
     * 热钱包秘钥地址
     */
    private String hotFile;


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getSocketPort() {
        return socketPort;
    }

    public void setSocketPort(String socketPort) {
        this.socketPort = socketPort;
    }

    public String getLastSync() {
        return lastSync;
    }

    public void setLastSync(String lastSync) {
        this.lastSync = lastSync;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getColdAddress() {
        return coldAddress;
    }

    public void setColdAddress(String coldAddress) {
        this.coldAddress = coldAddress;
    }

    public String getHotAddress() {
        return hotAddress;
    }

    public void setHotAddress(String hotAddress) {
        this.hotAddress = hotAddress;
    }

    public String getHotPwd() {
        return hotPwd;
    }

    public void setHotPwd(String hotPwd) {
        this.hotPwd = hotPwd;
    }

    public String getHotFile() {
        return hotFile;
    }

    public void setHotFile(String hotFile) {
        this.hotFile = hotFile;
    }

    public String getLastTransOffset() {
        return lastTransOffset;
    }

    public void setLastTransOffset(String lastTransOffset) {
        this.lastTransOffset = lastTransOffset;
    }
}
