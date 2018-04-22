package com.mexc.dao.model.vcoin;

public class MexcVcoinConfig {
    private Integer sysRechargeBlock;


    private Integer sysCashBlock;


    private String thresholdHotToCold;

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }
    public Integer getSysRechargeBlock() {
        return sysRechargeBlock;
    }


    public void setSysRechargeBlock(Integer sysRechargeBlock) {
        this.sysRechargeBlock = sysRechargeBlock;
    }


    public Integer getSysCashBlock() {
        return sysCashBlock;
    }


    public void setSysCashBlock(Integer sysCashBlock) {
        this.sysCashBlock = sysCashBlock;
    }


    public String getThresholdHotToCold() {
        return thresholdHotToCold;
    }


    public void setThresholdHotToCold(String thresholdHotToCold) {
        this.thresholdHotToCold = thresholdHotToCold;
    }
}
