package com.mexc.dao.vo;

import com.alibaba.fastjson.JSON;
import com.laile.esf.common.util.MD5Util;
import com.mexc.common.constant.CommonConstant;
import com.mexc.common.util.TimeUtil;
import com.mexc.common.util.jedis.RedisUtil;
import com.mexc.dao.vo.tradingview.TradingViewDataVo;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Random;
import java.util.Timer;

/**
 * Created by huangxinguang on 2018/1/11 下午10:13.
 */
@Component
public class Test {
    static int connt = 0;

    /*static {
        System.out.print("redis 开始填充数据");

        new Thread(new Runnable() {

            @Override
            public void run() {
                boolean flag = true;
                while(flag) {
                    TradingViewDataVo tradingViewData = new TradingViewDataVo();

                    Calendar now = Calendar.getInstance();
                    Long currentTime = TimeUtil.getSecondTime(now.getTime());
                    now.add(Calendar.MINUTE, -1);//减1分钟

                    tradingViewData.setT(currentTime);
                    tradingViewData.setH(Double.valueOf(new Random().nextInt(100))/100d+0.001d);
                    tradingViewData.setL(Double.valueOf(new Random().nextInt(10))/100d-0.001d);
                    tradingViewData.setV(170 + (double)(new Random().nextInt(1000)));
                    tradingViewData.setO(Double.valueOf(new Random().nextInt(100))/100d+0.001d);
                    tradingViewData.setC(Double.valueOf(new Random().nextInt(100))/100d+0.001d);
                    String key =  "TRADING_1_PREFIX_"+MD5Util.MD5("a3551348feb411e7a6d000163e0205ba" + "228bdc1ceed311e7881b00163e08dae5");

                    //RedisUtil.zadd("TRADING_1_PREFIX_ea3b4eaa96d0788834840ad1eca11577", Double.valueOf(currentTime), JSON.toJSONString(tradingViewData));
                    //RedisUtil.zadd("TRADING_1_PREFIX_f6b893498af598d0232af580aec9e2f6", Double.valueOf(currentTime), JSON.toJSONString(tradingViewData));
                    RedisUtil.zadd(key, Double.valueOf(currentTime), JSON.toJSONString(tradingViewData));

                    System.out.println(connt++);
                    if(connt==500) {
                        flag = false;
                    }
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }*/
}
