package com.mexc.admin.controller.index;

import com.mexc.admin.controller.WebController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by huangxinguang on 2017/9/26 下午3:24.
 */

@Controller
@RequestMapping
public class IndexController extends WebController {

    @RequestMapping(value = "/toMainPage")
    public ModelAndView toMainPage() {
        ModelAndView mav = getModelAndView();
        mav.setViewName("/index/main");
        return mav;
    }

    @RequestMapping(value = "/index")
    public ModelAndView toIndex() {
        ModelAndView mav = getModelAndView();
        mav.addObject("admin",getSessionUser());
        mav.setViewName("/index");
        return mav;
    }

}
