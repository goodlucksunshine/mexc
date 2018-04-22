package com.mexc.web.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.laile.esf.common.util.StringUtil;
import com.mexc.common.constant.CommonConstant;
import com.mexc.common.util.BigDecimalUtil;
import com.mexc.common.util.jedis.RedisUtil;
import com.mexc.dao.dto.order.OrderDto;
import com.mexc.dao.dto.socket.SocketMsg;
import com.mexc.dao.model.market.MexcMarket;
import com.mexc.dao.model.vcoin.MexcVCoin;
import com.mexc.dao.vo.vcoin.entrust.EntrustOrder;
import com.mexc.dao.vo.vcoin.trade.*;
import com.mexc.match.engine.util.QueueKeyUtil;
import com.mexc.web.constant.WebConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.math.BigDecimal;
import java.util.*;

/**
 * socket处理类
 */
public class TextMessageHandler extends TextWebSocketHandler {
    private static final Logger logger = LoggerFactory.getLogger(TextMessageHandler.class);

    private static final Map<String, WebSocketSession> users;

    static {
        users = new HashMap<String, WebSocketSession>();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        /*
         * 链接成功后会触发此方法，可在此处对离线消息什么的进行处理
         */
        users.put(session.getId(), session);
        //String username = (String) session.getAttributes().get(WebConstant.DEFAULT_WEBSOCKET_USERNAME);
        logger.info("connect success");
        //logger.info(username + " connect success ...");
        //session.sendMessage(new TextMessage(username + " 链接成功!!"));
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session, message);
    }

    /*
     * 前端 websocket.send() 会触发此方法
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            //logger.debug("message -> " + message.getPayload());
            //如果登陆，拿到用户账号
            String account = null;
            Object accountObj = session.getAttributes().get(WebConstant.DEFAULT_WEBSOCKET_USERNAME);
            if (accountObj != null) {
                account = String.valueOf(accountObj);
            }
            //解析客户端发过来的消息
            SocketMsg msg = JSONObject.parseObject(message.getPayload(), SocketMsg.class);
            String responseDataJson = null;

            //从缓存中获取信息
            if (msg.getType().equals("1")) {
                responseDataJson = getEntrustOrder(msg.getMarketId(), msg.getVcoinId());
            } else if (msg.getType().equals("2")) {
                responseDataJson = getTradeGroupInfo(account);
            } else if (msg.getType().equals("3")) {
                responseDataJson = getUserCurrentEntrustOrder(msg.getMarketId(), msg.getVcoinId(), account);
            }

            //发送消息数据到客户端
            session.sendMessage(new TextMessage(responseDataJson));
        }catch (Exception e) {
            logger.error("websocket处理消息异常",e);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        users.remove(session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info("websocket connection closed......");
        users.remove(session.getId());
    }

    /**
     * 从缓存中获取委托单
     *
     * @param marketId 市场ID
     * @param vcoinId  币种ID
     *
     * @return
     */
    public String getEntrustOrder(String marketId, String vcoinId) {
        Map<String, Object> dataMap = new HashMap<>();

        //获取买
        String tradeType = CommonConstant.BUY.toString();
        String buyKey = QueueKeyUtil.getKey(marketId, vcoinId, tradeType);
        String buyJson = RedisUtil.get(CommonConstant.ENTRUST_BUY_PREFIX + buyKey);
        String buySum = RedisUtil.get(CommonConstant.ENTRUST_BUY_SUM_PREFIX + buyKey);

        if (!StringUtils.isEmpty(buyJson)) {
            List<EntrustOrder> buyOrder = JSONObject.parseArray(buyJson, EntrustOrder.class);
            dataMap.put("buyList", buyOrder);
        }
        if (!StringUtils.isEmpty(buySum)) {
            dataMap.put("buySum", BigDecimalUtil.significand(new BigDecimal(buySum),9).toPlainString());
        }

        //获取卖
        tradeType = CommonConstant.SELL.toString();
        String sellKey = QueueKeyUtil.getKey(marketId, vcoinId, tradeType);
        String sellJson = RedisUtil.get(CommonConstant.ENTRUST_SELL_PREFIX + sellKey);
        String sellSum = RedisUtil.get(CommonConstant.ENTRUST_SELL_SUM_PREFIX + sellKey);
        if (!StringUtils.isEmpty(sellJson)) {
            List<EntrustOrder> sellOrder = JSONObject.parseArray(sellJson, EntrustOrder.class);
            dataMap.put("sellList", sellOrder);
        }
        if (!StringUtils.isEmpty(buySum)) {
            dataMap.put("sellSum", BigDecimalUtil.significand(new BigDecimal(sellSum),9).toPlainString());
        }

        //获取交易记录
        String tradeKey = QueueKeyUtil.getKey(marketId, vcoinId);
        String tradeJson = RedisUtil.get(CommonConstant.TRADE_PREFIX + tradeKey);
        if (!StringUtils.isEmpty(tradeJson)) {
            List<TradeOrderVo> recentTrade = JSONObject.parseArray(tradeJson, TradeOrderVo.class);
            dataMap.put("tradeList", recentTrade);
        }

        //获取交易对行情
        String tradeGroupInfoJson = RedisUtil.get(CommonConstant.TRADE_GROUP + tradeKey);
        TradeGroupInfoVo tradeGroupInfo = JSONObject.parseObject(tradeGroupInfoJson, TradeGroupInfoVo.class);
        dataMap.put("tradeGroupInfo", tradeGroupInfo);
        dataMap.put("type", 1);

        return JSON.toJSONStringWithDateFormat(dataMap, "HH:mm:ss");
    }

    /**
     * 获取用户当前委托
     * @param marketId
     * @param vcoinId
     * @param account
     * @return
     */
    public String getUserCurrentEntrustOrder(String marketId,String vcoinId,String account) {
        Map<String,Object> dataMap = new HashMap<>();
        //从缓存中去所有，按时间排序
        List<OrderDto> currentList = new ArrayList<>();
        String userCurrentEntrustKey = CommonConstant.USER_CURRENT_ENTRUST+marketId+vcoinId+account;
        List<String> currentEntrustJsonList = RedisUtil.hvals(userCurrentEntrustKey);
        if(!CollectionUtils.isEmpty(currentEntrustJsonList)) {
            for(String currentEntrustJson : currentEntrustJsonList) {
                OrderDto orderDto = JSON.parseObject(currentEntrustJson,OrderDto.class);
                currentList.add(orderDto);
            }
        }
        Collections.sort(currentList, new Comparator<OrderDto>() {
            public int compare(OrderDto o1, OrderDto o2) {
                return -o1.getCreateTime().compareTo(o2.getCreateTime());
            }
        });
        //取前五条
        int rows = currentEntrustJsonList.size() <= 5 ? currentEntrustJsonList.size() : 5;
        List<OrderDto> topCurrentEntrustList = new ArrayList<>();
        for(int index = 0;index < rows;index ++) {
            topCurrentEntrustList.add(currentList.get(index));
        }
        dataMap.put("type",3);
        dataMap.put("currentUserEntrust",topCurrentEntrustList);
        return JSON.toJSONStringWithDateFormat(dataMap, "yyyy-MM-dd HH:mm:ss");
    }


    /**
     * 获取交易对行情
     *
     * @return
     */
    public String getTradeGroupInfo(String account) {
        Map<String, Object> dataMap = new HashMap<>();
        try {
            List<TradeGroupInfoVo> collectList = new ArrayList<>();//用户自选交易对
            List<TradeGroupDataVo> tradeGroupDataList = new ArrayList<>();//所有市场交易对行情
            String marketListJson = RedisUtil.get(CommonConstant.MARKET_LIST);
            if (!StringUtils.isEmpty(marketListJson)) {
                List<MexcMarket> marketList = JSONObject.parseArray(marketListJson, MexcMarket.class);
                for (MexcMarket market : marketList) {

                    List<TradeGroupInfoVo> marketTradeGroupList = new ArrayList<>();
                    String marketKey = QueueKeyUtil.getKey(market.getId());
                    String vcoinListJson = RedisUtil.get(CommonConstant.MARKET_VCOIN_LIST_PREFIX + marketKey);
                    List<MexcVCoin> vcoinList = JSONObject.parseArray(vcoinListJson, MexcVCoin.class);

                    if(!CollectionUtils.isEmpty(vcoinList)) {
                        for (MexcVCoin vcoin : vcoinList) {
                            String marketVcoinKey = QueueKeyUtil.getKey(market.getId(), vcoin.getId());

                            String tradeGroupInfoJson = RedisUtil.get(CommonConstant.TRADE_GROUP + marketVcoinKey);//获取交易对行情
                            TradeGroupInfoVo tradeGroupInfo = JSONObject.parseObject(tradeGroupInfoJson, TradeGroupInfoVo.class);

                            //用户登录了，添加收藏值
                            if (!StringUtil.isEmpty(account)) {
                                String userGroupKey = QueueKeyUtil.getUserGroupKey(market.getId(), vcoin.getId(), account);
                                String collect = RedisUtil.get(userGroupKey);
                                if (!StringUtil.isEmpty(collect) && collect.equals("1")) {
                                    tradeGroupInfo.setCollect(1);
                                    collectList.add(tradeGroupInfo);
                                }
                            }
                            marketTradeGroupList.add(tradeGroupInfo);
                        }
                    }
                    TradeGroupDataVo tradeGroupData = new TradeGroupDataVo();
                    tradeGroupData.setMarketName(market.getMarketName());
                    tradeGroupData.setMarketId(market.getId());
                    tradeGroupData.setList(marketTradeGroupList);
                    tradeGroupDataList.add(tradeGroupData);
                }
            }
            dataMap.put("tradeGroup", tradeGroupDataList);
            dataMap.put("collectList",collectList);
            dataMap.put("type", 2);
        }catch (Exception e){
            logger.error("获取交易对异常",e);
        }
        return JSON.toJSONString(dataMap, SerializerFeature.DisableCircularReferenceDetect);
    }
}
