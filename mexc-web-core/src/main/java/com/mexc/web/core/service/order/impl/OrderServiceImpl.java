package com.mexc.web.core.service.order.impl;

import com.alibaba.fastjson.JSON;
import com.laile.esf.common.exception.BusinessException;
import com.laile.esf.common.exception.ResultCode;
import com.laile.esf.common.exception.SystemException;
import com.laile.esf.common.util.DateUtil;
import com.laile.esf.common.util.RandomUtil;
import com.mexc.common.base.ResultVo;
import com.mexc.common.constant.CommonConstant;
import com.mexc.common.constant.ResCode;
import com.mexc.common.constant.TradingViewConstant;
import com.mexc.common.util.LogUtil;
import com.mexc.common.util.UUIDUtil;
import com.mexc.common.util.jedis.RedisUtil;
import com.mexc.dao.delegate.market.MarketDelegate;
import com.mexc.dao.delegate.member.MemberAssetDelegate;
import com.mexc.dao.delegate.member.MemberDelegate;
import com.mexc.dao.delegate.plat.PlatAssetDelegate;
import com.mexc.dao.delegate.plat.PlatAssetDetailDelegate;
import com.mexc.dao.delegate.vcoin.MexcEnTrustDelegate;
import com.mexc.dao.delegate.vcoin.MexcTradeDelegate;
import com.mexc.dao.delegate.vcoin.MexcTradeDetailDelegate;
import com.mexc.dao.delegate.vcoin.VCoinDelegate;
import com.mexc.dao.delegate.wallet.MexcAssetCashDelegate;
import com.mexc.dao.delegate.wallet.MexcAssetRechargeDelegate;
import com.mexc.dao.delegate.wallet.MexcAssetTransDelegate;
import com.mexc.dao.dto.order.CancelEntrustTradeDto;
import com.mexc.dao.dto.order.EntrustTradeDto;
import com.mexc.dao.dto.order.Match.MatchOrder;
import com.mexc.dao.dto.order.OrderDto;
import com.mexc.dao.dto.order.OrderQueryDto;
import com.mexc.dao.dto.tradingview.TradingViewDto;
import com.mexc.dao.dto.vcoin.UpdateEntrustDto;
import com.mexc.dao.javaenum.EntrustOrderEnum;
import com.mexc.dao.javaenum.TradeOrderEnum;
import com.mexc.dao.model.market.MexcMarket;
import com.mexc.dao.model.member.MexcAssetTrans;
import com.mexc.dao.model.member.MexcMember;
import com.mexc.dao.model.member.MexcMemberAsset;
import com.mexc.dao.model.plat.MexcPlatAsset;
import com.mexc.dao.model.plat.MexcPlatAssetDetail;
import com.mexc.dao.model.vcoin.*;
import com.mexc.dao.model.wallet.MexcAssetCash;
import com.mexc.dao.model.wallet.MexcAssetCashVcoin;
import com.mexc.dao.model.wallet.MexcAssetRecharge;
import com.mexc.dao.vo.order.HistoryTradeOrderVo;
import com.mexc.dao.vo.tradingview.TradingViewDataVo;
import com.mexc.dao.vo.tradingview.TradingViewVo;
import com.mexc.match.engine.service.IExchangeMatchService;
import com.mexc.match.engine.util.QueueKeyUtil;
import com.mexc.mq.core.IMqProducerService;
import com.mexc.mq.util.FastJsonMessageConverter;
import com.mexc.mq.vo.MqMsgVo;
import com.mexc.vcoin.TokenEnum;
import com.mexc.web.core.service.order.IOrderService;
import com.mexc.web.core.service.order.IUserEntrustOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;


@Service
public class OrderServiceImpl implements IOrderService {

    private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Resource
    MexcEnTrustDelegate mexcEnTrustDelegate;

    @Resource
    MarketDelegate marketDelegate;

    @Resource
    MexcTradeDelegate mexcTradeDelegate;

    @Resource
    MexcTradeDetailDelegate mexcTradeDetailDelegate;

    @Resource
    MemberDelegate memberDelegate;

    @Resource
    MemberAssetDelegate memberAssetDelegate;

    @Resource
    VCoinDelegate vCoinDelegate;

    @Resource
    MexcAssetTransDelegate mexcAssetTransDelegate;

    @Resource
    IMqProducerService mqProducerService;

    @Resource
    FastJsonMessageConverter fastJsonMessageConverter;

    @Resource
    PlatAssetDelegate platAssetDelegate;

    @Resource
    PlatAssetDetailDelegate platAssetDetailDelegate;

    @Resource
    MexcAssetRechargeDelegate mexcAssetRechargeDelegate;

    @Resource
    MexcAssetCashDelegate mexcAssetCashDelegate;

    @Resource
    IUserEntrustOrderService userEntrustOrderService;

    @Autowired
    IExchangeMatchService exchangeMatchService;


    @Value("${mq.queue.btc.market.trade}")
    private String btcQueue;

    @Value("${mq.queue.eth.market.trade}")
    private String ethQueue;


    public MexcEnTrust queryEntrust(String tradeNo) {
        return mexcEnTrustDelegate.queryEntrust(tradeNo);
    }

    public List<OrderDto> queryEntrustOrder(OrderQueryDto queryDto) {
        MexcMember mexcMember = memberDelegate.queryMermberByAccount(queryDto.getAccount());
        queryDto.setMemberId(mexcMember.getId());
        return mexcEnTrustDelegate.queryOrderList(queryDto);
    }

    @Override
    public List<OrderDto> queryEntrustOrderLimit(OrderQueryDto queryDto, Integer limit) {
        MexcMember mexcMember = memberDelegate.queryMermberByAccount(queryDto.getAccount());
        queryDto.setMemberId(mexcMember.getId());
        return mexcEnTrustDelegate.queryOrderLimit(queryDto, limit);
    }

    @Override
    public List<HistoryTradeOrderVo> queryTradeOrder(OrderQueryDto queryDto) {
        MexcMember mexcMember = memberDelegate.queryMermberByAccount(queryDto.getAccount());
        queryDto.setMemberId(mexcMember.getId());
        return mexcTradeDelegate.queryTradeOrder(queryDto);
    }

    @Transactional
    public void doMatchAndUpdate(MatchOrder entrustOrder, ResultVo<List<MatchOrder>> resultVo) {
        /** 更新资金和状态及添加交易记录*/
        List<MatchOrder> matchOrderList = resultVo.getData();
        if (!CollectionUtils.isEmpty(matchOrderList)) {
            this.updateEntrustAndMatchOrderInfo(entrustOrder, matchOrderList);
        }
    }

