package redis.test;

import com.mexc.common.util.BigDecimalUtil;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * Created by huangxinguang on 2018/3/12 下午7:12.
 */
public class TestBigDecimal {

    @Test
    public void test() {
        BigDecimal d = new BigDecimal("11111.0000111000001").stripTrailingZeros();
        int scale = 8;
        BigDecimal d1 = BigDecimalUtil.significand(d,scale);
        BigDecimal d2 = BigDecimalUtil.decimal(d,scale);
        System.out.println(d+"保留"+scale+"位有效数字："+d1.toPlainString());
        System.out.println(d+"保留小数点之后"+scale+"位小数："+d2.toPlainString());
    }
}
