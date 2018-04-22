package com.mexc.api.controller.member;

import com.mexc.api.controller.WebController;
import com.mexc.api.service.*;
import com.mexc.api.vo.BaseResponse;
import com.mexc.api.vo.R;
import com.mexc.api.vo.asset.*;
import com.mexc.api.vo.member.*;
import com.mexc.api.vo.trade.CollectRequest;
import com.mexc.common.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by huangxinguang on 2018/2/1 下午2:11.
 */
@Controller
@RequestMapping("/api/user")
public class MemberController extends WebController {

    @Autowired
    private IUserService userService;
    @Autowired
    private ITradeGroupService tradeGroupService;
    @Autowired
    private IAssetService assetService;

    @ResponseBody
    @RequestMapping(value = "/resetPwd",method = RequestMethod.POST)
    public Object resetPwd(ResetPwdRequest resetPwdRequest) {
        BaseResponse resetPwdResponse = userService.resetPwd(resetPwdRequest);
        return R.ok().put("data",resetPwdResponse);
    }

    @ResponseBody
    @RequestMapping(value = "/changePwd",method = RequestMethod.POST)
    public Object changePwd(ChangePwdRequest changePwdRequest) {
        changePwdRequest.setUserId(getSessionUser().getUserId());
        BaseResponse changePwdResponse = userService.changePwd(changePwdRequest);
        return R.ok().put("data",changePwdResponse);
    }

    @ResponseBody
    @RequestMapping(value = "/getUserInfo",method = RequestMethod.POST)
    public Object getUserInfo(UserInfoRequest userInfoRequest) {
        userInfoRequest.setUserId(userInfoRequest.getUserId());
        UserInfoResponse userInfoResponse = userService.getUserInfo(userInfoRequest);
        return R.ok().put("data",userInfoResponse);
    }

    @ResponseBody
    @RequestMapping(value = "/addOrCancelCollect",method = RequestMethod.POST)
    public Object addOrCancelCollect(CollectRequest collectRequest) {
        collectRequest.setUserId(getSessionUser().getUserId());
        BaseResponse collectResponse = tradeGroupService.addOrCancelCollect(collectRequest);
        return R.ok().put("data",collectResponse);
    }

    @ResponseBody
    @RequestMapping(value = "/cashRecord",method = RequestMethod.POST)
    public Object cashRecord(CashOrRechargeRequest cashRequest) {
        cashRequest.setUserId(getSessionUser().getUserId());
        CashResponse cashResponse = assetService.cashRecord(cashRequest);
        return R.ok().put("data",cashResponse);
    }

    @ResponseBody
    @RequestMapping(value = "/rechargeRecord",method = RequestMethod.POST)
    public Object rechargeRecord(CashOrRechargeRequest rechargeRequest) {
        rechargeRequest.setUserId(getSessionUser().getUserId());
        RechargeResponse rechargeResponse = assetService.rechargeRecord(rechargeRequest);
        return R.ok().put("data",rechargeResponse);
    }

    @ResponseBody
    @RequestMapping(value = "/rechargeRecord",method = RequestMethod.POST)
    public Object getRechargeAddress(RechargeAddressRequest rechargeAddressRequest) {
        rechargeAddressRequest.setUserId(getSessionUser().getUserId());
        RechargeAddressResponse rechargeAddressResponse = assetService.getRechargeAddress(rechargeAddressRequest);
        return R.ok().put("data",rechargeAddressResponse);
    }

    @ResponseBody
    @RequestMapping(value = "/cash",method = RequestMethod.POST)
    public Object cash(CashRequest cashRequest) {
        cashRequest.setUserId(getSessionUser().getUserId());
        BaseResponse cashResponse = assetService.cash(cashRequest);
        return R.ok().put("data",cashResponse);
    }

}
