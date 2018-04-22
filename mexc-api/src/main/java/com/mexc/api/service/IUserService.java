package com.mexc.api.service;

import com.mexc.api.vo.BaseResponse;
import com.mexc.api.vo.member.*;

/**
 * Created by huangxinguang on 2018/2/1 下午4:49.
 */
public interface IUserService {
    /** 登陆*/
    LoginResponse login(LoginRequest request);
    /** 发送邮箱验证码*/
    BaseResponse sendAuthCode(AuthCodeRequest request);
    /** 注册*/
    BaseResponse register(RegisterRequest registerRequest);
    /** 重置密码*/
    BaseResponse resetPwd(ResetPwdRequest resetPwdRequest);
    /** 修改密码*/
    BaseResponse changePwd(ChangePwdRequest changePwdRequest);
    /** 获取用户信息*/
    UserInfoResponse getUserInfo(UserInfoRequest userInfoRequest);
}
