package com.mexc.common.util;

import com.laile.esf.common.util.MathUtil;

import java.math.BigDecimal;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2018/1/12
 * Time: 下午3:14
 */
public class BigDecimalFormat extends MathUtil {


    public static BigDecimal roundScale(BigDecimal srcDecimal, int scale) {
        if (srcDecimal == null) {
            return BigDecimal.ZERO;
        }
        return srcDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal round8(BigDecimal srcDecimal) {
        return roundScale(srcDecimal, 8);
    }

    public static BigDecimal round6(BigDecimal srcDecimal) {
        return roundScale(srcDecimal, 6);
    }

    public static BigDecimal round4(BigDecimal srcDecimal) {
        return roundScale(srcDecimal, 4);
    }
}
