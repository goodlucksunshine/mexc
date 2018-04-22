package com.mexc.common.util;

import com.laile.esf.common.exception.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by huangxinguang on 2018/1/5 下午3:48.
 */
public class TimeUtil {
    private static Logger logger = LoggerFactory.getLogger(TimeUtil.class);

    public static long getTime(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str;
        long time;
        try {
            str = df.format(date);
            time = df.parse(str).getTime();
        }catch (Exception e) {
            logger.error("获取timce异常",e);
            throw new RuntimeException(ResultCode.COMMON_ERROR,e);
        }
        return time;
    }

    /**
     * 10位去掉毫秒
     * @param date
     * @return
     */
    public static long getSecondTime(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str;
        long time;
        try {
            str = df.format(date);
            time = df.parse(str).getTime() / 1000;
        }catch (Exception e) {
            logger.error("获取timce异常",e);
            throw new RuntimeException(ResultCode.COMMON_ERROR,e);
        }
        return time;
    }
}
