package com.mexc.admin.core.service.asset.impl;

import com.alibaba.fastjson.JSON;
import com.laile.esf.common.util.DateUtil;
import com.laile.esf.common.util.Page;
import com.laile.esf.common.util.RandomUtil;
import com.mexc.admin.core.service.asset.IAssetCashService;
import com.mexc.common.util.LogUtil;
import com.mexc.dao.delegate.wallet.MexcAssetCashDelegate;
import com.mexc.dao.dto.asset.AssetCashDto;
import com.mexc.dao.vo.asset.AssetCashVo;
import com.mexc.mq.core.IMqProducerService;
import com.mexc.mq.util.FastJsonMessageConverter;
import com.mexc.mq.vo.MqMsgVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangxinguang on 2018/1/22 下午5:55.
 */
@Service
public class AssetCashService implements IAssetCashService {
    private static Logger logger = LoggerFactory.getLogger(AssetCashService.class);
    @Autowired
    private MexcAssetCashDelegate mexcAssetCashDelegate;
    @Resource
    IMqProducerService mqProducerService;
    @Resource
    FastJsonMessageConverter fastJsonMessageConverter;

    private String cashQue="cash";

    @Override
    public Page<AssetCashVo> queryAssetCashPage(AssetCashDto assetCashDto) {
        return mexcAssetCashDelegate.queryAssetCashPage(assetCashDto);
    }

    @Override
    public Page<AssetCashVo> queryAssetCashPageByCondition(AssetCashDto assetCashDto) {
        if (null != assetCashDto && null != assetCashDto.getSearchMethod()) {
            if (assetCashDto.getSearchMethod().equals("1")) {
                assetCashDto.setStartTime(null);
                assetCashDto.setEndTime(null);
            }
            if (assetCashDto.getSearchMethod().equals("2")) {
                assetCashDto.setMonth("");
            }
        }
        return mexcAssetCashDelegate.queryAssetCashPageByCondition(assetCashDto);
    }

    @Override
    public String cashReSend(String cashId) {
        int result = mexcAssetCashDelegate.cashResend(cashId);
        if (result < 1) {
            return "更新提现订单状态失败,请检查订单状态是否已经发生变化";
        }
        MqMsgVo<Map<String, String>> mqMsgVo = new MqMsgVo<>();
        Map<String, String> map = new HashMap<>();
        map.put("cashId", cashId);
        mqMsgVo.setMsgId(RandomUtil.randomStr(8))
                .setContent(map)
                .setInsertTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        logger.info(LogUtil.msg("UserServiceImpl:creatAsset", "异步发送创建用户资产队列，data:" + JSON.toJSONString(mqMsgVo)));
        mqProducerService.convertAndSend(cashQue, fastJsonMessageConverter.sendMessage(mqMsgVo));
        logger.info(LogUtil.msg("UserServiceImpl:creatAsset", "异步发送创建用户资产队列，data:" + JSON.toJSONString(mqMsgVo)));
        return "success";
    }
}
