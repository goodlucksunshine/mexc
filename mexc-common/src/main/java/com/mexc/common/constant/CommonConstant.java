package com.mexc.common.constant;

/**
 * @author liuxingmi
 * @datetime 2017年12月25日 下午3:28:31
 * @desc 通用常量
 */
public class CommonConstant {

	/**
	 * 买
	 */
	public final static Integer BUY = 1;
	
	/**
	 * 卖
	 */
	public final static Integer SELL = 2;


	public final static String secrect="i'm_is_a_private_key";


	/**
	 * 委托买缓存前缀
	 */
	public static final String ENTRUST_BUY_PREFIX = "ENTRUST_BUY_PREFIX_";
	/**
	 * 委托买总和
	 */
	public static final String ENTRUST_BUY_SUM_PREFIX = "ENTRUST_BUY_SUM_PREFIX_";
	/**
	 * 委托卖缓存前缀
	 */
	public static final String ENTRUST_SELL_PREFIX = "ENTRUST_SELL_PREFIX_";
	/**
	 * 委托卖总和
	 */
	public static final String ENTRUST_SELL_SUM_PREFIX = "ENTRUST_SELL_SUM_PREFIX_";
	/**
	 * 历史交易
	 */
	public static final String TRADE_PREFIX = "TRADE_PREFIX_";

	/**
	 * 市场币种列表
	 */
	public static final String MARKET_VCOIN_LIST_PREFIX = "MARKET_VCOIN_LIST_PREFIX_";

	/**
	 * 市场列表
	 */
	public static final String MARKET_LIST = "MARKET_LIST";

	/**
	 * 交易对
	 */
	public static final String TRADE_GROUP = "trade_group";
	/**
	 * 用户委托单
	 */
	public static final String USER_CURRENT_ENTRUST="user_current_entrust";
	/**
	 * 邮箱池
	 */
	public static final String MEXC_EMAIL_POOL = "mexc_email_pool";
}
