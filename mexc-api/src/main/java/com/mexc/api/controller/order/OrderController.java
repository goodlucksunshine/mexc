package com.mexc.api.controller.order;

import com.mexc.api.service.IOrderService;
import com.mexc.api.vo.BaseResponse;
import com.mexc.api.vo.R;
import com.mexc.api.vo.order.*;
import com.mexc.common.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by huangxinguang on 2018/2/6 下午5:58.
 */
@Controller
@RequestMapping("/api/user")
public class OrderController extends BaseController {

    @Autowired
    private IOrderService orderService;

    @ResponseBody
    @RequestMapping(value = "/getDealHis",method = RequestMethod.POST)
    public Object getDealHis(DealHisRequest dealHisRequest) {
        DealHisResponse dealHisResponse = orderService.getDealHis(dealHisRequest);
        return R.ok().put("data",dealHisResponse);
    }

    @ResponseBody
    @RequestMapping(value = "/getEntrustHis",method = RequestMethod.POST)
    public Object getEntrustHis(EntrustHisRequest entrustRequest) {
        EntrustHisResponse entrustHisResponse = orderService.getEntrustHis(entrustRequest);
        return R.ok().put("data",entrustHisResponse);
    }

    @ResponseBody
    @RequestMapping(value = "/doEntrust",method = RequestMethod.POST)
    public Object doEntrust(EntrustRequest entrustRequest) {
        BaseResponse entrustResponse = orderService.doEntrust(entrustRequest);
        return R.ok().put("data",entrustResponse);
    }

    @ResponseBody
    @RequestMapping(value = "/doCancel",method = RequestMethod.POST)
    public Object doCancel(EntrustCancelRequest entrustCancelRequest) {
        BaseResponse cancelResponse = orderService.doCancel(entrustCancelRequest);
        return R.ok().put("data",cancelResponse);
    }
}