    /**
     * 更新 撮合单 和委托单 交易明细和资产
     *
     * @param entrustOrder
     * @param matchOrderList
     */
    public void updateEntrustAndMatchOrderInfo(MatchOrder entrustOrder, List<MatchOrder> matchOrderList) {
        logger.info(LogUtil.msg("OrderServiceImpl:updateMatchEntrustTradeInfo", "撮合交易结果处理开始..."));
        try {
            List<MatchOrder> processMatchOrderList = new ArrayList<>();
            List<MexcTradeDetail> tradeDetails = new ArrayList<>();

            String tradeId = UUIDUtil.get32UUID();

            /**撮合交易结果处理**/
            for (MatchOrder matchOrder : matchOrderList) {
                logger.info(LogUtil.msg("OrderServiceImpl:updateMatchOrderInfo", "开始循环处理" + JSON.toJSONString(matchOrder)));
                MexcMarket market = marketDelegate.selectByPrimaryKey(matchOrder.getMarketId());
                MexcMember member = memberDelegate.selectByPrimaryKey(matchOrder.getMemberId());
                MexcVCoin vCoin = vCoinDelegate.selectByPrimaryKey(matchOrder.getVcoinId());

                /** 查询手续费,并计算买卖手续费 **/
                MexcVCoinFee tradeVCoinFee = vCoinDelegate.queryVCoinFee(vCoin.getId());//副币手续费
                MexcVCoinFee mainVCoinFee = vCoinDelegate.queryVCoinFee(market.getVcoinId());//市场主币手续费

                BigDecimal buyOrSellfeeRate = new BigDecimal("0");//手续费率
                BigDecimal buyOrSellfee = new BigDecimal("0");//手续费
                if (matchOrder.getTradeType() == CommonConstant.BUY) {
                    buyOrSellfeeRate = tradeVCoinFee.getBuyRate();//买费率
                    buyOrSellfee = matchOrder.getTradedNumber().multiply(buyOrSellfeeRate);
                } else if (matchOrder.getTradeType() == CommonConstant.SELL) {
                    buyOrSellfeeRate = mainVCoinFee.getSellRate();//卖费率
                    BigDecimal sellAmount = matchOrder.getTradedNumber().multiply(matchOrder.getPrice());
                    buyOrSellfee = sellAmount.multiply(buyOrSellfeeRate);
                }
                processMatchOrderList.add(matchOrder);

                /** 主单：组装并保存撮合单交易记录 **/
                if (entrustOrder.getTradeNo().equals(matchOrder.getTradeNo())) {
                    MexcTrade mexcTrade = new MexcTrade();
                    mexcTrade.setId(tradeId);
                    mexcTrade.setTradeNumber(matchOrder.getTradedNumber());
                    mexcTrade.setTradeTotalAmount(matchOrder.getPrice().multiply(matchOrder.getTradedNumber()));
                    mexcTrade.setMarketId(matchOrder.getMarketId());
                    mexcTrade.setMarketName(market.getMarketName());
                    mexcTrade.setStatus(TradeOrderEnum.TRADE.getStatus());
                    mexcTrade.setMemberId(matchOrder.getMemberId());
                    mexcTrade.setTradeType(matchOrder.getTradeType());
                    mexcTrade.setTradeVcoinId(matchOrder.getVcoinId());
                    mexcTrade.setTradelVcoinNameEn(vCoin.getVcoinNameEn());
                    mexcTrade.setTradeNo(matchOrder.getTradeNo());
                    mexcTrade.setTradePrice(matchOrder.getPrice());
                    mexcTrade.setCreateBy(member.getId());
                    mexcTrade.setCreateByName(member.getAccount());
                    mexcTrade.setCreateTime(DateUtil.format(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS));
                    mexcTrade.setTradeRate(buyOrSellfeeRate);
                    mexcTrade.setTradeFee(buyOrSellfee);
                    mexcTradeDelegate.insert(mexcTrade);
                    logger.info(LogUtil.msg("OrderServiceImpl:updateMatchOrderInfo", "组装交易单并保存"));
                }


                /** 主单撮合明细：组装委托交易明细并保存:记录一个委托单撮合到哪些单 **/
                if (!matchOrder.getTradeNo().equals(entrustOrder.getTradeNo())) {
                    MexcTradeDetail tradeDetail = new MexcTradeDetail();
                    tradeDetail.setId(UUIDUtil.get32UUID());
                    tradeDetail.setMarketId(market.getId());
                    tradeDetail.setCreateBy(member.getId());
                    tradeDetail.setCreateByName(member.getAccount());
                    tradeDetail.setCreateTime(new Date());
                    tradeDetail.setTradeNumber(matchOrder.getTradedNumber());
                    tradeDetail.setMemberId(member.getId());
                    tradeDetail.setTransNo(matchOrder.getTradeNo());
                    tradeDetail.setTradeVcoinId(matchOrder.getVcoinId());
                    tradeDetail.setTradeTotalAmount(matchOrder.getPrice().multiply(matchOrder.getTradedNumber()));
                    tradeDetail.setTradeNo(entrustOrder.getTradeNo());
                    tradeDetail.setTradePrice(matchOrder.getPrice());
                    tradeDetail.setType(1);
                    tradeDetail.setTradeRate(buyOrSellfeeRate);
                    tradeDetail.setTradeFee(buyOrSellfee);
                    tradeDetail.setTradeId(tradeId);
                    tradeDetails.add(tradeDetail);
                    logger.info(LogUtil.msg("OrderServiceImpl:updateMatchOrderInfo", "组装委托交易明细并保存:记录一个委托单撮合到哪些单"));
                }
            }

            if (org.apache.commons.collections.CollectionUtils.isNotEmpty(processMatchOrderList)) {
                for (MatchOrder matchOrder : processMatchOrderList) {
                    /** 更新资金明细 */
                    updateMatchOrder(entrustOrder, matchOrder);
                }
            }
            mexcTradeDetailDelegate.insertBatch(tradeDetails);
        } catch (Exception e) {
            logger.error("撮合交易失败", e);
            throw new SystemException(ResultCode.COMMON_ERROR, "撮合交易失败");
        }
    }

