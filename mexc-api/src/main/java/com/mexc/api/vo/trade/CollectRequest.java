package com.mexc.api.vo.trade;


/**
 * Created by huangxinguang on 2018/2/6 上午9:55.
 */
public class CollectRequest  {
    private String userId;
    /**
     * 交易对
     */
    private String bargainId;
    /**
     * 是否收藏
     */
    private Integer isCollected;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBargainId() {
        return bargainId;
    }

    public void setBargainId(String bargainId) {
        this.bargainId = bargainId;
    }

    public Integer getIsCollected() {
        return isCollected;
    }

    public void setIsCollected(Integer isCollected) {
        this.isCollected = isCollected;
    }
}
