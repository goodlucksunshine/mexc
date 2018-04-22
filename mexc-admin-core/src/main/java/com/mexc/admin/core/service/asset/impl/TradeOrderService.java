package com.mexc.admin.core.service.asset.impl;

import com.laile.esf.common.util.Page;
import com.mexc.admin.core.service.asset.ITradeOrderService;
import com.mexc.dao.delegate.vcoin.MexcTradeDelegate;
import com.mexc.dao.dto.order.OrderQueryDto;
import com.mexc.dao.model.vcoin.MexcTrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huangxinguang on 2018/1/24 下午4:03.
 */
@Service
public class TradeOrderService implements ITradeOrderService {

    @Autowired
    private MexcTradeDelegate mexcTradeDelegate;

    @Override
    public Page<MexcTrade> queryTradeOrderPage(OrderQueryDto queryDto) {
        return mexcTradeDelegate.queryTradeOrderPage(queryDto);
    }
}
