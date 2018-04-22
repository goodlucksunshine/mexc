package com.mexc.admin.controller.asset;

import com.laile.esf.common.util.Page;
import com.mexc.admin.controller.WebController;
import com.mexc.admin.core.service.asset.impl.AssetRechargeService;
import com.mexc.dao.dto.asset.AssetRechargeDto;
import com.mexc.dao.model.wallet.MexcAssetRecharge;
import com.mexc.dao.vo.asset.AssetRechargeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by huangxinguang on 2018/1/22 下午5:02.
 */
@Controller
@RequestMapping("/asset/recharge")
public class AssetRechargeController extends WebController {

    @Autowired
    private AssetRechargeService assetRechargeService;

    @RequestMapping("/list")
    public ModelAndView list(AssetRechargeDto rechargeDto) {
        ModelAndView modelAndView = getModelAndView();
        Page<AssetRechargeVo> page = assetRechargeService.queryAssetRechargePage(rechargeDto);
        modelAndView.addObject("queryDto",rechargeDto);
        modelAndView.addObject("page",page);
        modelAndView.setViewName("/asset/recharge/list");
        return modelAndView;
    }

    @RequestMapping("/summaryList")
    public ModelAndView summaryList(AssetRechargeDto rechargeDto) {
        ModelAndView modelAndView = getModelAndView();
        Page<AssetRechargeVo> page = assetRechargeService.queryAssetRechargeByCondition(rechargeDto);
        modelAndView.addObject("queryDto",rechargeDto);
        modelAndView.addObject("page",page);
        modelAndView.setViewName("/asset/recharge/summaryList");
        return modelAndView;
    }
}
