package com.mexc.admin.core.service.asset;

import com.laile.esf.common.util.Page;
import com.mexc.dao.dto.order.CancelEntrustTradeDto;
import com.mexc.dao.dto.order.OrderQueryDto;
import com.mexc.dao.model.vcoin.MexcEnTrust;

/**
 * Created by huangxinguang on 2018/1/23 上午10:06.
 */
public interface IEntrustOrderService {

    Page<MexcEnTrust> queryEntrustOrderPage(OrderQueryDto queryDto);

    void cancelEntrustOrder(CancelEntrustTradeDto cancelEntrustTradeDto);
}
