package com.mexc.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.laile.esf.common.util.DateUtil;
import com.laile.esf.common.util.RandomUtil;
import com.mexc.api.service.IOrderService;
import com.mexc.api.vo.BaseResponse;
import com.mexc.api.vo.order.*;
import com.mexc.common.constant.CommonConstant;
import com.mexc.common.util.LogUtil;
import com.mexc.dao.delegate.market.MarketDelegate;
import com.mexc.dao.delegate.market.MarketVCoinDelegate;
import com.mexc.dao.delegate.member.MemberAssetDelegate;
import com.mexc.dao.delegate.member.MemberDelegate;
import com.mexc.dao.delegate.vcoin.MexcEnTrustDelegate;
import com.mexc.dao.delegate.vcoin.MexcTradeDelegate;
import com.mexc.dao.delegate.vcoin.MexcTradeDetailDelegate;
import com.mexc.dao.delegate.vcoin.VCoinDelegate;
import com.mexc.dao.dto.order.Match.MatchOrder;
import com.mexc.dao.dto.order.OrderDto;
import com.mexc.dao.dto.order.OrderQueryDto;
import com.mexc.dao.javaenum.EntrustOrderEnum;
import com.mexc.dao.model.market.MexcMarket;
import com.mexc.dao.model.market.MexcMarketVCoin;
import com.mexc.dao.model.member.MexcMember;
import com.mexc.dao.model.member.MexcMemberAsset;
import com.mexc.dao.model.vcoin.MexcEnTrust;
import com.mexc.dao.model.vcoin.MexcTradeDetail;
import com.mexc.dao.model.vcoin.MexcVCoin;
import com.mexc.match.engine.service.impl.ExchangeMatchServiceImpl;
import com.mexc.match.engine.util.QueueKeyUtil;
import com.mexc.mq.core.IMqProducerService;
import com.mexc.mq.util.FastJsonMessageConverter;
import com.mexc.mq.vo.MqMsgVo;
import com.mexc.vcoin.TokenEnum;
import com.mexc.web.core.service.order.impl.UserEntrustOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by huangxinguang on 2018/2/6 上午11:36.
 * 订单
 */
@Service
public class OrderService implements IOrderService {
    private static Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private MemberDelegate memberDelegate;

    @Autowired
    private MexcTradeDelegate mexcTradeDelegate;

    @Autowired
    private MexcEnTrustDelegate mexcEnTrustDelegate;

    @Autowired
    private MarketVCoinDelegate marketVCoinDelegate;

    @Autowired
    private MarketDelegate marketDelegate;

    @Autowired
    private VCoinDelegate vCoinDelegate;

    @Autowired
    private MemberAssetDelegate memberAssetDelegate;

    @Value("${mq.queue.btc.market.trade}")
    private String btcQueue;

    @Value("${mq.queue.eth.market.trade}")
    private String ethQueue;

    @Resource
    private IMqProducerService mqProducerService;

    @Resource
    private FastJsonMessageConverter fastJsonMessageConverter;

    @Autowired
    private UserEntrustOrderService userEntrustOrderService;

    @Autowired
    private MexcTradeDetailDelegate mexcTradeDetailDelegate;

