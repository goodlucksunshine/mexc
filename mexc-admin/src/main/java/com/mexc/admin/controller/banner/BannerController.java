package com.mexc.admin.controller.banner;

import com.laile.esf.common.util.Page;
import com.laile.esf.common.util.StringUtil;
import com.mexc.admin.controller.WebController;
import com.mexc.admin.core.service.banner.IBannerService;
import com.mexc.common.util.R;
import com.mexc.dao.model.banner.MexcBanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * Created by huangxinguang on 2018/1/25 下午4:10.
 */
@Controller
@RequestMapping("/banner")
public class BannerController extends WebController {

    @Autowired
    private IBannerService bannerService;

    @RequestMapping("/list")
    public ModelAndView list(Integer currentPage,Integer showCount,String searchKey) {
        ModelAndView modelAndView = getModelAndView();
        Page<MexcBanner> page = bannerService.queryBannerPage(currentPage,showCount,searchKey);
        modelAndView.addObject("page",page);
        modelAndView.setViewName("/banner/list");
        return modelAndView;
    }

    @RequestMapping("/add")
    public ModelAndView add() {
        ModelAndView modelAndView = getModelAndView();
        modelAndView.setViewName("/banner/add");
        return modelAndView;
    }

    @RequestMapping("/edit")
    public ModelAndView edit(String id) {
        ModelAndView modelAndView = getModelAndView();
        MexcBanner banner = bannerService.queryBanner(id);
        modelAndView.addObject("banner",banner);
        modelAndView.setViewName("/banner/edit");
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/saveOrUpdate")
    public R saveOrUpdate(MexcBanner banner,@RequestParam("file") MultipartFile file) {
        if (file != null && file.getSize() > 0) {
            String fileId = this.uploadFile(getSessionUser(), file);
            banner.setImageUrl(fileId);
        }
        if(StringUtil.isEmpty(banner.getId())) {
            banner.setCreateBy(getSessionUser().getId().toString());
            banner.setCreateByName(getSessionUser().getAdminName());
            banner.setCreateTime(new Date());
        }else {
            banner.setUpdateBy(getSessionUser().getId().toString());
            banner.setUpdateByName(getSessionUser().getAdminName());
            banner.setUpdateTime(new Date());
        }
        bannerService.saveOrUpdate(banner);
        return R.ok();
    }

    @ResponseBody
    @RequestMapping("/delete")
    public R delete(String id) {
        bannerService.delete(id);
        return R.ok();
    }

}
