package com.mexc.api.controller.member;

import com.mexc.api.service.ICoinService;
import com.mexc.api.service.ITradeGroupService;
import com.mexc.api.vo.R;
import com.mexc.api.vo.coin.CoinResponse;
import com.mexc.api.vo.trade.TradeGroupRequest;
import com.mexc.api.vo.trade.TradeGroupResponse;
import com.mexc.common.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by huangxinguang on 2018/2/6 下午6:32.
 */
@Controller
@RequestMapping("/api/trade")
public class TradeController extends BaseController {

    @Autowired
    private ITradeGroupService tradeGroupService;

    @Autowired
    private ICoinService coinService;

    @ResponseBody
    @RequestMapping(value = "/getTradeGroup",method = RequestMethod.POST)
    public Object getTradeGroup(TradeGroupRequest tradeGroupRequest) {
        TradeGroupResponse tradeGroupResponse = tradeGroupService.getTradeGroup(tradeGroupRequest);
        return R.ok().put("data",tradeGroupResponse);
    }

    @ResponseBody
    @RequestMapping(value = "/getAllCoin",method = RequestMethod.POST)
    public Object getAllCoin() {
        CoinResponse coinResponse = coinService.getAllCoin();
        return R.ok().put("data",coinResponse);
    }

}