    public void updateMatchOrder(MatchOrder entrustOrder, MatchOrder matchOrder) {
        MexcMarket market = marketDelegate.selectByPrimaryKey(matchOrder.getMarketId());
        MexcMember member = memberDelegate.selectByPrimaryKey(matchOrder.getMemberId());
        MexcVCoin vCoin = vCoinDelegate.selectByPrimaryKey(matchOrder.getVcoinId());

        /** 查询手续费,并计算买卖手续费 **/
        MexcVCoinFee tradeVCoinFee = vCoinDelegate.queryVCoinFee(vCoin.getId());//副币手续费
        MexcVCoinFee mainVCoinFee = vCoinDelegate.queryVCoinFee(market.getVcoinId());//市场主币手续费

        BigDecimal buyOrSellfeeRate = new BigDecimal("0");//手续费率
        BigDecimal buyOrSellfee = new BigDecimal("0");//手续费
        if (matchOrder.getTradeType() == CommonConstant.BUY) {
            buyOrSellfeeRate = tradeVCoinFee.getBuyRate();//买费率
            buyOrSellfee = matchOrder.getTradedNumber().multiply(buyOrSellfeeRate);
        } else if (matchOrder.getTradeType() == CommonConstant.SELL) {
            buyOrSellfeeRate = mainVCoinFee.getSellRate();//卖费率
            BigDecimal sellAmount = matchOrder.getTradedNumber().multiply(matchOrder.getPrice());
            buyOrSellfee = sellAmount.multiply(buyOrSellfeeRate);
        }


        /** 交易和 撮合单明细*/
        if (!entrustOrder.getTradeNo().equals(matchOrder.getTradeNo())) {
            /** 组装并保存撮合单交易记录 **/
            MexcTrade mexcTrade = new MexcTrade();
            String tradeId = UUIDUtil.get32UUID();
            mexcTrade.setId(tradeId);
            mexcTrade.setTradeNumber(matchOrder.getTradedNumber());
            mexcTrade.setTradeTotalAmount(matchOrder.getPrice().multiply(matchOrder.getTradedNumber()));
            mexcTrade.setMarketId(matchOrder.getMarketId());
            mexcTrade.setMarketName(market.getMarketName());
            mexcTrade.setStatus(TradeOrderEnum.TRADE.getStatus());
            mexcTrade.setMemberId(matchOrder.getMemberId());
            mexcTrade.setTradeType(matchOrder.getTradeType());
            mexcTrade.setTradeVcoinId(matchOrder.getVcoinId());
            mexcTrade.setTradelVcoinNameEn(vCoin.getVcoinNameEn());
            mexcTrade.setTradeNo(matchOrder.getTradeNo());
            mexcTrade.setTradePrice(matchOrder.getPrice());
            mexcTrade.setCreateBy(member.getId());
            mexcTrade.setCreateByName(member.getAccount());
            mexcTrade.setCreateTime(DateUtil.format(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS));
            mexcTrade.setTradeRate(buyOrSellfeeRate);
            mexcTrade.setTradeFee(buyOrSellfee);
            mexcTradeDelegate.insert(mexcTrade);
            logger.info(LogUtil.msg("OrderServiceImpl:updateMatchOrderInfo", "组装交易单并保存"));

            /**撮合单明细*/
            MexcTradeDetail tradeDetail = new MexcTradeDetail();
            tradeDetail.setId(UUIDUtil.get32UUID());
            tradeDetail.setMarketId(market.getId());
            tradeDetail.setCreateBy(member.getId());
            tradeDetail.setCreateByName(member.getAccount());
            tradeDetail.setCreateTime(new Date());
            tradeDetail.setTradeNumber(matchOrder.getTradedNumber());
            tradeDetail.setMemberId(member.getId());
            tradeDetail.setTransNo(entrustOrder.getTradeNo());
            tradeDetail.setTradeVcoinId(matchOrder.getVcoinId());
            tradeDetail.setTradeTotalAmount(matchOrder.getPrice().multiply(matchOrder.getTradedNumber()));
            tradeDetail.setTradeNo(matchOrder.getTradeNo());
            tradeDetail.setTradePrice(matchOrder.getPrice());
            tradeDetail.setType(1);
            tradeDetail.setTradeRate(buyOrSellfeeRate);
            tradeDetail.setTradeFee(buyOrSellfee);
            tradeDetail.setTradeId(tradeId);
            mexcTradeDetailDelegate.insert(tradeDetail);
            logger.info(LogUtil.msg("OrderServiceImpl:updateMatchOrderInfo", "组装委托交易明细并保存:记录一个委托单撮合到哪些单"));
        }


        /** 更新委托订单的数量和状态 **/
        MexcEnTrust mexcEnTrust = mexcEnTrustDelegate.queryEntrust(matchOrder.getTradeNo());
        if (mexcEnTrust == null) {
            logger.error("该委托单不存在,entrust:{}", JSON.toJSONString(mexcEnTrust));
            throw new BusinessException(ResultCode.COMMON_ERROR, "该委托单不存在.");
        }


        /** 更新委托单 */
        UpdateEntrustDto updateEntrustDto = new UpdateEntrustDto();
        updateEntrustDto.setId(mexcEnTrust.getId());
        updateEntrustDto.setTradeNo(matchOrder.getTradeNo());
        updateEntrustDto.setTradedNumber(matchOrder.getTradedNumber());
        updateEntrustDto.setTradedAmount(matchOrder.getTradedNumber().multiply(matchOrder.getPrice()));
        updateEntrustDto.setUpdateByName(member.getAccount());
        updateEntrustDto.setUpdateBy(member.getId());
        updateEntrustDto.setUpdateTime(new Date());

        if (matchOrder.getTradedNumber().compareTo(matchOrder.getTradableNumber()) == 0) {//全部交易
            updateEntrustDto.setStatus(EntrustOrderEnum.COMPLETED.getStatus());
        } else if (matchOrder.getTradedNumber().compareTo(matchOrder.getTradableNumber()) < 0) {//部分交易
            updateEntrustDto.setStatus(EntrustOrderEnum.PART_COMPLETED.getStatus());//部成
        }

        int rows = mexcEnTrustDelegate.updateEntrustInfo(updateEntrustDto);
        if (rows < 1) {
            logger.error("修改委托状态失败,有可能状态已发生更改,entrust:{}", JSON.toJSONString(mexcEnTrust));
            throw new BusinessException(ResultCode.COMMON_ERROR, "修改委托状态失败,请检查委托状态是否已经改.");
        }

        /**更新当前用户委托缓存**/
        if (matchOrder.getTradedNumber().compareTo(matchOrder.getTradableNumber()) == 0) {//全部交易
            userEntrustOrderService.deleteCurrentEntrustOrderCache(mexcEnTrust);//从当前委托缓存中删除
        } else if (matchOrder.getTradedNumber().compareTo(matchOrder.getTradableNumber()) < 0) {//部分交易
            userEntrustOrderService.updateCurrentEntrustOrderCache(mexcEnTrust);//更新用户当前委托缓存
        }
        logger.info(LogUtil.msg("OrderServiceImpl:updateMatchOrderInfo", "更新当前用户委托缓存"));


        /**更新用户资产、增加出入账记录、手续费**/
        MexcMemberAsset mainVcoinAsset = memberAssetDelegate.queryMemberAsset(matchOrder.getMemberId(), market.getVcoinId());//市场主币资产
        MexcMemberAsset currentVcoinAsset = memberAssetDelegate.queryMemberAsset(matchOrder.getMemberId(), vCoin.getId());//当前交易币资产
        if (matchOrder.getTradeType() == CommonConstant.BUY) {
            logger.info(LogUtil.msg("OrderServiceImpl:updateMatchOrderInfo", "撮合成功的买单：" + JSON.toJSONString(matchOrder)));

            BigDecimal tradeAmount = matchOrder.getPrice().multiply(matchOrder.getTradedNumber());//委托金额：交易价格 * 交易数量
            memberAssetDelegate.unfrozenAmount(mainVcoinAsset.getId(), tradeAmount);//解冻
            BigDecimal outAmount = matchOrder.getTradedNumber().multiply(matchOrder.getPrice());//主笔出账：成交价 * 交易数量
            BigDecimal inAmount = matchOrder.getTradedNumber().subtract(buyOrSellfee);//入账 : 交易金额 - 手续费

            memberAssetDelegate.assetOutcomeing(mainVcoinAsset.getId(), outAmount);//出账
            logger.info(LogUtil.msg("OrderServiceImpl:updateMatchOrderInfo", "市场主币出账："));
            memberAssetDelegate.assetIncomeing(currentVcoinAsset.getId(), inAmount);//入账
            logger.info(LogUtil.msg("OrderServiceImpl:updateMatchOrderInfo", "交易币出账："));


            //买手续费 :副币到账，扣除副币的手续费
            MexcAssetTrans feeTrans = new MexcAssetTrans();
            feeTrans.setAssetId(currentVcoinAsset.getId());
            feeTrans.setTradeType("3");//手续费 ： -1:提现 1:冲值 2:委托 3:手续费 4：资产返还 5:平台充值
            feeTrans.setTransNo(matchOrder.getTradeNo());
            feeTrans.setTransAmount(buyOrSellfee);
            feeTrans.setTransType(-1);//出账
            feeTrans.setTransTime(new Date());
            mexcAssetTransDelegate.insert(feeTrans);
            logger.info(LogUtil.msg("OrderServiceImpl:updateMatchOrderInfo", "交易币手续费："));

            //市场主币出账
            MexcAssetTrans assetTrans = new MexcAssetTrans();
            assetTrans.setAssetId(mainVcoinAsset.getId());
            assetTrans.setTradeType("2");//委托
            assetTrans.setTransNo(matchOrder.getTradeNo());
            assetTrans.setTransAmount(outAmount);
            assetTrans.setTransType(-1);//出账
            assetTrans.setTransTime(new Date());
            mexcAssetTransDelegate.insert(assetTrans);
            logger.info(LogUtil.msg("OrderServiceImpl:updateMatchOrderInfo", "副币入账："));

            //副币入账
            assetTrans = new MexcAssetTrans();
            assetTrans.setAssetId(currentVcoinAsset.getId());
            assetTrans.setTradeType("2");//委托
            assetTrans.setTransNo(matchOrder.getTradeNo());
            assetTrans.setTransAmount(inAmount);
            assetTrans.setTransType(1);//入账
            assetTrans.setTransTime(new Date());
            mexcAssetTransDelegate.insert(assetTrans);
            logger.info(LogUtil.msg("OrderServiceImpl:updateMatchOrderInfo", "添加手续费到平台"));

            //添加手续费到平台
            try {
                MexcPlatAsset platAsset = platAssetDelegate.queryPlatAsset(matchOrder.getVcoinId());
                platAssetDelegate.assetIncome(platAsset.getId(), buyOrSellfee);
                logger.info(LogUtil.msg("OrderServiceImpl:updateMatchOrderInfo", "添加手续费到平台明细"));
                //添加手续费到平台明细
                MexcPlatAssetDetail platAssetDetail = new MexcPlatAssetDetail();
                platAssetDetail.setAmount(buyOrSellfee);
                platAssetDetail.setPlatId(platAsset.getId());
                platAssetDetail.setOptTime(new Date());
                platAssetDetail.setOptType(1);
                platAssetDetail.setBalance(buyOrSellfee);
                platAssetDetail.setTradeFee(buyOrSellfee);
                platAssetDetail.setTradeRate(buyOrSellfeeRate);
                platAssetDetailDelegate.insert(platAssetDetail);
            } catch (Exception e) {
                logger.error(LogUtil.msg("OrderServiceImpl:updateMatchOrderInfo", "添加平台资产异常"), e);
            }

            logger.info(LogUtil.msg("OrderServiceImpl:updateMatchOrderInfo", "完成"));

        } else if (matchOrder.getTradeType() == CommonConstant.SELL) {
            logger.info(LogUtil.msg("OrderServiceImpl:updateMatchOrderInfo", "撮合成功的卖单：" + JSON.toJSONString(matchOrder)));
            BigDecimal inAmount = matchOrder.getTradedNumber().multiply(matchOrder.getPrice());//主币入账：交易数量 * 成交价
            BigDecimal outAmount = matchOrder.getTradedNumber();//副币出账：交易数量
            BigDecimal actualInAmount = inAmount.subtract(buyOrSellfee);//主币实际入账：市场主币入账-手续费

            memberAssetDelegate.unfrozenAmount(currentVcoinAsset.getId(), outAmount);//解冻
            memberAssetDelegate.assetOutcomeing(currentVcoinAsset.getId(), outAmount);//出账
            memberAssetDelegate.assetIncomeing(mainVcoinAsset.getId(), actualInAmount);//入账
            logger.info(LogUtil.msg("OrderServiceImpl:updateMatchOrderInfo", "市场主币入账："));

            //卖手续费 :主币到账，扣除主币的手续费
            MexcAssetTrans feeTrans = new MexcAssetTrans();
            feeTrans.setAssetId(mainVcoinAsset.getId());
            feeTrans.setTradeType("3");//手续费 ： -1:提现 1:冲值 2:委托 3:手续费 4：资产返还 5:平台充值
            feeTrans.setTransNo(matchOrder.getTradeNo());
            feeTrans.setTransAmount(buyOrSellfee);
            feeTrans.setTransType(-1);//出账
            feeTrans.setTransTime(new Date());
            mexcAssetTransDelegate.insert(feeTrans);
            logger.info(LogUtil.msg("OrderServiceImpl:updateMatchOrderInfo", "主币手续费："));

            //市场主币入账
            MexcAssetTrans assetTrans = new MexcAssetTrans();
            assetTrans.setAssetId(mainVcoinAsset.getId());
            assetTrans.setTradeType("2");//委托
            assetTrans.setTransNo(matchOrder.getTradeNo());
            assetTrans.setTransAmount(actualInAmount);
            assetTrans.setTransType(1);//入账
            assetTrans.setTransTime(new Date());
            mexcAssetTransDelegate.insert(assetTrans);
            logger.info(LogUtil.msg("OrderServiceImpl:updateMatchOrderInfo", "副币出账："));
            //副币出账
            assetTrans = new MexcAssetTrans();
            assetTrans.setAssetId(currentVcoinAsset.getId());
            assetTrans.setTradeType("2");//委托
            assetTrans.setTransNo(matchOrder.getTradeNo());
            assetTrans.setTransAmount(outAmount);
            assetTrans.setTransType(-1);//出账
            assetTrans.setTransTime(new Date());
            mexcAssetTransDelegate.insert(assetTrans);
            logger.info(LogUtil.msg("OrderServiceImpl:updateMatchOrderInfo", "添加手续费到平台："));
            //添加手续费到平台
            try {
                MexcPlatAsset platAsset = platAssetDelegate.queryPlatAsset(matchOrder.getVcoinId());
                platAssetDelegate.assetIncome(platAsset.getId(), buyOrSellfee);
                logger.info(LogUtil.msg("OrderServiceImpl:updateMatchOrderInfo", "添加手续费到平台："));
                //添加手续费到平台明细
                MexcPlatAssetDetail platAssetDetail = new MexcPlatAssetDetail();
                platAssetDetail.setAmount(buyOrSellfee);
                platAssetDetail.setPlatId(platAsset.getId());
                platAssetDetail.setOptTime(new Date());
                platAssetDetail.setOptType(1);
                platAssetDetail.setBalance(buyOrSellfee);
                platAssetDetail.setTradeFee(buyOrSellfee);
                platAssetDetail.setTradeRate(buyOrSellfeeRate);
                platAssetDetailDelegate.insert(platAssetDetail);
            } catch (Exception e) {
                logger.error(LogUtil.msg("OrderServiceImpl:updateMatchOrderInfo", "添加平台资产异常"), e);
            }
        }
    }

