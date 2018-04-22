package com.mexc.member.user;


import com.mexc.dao.dto.asset.AssetQueryDto;
import com.mexc.dao.model.common.MexcLoginLog;
import com.mexc.dao.model.member.MexcMember;
import com.mexc.dao.model.member.MexcMemberAuth;
import com.mexc.member.dto.request.*;
import com.mexc.member.dto.response.*;

import java.util.List;
import java.util.Map;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 17/12/6
 * Time: 下午4:22
 */
public interface IUserService {

    UserRegisterResponse register(UserRegisterRequest registerRequest);

    LoginResponse login(LoginRequest loginRequest);

    FinishRegisterResponse finishRegister(FinishRegisterRequest registerRequest);


    RegSendMailResponse reSendRegEmail(RegSendMailRequest mailRequest);

    GoogleAuthInfoResponse getGoogleAuthInfo(String account);

    GoogleAuthBindResponse bindGoogleAuth(GoogleAuthRequest googleAuthRequest);

    BaseResponse unbindGoogleAuth(GoogleAuthRequest googleAuthRequest);

    Boolean checkGoogleCode(String account, Long validationCode);

    UserChangePwdResponse changePwd(UserChangePwdRequest changePwdRequest);

    BaseResponse identityAuth(IdentityAuthRequest request);

    MexcMember queryMemberByAccount(String account);

    List<MexcLoginLog> queryRecentLoginLog();

    MexcMemberAuth queryIdentityInfo(String memberId);
    Map<String, String> cashLimit();

    Map<String,String> queryBtcAndUsdValue(AssetQueryDto assetQueryDto);


    /**
     * 发送邮箱验证码
     * @param email
     */
    BaseResponse sendEmailAuthCode(String email);

    /**
     * 校验邮箱
     * @param checkEmailRequest
     * @return
     */
    BaseResponse checkEmail(CheckEmailRequest checkEmailRequest);

    /**
     * @param emailAuthRequest
     */
    EmailAuthResponse resetPassword(EmailAuthRequest emailAuthRequest);


}
