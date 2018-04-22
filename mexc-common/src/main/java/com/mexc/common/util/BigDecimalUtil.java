package com.mexc.common.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * Created by huangxinguang on 2018/3/12 下午1:38.
 */
public class BigDecimalUtil {
    /**保留几位有效数字
     * @param oldDouble
     * @param scale
     * @return
     */
    public static BigDecimal significand(BigDecimal oldDouble, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "scale指定的精度为非负值");
        }
        /**
         * RoundingMode：舍入模式
         * UP：远离零方向舍入的舍入模式；
         * DOWN：向零方向舍入的舍入模式；
         * CEILING： 向正无限大方向舍入的舍入模式；
         * FLOOR：向负无限大方向舍入的舍入模式；
         * HALF_DOWN：向最接近数字方向舍入的舍入模式，如果与两个相邻数字的距离相等，则向下舍入；
         * HALF_UP：向最接近数字方向舍入的舍入模式，如果与两个相邻数字的距离相等，则向上舍入；
         * HALF_EVEN：向最接近数字方向舍入的舍入模式，如果与两个相邻数字的距离相等，则向相邻的偶数舍入;(在重复进行一系列计算时,此舍入模式可以将累加错误减到最小)
         * UNNECESSARY：用于断言请求的操作具有精确结果的舍入模式，因此不需要舍入。
         */
        RoundingMode rMode =null;
        //rMode=RoundingMode.FLOOR;
        //下面这种情况，其实和FLOOR一样的。
        if(oldDouble.compareTo(BigDecimal.ZERO) > 0){
            rMode=RoundingMode.DOWN;
        }else{
            rMode=RoundingMode.UP;
        }

        //此处的scale表示的是，几位有效位数
        BigDecimal b;
        int intLength = String.valueOf(oldDouble.intValue()).length();
        if(intLength > scale) {
            b = new BigDecimal(oldDouble.toPlainString(),new MathContext(scale,rMode));
        }else {
            scale = scale - intLength;
            b = new BigDecimal(oldDouble.toPlainString()).setScale(scale,rMode);
        }

        return b;
    }
    /**小数点之后保留几位小数(此处，我们用BigDecimal提供的（除以div）方法实现)
     * @param oldDouble
     * @param scale
     * @return
     */
    public static BigDecimal decimal(BigDecimal oldDouble, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = oldDouble;
        BigDecimal one = new BigDecimal("1");
        //return b.divide(one, scale, BigDecimal.ROUND_FLOOR).doubleValue();
        if(oldDouble.compareTo(BigDecimal.ZERO) > 0){
            //此处的scale表示的是，小数点之后的精度。
            return b.divide(one, scale, BigDecimal.ROUND_DOWN);
        }else{
            return b.divide(one, scale, BigDecimal.ROUND_UP);
        }
    }
    public static void main(String[] args) {
        //BigDecimal d = new BigDecimal("11111111112.545675677");
        BigDecimal d = new BigDecimal("11111212121212.0000111000001").stripTrailingZeros();
        int scale = 8;
        BigDecimal d1 = significand(d,scale);
        BigDecimal d2 = decimal(d,scale);
        System.out.println(d+"保留"+scale+"位有效数字："+d1.toPlainString());
        System.out.println(d+"保留小数点之后"+scale+"位小数："+d2.toPlainString());


    }
}