    /**
     * 手动交易
     *
     * @param tradeDto
     */
    @Transactional
    public void handEntrustTrade(EntrustTradeDto tradeDto) {
        MexcMarket market = marketDelegate.selectByPrimaryKey(tradeDto.getMarketId());
        MexcMember member = memberDelegate.queryMermberByAccount(tradeDto.getAccount());
        MexcVCoin vCoin = vCoinDelegate.selectByPrimaryKey(tradeDto.getVcoinId());

        MexcMemberAsset mainVcoinAsset = memberAssetDelegate.queryMemberAsset(member.getId(), market.getVcoinId());//市场主币资产
        MexcMemberAsset currentVcoinAsset = memberAssetDelegate.queryMemberAsset(member.getId(), vCoin.getId());//当前币交易资产

        /** 交易金额 */
        BigDecimal tradeAmount = new BigDecimal(tradeDto.getTradeNumber()).multiply(new BigDecimal(tradeDto.getTradePrice()));

        /** 校验资产 **/
        if (tradeDto.getTradeType() == CommonConstant.BUY) {
            if (tradeAmount.compareTo(mainVcoinAsset.getBalanceAmount()) > 0) {
                logger.info("OrderServiceImpl-> entrustTrade：持仓不足");
                throw new BusinessException(ResultCode.COMMON_ERROR, "持仓不足");
            }
        } else if (tradeDto.getTradeType() == CommonConstant.SELL) {
            if (new BigDecimal(tradeDto.getTradeNumber()).compareTo(currentVcoinAsset.getBalanceAmount()) > 0) {
                logger.info("OrderServiceImpl->entrustTrade：卖出超额");
                throw new BusinessException(ResultCode.COMMON_ERROR, "卖出超额");
            }
        }

        /** 组装交易订单 **/
        String entrustTime = DateUtil.format(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS);
        MatchOrder mainOrder = new MatchOrder();
        String tradeNo = UUIDUtil.get32UUID();
        mainOrder.setTradeNo(tradeNo);
        mainOrder.setEntrustNumber(new BigDecimal(tradeDto.getTradeNumber()));
        mainOrder.setTradeType(tradeDto.getTradeType());
        mainOrder.setMarketId(market.getId());
        mainOrder.setPrice(new BigDecimal(tradeDto.getTradePrice()));
        mainOrder.setTradedNumber(new BigDecimal("0"));
        mainOrder.setTradableNumber(new BigDecimal(tradeDto.getTradeNumber()));
        mainOrder.setVcoinId(vCoin.getId());
        mainOrder.setMemberId(member.getId());
        mainOrder.setEntrustTime(entrustTime);

        /** 寻找匹配订单 */
        ResultVo<List<MatchOrder>> resultVo = exchangeMatchService.match(mainOrder);
        if (!ResCode.SUCCESS.equals(resultVo.getCode()) || resultVo.getData() == null) {
            throw new BusinessException(ResultCode.COMMON_ERROR, "成交失败，此价位已经被交易完");
        }


        List<MatchOrder> matchOrderList = resultVo.getData();

        /** 更新交易单 */
        updateMainOrder(mainOrder, matchOrderList);

        /** 更新匹配订单 */
        for (MatchOrder matchOrder : matchOrderList) {
            if (!matchOrder.getTradeNo().equals(tradeNo)) {
                updateMatchOrder(mainOrder, matchOrder);
            }
        }
    }

