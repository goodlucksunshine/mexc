package com.mexc.api.vo.asset;

import com.mexc.api.vo.BaseResponse;

/**
 * Created by huangxinguang on 2018/2/6 上午11:46.
 */
public class RechargeAddressResponse extends BaseResponse {
    private String chargeAddress;

    public String getChargeAddress() {
        return chargeAddress;
    }

    public void setChargeAddress(String chargeAddress) {
        this.chargeAddress = chargeAddress;
    }
}
