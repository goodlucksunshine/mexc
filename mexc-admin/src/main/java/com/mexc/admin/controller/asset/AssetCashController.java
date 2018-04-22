package com.mexc.admin.controller.asset;

import com.laile.esf.common.util.Page;
import com.mexc.admin.controller.WebController;
import com.mexc.admin.core.service.asset.IAssetCashService;
import com.mexc.common.util.R;
import com.mexc.dao.dto.asset.AssetCashDto;
import com.mexc.dao.vo.asset.AssetCashVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by huangxinguang on 2018/1/22 下午5:50.
 */
@Controller
@RequestMapping("/asset/cash")
public class AssetCashController extends WebController {

    @Autowired
    private IAssetCashService assetCashService;

    @RequestMapping("/list")
    public ModelAndView list(AssetCashDto assetCashDto) {
        ModelAndView modelAndView = getModelAndView();
        Page<AssetCashVo> page = assetCashService.queryAssetCashPage(assetCashDto);
        modelAndView.addObject("queryDto", assetCashDto);
        modelAndView.addObject("page", page);
        modelAndView.setViewName("/asset/cash/list");
        return modelAndView;
    }

    @RequestMapping("/summaryList")
    public ModelAndView summaryList(AssetCashDto assetCashDto) {
        ModelAndView modelAndView = getModelAndView();
        Page<AssetCashVo> page = assetCashService.queryAssetCashPageByCondition(assetCashDto);
        modelAndView.addObject("queryDto", assetCashDto);
        modelAndView.addObject("page", page);
        modelAndView.setViewName("/asset/cash/summaryList");
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/reSend")
    public R cashResend(String cashId) {
        if (StringUtils.isEmpty(cashId)) {
            return R.ok("提现单号为空");
        }
        String msg = "";
        try {
            msg = assetCashService.cashReSend(cashId);
        } catch (Exception e) {
            logger.error("提现补单异常", e);
            msg = "提现补单异常";
        }
        return R.ok(msg);
    }
}
