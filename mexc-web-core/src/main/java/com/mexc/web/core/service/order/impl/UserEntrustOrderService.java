package com.mexc.web.core.service.order.impl;

import com.alibaba.fastjson.JSON;
import com.mexc.common.constant.CommonConstant;
import com.mexc.common.util.jedis.RedisUtil;
import com.mexc.dao.delegate.member.MemberDelegate;
import com.mexc.dao.dto.order.OrderDto;
import com.mexc.dao.model.member.MexcMember;
import com.mexc.dao.model.vcoin.MexcEnTrust;
import com.mexc.web.core.service.order.IUserEntrustOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangxinguang on 2018/1/26 下午2:28.
 */
@Service
public class UserEntrustOrderService implements IUserEntrustOrderService {
    private static Logger logger = LoggerFactory.getLogger(UserEntrustOrderService.class);

    @Autowired
    private MemberDelegate memberDelegate;

    @Override
    public void addCurrentEntrustOrderCache(MexcEnTrust enTrust) {
        MexcMember member = memberDelegate.selectByPrimaryKey(enTrust.getMemberId());
        String userEntrustKey = CommonConstant.USER_CURRENT_ENTRUST + enTrust.getMarketId() + enTrust.getTradeVcoinId() + member.getAccount();
        try {
            //把用户委托单放入redis用户当前委托单列表中
            Map<String, String> entrustOrderMap = new HashMap<>();
            OrderDto orderDto = buildOrderDto(enTrust);
            entrustOrderMap.put(orderDto.getTradeNo(), JSON.toJSONString(orderDto));
            RedisUtil.hmset(userEntrustKey, entrustOrderMap);
            logger.info("委托：redis缓存：用户当前委托列表中增加，key: {}", userEntrustKey);
        }catch (Exception e) {
            logger.error("委托：添加到当前用户委托缓存异常,key {}",userEntrustKey);
        }
    }

    @Override
    public void deleteCurrentEntrustOrderCache(MexcEnTrust enTrust) {
        MexcMember member = memberDelegate.selectByPrimaryKey(enTrust.getMemberId());
        String userCurrentEntrustKey = CommonConstant.USER_CURRENT_ENTRUST + enTrust.getMarketId() + enTrust.getTradeVcoinId() + member.getAccount();
        try {
            //从用户委托单列表中删除 reids
            RedisUtil.hdel(userCurrentEntrustKey, enTrust.getTradeNo());
            logger.info("redis缓存：用户当前委托列表中删除，key: {} field:{}", userCurrentEntrustKey, enTrust.getTradeNo());
        }catch (Exception e) {
            logger.error("从删除当前用户委托缓存异常,key {}",userCurrentEntrustKey);
        }
    }

    @Override
    public void updateCurrentEntrustOrderCache(MexcEnTrust enTrust) {
        this.deleteCurrentEntrustOrderCache(enTrust);
        this.addCurrentEntrustOrderCache(enTrust);
    }

    private OrderDto buildOrderDto(MexcEnTrust enTrust) {
        OrderDto orderDto = new OrderDto();
        orderDto.setMarketId(enTrust.getMarketId());
        orderDto.setCreateTime(enTrust.getCreateTime());
        orderDto.setMarketName(enTrust.getMarketName());
        orderDto.setMemberId(enTrust.getMemberId());
        orderDto.setStatus(enTrust.getStatus().toString());
        orderDto.setTradelVcoinNameEn(enTrust.getTradelVcoinNameEn());
        orderDto.setTradeVcoinId(enTrust.getTradeVcoinId());
        orderDto.setTradeType(enTrust.getTradeType().toString());
        orderDto.setTradeNo(enTrust.getTradeNo());
        if(enTrust.getTradePrice() != null) {
            orderDto.setTradePrice(enTrust.getTradePrice().toPlainString());
        }
        if(enTrust.getTradeFee() != null) {
            orderDto.setTradeFee(enTrust.getTradeFee().toPlainString());
        }
        if(enTrust.getTradeNumber() != null) {
            orderDto.setTradeNumber(enTrust.getTradeNumber().toPlainString());
        }
        if(enTrust.getTradeAvgPrice() != null) {
            orderDto.setTradeAvgPrice(enTrust.getTradeAvgPrice().toPlainString());
        }
        if(enTrust.getTradeRemainNumber() != null) {
            orderDto.setTradeRemainNumber(enTrust.getTradeRemainNumber().toPlainString());
        }
        if(enTrust.getTradeTotalAmount() != null) {
            orderDto.setTradeTotalAmount(enTrust.getTradeTotalAmount().toPlainString());
        }
        if(enTrust.getTradeRemainAmount() != null) {
            orderDto.setTradeRemainAmount(enTrust.getTradeRemainAmount().toPlainString());
        }
        return orderDto;
    }
}
