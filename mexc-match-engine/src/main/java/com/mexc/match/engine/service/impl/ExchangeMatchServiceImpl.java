package com.mexc.match.engine.service.impl;

import com.alibaba.fastjson.JSON;
import com.mexc.common.base.ResultVo;
import com.mexc.common.constant.CommonConstant;
import com.mexc.common.constant.ResCode;
import com.mexc.common.util.LogUtil;
import com.mexc.dao.delegate.vcoin.MexcEnTrustDelegate;
import com.mexc.dao.dto.order.Match.MatchOrder;
import com.mexc.dao.dto.order.Match.MatchOrderQuery;
import com.mexc.match.engine.service.IExchangeMatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangxinguang on 2018/3/3 下午3:19.
 * 撮合
 */
@Service("exchangeMatchService")
public class ExchangeMatchServiceImpl implements IExchangeMatchService {
    private Logger logger = LoggerFactory.getLogger(ExchangeMatchServiceImpl.class);

    @Autowired
    private MexcEnTrustDelegate mexcEnTrustDelegate;

    public ResultVo<List<MatchOrder>> match(MatchOrder entrustOrder) {
        logger.info(LogUtil.msg("ExchangeMatchServiceImpl:match", "收到委托请求，vo:" + JSON.toJSONString(entrustOrder)));
        ResultVo<List<MatchOrder>> resultVo = new ResultVo<>();

        /** 校验委托订单 */
        if (entrustOrder == null) {
            resultVo.setCode(ResCode.FAIL);
            resultVo.setErrMsg("委托数据为空");
            logger.info(LogUtil.msg("ExchangeMatchServiceImpl:match", "委托数据为空"));
            return resultVo;
        }
        logger.info("委托单校验成功:{}", JSON.toJSONString(entrustOrder));
        /** 查询匹配的订单 */
        List<MatchOrder> matchList = new ArrayList<>();
        try {
            MatchOrderQuery matchOrderQuery = new MatchOrderQuery();
            matchOrderQuery.setMarketId(entrustOrder.getMarketId());
            matchOrderQuery.setVcoinId(entrustOrder.getVcoinId());
            matchOrderQuery.setPrice(entrustOrder.getPrice());
            matchOrderQuery.setStatus("1,3");
            if (entrustOrder.getTradeType() == CommonConstant.BUY) {
                matchOrderQuery.setTradeType(CommonConstant.SELL);
            } else if (entrustOrder.getTradeType() == CommonConstant.SELL) {
                matchOrderQuery.setTradeType(CommonConstant.BUY);
            }
            matchList = mexcEnTrustDelegate.queryMatchEntrustOrder(matchOrderQuery);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(LogUtil.msg("ExchangeMatchServiceImpl:match", "查询撮合异常"));
        }
        logger.info("委托单匹配:{},list:{}", JSON.toJSONString(entrustOrder), JSON.toJSONString(matchList));
        /** 校验撮合结果 */
        if (CollectionUtils.isEmpty(matchList)) {
            resultVo.setCode(ResCode.SUCCESS);
            resultVo.setErrMsg("没有撮合到订单");
            logger.info(LogUtil.msg("ExchangeMatchServiceImpl:match", "没有撮合到订单"));
            return resultVo;
        }
        logger.info("委托单撮合:{}", JSON.toJSONString(entrustOrder));
        List<MatchOrder> tradedMatchOrderList = new ArrayList<>();//撮合交易返回的结果集
        BigDecimal entrustOrderTradableNumber = entrustOrder.getTradableNumber();//委托单可交易数量
        BigDecimal tradedNumber = new BigDecimal("0");//交易数量

        /** 循环吃单 */
        for (MatchOrder matchOrder : matchList) {

            /** 更新数量 */
            BigDecimal matchOrderTradableNumber = matchOrder.getTradableNumber();
            if (entrustOrderTradableNumber.compareTo(matchOrderTradableNumber) <= 0) {//如果委托单可交易数量小于撮合单可交易数量，直接撮合成功
                tradedNumber = tradedNumber.add(entrustOrderTradableNumber);
                matchOrder.setTradedNumber(entrustOrderTradableNumber);
                tradedMatchOrderList.add(matchOrder);
                break;
            } else {
                tradedNumber = tradedNumber.add(matchOrderTradableNumber);
                entrustOrderTradableNumber = entrustOrderTradableNumber.subtract(matchOrderTradableNumber);
                matchOrder.setTradedNumber(matchOrderTradableNumber);
                tradedMatchOrderList.add(matchOrder);
            }
            if (entrustOrderTradableNumber.compareTo(new BigDecimal("0")) == 0) {//为零：委托单数量撮合完了
                break;
            }
        }
        logger.info("数量匹配完成");
        /** 更新委托单交易数量 */
        entrustOrder.setTradedNumber(tradedNumber);
        logger.info("更新委托撮合结果:{}", JSON.toJSONString(entrustOrder));
        tradedMatchOrderList.add(entrustOrder);

        logger.debug(LogUtil.msg("ExchangeMatchServiceImpl:match", "本次撮合交易订单列表：" + JSON.toJSONString(tradedMatchOrderList)));
        resultVo.setCode(ResCode.SUCCESS);
        resultVo.setData(tradedMatchOrderList);

        return resultVo;
    }


}
