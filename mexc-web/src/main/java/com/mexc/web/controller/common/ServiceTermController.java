package com.mexc.web.controller.common;

import com.mexc.web.controller.MemberController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by huangxinguang on 2018/1/15 下午2:48.
 */
@Controller
@RequestMapping("/serviceterm")
public class ServiceTermController extends MemberController {

    @RequestMapping
    public ModelAndView serviceTerm() {
        ModelAndView modelAndView = getModelAndView();
        modelAndView.setViewName("/serviceTerm");
        return modelAndView;
    }
}
