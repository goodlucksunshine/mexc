package com.mexc.match.engine.util;

import com.laile.esf.common.util.MD5Util;

/**
 * @author liuxingmi
 * @datetime 2017年12月26日 下午8:23:54
 * @desc 交易队列key
 */
public class QueueKeyUtil {

	/**
	 * @author liuxingmi
	 * @datetime 2017年12月26日 下午8:20:37
	 * @desc 获取队列key
	 * @param marketId
	 * @param vcoinId
	 * @return String
	 */
	public static String getKey(String marketId, String vcoinId){
	
		return MD5Util.MD5(marketId + vcoinId);
		
	}

	public static String getKey(String marketId, String vcoinId,String tradeType){

		return MD5Util.MD5(marketId + vcoinId + tradeType);

	}

	public static String getKey(String marketId){
		return MD5Util.MD5(marketId );
	}

	public static String getUserGroupKey(String marketId,String vcoinId,String account) {
		return MD5Util.MD5(marketId + vcoinId + account);
	}
}
