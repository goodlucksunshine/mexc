package com.mexc.admin.controller.market;

import com.laile.esf.common.util.Page;
import com.mexc.admin.controller.WebController;
import com.mexc.admin.core.service.vcoin.impl.VCoinService;
import com.mexc.common.util.R;
import com.mexc.common.util.UUIDUtil;
import com.mexc.dao.model.market.MexcMarketVCoin;
import com.mexc.dao.model.vcoin.MexcVCoin;
import com.mexc.dao.vo.market.MarketVCoinVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

/**
 * Created by huangxinguang on 2018/1/12 下午3:26.
 * 市场币种管理
 */
@Controller
@RequestMapping("/market/vcoinmanage")
public class MarketVCoinManageController extends WebController {

    @Autowired
    private VCoinService vCoinService;

    @RequestMapping("/list")
    public ModelAndView list(Integer currentPage, Integer showCount,String marketId,String searchKey) {
        ModelAndView modelAndView = getModelAndView();
        Page<MarketVCoinVo> page = vCoinService.queryMarketVCoinList(currentPage,showCount,marketId,searchKey);
        modelAndView.addObject("marketId",marketId);
        modelAndView.addObject("page",page);
        modelAndView.setViewName("/market/vcoin/list");
        return modelAndView;
    }

    @RequestMapping("/add")
    public ModelAndView add(String marketId) {
        ModelAndView modelAndView = new ModelAndView();
        List<MexcVCoin> vCoinList = vCoinService.queryMarketVCoinExcludeMainVCoin(marketId);
        modelAndView.addObject("marketId",marketId);
        modelAndView.addObject("vCoinList",vCoinList);
        modelAndView.setViewName("/market/vcoin/add");
        return modelAndView;
    }

    @RequestMapping("/edit")
    public ModelAndView edit(String id) {
        ModelAndView modelAndView = new ModelAndView();
        MarketVCoinVo marketVCoin = vCoinService.queryMarketVCoin(id);
        modelAndView.addObject("marketVCoin",marketVCoin);
        modelAndView.setViewName("/market/vcoin/edit");
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/save")
    public Object save(MexcMarketVCoin marketVCoin) {
        marketVCoin.setCreateBy(getSessionUser().getId().toString());
        marketVCoin.setCreateByName(getSessionUser().getAdminName());
        marketVCoin.setCreateTime(new Date());
        marketVCoin.setId(UUIDUtil.get32UUID());
        vCoinService.addMarketVCoin(marketVCoin);
        return R.ok();
    }

    @ResponseBody
    @RequestMapping("/update")
    public Object update(MexcMarketVCoin marketVCoin) {
        marketVCoin.setUpdateBy(getSessionUser().getId().toString());
        marketVCoin.setUpdareByName(getSessionUser().getAdminName());
        marketVCoin.setUpdateTime(new Date());
        vCoinService.updateMarketVCoin(marketVCoin);
        return R.ok();
    }

    @ResponseBody
    @RequestMapping("/del")
    public Object del(String id) {
        vCoinService.deleteMarketVCoin(id);
        return R.ok();
    }

}
