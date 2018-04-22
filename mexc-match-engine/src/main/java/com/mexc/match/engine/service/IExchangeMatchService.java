package com.mexc.match.engine.service;

import java.util.List;

import com.mexc.common.base.ResultVo;
import com.mexc.dao.dto.order.Match.MatchOrder;

public interface IExchangeMatchService {

	/**
	 * 撮合：
	 * @param entrustOrder
	 * @return 返回撮合单的交易数量和市价，委托单的撮合数量
	 */
	ResultVo<List<MatchOrder>> match(MatchOrder entrustOrder);
}
