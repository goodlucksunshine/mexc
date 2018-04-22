package com.mexc.admin.controller.market;

import com.laile.esf.common.util.Page;
import com.mexc.admin.controller.WebController;
import com.mexc.admin.core.service.market.impl.MarketService;
import com.mexc.admin.core.service.vcoin.impl.VCoinService;
import com.mexc.common.util.R;
import com.mexc.common.util.UUIDUtil;
import com.mexc.dao.model.market.MexcMarket;
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
 * Created by huangxinguang on 2018/1/30 上午9:58.
 * 市场币种推荐
 */
@Controller
@RequestMapping("/market/suggest")
public class MarketSuggestController extends WebController {

    @Autowired
    private VCoinService vCoinService;

    @Autowired
    private MarketService marketService;

    @RequestMapping("/list")
    public ModelAndView list(Integer currentPage,Integer showCount,String marketId,String searchKey) {
        ModelAndView modelAndView = getModelAndView();
        Page<MarketVCoinVo> page = vCoinService.queryMarketSuggestVCoinList(currentPage,showCount,marketId,searchKey);
        modelAndView.addObject("page",page);
        modelAndView.setViewName("/market/suggest/list");
        return modelAndView;
    }

    @RequestMapping("/add")
    public ModelAndView add() {
        ModelAndView modelAndView = getModelAndView();
        List<MexcMarket> marketList = marketService.queryMarketList();
        modelAndView.addObject("marketList",marketList);
        modelAndView.setViewName("/market/suggest/add");
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/getMarketVCoinList")
    public Object getMarketVCoinList(String marketId) {
        List<MexcVCoin> list = vCoinService.queryMarketNoSuggestVCoinList(marketId);
        return R.ok().put("vlist",list);
    }

    @ResponseBody
    @RequestMapping("/update")
    public Object update(MexcMarketVCoin marketVCoin) {
        vCoinService.updateMarketVCoinSuggest(marketVCoin);
        return R.ok();
    }

    @ResponseBody
    @RequestMapping("/del")
    public Object del(MexcMarketVCoin marketVCoin) {
        marketVCoin.setSuggest(0);
        vCoinService.updateMarketVCoinSuggest(marketVCoin);
        return R.ok();
    }

}
