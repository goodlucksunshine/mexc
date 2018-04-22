package com.mexc;

import com.alibaba.fastjson.JSON;
import com.mexc.common.util.TimeUtil;
import com.mexc.common.util.jedis.RedisUtil;
import com.mexc.dao.vo.tradingview.TradingViewDataVo;
import com.mexc.match.engine.util.QueueKeyUtil;
import com.mexc.vcoin.bitcoin.BitServerUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

/**
 * Created by huangxinguang on 2018/1/11 下午9:55.
 */
public class Test {

    public static void main(String[] args) {
       /* Calendar now = Calendar.getInstance();
        Long currentTime = TimeUtil.getSecondTime(now.getTime());
        now.add(Calendar.MINUTE,-1);//减1分钟
        Long startTime = TimeUtil.getSecondTime(now.getTime());

        //Long startTime = currentTime - 60 * 60 *1;//减1小时

        //Long startTime = currentTime - 60 * 60 * 24;//减1小时
*/
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//       Long startTime = 1513578711L;
//       Long currentTime = 1516170711L;
//
//        Date startDate = new Date(startTime * 1000);
//        Date endDate = new Date(currentTime * 1000);
//
//        String startDateStr = dateFormat.format(startDate);
//        String endDateStr = dateFormat.format(endDate);
//        System.out.println("startDate:"+startDateStr);
//        System.out.println("endDate:"+endDateStr);

//        boolean checkResult = BitServerUtil.checkAddress("");


    }
}