    @Transactional
    public void updateMainOrder(MatchOrder mainOrder, List<MatchOrder> matchOrderList) {
        if (mainOrder.getTradedNumber().compareTo(mainOrder.getTradableNumber()) < 0) {
            throw new BusinessException(ResultCode.COMMON_ERROR, "成交失败，当前提交数量超过可交易数量");
        }

        MexcMarket market = marketDelegate.selectByPrimaryKey(mainOrder.getMarketId());
        MexcMember member = memberDelegate.selectByPrimaryKey(mainOrder.getMemberId());
        MexcVCoin vCoin = vCoinDelegate.selectByPrimaryKey(mainOrder.getVcoinId());

        /** 查询手续费,并计算买卖手续费 **/
        MexcVCoinFee tradeVCoinFee = vCoinDelegate.queryVCoinFee(vCoin.getId());//副币手续费
        MexcVCoinFee mainVCoinFee = vCoinDelegate.queryVCoinFee(market.getVcoinId());//市场主币手续费

        BigDecimal buyOrSellfeeRate = new BigDecimal("0");//手续费率
        BigDecimal buyOrSellfee = new BigDecimal("0");//手续费
        if (mainOrder.getTradeType() == CommonConstant.BUY) {
            buyOrSellfeeRate = tradeVCoinFee.getBuyRate();//买费率
            buyOrSellfee = mainOrder.getTradedNumber().multiply(buyOrSellfeeRate);
        } else if (mainOrder.getTradeType() == CommonConstant.SELL) {
            buyOrSellfeeRate = mainVCoinFee.getSellRate();//卖费率
            BigDecimal sellAmount = mainOrder.getTradedNumber().multiply(mainOrder.getPrice());
            buyOrSellfee = sellAmount.multiply(buyOrSellfeeRate);
        }

        /** 组装并保存撮合单交易记录 **/
        MexcTrade mexcTrade = new MexcTrade();
        String tradeId = UUIDUtil.get32UUID();
        mexcTrade.setId(tradeId);
        mexcTrade.setTradeNumber(mainOrder.getTradedNumber());
        mexcTrade.setTradeTotalAmount(mainOrder.getPrice().multiply(mainOrder.getTradedNumber()));
        mexcTrade.setMarketId(mainOrder.getMarketId());
        mexcTrade.setMarketName(market.getMarketName());
        mexcTrade.setStatus(TradeOrderEnum.TRADE.getStatus());
        mexcTrade.setMemberId(mainOrder.getMemberId());
        mexcTrade.setTradeType(mainOrder.getTradeType());
        mexcTrade.setTradeVcoinId(mainOrder.getVcoinId());
        mexcTrade.setTradelVcoinNameEn(vCoin.getVcoinNameEn());
        mexcTrade.setTradeNo(mainOrder.getTradeNo());
        mexcTrade.setTradePrice(mainOrder.getPrice());
        mexcTrade.setCreateBy(member.getId());
        mexcTrade.setCreateByName(member.getAccount());
        mexcTrade.setCreateTime(DateUtil.format(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS));
        mexcTrade.setTradeRate(buyOrSellfeeRate);
        mexcTrade.setTradeFee(buyOrSellfee);
        mexcTradeDelegate.insert(mexcTrade);
        logger.info(LogUtil.msg("OrderServiceImpl:updateMatchOrderInfo", "组装交易单并保存"));

        /** 主订单的撮合明细 */
        for (MatchOrder matchOrder : matchOrderList) {
            if (!mainOrder.getTradeNo().equals(mainOrder.getTradeNo())) {
                MexcTradeDetail tradeDetail = new MexcTradeDetail();
                tradeDetail.setId(UUIDUtil.get32UUID());
                tradeDetail.setMarketId(market.getId());
                tradeDetail.setCreateBy(member.getId());
                tradeDetail.setCreateByName(member.getAccount());
                tradeDetail.setCreateTime(new Date());
                tradeDetail.setTradeNumber(matchOrder.getTradedNumber());
                tradeDetail.setMemberId(member.getId());
                tradeDetail.setTransNo(matchOrder.getTradeNo());
                tradeDetail.setTradeVcoinId(matchOrder.getVcoinId());
                tradeDetail.setTradeTotalAmount(matchOrder.getPrice().multiply(matchOrder.getTradedNumber()));
                tradeDetail.setTradeNo(mainOrder.getTradeNo());
                tradeDetail.setTradePrice(matchOrder.getPrice());
                tradeDetail.setType(1);
                tradeDetail.setTradeRate(buyOrSellfeeRate);
                tradeDetail.setTradeFee(buyOrSellfee);
                tradeDetail.setTradeId(tradeId);
                mexcTradeDetailDelegate.insert(tradeDetail);
            }
            logger.info(LogUtil.msg("OrderServiceImpl:updateMainOrder", "组装委托交易明细并保存:记录一个委托单撮合到哪些单"));
        }


        /**更新用户资产、增加出入账记录、手续费**/
        MexcMemberAsset mainVcoinAsset = memberAssetDelegate.queryMemberAsset(mainOrder.getMemberId(), market.getVcoinId());//市场主币资产
        MexcMemberAsset currentVcoinAsset = memberAssetDelegate.queryMemberAsset(mainOrder.getMemberId(), vCoin.getId());//当前交易币资产
        if (mainOrder.getTradeType() == CommonConstant.BUY) {
            logger.info(LogUtil.msg("OrderServiceImpl:updateMatchOrderInfo", "撮合成功的买单：" + JSON.toJSONString(mainOrder)));

            BigDecimal outAmount = mainOrder.getTradedNumber().multiply(mainOrder.getPrice());//主笔出账：成交价 * 交易数量
            BigDecimal inAmount = mainOrder.getTradedNumber().subtract(buyOrSellfee);//入账 : 交易金额 - 手续费

            memberAssetDelegate.assetOutcomeing(mainVcoinAsset.getId(), outAmount);//出账
            logger.info(LogUtil.msg("OrderServiceImpl:updateMatchOrderInfo", "市场主币出账："));
            memberAssetDelegate.assetIncomeing(currentVcoinAsset.getId(), inAmount);//入账
            logger.info(LogUtil.msg("OrderServiceImpl:updateMatchOrderInfo", "交易币出账："));


            //买手续费 :副币到账，扣除副币的手续费
            MexcAssetTrans feeTrans = new MexcAssetTrans();
            feeTrans.setAssetId(currentVcoinAsset.getId());
            feeTrans.setTradeType("3");//手续费 ： -1:提现 1:冲值 2:委托 3:手续费 4：资产返还 5:平台充值
            feeTrans.setTransNo(mainOrder.getTradeNo());
            feeTrans.setTransAmount(buyOrSellfee);
            feeTrans.setTransType(-1);//出账
            feeTrans.setTransTime(new Date());
            mexcAssetTransDelegate.insert(feeTrans);
            logger.info(LogUtil.msg("OrderServiceImpl:updateMatchOrderInfo", "交易币手续费："));

            //市场主币出账
            MexcAssetTrans assetTrans = new MexcAssetTrans();
            assetTrans.setAssetId(mainVcoinAsset.getId());
            assetTrans.setTradeType("2");//委托
            assetTrans.setTransNo(mainOrder.getTradeNo());
            assetTrans.setTransAmount(outAmount);
            assetTrans.setTransType(-1);//出账
            assetTrans.setTransTime(new Date());
            mexcAssetTransDelegate.insert(assetTrans);
            logger.info(LogUtil.msg("OrderServiceImpl:updateMatchOrderInfo", "副币入账："));

            //副币入账
            assetTrans = new MexcAssetTrans();
            assetTrans.setAssetId(currentVcoinAsset.getId());
            assetTrans.setTradeType("2");//委托
            assetTrans.setTransNo(mainOrder.getTradeNo());
            assetTrans.setTransAmount(inAmount);
            assetTrans.setTransType(1);//入账
            assetTrans.setTransTime(new Date());
            mexcAssetTransDelegate.insert(assetTrans);
            logger.info(LogUtil.msg("OrderServiceImpl:updateMatchOrderInfo", "添加手续费到平台"));

            //添加手续费到平台
            try {
                MexcPlatAsset platAsset = platAssetDelegate.queryPlatAsset(mainOrder.getVcoinId());
                platAssetDelegate.assetIncome(platAsset.getId(), buyOrSellfee);
                logger.info(LogUtil.msg("OrderServiceImpl:updateMatchOrderInfo", "添加手续费到平台明细"));
                //添加手续费到平台明细
                MexcPlatAssetDetail platAssetDetail = new MexcPlatAssetDetail();
                platAssetDetail.setAmount(buyOrSellfee);
                platAssetDetail.setPlatId(platAsset.getId());
                platAssetDetail.setOptTime(new Date());
                platAssetDetail.setOptType(1);
                platAssetDetail.setBalance(buyOrSellfee);
                platAssetDetail.setTradeFee(buyOrSellfee);
                platAssetDetail.setTradeRate(buyOrSellfeeRate);
                platAssetDetailDelegate.insert(platAssetDetail);
            } catch (Exception e) {
                logger.error(LogUtil.msg("OrderServiceImpl:updateMatchOrderInfo", "添加平台资产异常"), e);
            }

            logger.info(LogUtil.msg("OrderServiceImpl:updateMatchOrderInfo", "完成"));

        } else if (mainOrder.getTradeType() == CommonConstant.SELL) {
            logger.info(LogUtil.msg("OrderServiceImpl:updateMatchOrderInfo", "撮合成功的卖单：" + JSON.toJSONString(mainOrder)));
            BigDecimal inAmount = mainOrder.getTradedNumber().multiply(mainOrder.getPrice());//主币入账：交易数量 * 成交价
            BigDecimal outAmount = mainOrder.getTradedNumber();//副币出账：交易数量
            BigDecimal actualInAmount = inAmount.subtract(buyOrSellfee);//主币实际入账：市场主币入账-手续费

            memberAssetDelegate.assetOutcomeing(currentVcoinAsset.getId(), outAmount);//出账
            memberAssetDelegate.assetIncomeing(mainVcoinAsset.getId(), actualInAmount);//入账
            logger.info(LogUtil.msg("OrderServiceImpl:updateMatchOrderInfo", "市场主币入账："));

            //卖手续费 :主币到账，扣除主币的手续费
            MexcAssetTrans feeTrans = new MexcAssetTrans();
            feeTrans.setAssetId(mainVcoinAsset.getId());
            feeTrans.setTradeType("3");//手续费 ： -1:提现 1:冲值 2:委托 3:手续费 4：资产返还 5:平台充值
            feeTrans.setTransNo(mainOrder.getTradeNo());
            feeTrans.setTransAmount(buyOrSellfee);
            feeTrans.setTransType(-1);//出账
            feeTrans.setTransTime(new Date());
            mexcAssetTransDelegate.insert(feeTrans);
            logger.info(LogUtil.msg("OrderServiceImpl:updateMatchOrderInfo", "主币手续费："));

            //市场主币入账
            MexcAssetTrans assetTrans = new MexcAssetTrans();
            assetTrans.setAssetId(mainVcoinAsset.getId());
            assetTrans.setTradeType("2");//委托
            assetTrans.setTransNo(mainOrder.getTradeNo());
            assetTrans.setTransAmount(actualInAmount);
            assetTrans.setTransType(1);//入账
            assetTrans.setTransTime(new Date());
            mexcAssetTransDelegate.insert(assetTrans);
            logger.info(LogUtil.msg("OrderServiceImpl:updateMatchOrderInfo", "副币出账："));
            //副币出账
            assetTrans = new MexcAssetTrans();
            assetTrans.setAssetId(currentVcoinAsset.getId());
            assetTrans.setTradeType("2");//委托
            assetTrans.setTransNo(mainOrder.getTradeNo());
            assetTrans.setTransAmount(outAmount);
            assetTrans.setTransType(-1);//出账
            assetTrans.setTransTime(new Date());
            mexcAssetTransDelegate.insert(assetTrans);
            logger.info(LogUtil.msg("OrderServiceImpl:updateMatchOrderInfo", "添加手续费到平台："));
            //添加手续费到平台
            try {
                MexcPlatAsset platAsset = platAssetDelegate.queryPlatAsset(mainOrder.getVcoinId());
                platAssetDelegate.assetIncome(platAsset.getId(), buyOrSellfee);
                logger.info(LogUtil.msg("OrderServiceImpl:updateMatchOrderInfo", "添加手续费到平台："));
                //添加手续费到平台明细
                MexcPlatAssetDetail platAssetDetail = new MexcPlatAssetDetail();
                platAssetDetail.setAmount(buyOrSellfee);
                platAssetDetail.setPlatId(platAsset.getId());
                platAssetDetail.setOptTime(new Date());
                platAssetDetail.setOptType(1);
                platAssetDetail.setBalance(buyOrSellfee);
                platAssetDetail.setTradeFee(buyOrSellfee);
                platAssetDetail.setTradeRate(buyOrSellfeeRate);
                platAssetDetailDelegate.insert(platAssetDetail);
            } catch (Exception e) {
                logger.error(LogUtil.msg("OrderServiceImpl:updateMatchOrderInfo", "添加平台资产异常"), e);
            }
        }
    }


