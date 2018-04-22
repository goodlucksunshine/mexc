package com.mexc.admin.controller.member;

import com.laile.esf.common.util.Page;
import com.mexc.admin.controller.WebController;
import com.mexc.admin.core.service.member.IMemberService;
import com.mexc.dao.model.member.MexcMember;
import com.mexc.dao.vo.wallet.MemberAssetCheckVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/memberCheck")
public class MemberExceptionController extends WebController {
    @Autowired
    private IMemberService memberService;
    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView modelAndView = getModelAndView();
        List<MemberAssetCheckVo> list = memberService.queryExceptionUser();
        modelAndView.addObject("list",list);
        modelAndView.setViewName("/member/exceptionUser/list");
        return modelAndView;
    }

}
