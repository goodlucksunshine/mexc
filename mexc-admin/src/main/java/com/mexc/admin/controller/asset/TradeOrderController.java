package com.mexc.admin.controller.asset;

import com.laile.esf.common.util.Page;
import com.mexc.admin.controller.WebController;
import com.mexc.admin.core.service.asset.ITradeOrderService;
import com.mexc.dao.dto.order.OrderQueryDto;
import com.mexc.dao.model.vcoin.MexcTrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by huangxinguang on 2018/1/24 下午4:01.
 */
@Controller
@RequestMapping("/asset/trade")
public class TradeOrderController extends WebController {

    @Autowired
    private ITradeOrderService tradeOrderService;

    @RequestMapping("/orderList")
    public ModelAndView orderList(OrderQueryDto queryDto) {
        ModelAndView modelAndView = getModelAndView();
        Page<MexcTrade> page = tradeOrderService.queryTradeOrderPage(queryDto);
        modelAndView.addObject("queryDto",queryDto);
        modelAndView.addObject("page",page);
        modelAndView.setViewName("/asset/trade/list");
        return modelAndView;
    }
}
