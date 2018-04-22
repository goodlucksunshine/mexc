package com.mexc.admin.controller.asset;

import com.laile.esf.common.util.Page;
import com.mexc.admin.controller.WebController;
import com.mexc.admin.core.service.asset.IAssetTransService;
import com.mexc.dao.dto.asset.AssetQueryDto;
import com.mexc.dao.vo.asset.AssetTransVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by huangxinguang on 2018/1/22 下午3:53.
 */
@Controller
@RequestMapping("/asset/trans")
public class AssetTransController extends WebController {

    @Autowired
    private IAssetTransService assetTransService;

    @RequestMapping("/list")
    public ModelAndView list(AssetQueryDto queryDto) {
        ModelAndView modelAndView = getModelAndView();
        Page<AssetTransVo> page = assetTransService.queryAsserTransList(queryDto);
        modelAndView.addObject("queryDto",queryDto);
        modelAndView.addObject("page",page);
        modelAndView.setViewName("/asset/detail/list");
        return modelAndView;
    }
}
