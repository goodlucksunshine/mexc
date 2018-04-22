package com.mexc.dao.vo.vcoin.trade;

import java.util.List;

/**
 * Created by huangxinguang on 2018/1/13 下午1:22.
 * 市场交易对信息 展示
 */
public class TradeGroupDataVo {
    private String marketId;

    private String marketName;

    private List<TradeGroupInfoVo> list;

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public List<TradeGroupInfoVo> getList() {
        return list;
    }

    public void setList(List<TradeGroupInfoVo> list) {
        this.list = list;
    }
}