    /**
     * 委托交易/买入，卖出
     *
     * @param tradeDto
     */
    public void entrustTrade(EntrustTradeDto tradeDto) {
        try {
            MexcMarket market = marketDelegate.selectByPrimaryKey(tradeDto.getMarketId());
            MexcMember member = memberDelegate.queryMermberByAccount(tradeDto.getAccount());
            MexcVCoin vCoin = vCoinDelegate.selectByPrimaryKey(tradeDto.getVcoinId());

            MexcMemberAsset mainVcoinAsset = memberAssetDelegate.queryMemberAsset(member.getId(), market.getVcoinId());//市场主币资产
            MexcMemberAsset currentVcoinAsset = memberAssetDelegate.queryMemberAsset(member.getId(), vCoin.getId());//当前币交易资产

            /**交易金额*/
            BigDecimal tradeAmount = new BigDecimal(tradeDto.getTradeNumber()).multiply(new BigDecimal(tradeDto.getTradePrice()));

            /** 校验资产 **/
            if (tradeDto.getTradeType().equals(CommonConstant.BUY.toString())) {
                if (tradeAmount.compareTo(mainVcoinAsset.getBalanceAmount()) > 0) {
                    logger.info("OrderServiceImpl-> entrustTrade：持仓不足");
                    throw new BusinessException(ResultCode.COMMON_ERROR, "持仓不足");
                }
            } else if (tradeDto.getTradeType().equals(CommonConstant.SELL.toString())) {
                if (new BigDecimal(tradeDto.getTradeNumber()).compareTo(currentVcoinAsset.getBalanceAmount()) > 0) {
                    logger.info("OrderServiceImpl->entrustTrade：卖出超额");
                    throw new BusinessException(ResultCode.COMMON_ERROR, "卖出超额");
                }
            }

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
            mexcEnTrust.setTradeType(tradeDto.getTradeType());
            mexcEnTrust.setTradePrice(new BigDecimal(tradeDto.getTradePrice()));
            mexcEnTrust.setTradeNumber(new BigDecimal(tradeDto.getTradeNumber()));
            mexcEnTrust.setTradeRemainNumber(new BigDecimal(tradeDto.getTradeNumber()));
            mexcEnTrust.setTradeTotalAmount(tradeAmount);
            mexcEnTrust.setTradeRemainAmount(tradeAmount);
            mexcEnTrust.setTradeRate(new BigDecimal("0"));
            mexcEnTrust.setTradeFee(new BigDecimal("0"));
            String assetId = "";
            //委托单 不收手续费
            if (tradeDto.getTradeType() == CommonConstant.BUY) {
                assetId = mainVcoinAsset.getId();
            } else if (tradeDto.getTradeType() == CommonConstant.SELL) {
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
            entrustOrder.setTradableNumber(new BigDecimal(tradeDto.getTradeNumber()));
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
                int result = mexcEnTrustDelegate.updateMqStatus(entrustOrder.getTradeNo());
                if (result < 0) {
                    logger.warn("更改委托发送MQ异常:{}", entrustOrder.getTradeNo());
                }
            } catch (Exception e) {
                logger.error(LogUtil.msg("OrderServiceImpl:entrustTrade", "异步发送委托交易队列，data:" + JSON.toJSONString(mqMsgVo)), e);
            }
            /** 修改订单存放队列状态 **/
            mexcEnTrustDelegate.updateMqStatus(mexcEnTrust.getTradeNo());
            /** 添加到当前用户委托缓存列表中 **/
            userEntrustOrderService.addCurrentEntrustOrderCache(mexcEnTrust);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("委托交易失败", e);
            throw new SystemException(ResultCode.COMMON_ERROR, "委托交易失败");
        }
    }


