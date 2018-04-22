package com.mexc.dao.dto.tradingview;

import java.util.Date;

/**
 * Created by huangxinguang on 2018/1/9 下午7:22.
 * 按时间段查询
 */
public class TradingQueryDto {
    /**
     * 市场ID
     */
    public String marketId;
    /**
     * 币种ID
     */
    public String vcoinId;
    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 最右侧：unix timestamp (UTC) or rightmost required bar
     */
    private Date endTime;

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public String getVcoinId() {
        return vcoinId;
    }

    public void setVcoinId(String vcoinId) {
        this.vcoinId = vcoinId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
