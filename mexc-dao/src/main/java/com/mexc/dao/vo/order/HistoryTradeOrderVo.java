package com.mexc.dao.vo.order;

/**
 * Created by huangxinguang on 2018/3/5 上午11:27.
 * 历史成交
 */
public class HistoryTradeOrderVo {

    /***交易编号***/
    private String tradeNo;
    /***
     * 用户id
     * */
    private String memberId;
    /***
     * 市场id
     **/
    private String marketId;
    /***
     * 市场名称
     **/
    private String marketName;
    /***
     * 虚拟币id
     **/
    private String tradeVcoinId;
    /***
     * 虚拟币名称
     **/
    private String tradelVcoinNameEn;
    /***
     * trade_type:1-买，2-卖
     **/
    private String tradeType;
    /***
     * 1：已交易 0：交易中
     */
    private String status;
    /***
     * 成交时间
     **/
    private String createTime;
    /***
     * 成交均价
     */
    private String tradeAvgPrice;
    /***
     * 数量
     */
    private String tradeTotalNumber;
    /***
     * 成交总额
     */
    private String tradeTotalAmount;
    /**
     * 手续费
     */
    private String tradeTotalFee;

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

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

    public String getTradeVcoinId() {
        return tradeVcoinId;
    }

    public void setTradeVcoinId(String tradeVcoinId) {
        this.tradeVcoinId = tradeVcoinId;
    }

    public String getTradelVcoinNameEn() {
        return tradelVcoinNameEn;
    }

    public void setTradelVcoinNameEn(String tradelVcoinNameEn) {
        this.tradelVcoinNameEn = tradelVcoinNameEn;
    }

    public String getTradeAvgPrice() {
        return tradeAvgPrice;
    }

    public void setTradeAvgPrice(String tradeAvgPrice) {
        this.tradeAvgPrice = tradeAvgPrice;
    }

    public String getTradeTotalAmount() {
        return tradeTotalAmount;
    }

    public void setTradeTotalAmount(String tradeTotalAmount) {
        this.tradeTotalAmount = tradeTotalAmount;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTradeTotalNumber() {
        return tradeTotalNumber;
    }

    public void setTradeTotalNumber(String tradeTotalNumber) {
        this.tradeTotalNumber = tradeTotalNumber;
    }

    public String getTradeTotalFee() {
        return tradeTotalFee;
    }

    public void setTradeTotalFee(String tradeTotalFee) {
        this.tradeTotalFee = tradeTotalFee;
    }
}
