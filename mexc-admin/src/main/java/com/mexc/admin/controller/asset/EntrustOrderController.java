package com.mexc.admin.controller.asset;

import com.laile.esf.common.annotation.validator.Validator;
import com.laile.esf.common.util.Page;
import com.mexc.admin.controller.WebController;
import com.mexc.admin.core.service.asset.IEntrustOrderService;
import com.mexc.common.util.R;
import com.mexc.dao.dto.order.CancelEntrustTradeDto;
import com.mexc.dao.dto.order.OrderQueryDto;
import com.mexc.dao.model.vcoin.MexcEnTrust;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by huangxinguang on 2018/1/23 上午10:04.
 */
@Controller
@RequestMapping("/asset/entrust")
public class EntrustOrderController extends WebController {

    @Autowired
    private IEntrustOrderService entrustOrderService;

    @RequestMapping("/orderList")
    public ModelAndView orderList(OrderQueryDto queryDto) {
        ModelAndView modelAndView = getModelAndView();
        Page<MexcEnTrust> page = entrustOrderService.queryEntrustOrderPage(queryDto);
        modelAndView.addObject("queryDto",queryDto);
        modelAndView.addObject("page",page);
        modelAndView.setViewName("/asset/entrust/list");
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/orderCancel")
    public Object cancelEntrustOrder(CancelEntrustTradeDto cancelDto) {
        Validator.valid(cancelDto);
        entrustOrderService.cancelEntrustOrder(cancelDto);
        return R.ok();
    }
}
