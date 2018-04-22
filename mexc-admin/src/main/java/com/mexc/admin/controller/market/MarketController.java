package com.mexc.admin.controller.market;

import com.laile.esf.common.util.Page;
import com.mexc.admin.controller.WebController;
import com.mexc.admin.core.service.market.IMarketService;
import com.mexc.admin.core.service.vcoin.IVCoinService;
import com.mexc.common.util.R;
import com.mexc.dao.model.market.MexcMarket;
import com.mexc.dao.model.vcoin.MexcVCoin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

/**
 * Created by huangxinguang on 2017/11/23 下午4:48.
 */
@Controller
@RequestMapping("/market")
public class MarketController extends WebController {

    @Autowired
    private IMarketService marketService;

    @Autowired
    private IVCoinService vCoinService;


    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView list(Integer currentPage, Integer showCount,String searchKey) {
        ModelAndView modelAndView = getModelAndView();
        Page<MexcMarket> page = marketService.queryMarketListPage(currentPage,showCount,searchKey);
        modelAndView.addObject("page",page);
        modelAndView.addObject("searchKey",searchKey);
        modelAndView.setViewName("/market/list");
        return modelAndView;
    }

    @RequestMapping("/add")
    public ModelAndView add() {
        ModelAndView modelAndView = getModelAndView();
        List<MexcVCoin> mainCoinList = vCoinService.queryMainVCoinList();
        modelAndView.addObject("mainCoinList",mainCoinList);
        modelAndView.setViewName("/market/add");
        return modelAndView;
    }

    @RequestMapping("/edit")
    public ModelAndView edit(String id) {
        ModelAndView modelAndView = getModelAndView();
        MexcMarket market = marketService.selectByPrimaryKey(id);
        List<MexcVCoin> mainCoinList = vCoinService.queryMainVCoinList();
        modelAndView.addObject("market",market);
        modelAndView.addObject("mainCoinList",mainCoinList);
        modelAndView.setViewName("/market/add");
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/saveOrUpdate")
    public R saveOrUpdate(MexcMarket market) {
        market.setCreateBy(getSessionUser().getId().toString());
        market.setCreateByName(getSessionUser().getAdminName());
        market.setCreateTime(new Date());
        market.setUpdateBy(getSessionUser().getId().toString());
        market.setUpdareByName(getSessionUser().getAdminName());
        market.setUpdateTime(new Date());
        marketService.saveOrUpdate(market);
        return R.ok();
    }

    @ResponseBody
    @RequestMapping("/del")
    public R del(String id) {
        marketService.delete(id);
        return R.ok();
    }
}
