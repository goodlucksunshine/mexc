package com.mexc.web.controller.common;

import com.mexc.web.controller.MemberController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by huangxinguang on 2018/1/14 下午7:23.
 */
@Controller
@RequestMapping("/privacypolicy")
public class PrivacyPolicyController extends MemberController {

    @RequestMapping
    public ModelAndView service() {
        ModelAndView modelAndView = getModelAndView();
        modelAndView.setViewName("/privacyPolicy");
        return modelAndView;
    }
}
