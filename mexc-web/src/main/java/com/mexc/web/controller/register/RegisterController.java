package com.mexc.web.controller.register;

import com.laile.esf.common.annotation.validator.Validator;
import com.mexc.common.BusCode;
import com.mexc.common.base.BaseController;
import com.mexc.member.dto.request.FinishRegisterRequest;
import com.mexc.member.dto.request.RegSendMailRequest;
import com.mexc.member.dto.request.UserRegisterRequest;
import com.mexc.member.dto.response.FinishRegisterResponse;
import com.mexc.member.dto.response.RegSendMailResponse;
import com.mexc.member.dto.response.UserRegisterResponse;
import com.mexc.member.user.IUserService;
import com.mexc.web.constant.WebConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 17/12/11
 * Time: 下午6:10
 */
@Controller
public class RegisterController extends BaseController {

    @Resource
    IUserService userService;

    @ResponseBody
    @RequestMapping("/register")
    public Object register(UserRegisterRequest registerRequest) {
        Validator.valid(registerRequest);
        String captchaCode = (String)getRequest().getSession().getAttribute(WebConstant.CAPTCHA_CODE);
        if(!registerRequest.getRegCaptchaCode().equalsIgnoreCase(captchaCode)) {
            UserRegisterResponse registerResponse = new UserRegisterResponse();
            registerResponse.setAccount(registerRequest.getRegAccount());
            registerResponse.setCode(BusCode.MEXC_00008.getCode());
            registerResponse.setMsg(BusCode.MEXC_00008.getMsg());
            return registerResponse;
        }
        UserRegisterResponse registerResponse = userService.register(registerRequest);
        return registerResponse;
    }

    @RequestMapping("/to_reg")
    public ModelAndView to_reg() {
        ModelAndView modelAndView = getModelAndView();
        modelAndView.setViewName("/register");
        return modelAndView;
    }


    @ResponseBody
    @RequestMapping("/sendMail")
    public Object sendMail(RegSendMailRequest mailRequest) {
        RegSendMailResponse sendMailResponse = userService.reSendRegEmail(mailRequest);
        return sendMailResponse;
    }


    @RequestMapping("/rgvalid")
    public ModelAndView rgvalid(FinishRegisterRequest finishRegisterRequest) {
        ModelAndView modelAndView = getModelAndView();
        FinishRegisterResponse finishRegisterResponse = userService.finishRegister(finishRegisterRequest);
        modelAndView.addObject("res", finishRegisterResponse);
        modelAndView.setViewName("/regsuccess");
        return modelAndView;
    }
}
