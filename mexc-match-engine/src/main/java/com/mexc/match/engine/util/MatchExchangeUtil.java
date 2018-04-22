package com.mexc.match.engine.util;

import com.laile.esf.common.util.StringUtil;
import com.mexc.common.util.jedis.RedisUtil;


public class MatchExchangeUtil {

	public final static String TRADED_PRICE_PREFIX = "TRADED_PRICE";

	public static Double getPrice(String key) {
		String maxBuyStr = RedisUtil.get(key);
		return StringUtil.isEmpty(maxBuyStr) ? null : Double.valueOf(maxBuyStr);
	}

}
