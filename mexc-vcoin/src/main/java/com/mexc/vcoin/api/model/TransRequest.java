package com.mexc.vcoin.api.model;

import java.math.BigInteger;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2017/12/25
 * Time: 下午4:18
 */
public class TransRequest {

    /**
     * 转出账户
     */
    private String from;
    /**
     * 转入账户
     */
    private String to;
    /**
     * 转出数量
     */
    private BigInteger value;
    /**
     * 密码
     */
    private String pwd;
    /**
     * 短语
     */
    private String phrase;
    /**
     * 合约地址
     */
    private String contractAddress;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public BigInteger getValue() {
        return value;
    }

    public void setValue(BigInteger value) {
        this.value = value;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }
}
