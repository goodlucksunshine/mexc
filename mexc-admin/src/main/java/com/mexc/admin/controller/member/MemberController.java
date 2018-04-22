package com.mexc.admin.controller.member;

import com.laile.esf.common.util.Page;
import com.mexc.admin.controller.WebController;
import com.mexc.admin.core.service.member.IMemberService;
import com.mexc.common.BusCode;
import com.mexc.common.util.R;
import com.mexc.dao.dto.asset.AssetQueryDto;
import com.mexc.dao.dto.asset.RechargeRequest;
import com.mexc.dao.dto.member.MemberQueryDto;
import com.mexc.dao.model.member.MexcMember;
import com.mexc.dao.model.member.MexcMemberAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangxinguang on 2017/11/24 下午2:34.
 */
@Controller
@RequestMapping("/member")
public class MemberController extends WebController {

    @Autowired
    private IMemberService memberService;


    @RequestMapping("/list")
    public ModelAndView list(MemberQueryDto queryDto) {
        ModelAndView modelAndView = getModelAndView();
        Page<MexcMember> page = memberService.queryMemberListPage(queryDto);
        modelAndView.addObject("queryDto",queryDto);
        modelAndView.addObject("page", page);
        modelAndView.setViewName("/member/list");
        return modelAndView;
    }

    @RequestMapping("/view")
    public ModelAndView view(String id) {
        ModelAndView modelAndView = getModelAndView();
        MexcMember member = memberService.selectByPrimaryKey(id);
        modelAndView.addObject("member", member);
        modelAndView.setViewName("/member/view");
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/del")
    public R del(String id) {
        memberService.deleteByPrimaryKey(id);
        return R.ok();
    }

    @ResponseBody
    @RequestMapping("/frozen")
    public R frozen(String id) {
        MexcMember member = new MexcMember();
        member.setId(id);
        member.setStatus(-1);
        memberService.updateByPrimaryKeySelective(member);
        return R.ok();
    }

    @ResponseBody
    @RequestMapping("/unfrozen")
    public R unfrozen(String id) {
        MexcMember member = new MexcMember();
        member.setId(id);
        member.setStatus(1);
        memberService.updateByPrimaryKeySelective(member);
        return R.ok();
    }

    @RequestMapping("/authList")
    public ModelAndView identityAuthPage(Integer currentPage, Integer showCount,Integer status, String searchKey) {
        ModelAndView modelAndView = getModelAndView();
        Page<MexcMemberAuth> authPage = memberService.queryAuthPage(currentPage, showCount,status, searchKey);
        modelAndView.addObject("status",status);
        modelAndView.addObject("page", authPage);
        modelAndView.setViewName("/member/auth/list");
        return modelAndView;
    }

    @RequestMapping("/authDetail")
    public ModelAndView identityAuthDetail(String id) {
        ModelAndView modelAndView = getModelAndView();
        MexcMemberAuth auth = memberService.queryAuth(id);
        modelAndView.addObject("auth", auth);
        modelAndView.setViewName("/member/auth/auth");
        return modelAndView;
    }


    @ResponseBody
    @RequestMapping("/auditAuth")
    public Object identityAudit(String authId, Integer status) {
        HashMap map = new HashMap();
        try {
            int result = memberService.auditAUth(authId, status);
            if (result < 1) {
                map.put("code", BusCode.MEXC_99999.getCode());
                map.put("msg", BusCode.MEXC_99999.getMsg());
            } else {
                map.put("code", BusCode.MEXC_00000.getCode());
                map.put("msg", BusCode.MEXC_00000.getMsg());
            }
            return map;
        } catch (Exception e) {
            logger.error("审核身份认证异常",e);
            map.put("code", BusCode.MEXC_99999.getCode());
            map.put("msg", BusCode.MEXC_99999.getMsg());
            return map;
        }
    }


    @RequestMapping("/assetPage")
    public ModelAndView memberAssetList(AssetQueryDto dto) {
        ModelAndView modelAndView = getModelAndView();
        modelAndView.addObject("dto", dto);
        modelAndView.addObject("page", memberService.queryAssetPage(dto));
        modelAndView.setViewName("/member/asset/list");
        return modelAndView;
    }


    @ResponseBody
    @RequestMapping("/assetRecharge")
    public Object assetRecharge(RechargeRequest dto) {
        Map map = new HashMap();
        boolean result = memberService.assetRecharge(dto);
        if (result) {
            map.put("code", BusCode.MEXC_00000.getCode());
        } else {
            map.put("code", BusCode.MEXC_99999.getCode());
            map.put("msg", "充值失败,请重新尝试");
        }
        return map;
    }


}
