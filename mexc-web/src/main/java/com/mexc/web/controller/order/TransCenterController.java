package com.mexc.web.controller.order;

import com.mexc.common.util.R;
import com.mexc.dao.dto.order.OrderDto;
import com.mexc.dao.dto.order.OrderQueryDto;
import com.mexc.dao.model.market.MexcMarket;
import com.mexc.dao.model.member.MexcMember;
import com.mexc.dao.model.member.MexcMemberAsset;
import com.mexc.dao.model.vcoin.MexcVCoin;
import com.mexc.dao.model.vcoin.MexcVCoinFee;
import com.mexc.dao.vo.vcoin.trade.LatestTradeVo;
import com.mexc.member.dto.common.LoginUserDto;
import com.mexc.member.user.IUserService;
import com.mexc.web.controller.MemberController;
import com.mexc.web.core.service.asset.IAssetService;
import com.mexc.web.core.service.market.IMarketService;
import com.mexc.web.core.service.order.IOrderService;
import com.mexc.web.core.service.trade.ITradeService;
import com.mexc.web.core.service.vcoin.IVcoinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Date: 17/12/16
 * Time: 下午5:06
 */
@RequestMapping("/trans")
@Controller
public class TransCenterController extends MemberController {
    private static final Logger logger = LoggerFactory.getLogger(TransCenterController.class);

    @Resource
    IOrderService orderService;

    @Resource
    IVcoinService vcoinService;

    @Resource
    IMarketService marketService;

    @Resource
    IUserService userService;

    @Resource
    IAssetService assetService;


    @Resource
    ITradeService tradeService;

    @RequestMapping("/center")
    public ModelAndView transCenter(String marketId, String vcoinId) {

        ModelAndView modelAndView = getModelAndView();

        //当前委托，历史委托
        LoginUserDto loginUserDto = getSessionUser();
        MexcVCoin vCoin = vcoinService.queryVCoinById(vcoinId);
        MexcMarket market = marketService.queryMarketById(marketId);
        if (market == null || vCoin == null) {//查询默认交易对
            market = marketService.queryMarketHasVCoin();
            vCoin = vcoinService.queryDefaultVCoin(market.getId());
        }
        //最新交易
        LatestTradeVo latestTrade = tradeService.queryLatestTrade(market.getId(),vCoin.getId());
        if(latestTrade==null){
            latestTrade=new LatestTradeVo();
        }
        modelAndView.addObject("latestTradePrice", latestTrade.getTradePrice());

        if (loginUserDto != null) {
            try {
                OrderQueryDto current = new OrderQueryDto();
                current.setAccount(loginUserDto.getAccount());
                current.setStatus("1,3");
                List<OrderDto> currentOrder = orderService.queryEntrustOrderLimit(current, 5);
                OrderQueryDto history = new OrderQueryDto();
                history.setAccount(loginUserDto.getAccount());
                history.setStatus("2,4,5");
                List<OrderDto> historyOrder = orderService.queryEntrustOrderLimit(history, 5);
                modelAndView.addObject("current", currentOrder);
                modelAndView.addObject("history", historyOrder);
                //查询用户
                MexcMember member = userService.queryMemberByAccount(loginUserDto.getAccount());

                //当前币种资产
                MexcMemberAsset currentVCoinAsset = assetService.queryMemberAsset(member.getId(), vCoin.getId());

                //市场主币资产
                MexcMemberAsset mainVCoinAsset = assetService.queryMemberAsset(member.getId(), market.getVcoinId());
                //手续费
                MexcVCoinFee currentVCoinFee = vcoinService.queryVCoinFee(vCoin.getId());
                MexcVCoinFee mainVCoinFee = vcoinService.queryVCoinFee(market.getVcoinId());
                //查询用户是否收藏此交易对
                boolean collect = vcoinService.checkCollect(market.getId(), vCoin.getId(), member.getId());
                modelAndView.addObject("collect", collect);
                modelAndView.addObject("currentVCoinAsset", currentVCoinAsset);
                modelAndView.addObject("mainVCoinAsset", mainVCoinAsset);
                modelAndView.addObject("currentVCoinFee", currentVCoinFee);
                modelAndView.addObject("mainVCoinFee", mainVCoinFee);
                modelAndView.addObject("login", true);
                modelAndView.addObject("memberId", member.getId());
            }catch (Exception e) {
                e.printStackTrace();
                logger.error("获取交易中心数据异常",e);
            }

        }
        modelAndView.addObject("market", market);
        modelAndView.addObject("vcoin", vCoin);

        modelAndView.setViewName("/transcenter");
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/getCurrentEntrustList")
    public Object getCurrentEntrustList() {
        OrderQueryDto current = new OrderQueryDto();
        current.setAccount(getSessionUser().getAccount());
        current.setStatus("1,3");
        List<OrderDto> currentOrder = orderService.queryEntrustOrderLimit(current, 5);
        return R.ok().put("current",currentOrder);
    }

    @ResponseBody
    @RequestMapping("/getHistoryEntrustList")
    public Object getHistoryEntrustList() {
        OrderQueryDto history = new OrderQueryDto();
        history.setAccount(getSessionUser().getAccount());
        history.setStatus("2,4,5");
        List<OrderDto> historyOrder = orderService.queryEntrustOrderLimit(history, 5);
        return R.ok().put("history",historyOrder);
    }

    /**
     * 获取交易币的资产
     * @param memberId
     * @param vcoinId
     * @return
     */
    @ResponseBody
    @RequestMapping("/getTradeVCoinBalance")
    public Object getTradeVCoinBalance(String memberId,String vcoinId) {
        MexcMemberAsset memberAsset = assetService.queryMemberAsset(memberId, vcoinId);
        return R.ok().put("balance",memberAsset.getBalanceAmount());
    }

    /**
     * 获取市场主币余额
     * @param memberId
     * @param marketId
     * @return
     */
    @ResponseBody
    @RequestMapping("/getMarketVCoinBalance")
    public Object getMarketVCoinBalance(String memberId,String marketId) {
        MexcMarket market = marketService.queryMarketById(marketId);
        MexcMemberAsset memberAsset = assetService.queryMemberAsset(memberId, market.getVcoinId());
        return R.ok().put("balance",memberAsset.getBalanceAmount());
    }

    /**
     * k线图
     *
     * @return
     */
    @RequestMapping("/toTradingViewPage")
    public ModelAndView toTradingViewPage(String marketId, String vcoinId) {
        MexcVCoin vCoin = vcoinService.queryVCoinById(vcoinId);
        MexcMarket market = marketService.queryMarketById(marketId);
        ModelAndView modelAndView = getModelAndView();
        modelAndView.addObject("market", market);
        modelAndView.addObject("vcoin", vCoin);
        modelAndView.setViewName("/ktradeview");
        return modelAndView;
    }
}
