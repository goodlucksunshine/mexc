package com.mexc.admin.core.service.asset;

import com.laile.esf.common.util.Page;
import com.mexc.dao.dto.order.OrderQueryDto;
import com.mexc.dao.model.vcoin.MexcTrade;

/**
 * Created by huangxinguang on 2018/1/24 下午4:03.
 */
public interface ITradeOrderService {

    /**
     * 查询订单
     * @param queryDto
     * @return
     */
    Page<MexcTrade> queryTradeOrderPage(OrderQueryDto queryDto);
}
