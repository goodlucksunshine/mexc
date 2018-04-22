package com.mexc.api.service;

import com.mexc.api.vo.BaseResponse;
import com.mexc.api.vo.asset.*;

/**
 * Created by huangxinguang on 2018/2/6 上午10:21.
 */
public interface IAssetService {
    /**
     * 提现记录
     * */
    CashResponse cashRecord(CashOrRechargeRequest cashRequest);
    /**
     * 充值记录
     * */
    RechargeResponse rechargeRecord(CashOrRechargeRequest rechargeRequest);
    /**
     * 获取充值地址
     * */
    RechargeAddressResponse getRechargeAddress(RechargeAddressRequest rechargeAddressRequest);
    /**
     * 提现
     * */
    BaseResponse cash(CashRequest cashRequest);
}
