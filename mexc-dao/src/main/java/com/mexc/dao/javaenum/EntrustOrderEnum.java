package com.mexc.dao.javaenum;

/**
 * Created by huangxinguang on 2018/1/4 下午8:24.
 * 委托订单状态
 */
public enum EntrustOrderEnum {

    UNCOMPLETED("未完成",1),COMPLETED("已完成",2),PART_COMPLETED("部分成交",3),CANCEL("已撤交",4),PART_CANCEL("部撤",5);

    private String desc;

    private Integer status;

    EntrustOrderEnum(String desc,Integer status) {
        this.status = status;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
