package com.mexc.dao.vo.vcoin.trade;

/**
 * Created by huangxinguang on 2018/1/5 下午7:43.
 * 交易24小时涨跌
 */
public class Trade24HUpDownVo {
    /**
     * 涨跌
     */
    private String upOrDown="0";

    /**
     * 涨跌率
     */
    private String upOrDownRate="-%";


    public String getUpOrDown() {
        return upOrDown;
    }

    public void setUpOrDown(String upOrDown) {
        this.upOrDown = upOrDown;
    }

    public String getUpOrDownRate() {
        return upOrDownRate;
    }

    public void setUpOrDownRate(String upOrDownRate) {
        this.upOrDownRate = upOrDownRate;
    }
}