    /**
     * 撤单
     *
     * @param cancelEntrustTradeDto
     */
    @Transactional
    public void cancelEntrustTrade(CancelEntrustTradeDto cancelEntrustTradeDto) {
        MexcMember member = memberDelegate.queryMermberByAccount(cancelEntrustTradeDto.getAccount());
        MexcEnTrust enTrust = mexcEnTrustDelegate.queryEntrust(cancelEntrustTradeDto.getTradeNo());

        /** 更新委托状态:全撤 状态改为已撤 、部撤状态改为部分成交 **/
        if (enTrust.getTradeRemainNumber().compareTo(enTrust.getTradeNumber()) < 0) {
            enTrust.setStatus(EntrustOrderEnum.PART_CANCEL.getStatus());
        } else {
            enTrust.setStatus(EntrustOrderEnum.CANCEL.getStatus());
        }
        int rows = mexcEnTrustDelegate.updateEntrustToCancel(enTrust);
        if (rows < 1) {
            logger.error("撤单：修改委托状态失败,有可能状态已发生更改,entrust:{}", JSON.toJSONString(enTrust));
            throw new BusinessException(ResultCode.COMMON_ERROR, "修改委托状态失败,请检查委托状态是否已经更改.");
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
            throw new BusinessException(ResultCode.COMMON_ERROR, "撤单：解冻资产异常。");
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
            throw new BusinessException(ResultCode.COMMON_ERROR, "更新委托明细删除委托缓存异常.");
        }
    }


