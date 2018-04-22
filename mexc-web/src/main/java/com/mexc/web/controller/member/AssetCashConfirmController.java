package com.mexc.web.controller.member;

import com.laile.esf.common.exception.BusinessException;
import com.mexc.common.base.BaseController;
import com.mexc.web.core.service.asset.IAssetService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2018/1/1
 * Time: 下午2:48
 */
@Controller
public class AssetCashConfirmController extends BaseController {


    @Resource
    IAssetService assetService;

    @RequestMapping("/cash_confirm")
    public ModelAndView cashConfirm(String c_id, String t_k, String s_t, String sign) {
        ModelAndView modelAndView = getModelAndView();
        try {
            assetService.cashConfirm(s_t, t_k, sign, c_id);
            modelAndView.setViewName("/cashConfirmSuccess");
        } catch (BusinessException e) {
            logger.error("提现确认异常", e);
            modelAndView.addObject("msg", e.getMessage());
            modelAndView.setViewName("/cashConfirmFail");
        } catch (Exception e) {
            logger.error("提现确认异常", e);
            modelAndView.setViewName("/cashConfirmFail");
        }
        return modelAndView;
    }
}
