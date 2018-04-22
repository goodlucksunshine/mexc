package com.mexc.admin.controller.activity;

import com.laile.esf.common.util.Page;
import com.mexc.admin.core.service.activity.IActivityService;
import com.mexc.admin.core.service.vcoin.IVCoinService;
import com.mexc.common.base.BaseController;
import com.mexc.common.util.R;
import com.mexc.dao.dto.activity.ActivityRuleDto;
import com.mexc.dao.dto.activity.ActivityRuleSearchDto;
import com.mexc.dao.dto.activity.ActivitySearchDto;
import com.mexc.dao.model.activity.MexcActivity;
import com.mexc.dao.model.activity.MexcActivityCandies;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2018/2/7
 * Time: 下午4:51
 */
@RequestMapping("/activity")
@Controller
public class ActivityController extends BaseController {

    @Resource
    IActivityService activityService;
    @Resource
    IVCoinService ivCoinService;

    @InitBinder
    public void initBinder(ServletRequestDataBinder bin) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        CustomDateEditor cust = new CustomDateEditor(sdf, true);
        bin.registerCustomEditor(Date.class, cust);
    }


    @RequestMapping("/actPage")
    public ModelAndView activityPage(ActivitySearchDto dto) {
        ModelAndView modelAndView = getModelAndView();
        Page<MexcActivity> activityPage = activityService.queryActivityPage(dto);
        modelAndView.addObject("page", activityPage);
        modelAndView.setViewName("activity/list");
        return modelAndView;
    }

    @RequestMapping("/actDetail")
    public ModelAndView activityDetail(String activityId) {
        ModelAndView modelAndView = getModelAndView();
        MexcActivity detail = activityService.queryActivityDetail(activityId);
        modelAndView.addObject("activity", detail);
        modelAndView.setViewName("activity/add");
        return modelAndView;
    }

    @RequestMapping("/actAdd")
    public ModelAndView actAdd() {
        ModelAndView modelAndView = getModelAndView();
        modelAndView.setViewName("activity/add");
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/saveActivity")
    public R saveActivity(MexcActivity mexcActivity) {
        activityService.saveOrUpdate(mexcActivity);
        return R.ok();
    }


    @RequestMapping("/rulePage")
    public ModelAndView queryRuleList(ActivityRuleSearchDto searchDto) {
        ModelAndView modelAndView = getModelAndView();
        Page<ActivityRuleDto> page = activityService.queryRulePage(searchDto);
        modelAndView.addObject("page", page);
        modelAndView.addObject("searchDto", searchDto);
        modelAndView.setViewName("activity/rule/list");
        return modelAndView;
    }


    @RequestMapping("/ruleAdd")
    public ModelAndView ruleAdd(String activityId) {
        ModelAndView modelAndView = getModelAndView();
        modelAndView.addObject("vcoin", ivCoinService.queryAllVCoinList());
        MexcActivityCandies candies = new MexcActivityCandies();
        candies.setActivityId(activityId);
        modelAndView.addObject("rule", candies);
        modelAndView.setViewName("activity/rule/add");
        return modelAndView;
    }

    @RequestMapping("/ruleDetail")
    public ModelAndView ruleAdd(Integer ruleId) {
        ModelAndView modelAndView = getModelAndView();
        MexcActivityCandies candies = activityService.queryRuleDetail(ruleId);
        candies.setPercent(candies.getPercent().multiply(new BigDecimal(100)));
        modelAndView.addObject("vcoin", ivCoinService.queryAllVCoinList());
        modelAndView.addObject("rule", candies);
        modelAndView.setViewName("activity/rule/add");
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/saveOrUpdate")
    public R ruleDetail(MexcActivityCandies candies) {
        candies.setPercent(candies.getPercent().divide(new BigDecimal(100)));
        candies.setRuleStartTime(new Date());
        candies.setRuleEndTime(new Date());
        candies.setExecStatus(0);
        activityService.saveOrUpdateRule(candies);
        return R.ok();
    }


    @ResponseBody
    @RequestMapping("/execRule")
    public R execRule(Integer ruleId) {
        activityService.execActivityRule(ruleId);
        return R.ok();
    }
}
