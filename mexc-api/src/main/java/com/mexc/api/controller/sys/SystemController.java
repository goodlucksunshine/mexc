package com.mexc.api.controller.sys;

import com.mexc.api.service.ISystemService;
import com.mexc.api.vo.R;
import com.mexc.api.vo.order.DealHisRequest;
import com.mexc.api.vo.order.DealHisResponse;
import com.mexc.api.vo.order.EntrustHisRequest;
import com.mexc.api.vo.order.EntrustHisResponse;
import com.mexc.api.vo.sys.FlashImageResponse;
import com.mexc.api.vo.sys.VersionResponse;
import com.mexc.common.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by huangxinguang on 2018/2/6 下午5:59.
 */
@Controller
@RequestMapping("/api/sys")
public class SystemController extends BaseController {

    @Autowired
    private ISystemService systemService;

    @ResponseBody
    @RequestMapping(value = "/getVersion",method = RequestMethod.POST)
    public Object getVersion() {
        VersionResponse versionResponse = systemService.getVersion();
        return R.ok().put("data",versionResponse);
    }

    @ResponseBody
    @RequestMapping(value = "/getFlashImage",method = RequestMethod.POST)
    public Object getFlashImage() {
        FlashImageResponse flashImageResponse = systemService.getFlashImage();
        return R.ok().put("data",flashImageResponse);
    }
}