    /**
     * 获取K 线图数据
     *
     * @param tradingViewDto
     *
     * @return
     */
    public Map<String, Object> getTradingData(TradingViewDto tradingViewDto) {
        logger.debug("TradingViewDto params:{}", JSON.toJSONString(tradingViewDto));
        Map<String, Object> resultMap = new TreeMap<>();
        TradingViewVo tradingViewVo = new TradingViewVo();

        List<Long> t = new ArrayList<>();
        List<Double> o = new ArrayList<>();//开盘价 (可选)
        List<Double> h = new ArrayList<>();//最高价（可选）
        List<Double> l = new ArrayList<>();//最低价(可选)
        List<Double> c = new ArrayList<>();//收盘价
        List<Double> v = new ArrayList<>();//成交量 (可选)

        Set<String> tradingViewSet = null;
        try {
            String key;
            String resolution = tradingViewDto.getResolution();
            switch (resolution) {
                case "1": {
                    key = TradingViewConstant.TRADING_1_PREFIX + QueueKeyUtil.getKey(tradingViewDto.getMarketId(), tradingViewDto.getVcoinId());
                    break;
                }
                case "5": {
                    key = TradingViewConstant.TRADING_5_PREFIX + QueueKeyUtil.getKey(tradingViewDto.getMarketId(), tradingViewDto.getVcoinId());
                    break;
                }
                case "15": {
                    key = TradingViewConstant.TRADING_15_PREFIX + QueueKeyUtil.getKey(tradingViewDto.getMarketId(), tradingViewDto.getVcoinId());
                    break;
                }
                case "30": {
                    key = TradingViewConstant.TRADING_30_PREFIX + QueueKeyUtil.getKey(tradingViewDto.getMarketId(), tradingViewDto.getVcoinId());
                    break;
                }
                case "1h": {
                    key = TradingViewConstant.TRADING_1h_PREFIX + QueueKeyUtil.getKey(tradingViewDto.getMarketId(), tradingViewDto.getVcoinId());
                    break;
                }
                case "D": {
                    key = TradingViewConstant.TRADING_1d_PREFIX + QueueKeyUtil.getKey(tradingViewDto.getMarketId(), tradingViewDto.getVcoinId());
                    break;
                }
                case "1M": {
                    key = TradingViewConstant.TRADING_1m_PREFIX + QueueKeyUtil.getKey(tradingViewDto.getMarketId(), tradingViewDto.getVcoinId());
                    break;
                }
                default: {
                    key = TradingViewConstant.TRADING_1_PREFIX + QueueKeyUtil.getKey(tradingViewDto.getMarketId(), tradingViewDto.getVcoinId());
                    break;
                }
            }
            tradingViewSet = RedisUtil.zrangeByScore(key, tradingViewDto.getTo(), tradingViewDto.getFrom());
            logger.debug("tradingViewVo:{}", JSON.toJSONString(tradingViewVo));
        } catch (Exception e) {
            logger.error("获取数据异常", e);
            resultMap.put("s", "error");
            resultMap.put("errmsg", "redis获取数据异常");
        }

        if (CollectionUtils.isEmpty(tradingViewSet)) {
            resultMap.put("s", "no_data");
            return resultMap;
        }

        TradingViewDataVo tradingViewData;
        String[] tradingViewJsonArray = tradingViewSet.toArray(new String[]{});
        for (int index = tradingViewJsonArray.length - 1; index >= 0; index--) {
            tradingViewData = JSON.parseObject(tradingViewJsonArray[index], TradingViewDataVo.class);
            t.add(tradingViewData.getT());
            h.add(tradingViewData.getH());
            l.add(tradingViewData.getL());
            o.add(tradingViewData.getO());
            c.add(tradingViewData.getC());
            v.add(tradingViewData.getV());
        }
        resultMap.put("s", "ok");
        resultMap.put("t", t);
        resultMap.put("c", c);
        resultMap.put("o", o);
        resultMap.put("h", h);
        resultMap.put("l", l);
        resultMap.put("v", v);
        return resultMap;
    }

    @Override
    public List<MexcAssetRecharge> queryAssetRechargeList(String memberId) {
        return mexcAssetRechargeDelegate.queryAssetRecharge(memberId);
    }

    @Override
    public List<MexcAssetCash> queryAssetCashList(String memberId) {
        return mexcAssetCashDelegate.queryAssetCash(memberId);
    }

    @Override
    public List<MexcAssetCashVcoin> queryAssetCashVcoinList(String memberId) {
        return mexcAssetCashDelegate.queryAssetCashVcoin(memberId);
    }

}
