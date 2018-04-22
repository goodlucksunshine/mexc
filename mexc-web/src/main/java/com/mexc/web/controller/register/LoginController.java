package com.mexc.web.controller.register;

import com.alibaba.fastjson.JSON;
import com.mexc.common.BusCode;
import com.mexc.common.base.BaseController;
import com.mexc.common.util.IPUtils;
import com.mexc.common.util.R;
import com.mexc.common.util.SensitiveInfoUtils;
import com.mexc.common.util.jedis.RedisUtil;
import com.mexc.member.dto.request.LoginRequest;
import com.mexc.member.dto.response.LoginResponse;
import com.mexc.member.dto.response.UserRegisterResponse;
import com.mexc.member.user.IUserService;
import com.mexc.web.constant.WebConstant;
import com.mexc.web.core.service.asset.IAssetService;
import com.mexc.web.utils.WebUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 17/12/10
 * Time: 下午3:52
 */
@Controller
public class LoginController extends BaseController {

    @Resource
    IUserService userService;
    @Resource
    IAssetService assetService;

    @ResponseBody
    @RequestMapping("/login")
    public Object login(LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        loginRequest.setIp(IPUtils.getIpAddr(request));
        String u_id = WebUtil.getCookie(request, "u_id");
        if (StringUtils.isNotEmpty(u_id) && StringUtils.isNotEmpty(RedisUtil.get(u_id))) {
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setCode(BusCode.MEXC_00006.getCode());
            loginResponse.setMsg(BusCode.MEXC_00006.getMsg());
            return loginResponse;
        }
        logger.info("用户登录开始:{}", loginRequest);
        //校验验证码
        String captchaCode = (String)getRequest().getSession().getAttribute(WebConstant.CAPTCHA_CODE);
        if(!loginRequest.getCaptchaCode().equalsIgnoreCase(captchaCode)) {
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setAccount(loginRequest.getAccount());
            loginResponse.setCode(BusCode.MEXC_00008.getCode());
            loginResponse.setMsg(BusCode.MEXC_00008.getMsg());
            return loginResponse;
        }

        LoginResponse loginResponse = userService.login(loginRequest);
        //如果用户状态正确
        if ("1".equalsIgnoreCase(loginResponse.getStatus())) {
            getRequest().getSession().setAttribute("loginInfo", loginResponse);
            //判断二次认证类型
            if (loginResponse.getSecondAuthType() == 0) {
                //如果没有直接登录 ,有 则放一个session变量 证明用户登陆过
                String token = WebUtil.addLoginToken(request, response, loginResponse.getAccount());
                WebUtil.addCookie(request, response, "account", SensitiveInfoUtils.email(loginResponse.getAccount()));
                WebUtil.addCookie(request, response, "btcValue", loginResponse.getBtcValue());//btc估值
                WebUtil.addCookie(request, response, "usdValue", loginResponse.getUsdValue());//美元估值
                RedisUtil.set(token, JSON.toJSONString(loginResponse));
            } else {
                //有二次认证则存放一个标志
                getRequest().getSession().setAttribute("checkAccount", loginRequest.getAccount());
                getRequest().getSession().setAttribute("googleLoginAuth", true);
            }
        }

        logger.info("用户登录结束:{}", loginResponse);
        return loginResponse;
    }

    @ResponseBody
    @RequestMapping("/login/google_auth")
    public R googleAuth(String code, HttpServletRequest request, HttpServletResponse response) {
        Boolean googleCheck = (Boolean) getRequest().getSession().getAttribute("googleLoginAuth");
        String checkAccount = (String) getRequest().getSession().getAttribute("checkAccount");
        if (googleCheck != null && googleCheck) {
            boolean checkResult = userService.checkGoogleCode(checkAccount, Long.parseLong(code));
            if (checkResult) {
                String token = WebUtil.addLoginToken(request, response, checkAccount);
                WebUtil.addCookie(request, response, "account", SensitiveInfoUtils.email(checkAccount));
                LoginResponse loginResponse = (LoginResponse) getRequest().getSession().getAttribute("loginInfo");
                RedisUtil.set(token, JSON.toJSONString(loginResponse));
                return R.ok();
            } else {
                return R.error("验证失败");
            }
        } else {
            return R.error("未知的验证流程");
        }
    }


    @RequestMapping("/login/check")
    public ModelAndView tocheck() {
        ModelAndView modelAndView = getModelAndView();
        Boolean googleCheck = (Boolean) getRequest().getSession().getAttribute("googleLoginAuth");
        if (googleCheck == null) {
            modelAndView.setViewName("/404");
        } else {
            modelAndView.setViewName("/googlecheck");
        }
        return modelAndView;
    }


    @RequestMapping("/to_login")
    public ModelAndView toLogin() {
        ModelAndView modelAndView = getModelAndView();
        modelAndView.setViewName("/login");
        return modelAndView;
    }
}