    public DealHisResponse getDealHis(DealHisRequest dealHisRequest) {
        DealHisResponse response = new DealHisResponse();
        try {

            /**查询历史成交 并组装结果*/
            OrderQueryDto queryDto = new OrderQueryDto();
            queryDto.setMemberId(dealHisRequest.getUserId());
            queryDto.setStatus("1");
            /*List<OrderDto> orderList = mexcTradeDelegate.queryTradeOrder(queryDto);
            List<DealHisInfo> dealList = new ArrayList<>();
            for (OrderDto order : orderList) {
                DealHisInfo dealHisInfo = new DealHisInfo();
                dealHisInfo.setBargainName(order.getMarketName() + "/" + order.getTradelVcoinNameEn());
                dealHisInfo.setDealCount(order.getTradedNumber());
                dealHisInfo.setDealPrice(order.getTradePrice());
                dealHisInfo.setDealFee(order.getTradeFee());
                dealHisInfo.setDealStatus(Integer.valueOf(order.getTradeType()));
                dealHisInfo.setDealTime(DateUtil.parse(order.getCreateTime()));
                dealHisInfo.setTurnoverMoney(order.getTradeAmount());
                //查询交易对
                MexcMarketVCoin tradeGroup = marketVCoinDelegate.queryMarketVCoin(order.getMarketId(), order.getTradeVcoinId());
                dealHisInfo.setBargainId(tradeGroup.getId());
                dealList.add(dealHisInfo);
            }*/

            //response.setResultList(dealList);
            response.setResultCode(0);
            response.setMessage("查询成功");
        }catch (Exception e) {
            response.setResultCode(com.mexc.api.constant.ResultCode.MEXC_API_99999.getCode());
            response.setMessage(com.mexc.api.constant.ResultCode.MEXC_API_99999.getMsg());
            logger.error("查询异常",e);
            return response;
        }
        return response;
    }

    public EntrustHisResponse getEntrustHis(EntrustHisRequest entrustRequest) {
        EntrustHisResponse response = new EntrustHisResponse();
        try {

            OrderQueryDto queryDto = new OrderQueryDto();
            queryDto.setMemberId(entrustRequest.getUserId());
            queryDto.setStatus("2,4,5");
            List<OrderDto> entrustOrderList = mexcEnTrustDelegate.queryOrderList(queryDto);
            List<EntrustInfo> entrustInfoList = new ArrayList<>();
            for (OrderDto order : entrustOrderList) {
                EntrustInfo entrustInfo = new EntrustInfo();
                entrustInfo.setBargainName(order.getMarketName() + "/" + order.getTradelVcoinNameEn());
                entrustInfo.setDealCount(order.getTradedNumber());
                entrustInfo.setDealPrice(order.getTradePrice());
                entrustInfo.setDealStatus(Integer.valueOf(order.getTradeType()));
                entrustInfo.setDealTime(DateUtil.parse(order.getCreateTime()));
                MexcMarketVCoin tradeGroup = marketVCoinDelegate.queryMarketVCoin(order.getMarketId(), order.getTradeVcoinId());
                entrustInfo.setBargainId(tradeGroup.getId());
                /**计算成交率*/
                BigDecimal turnoverRate = new BigDecimal(order.getTradeNumber())
                        .divide(new BigDecimal(order.getTradedNumber()), 2, BigDecimal.ROUND_HALF_UP);
                entrustInfo.setTurnoverRate(turnoverRate.toPlainString());
                entrustInfoList.add(entrustInfo);
            }

            response.setResultList(entrustInfoList);
            response.setResultCode(0);
            response.setMessage("查询成功");
        }catch (Exception e) {
            response.setResultCode(com.mexc.api.constant.ResultCode.MEXC_API_99999.getCode());
            response.setMessage(com.mexc.api.constant.ResultCode.MEXC_API_99999.getMsg());
            logger.error("查询历史委托异常",e);
            return response;
        }

        return response;
    }

