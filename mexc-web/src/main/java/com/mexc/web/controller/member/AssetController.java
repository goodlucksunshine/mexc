package com.mexc.web.controller.member;

import com.mexc.common.BusCode;
import com.mexc.common.util.R;
import com.mexc.common.util.ThressDescUtil;
import com.mexc.dao.dto.asset.AssetQueryDto;
import com.mexc.dao.model.member.MexcMember;
import com.mexc.dao.model.wallet.MexcAssetCash;
import com.mexc.dao.model.wallet.MexcAssetCashVcoin;
import com.mexc.dao.model.wallet.MexcAssetRecharge;
import com.mexc.member.dto.common.LoginUserDto;
import com.mexc.member.user.IUserService;
import com.mexc.vcoin.bitcoin.BitServerUtil;
import com.mexc.web.controller.MemberController;
import com.mexc.web.core.model.request.MemberAssetCashRequest;
import com.mexc.web.core.model.response.MemberAssetCashResponse;
import com.mexc.web.core.service.asset.IAssetService;
import com.mexc.web.core.service.member.IMemberService;
import com.mexc.web.core.service.order.IOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 17/12/14
 * Time: 下午5:58
 */
@RequestMapping("/member/asset")
@Controller
public class AssetController extends MemberController {
    @Resource
    IAssetService assetService;
    @Resource
    IMemberService memberService;
    @Resource
    IOrderService orderService;
    @Resource
    IUserService userService;

    @RequestMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = getModelAndView();
        LoginUserDto sessionUser = getSessionUser();
        AssetQueryDto dto = new AssetQueryDto();
        dto.setAccount(sessionUser.getAccount());
        modelAndView.addObject("assetList", assetService.queryMemberAsset(dto));
        modelAndView.setViewName("/asset");
        return modelAndView;
    }

    /**
     * 历史充值
     * @return
     */
    @RequestMapping("/recharge")
    public ModelAndView recharge() {
        LoginUserDto loginUserDto = getSessionUser();
        ModelAndView modelAndView = getModelAndView();
        MexcMember member = userService.queryMemberByAccount(loginUserDto.getAccount());
        List<MexcAssetRecharge> recharge = orderService.queryAssetRechargeList(member.getId());
        modelAndView.addObject("recharge",recharge);
        modelAndView.setViewName("/recharge");
        return modelAndView;
    }

    /**
     * 提现记录
     * @return
     */
    @RequestMapping("/cash")
    public ModelAndView cash() {
        LoginUserDto loginUserDto = getSessionUser();
        ModelAndView modelAndView = getModelAndView();
        MexcMember member = userService.queryMemberByAccount(loginUserDto.getAccount());
        List<MexcAssetCashVcoin> cash = orderService.queryAssetCashVcoinList(member.getId());
        modelAndView.addObject("cash",cash);
        modelAndView.setViewName("/cash");

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/googleCheck")
    public MemberAssetCashResponse googleCheck(String code) {
        MemberAssetCashResponse response = new MemberAssetCashResponse();
        getRequest().getSession().setAttribute("googleAuth", true);
        response.setCode(BusCode.MEXC_00000.getCode());
        response.setCashCheck(assetService.googleCodeCheck(getSessionUser().getAccount(), code));
        return response;
    }


    @ResponseBody
    @RequestMapping("/assetCash")
    public Object assetCash(MemberAssetCashRequest cashRequest) {
        MexcMember mexcMember = memberService.queryMemberByAccount(getSessionUser().getAccount());
        if (mexcMember.getSecondAuthType() != null && mexcMember.getSecondAuthType() == 2) {
            Boolean auth = (Boolean) getRequest().getSession().getAttribute("googleAuth");
            if (auth == null) {
                MemberAssetCashResponse response = new MemberAssetCashResponse();
                response.setCode(BusCode.MEXC_99999.getCode());
                response.setMsg("请先进行谷歌认证");
                return response;
            }
        }
        cashRequest.setAccount(getSessionUser().getAccount());
        MemberAssetCashResponse response = assetService.memberAssetCash(cashRequest);
        return response;
    }


    @ResponseBody
    @RequestMapping("/markList")
    public Object markList(String assetId) {
        String account = getSessionUser().getAccount();
        return R.ok(assetService.queryMarkList(account, assetId));
    }

    @ResponseBody
    @RequestMapping("/cashAmountCheck")
    public Object cashAmountCheck(MemberAssetCashRequest cashRequest) {
        return assetService.assetCashCheck(cashRequest.getAssetId(), cashRequest.getCashValue());
    }


}
