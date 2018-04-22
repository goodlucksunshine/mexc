package com.mexc.web.controller.order;

import com.laile.esf.common.annotation.validator.Validator;
import com.mexc.dao.dto.order.CancelEntrustTradeDto;
import com.mexc.dao.dto.order.OrderDto;
import com.mexc.dao.dto.order.OrderQueryDto;
import com.mexc.dao.model.member.MexcAssetTrans;
import com.mexc.dao.model.member.MexcMember;
import com.mexc.dao.model.wallet.MexcAssetCash;
import com.mexc.dao.model.wallet.MexcAssetRecharge;
import com.mexc.dao.vo.order.HistoryTradeOrderVo;
import com.mexc.member.dto.common.LoginUserDto;
import com.mexc.member.user.IUserService;
import com.mexc.web.controller.MemberController;
import com.mexc.web.core.service.order.IOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 17/12/16
 * Time: 下午3:18
 */
@RequestMapping("/member/order")
@Controller
public class OrderController extends MemberController {

    @Resource
    IOrderService orderService;

    @RequestMapping("/list")
    public ModelAndView queryOrder() {
        LoginUserDto loginUserDto = getSessionUser();
        ModelAndView modelAndView = getModelAndView();
        OrderQueryDto current = new OrderQueryDto();
        current.setAccount(loginUserDto.getAccount());
        current.setStatus("1,3");
        List<OrderDto> currentOrder = orderService.queryEntrustOrder(current);
        modelAndView.addObject("current", currentOrder);
        modelAndView.setViewName("/order");
        return modelAndView;
    }

    /**
     * 当前委托
     * @return
     */
    @RequestMapping("/currentEntrust")
    public ModelAndView currentEntrust() {
        LoginUserDto loginUserDto = getSessionUser();
        ModelAndView modelAndView = getModelAndView();
        OrderQueryDto current = new OrderQueryDto();
        current.setAccount(loginUserDto.getAccount());
        current.setStatus("1,3");
        List<OrderDto> currentOrder = orderService.queryEntrustOrder(current);
        modelAndView.addObject("currentEntrust",currentOrder);
        modelAndView.setViewName("/currentEntrustOrder");
        return modelAndView;
    }

    /**
     * 历史委托
     * @return
     */
    @RequestMapping("/historyEntrust")
    public ModelAndView historyEntrust() {
        LoginUserDto loginUserDto = getSessionUser();
        ModelAndView modelAndView = getModelAndView();
        OrderQueryDto history = new OrderQueryDto();
        history.setAccount(loginUserDto.getAccount());
        history.setStatus("2,4,5");
        List<OrderDto> historyEntrustOrder = orderService.queryEntrustOrder(history);
        modelAndView.addObject("historyEntrustOrder",historyEntrustOrder);
        modelAndView.setViewName("/historyEntrustOrder");
        return modelAndView;
    }

    /**
     * 历史成交
     * @return
     */
    @RequestMapping("/historyTrade")
    public ModelAndView historyTrade() {
        LoginUserDto loginUserDto = getSessionUser();
        ModelAndView modelAndView = getModelAndView();
        OrderQueryDto queryDto = new OrderQueryDto();
        queryDto.setAccount(loginUserDto.getAccount());
        queryDto.setStatus("1");
        List<HistoryTradeOrderVo> historyTrade = orderService.queryTradeOrder(queryDto);
        modelAndView.addObject("historyTrade",historyTrade);
        modelAndView.setViewName("/historyTradeOrder");
        return modelAndView;
    }
}
