package com.mexc.common.util;

import com.mexc.common.exception.TipsException;
import org.apache.commons.lang3.StringUtils;

/**
 * 数据校验
 * Created by huangxinguang on 2017/10/23 上午10:15.
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new TipsException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new TipsException(message);
        }
    }
}