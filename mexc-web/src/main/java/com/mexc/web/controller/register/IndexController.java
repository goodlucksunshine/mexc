package com.mexc.web.controller.register;

import com.mexc.common.base.BaseController;
import com.mexc.common.util.R;
import com.mexc.dao.model.banner.MexcBanner;
import com.mexc.web.core.service.banner.IBannerService;
import com.mexc.web.utils.ZenDeskUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.zendesk.client.v2.model.hc.Article;

import java.util.List;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 17/12/11
 * Time: 上午10:05
 */
@Controller
public class IndexController extends BaseController {

    @Autowired
    private IBannerService bannerService;

    @RequestMapping("/index")
    public ModelAndView index() {
        logger.info("request index");
        ModelAndView modelAndView = getModelAndView();
        List<MexcBanner> bannerList = bannerService.queryBannerLimit(5);
        modelAndView.addObject("bannerList", bannerList);
        modelAndView.setViewName("/index");
        logger.info("request index end");
        return modelAndView;
    }


    @ResponseBody
    @RequestMapping("/notice")
    public R notice() {
        List<Article> articles = null;
        try {
            articles = ZenDeskUtil.getInstall().getArticle();
        } catch (Exception e) {
            logger.info("公告加载异常", e);
        }
        return R.ok(articles);
    }


}