    /**
     * 委托交易
     * @param entrustRequest
     * @return
     */
    public BaseResponse doEntrust(EntrustRequest entrustRequest) {
        BaseResponse response = new BaseResponse();
        try {

            /**校验交易对*/
            MexcMarketVCoin tradeGroup = marketVCoinDelegate.selectByPrimaryKey(entrustRequest.getBargainId());
            if (tradeGroup == null) {
                logger.info(LogUtil.msg("OrderService:doEntrust", "bargainId[" + entrustRequest.getBargainId() + "]交易对不存在"));
                response.setResultCode(2);
                response.setMessage("交易对不存在");
                return response;
            }

            /**校验用户*/
            MexcMember member = memberDelegate.selectByPrimaryKey(entrustRequest.getUserId());
            if (member == null) {
                logger.info(LogUtil.msg("OrderService:doEntrust", "用户不存在"));
                response.setResultCode(3);
                response.setMessage("用户不存在");
                return response;
            }

            /**查询币种市场 和 交易对资产 */
            MexcMarket market = marketDelegate.selectByPrimaryKey(tradeGroup.getMarketId());
            MexcVCoin vCoin = vCoinDelegate.selectByPrimaryKey(tradeGroup.getVcoinId());
            MexcMemberAsset mainVcoinAsset = memberAssetDelegate.queryMemberAsset(member.getId(), market.getVcoinId());//市场主币资产
            MexcMemberAsset currentVcoinAsset = memberAssetDelegate.queryMemberAsset(member.getId(), vCoin.getId());//当前币交易资产

            /** 校验资产 **/
            if (entrustRequest.getTradeStatus().intValue() == CommonConstant.BUY.intValue()) {
                BigDecimal tradeAmount = new BigDecimal(entrustRequest.getOrderPrice())
                        .multiply(new BigDecimal(entrustRequest.getOrderCount()));

                if (tradeAmount.compareTo(mainVcoinAsset.getBalanceAmount()) > 0) {
                    logger.info("OrderServiceImpl-> entrustTrade：当前买入余额不足");
                    response.setResultCode(4);
                    response.setMessage("当前买入余额不足");
                    return response;
                }
            } else if (entrustRequest.getTradeStatus().intValue() == CommonConstant.SELL.intValue()) {
                if (new BigDecimal(entrustRequest.getOrderCount()).compareTo(currentVcoinAsset.getBalanceAmount()) > 0) {
                    logger.info("OrderServiceImpl->entrustTrade：当前卖出超过余额");
                    response.setResultCode(5);
                    response.setMessage("当前卖出超过余额");
                    return response;
                }
            }

            /**交易金额*/
            BigDecimal tradeAmount = new BigDecimal(entrustRequest.getOrderCount()).multiply(new BigDecimal(entrustRequest.getOrderPrice()));

            /** 组装并生成委托订单并冻结金额 **/
            String entrustTime = DateUtil.format(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS);
            MexcEnTrust mexcEnTrust = new MexcEnTrust();
            mexcEnTrust.setStatus(EntrustOrderEnum.UNCOMPLETED.getStatus());
            mexcEnTrust.setTradeNo(UUID.randomUUID().toString());
            mexcEnTrust.setTradeVcoinId(vCoin.getId());
            mexcEnTrust.setMemberId(member.getId());
            mexcEnTrust.setCreateBy(member.getId());
            mexcEnTrust.setCreateByName(member.getAccount());
            mexcEnTrust.setCreateTime(entrustTime);
            mexcEnTrust.setMarketId(market.getId());
            mexcEnTrust.setMarketName(market.getMarketName());
            mexcEnTrust.setTradelVcoinNameEn(vCoin.getVcoinNameEn());
            mexcEnTrust.setTradeType(entrustRequest.getTradeStatus());
            mexcEnTrust.setTradePrice(new BigDecimal(entrustRequest.getOrderPrice()));
            mexcEnTrust.setTradeNumber(new BigDecimal(entrustRequest.getOrderCount()));
            mexcEnTrust.setTradeRemainNumber(new BigDecimal(entrustRequest.getOrderCount()));
            mexcEnTrust.setTradeTotalAmount(tradeAmount);
            mexcEnTrust.setTradeRemainAmount(tradeAmount);
            mexcEnTrust.setTradeRate(new BigDecimal("0"));
            mexcEnTrust.setTradeFee(new BigDecimal("0"));
            String assetId = "";
            //委托单 不收手续费
            if (entrustRequest.getTradeStatus() == CommonConstant.BUY ) {
                assetId = mainVcoinAsset.getId();
            } else if (entrustRequest.getTradeStatus() == CommonConstant.SELL) {
                assetId = currentVcoinAsset.getId();
            }
            mexcEnTrustDelegate.entrustOrder(mexcEnTrust, assetId);


            /** 组装委托单消息并发送 **/
            MatchOrder entrustOrder = new MatchOrder();
            entrustOrder.setTradeNo(mexcEnTrust.getTradeNo());
            entrustOrder.setEntrustNumber(mexcEnTrust.getTradeNumber());
            entrustOrder.setTradeType(mexcEnTrust.getTradeType());
            entrustOrder.setMarketId(market.getId());
            entrustOrder.setPrice(mexcEnTrust.getTradePrice());
            entrustOrder.setTradedNumber(new BigDecimal("0"));
            //entrustOrder.setTradedPrice(new BigDecimal(0));
            entrustOrder.setTradableNumber(new BigDecimal(entrustRequest.getOrderCount()));
            entrustOrder.setVcoinId(vCoin.getId());
            entrustOrder.setMemberId(member.getId());
            entrustOrder.setEntrustTime(entrustTime);

            MqMsgVo<MatchOrder> mqMsgVo = new MqMsgVo<>();

            mqMsgVo.setMsgId(RandomUtil.randomStr(8))
                    .setContent(entrustOrder)
                    .setInsertTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            logger.info(LogUtil.msg("OrderServiceImpl:entrustTrade", "异步发送委托交易队列，data:" + JSON.toJSONString(mqMsgVo)));

            try {
                if (market.getMarketName().equalsIgnoreCase(TokenEnum.BIT_COIN.getCode())) {
                    mqProducerService.convertAndSend(btcQueue, fastJsonMessageConverter.sendMessage(mqMsgVo));
                } else if (market.getMarketName().equalsIgnoreCase(TokenEnum.ETH_COIN.getCode())) {
                    mqProducerService.convertAndSend(ethQueue, fastJsonMessageConverter.sendMessage(mqMsgVo));
                }
            } catch (Exception e) {
                logger.error(LogUtil.msg("OrderService:doEntrust", "异步发送委托交易队列，data:" + JSON.toJSONString(mqMsgVo)), e);
            }

            /** 添加到当前用户委托缓存列表中 **/
            userEntrustOrderService.addCurrentEntrustOrderCache(mexcEnTrust);

            response.setResultCode(0);
            response.setMessage("委托成功");
        }catch (Exception e) {
            logger.error("委托异常",e);
            response.setResultCode(com.mexc.api.constant.ResultCode.MEXC_API_99999.getCode());
            response.setMessage(com.mexc.api.constant.ResultCode.MEXC_API_99999.getMsg());
            return response;
        }

        return response;
    }

