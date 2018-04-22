package com.mexc.vcoin.api.model;

import java.math.BigInteger;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2017/12/26
 * Time: 上午11:22
 */
public class WalletBalance {

    private BigInteger balance;

    private Integer precision;

    public BigInteger getBalance() {
        return balance;
    }

    public void setBalance(BigInteger balance) {
        this.balance = balance;
    }

    public Integer getPrecision() {
        return precision;
    }

    public void setPrecision(Integer precision) {
        this.precision = precision;
    }
}
