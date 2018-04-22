package com.mexc.api.service;

import com.mexc.api.vo.coin.CoinResponse;

/**
 * Created by huangxinguang on 2018/2/6 下午2:40.
 */
public interface ICoinService {
    /**
     * 获取所有币种
     * @return
     */
    CoinResponse getAllCoin();
}
