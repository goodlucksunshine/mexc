package com.mexc.web.controller.tradingview;

import com.mexc.common.util.TimeUtil;
import com.mexc.dao.dto.tradingview.TradingViewDto;
import com.mexc.web.controller.MemberController;
import com.mexc.web.core.service.order.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by huangxinguang on 2018/1/8 上午10:51.
 */
@RequestMapping("/tradingview")
@Controller
public class TradingViewController extends MemberController {

    @Autowired
    private IOrderService orderService;

    @ResponseBody
    @RequestMapping("/history")
    public Object history(TradingViewDto tradingViewDto) {
        return orderService.getTradingData(tradingViewDto);
    }

    @ResponseBody
    @RequestMapping("/config")
    public Object config() {
        Map<String,Object> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("5");
        list.add("15");
        list.add("30");
        list.add("60");
        list.add("1D");
        list.add("1M");
        map.put("supports_search",false);
        map.put("supports_group_request",true);
        map.put("supported_resolutions",list);
        map.put("supports_timescale_marks",false);
        map.put("supports_marks",false);
        map.put("supports_time",true);
        return map;
    }

    @ResponseBody
    @RequestMapping("/time")
    public Object time() {
        return TimeUtil.getSecondTime(new Date());
    }

    @ResponseBody
    @RequestMapping("/symbol_info")
    public Object symbol_info(String group) {
        Map<String,Object> map = new HashMap<>();
        List<String> symbol = new ArrayList<>();
        symbol.add(group);

        map.put("symbol",symbol);
        map.put("exchange-listed","MEXC");
        map.put("exchange-traded","MEXC");
        map.put("type","bitcoin");
        map.put("minmov",1);
        map.put("minmov2",0);
        map.put("pricescale",100000000);
        map.put("has-intraday",true);
        map.put("has_no_volume",false);

        List<String> type = new ArrayList<>();
        type.add("stock");
        map.put("type",type);

        List<String> ticker = new ArrayList<>();
        ticker.add(group);
        map.put("ticker",ticker);
        map.put("timezone","Asia/Shanghai");
        map.put("session-regular","24x7");
        map.put("data_status","streaming");
        map.put("has_intraday",true);
        map.put("has_weekly_and_monthly",true);
        map.put("has_empty_bars",true);
        map.put("force_session_rebuild",true);
        map.put("has_seconds",true);
        map.put("has-dwm",true);

        List<Integer> second = new ArrayList<>();
        second.add(1);
        second.add(5);
        second.add(15);
        map.put("seconds_multipliers",second);

        List<Integer> intraday = new ArrayList<>();
        intraday.add(1);
        intraday.add(5);
        intraday.add(15);
        map.put("intraday_multipliers",intraday);

        map.put("has_daily",true);
        map.put("volume_precision",8);

        return map;
    }
}
