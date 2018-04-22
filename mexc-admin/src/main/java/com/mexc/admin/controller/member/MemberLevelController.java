package com.mexc.admin.controller.member;

import com.mexc.admin.controller.WebController;
import com.mexc.admin.core.service.level.ILevelService;
import com.mexc.common.util.R;
import com.mexc.dao.model.member.MexcLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

/**
 * Created by huangxinguang on 2017/11/27 下午5:31.
 */
@Controller
@RequestMapping("/member/level")
public class MemberLevelController extends WebController {

    @Autowired
    private ILevelService levelService;

    @ResponseBody
    @RequestMapping("/list")
    public R list() {
        List<MexcLevel> list = levelService.selectAll();
        return R.ok().put("data",list);
    }

    @RequestMapping("/edit")
    public ModelAndView edit() {
        ModelAndView modelAndView = getModelAndView();
        modelAndView.setViewName("/level/edit");
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/update")
    public R update(MexcLevel mexcLevel) {
        mexcLevel.setUpdateBy(getSessionUser().getId());
        mexcLevel.setUpdateByName(getSessionUser().getAdminName());
        mexcLevel.setUpdateTime(new Date());
        levelService.updateByPrimaryKeySelective(mexcLevel);
        return R.ok();
    }
}
