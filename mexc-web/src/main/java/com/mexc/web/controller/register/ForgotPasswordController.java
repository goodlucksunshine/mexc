package com.mexc.web.controller.register;

import com.laile.esf.common.annotation.validator.Validator;
import com.mexc.common.base.BaseController;
import com.mexc.member.dto.request.CheckEmailRequest;
import com.mexc.member.dto.request.EmailAuthRequest;
import com.mexc.member.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by huangxinguang on 2018/3/7 上午9:50.
 * 找回密码
 */
@Controller
public class ForgotPasswordController extends BaseController {

    @Autowired
    private IUserService userService;

    /**
     * 跳转找回密码页面
     * @return
     */
    @RequestMapping("/findPasswordPage")
    public ModelAndView findPasswordPage() {
        ModelAndView modelAndView = getModelAndView();
        modelAndView.setViewName("/findPassword");
        return modelAndView;
    }

    /**
     * 重置密码页面
     * @param checkEmailRequest
     * @return
     */
    @RequestMapping("/resetPasswordPage")
    public ModelAndView resetPasswordPage(CheckEmailRequest checkEmailRequest) {
        Validator.valid(checkEmailRequest);
        ModelAndView modelAndView = getModelAndView();
        modelAndView.addObject("account",checkEmailRequest.getAccount());
        userService.sendEmailAuthCode(checkEmailRequest.getAccount());
        modelAndView.setViewName("/resetPassword");
        return modelAndView;
    }

    /**
     * 检查邮箱
     * @param checkEmailRequest
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkEmail")
    public Object checkEmail(CheckEmailRequest checkEmailRequest) {
        Validator.valid(checkEmailRequest);
        return userService.checkEmail(checkEmailRequest);
    }

    /**
     * 充值新密码
     * @param emailAuthRequest
     * @return
     */
    @ResponseBody
    @RequestMapping("/resetPassword")
    public Object resetPassword(EmailAuthRequest emailAuthRequest) {
        Validator.valid(emailAuthRequest);
        return userService.resetPassword(emailAuthRequest);
    }


}
