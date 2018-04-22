package com.mexc.api.service.impl;

import com.laile.esf.common.util.StringUtil;
import com.mexc.api.constant.ResultCode;
import com.mexc.api.service.ICoinService;
import com.mexc.api.vo.coin.CoinInfo;
import com.mexc.api.vo.coin.CoinResponse;
import com.mexc.common.util.LogUtil;
import com.mexc.common.util.jedis.RedisUtil;
import com.mexc.dao.delegate.vcoin.VCoinDelegate;
import com.mexc.dao.model.vcoin.MexcVCoin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangxinguang on 2018/2/6 下午2:40.
 * 币种
 */
@Service
public class CoinService implements ICoinService {
    private static Logger logger = LoggerFactory.getLogger(CoinService.class);

    @Autowired
    private VCoinDelegate coinDelegate;

    /**
     * 获取所有币种
     * @return
     */
    public CoinResponse getAllCoin() {
        CoinResponse response = new CoinResponse();
        try {

            /** 查询所有币种 */
            List<CoinInfo> coinInfoList = new ArrayList<>();
            List<MexcVCoin> coinList = coinDelegate.queryCanUseVcoinList();
            if (!CollectionUtils.isEmpty(coinList)) {
                for (MexcVCoin coin : coinList) {
                    CoinInfo coinInfo = new CoinInfo();
                    coinInfo.setCurrencyId(coin.getId());
                    coinInfo.setCurrencyShortName(coin.getVcoinName());
                    coinInfo.setCurrencyFullName(coin.getVcoinNameFull());
                    coinInfoList.add(coinInfo);
                }
            }
            response.setResultList(coinInfoList);

            response.setResultCode(ResultCode.MEXC_API_00000.getCode());
            response.setMessage(ResultCode.MEXC_API_00000.getMsg());
        }catch (Exception e) {
            response.setResultCode(ResultCode.MEXC_API_99999.getCode());
            response.setMessage(ResultCode.MEXC_API_99999.getMsg());
            logger.error("查询币种异常",e);
            return response;
        }
        return response;
    }
}