    /**
     * 用户撤单
     * @param entrustCancelRequest
     * @return
     */
    public BaseResponse doCancel(EntrustCancelRequest entrustCancelRequest) {
        BaseResponse response = new BaseResponse();
        try {
            /**校验用户*/
            MexcMember member = memberDelegate.queryMermberByAccount(entrustCancelRequest.getUserId());
            if (member == null) {
                logger.info(LogUtil.msg("OrderService:doCancel", "用户不存在"));
                response.setResultCode(2);
                response.setMessage("用户不存在");
                return response;
            }
            /**校验订单*/
            MexcEnTrust enTrust = mexcEnTrustDelegate.queryEntrust(entrustCancelRequest.getOrderId());
            if (enTrust == null) {
                logger.info(LogUtil.msg("OrderService:doCancel", "订单不存在"));
                response.setResultCode(3);
                response.setMessage("订单不存在");
                return response;
            }

            /** 更新委托状态:全撤 状态改为已撤 、部撤状态改为部分成交 **/
            if (enTrust.getTradeRemainNumber().compareTo(enTrust.getTradeNumber()) < 0) {
                enTrust.setStatus(EntrustOrderEnum.PART_CANCEL.getStatus());
            } else {
                enTrust.setStatus(EntrustOrderEnum.CANCEL.getStatus());
            }
            int rows = mexcEnTrustDelegate.updateEntrustToCancel(enTrust);
            if (rows < 1) {
                logger.error("撤单：修改委托状态失败,有可能状态已发生更改,entrust:{}", JSON.toJSONString(enTrust));
                response.setResultCode(4);
                response.setMessage("修改委托状态失败,请检查委托状态是否已经更改.");
                return response;
            }

            /** 撤单：解冻用户资产**/
            try {
                MexcMarket market = marketDelegate.selectByPrimaryKey(enTrust.getMarketId());
                MexcMemberAsset mainVcoinAsset = memberAssetDelegate.queryMemberAsset(member.getId(), market.getVcoinId());//市场主币资产
                MexcMemberAsset currentVcoinAsset = memberAssetDelegate.queryMemberAsset(member.getId(), enTrust.getTradeVcoinId());//当前币交易资产
                if (enTrust.getTradeType().intValue() == CommonConstant.BUY.intValue()) {//买：解冻主币资产
                    BigDecimal unfrozenAmount = enTrust.getTradeRemainNumber().multiply(enTrust.getTradePrice());
                    memberAssetDelegate.unfrozenAmount(mainVcoinAsset.getId(), unfrozenAmount);
                } else if (enTrust.getTradeType().intValue() == CommonConstant.SELL.intValue()) {//卖：解冻副币资产
                    BigDecimal unfrozenAmount = enTrust.getTradeRemainNumber();
                    memberAssetDelegate.unfrozenAmount(currentVcoinAsset.getId(), unfrozenAmount);
                }
            } catch (Exception e) {
                logger.error("撤单：解冻资产异常", e);
                response.setResultCode(5);
                response.setMessage("解冻资产异常");
                return response;
            }

            try {
                /** 增加撤销明细 **/
                MexcTradeDetail cancelTradeDetail = new MexcTradeDetail();
                cancelTradeDetail.setMarketId(enTrust.getMarketId());
                cancelTradeDetail.setCreateBy(member.getId());
                cancelTradeDetail.setCreateByName(member.getAccount());
                cancelTradeDetail.setCreateTime(new Date());
                cancelTradeDetail.setTradeNumber(enTrust.getTradeRemainNumber());
                cancelTradeDetail.setMemberId(member.getId());
                cancelTradeDetail.setTradePrice(enTrust.getTradePrice());
                cancelTradeDetail.setTransNo(enTrust.getTradeNo());
                cancelTradeDetail.setTradeVcoinId(enTrust.getTradeVcoinId());
                cancelTradeDetail.setTradeTotalAmount(enTrust.getTradeRemainAmount());
                cancelTradeDetail.setTradeNo(enTrust.getTradeNo());
                cancelTradeDetail.setTradeRate(new BigDecimal(0));
                cancelTradeDetail.setTradeFee(new BigDecimal(0));
                cancelTradeDetail.setType(2);
                mexcTradeDetailDelegate.insert(cancelTradeDetail);
                logger.info("撤单：增加的撤单明细 {}", JSON.toJSONString(cancelTradeDetail));

                /**从当前用户委托列表中删除**/
                userEntrustOrderService.deleteCurrentEntrustOrderCache(enTrust);

            } catch (Exception e) {
                logger.error("撤单：更新委托明细删除委托缓存异常,entrust:{}", JSON.toJSONString(enTrust));
                response.setResultCode(6);
                response.setMessage("更新委托明细删除委托缓存异常");
                return response;
            }

            response.setResultCode(0);
            response.setMessage("撤单成功");
        }catch (Exception e) {
            logger.error("撤单异常",e);
            response.setResultCode(com.mexc.api.constant.ResultCode.MEXC_API_99999.getCode());
            response.setMessage(com.mexc.api.constant.ResultCode.MEXC_API_99999.getMsg());
            return response;
        }
        return response;
    }
}
