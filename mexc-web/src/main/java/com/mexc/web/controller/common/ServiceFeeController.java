package com.mexc.web.controller.common;

import com.mexc.web.controller.MemberController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by huangxinguang on 2018/1/15 下午2:49.
 */
@Controller
@RequestMapping("/servicefee")
public class ServiceFeeController extends MemberController {

    @RequestMapping
    public ModelAndView serviceFee() {
        ModelAndView modelAndView = getModelAndView();
        modelAndView.setViewName("/serviceFee");
        return modelAndView;
    }
}
