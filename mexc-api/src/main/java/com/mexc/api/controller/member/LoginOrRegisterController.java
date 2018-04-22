package com.mexc.api.controller.member;

import com.alibaba.fastjson.JSON;
import com.mexc.api.constant.ResultCode;
import com.mexc.api.service.IUserService;
import com.mexc.api.vo.BaseResponse;
import com.mexc.api.vo.R;
import com.mexc.api.vo.member.AuthCodeRequest;
import com.mexc.api.vo.member.LoginRequest;
import com.mexc.api.vo.member.LoginResponse;
import com.mexc.api.vo.member.RegisterRequest;
import com.mexc.common.base.BaseController;
import com.mexc.common.util.IPUtils;
import com.mexc.common.util.WebUtil;
import com.mexc.common.util.jedis.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by huangxinguang on 2018/2/6 下午5:20.
 */
@Controller
@RequestMapping("/api")
public class LoginOrRegisterController extends BaseController {

    @Autowired
    private IUserService userService;

    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Object login(LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        loginRequest.setIp(IPUtils.getIpAddr(request));
        String u_id = WebUtil.getCookie(request, "u_id");
        if (StringUtils.isNotEmpty(u_id) && StringUtils.isNotEmpty(RedisUtil.get(u_id))) {
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setResultCode(ResultCode.MEXC_API_00001.getCode());
            loginResponse.setMessage(ResultCode.MEXC_API_00001.getMsg());
            return loginResponse;
        }

        LoginResponse loginResponse = userService.login(loginRequest);
        if(loginResponse.getResultCode() == 0) {
            String token = WebUtil.addLoginToken(request, response, loginResponse.getUserId());
            RedisUtil.set(token, JSON.toJSONString(loginResponse));
        }

        return R.ok().put("data",loginResponse);
    }

    @ResponseBody
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public Object register(RegisterRequest registerRequest) {
        BaseResponse registerResponse = userService.register(registerRequest);
        return R.ok().put("data",registerResponse);
    }

    @ResponseBody
    @RequestMapping(value = "/authCode",method = RequestMethod.POST)
    public Object authCode(AuthCodeRequest authCodeRequest) {
        BaseResponse authCodeResponse = userService.sendAuthCode(authCodeRequest);
        return R.ok().put("data",authCodeResponse);
    }

}
