package com.mexc.task.quartz.btcvalue;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.laile.esf.common.exception.ResultCode;
import com.laile.esf.common.exception.SystemException;
import com.laile.esf.common.util.HttpClientUtil;
import com.laile.esf.common.util.SpringUtils;
import com.mexc.common.util.jedis.RedisUtil;
import com.mexc.dao.delegate.vcoin.VCoinDelegate;
import com.mexc.dao.dto.vcoin.BtcValueDto;
import com.mexc.dao.model.vcoin.MexcVCoin;
import com.mexc.task.quartz.tradingview.TradingOrderOneMinueSync;
import com.mexc.web.core.service.vcoin.IVcoinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangxinguang on 2018/1/19 下午4:35.
 * btc估值同步
 */
public class BTCValueSync {
    private static final Logger logger = LoggerFactory.getLogger(TradingOrderOneMinueSync.class);
    private static final String URL = "https://api.coinmarketcap.com/v1/ticker/bitcoin/";

    @Autowired
    private VCoinDelegate vCoinDelegate;

    public void sync() {
        List<MexcVCoin> vCoinList = vCoinDelegate.selectAll();
        for(MexcVCoin vCoin : vCoinList) {
            BtcValueDto btcValueDto = getCoinMarketCap(vCoin.getVcoinNameEn());
            logger.info(vCoin.getVcoinNameEn()+"-->redis添加估值："+JSON.toJSONString(btcValueDto));
            RedisUtil.set(vCoin.getVcoinNameEn()+"-"+vCoin.getId(),JSON.toJSONString(btcValueDto));
        }
    }

    /**
     * 获取币种市场行情
     * @param vcoinNameEn
     * @return
     */
    private BtcValueDto getCoinMarketCap(String vcoinNameEn) {
        Map<String,Object> params = new HashMap<>();
        params.put("convert",vcoinNameEn);
        BtcValueDto btcValueDto = new BtcValueDto();
        try {
            String json = HttpClientUtil.httpGetRequest(URL,params);
            JSONArray jsonArray = JSON.parseArray(json);
            JSONObject marketCap = jsonArray.getJSONObject(0);
            if(marketCap != null) {
                String priceBtc = (String)marketCap.get("price_btc");
                String priceUsd = (String)marketCap.get("price_usd");
                String priceVCoin = (String)marketCap.get("price_"+vcoinNameEn.toLowerCase());
                btcValueDto.setVcoinNameEn(vcoinNameEn);

                if(vcoinNameEn.equals("BTC")) {
                    btcValueDto.setPriceBtc(priceBtc);
                    btcValueDto.setPriceCurrent(priceBtc);
                    btcValueDto.setPriceUsb(priceUsd);
                }else {//非比特币转化为比特币估值
                    if(priceVCoin != null) {
                        BigDecimal convertPriceBtn = new BigDecimal(priceBtc).divide(new BigDecimal(priceVCoin), 8, BigDecimal.ROUND_HALF_UP);
                        btcValueDto.setPriceBtc(convertPriceBtn.toPlainString());
                        btcValueDto.setPriceCurrent(new BigDecimal("1").toPlainString());
                        btcValueDto.setPriceUsb(priceUsd);
                    }else {//没有发现估值为0
                        btcValueDto.setPriceCurrent(new BigDecimal("1").toPlainString());
                        btcValueDto.setPriceBtc("0");
                        btcValueDto.setPriceUsb(priceUsd);
                    }
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
            logger.error("BTCValueSync->getCoinMarketCap :获取BTC估值异常",e);
            throw new SystemException(ResultCode.COMMON_ERROR,"获取BTC估值异常");
        }
        return btcValueDto;
    }
}
