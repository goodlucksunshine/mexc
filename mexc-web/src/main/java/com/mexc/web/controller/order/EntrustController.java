package com.mexc.web.controller.order;

import com.laile.esf.common.annotation.validator.Validator;
import com.mexc.common.util.R;
import com.mexc.dao.dto.order.CancelEntrustTradeDto;
import com.mexc.dao.dto.order.EntrustTradeDto;
import com.mexc.dao.model.market.MexcMarket;
import com.mexc.dao.model.market.MexcMarketVcoinCollect;
import com.mexc.dao.model.member.MexcMember;
import com.mexc.dao.model.vcoin.MexcVCoin;
import com.mexc.member.user.IUserService;
import com.mexc.web.controller.MemberController;
import com.mexc.web.core.service.asset.IAssetService;
import com.mexc.web.core.service.market.IMarketService;
import com.mexc.web.core.service.order.IOrderService;
import com.mexc.web.core.service.vcoin.IVcoinService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
@RequestMapping("/member/trans")
public class EntrustController extends MemberController {

    @Resource
    IOrderService orderService;

    @Resource
    IVcoinService vcoinService;

    @Resource
    IUserService userService;

    /**
     * 提交委托单
     * @param tradeDto
     * @return
     */
    @ResponseBody
    @RequestMapping("/entrustTrade")
    public Object entrustTrade(EntrustTradeDto tradeDto) {
        Validator.valid(tradeDto);
        tradeDto.setAccount(getSessionUser().getAccount());
        orderService.entrustTrade(tradeDto);
        return R.ok();
    }

    /**
     * 手动交易
     * @param tradeDto
     * @return
     */
    @ResponseBody
    @RequestMapping("/handEntrustTrade")
    public Object handEntrustTrade(EntrustTradeDto tradeDto) {
        Validator.valid(tradeDto);
        tradeDto.setAccount(getSessionUser().getAccount());
        orderService.handEntrustTrade(tradeDto);
        return R.ok();
    }

    /**
     * 撤单
     * @return
     */
    @ResponseBody
    @RequestMapping("/cancelEntrustTrade")
    public Object cancelEntrustTrade(CancelEntrustTradeDto cancelEntrustTradeDto) {
        cancelEntrustTradeDto.setAccount(getSessionUser().getAccount());
        Validator.valid(cancelEntrustTradeDto);
        orderService.cancelEntrustTrade(cancelEntrustTradeDto);
        return R.ok();
    }


    /**
     * 添加自选
     * @param marketId
     * @param vcoinId
     * @return
     */
    @ResponseBody
    @RequestMapping("/collect")
    public Object collect(String marketId, String vcoinId) {
        MexcMarketVcoinCollect collect = new MexcMarketVcoinCollect();
        collect.setCreateByName(getSessionUser().getAccount());
        collect.setCreateBy(getSessionUser().getAccount());
        collect.setMarketId(marketId);
        collect.setVcoinId(vcoinId);
        vcoinService.collect(collect);
        return R.ok();
    }

    /**
     * 删除自选
     * @param marketId
     * @param vcoinId
     * @return
     */
    @ResponseBody
    @RequestMapping("/uncollect")
    public Object uncollect(String marketId, String vcoinId) {
        MexcMarketVcoinCollect collect = new MexcMarketVcoinCollect();
        MexcMember member = userService.queryMemberByAccount(getSessionUser().getAccount());
        collect.setCreateBy(member.getId());
        collect.setMarketId(marketId);
        collect.setVcoinId(vcoinId);
        collect.setCreateByName(member.getAccount());
        vcoinService.uncollect(collect);
        return R.ok();
    }
}
