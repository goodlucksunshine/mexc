package com.mexc.api.vo.sys;

import com.mexc.api.vo.BaseResponse;

/**
 * Created by huangxinguang on 2018/2/6 下午7:28.
 */
public class VersionResponse extends BaseResponse {
    private String versionNo;

    private String versionDesc;

    private String versionTime;

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    public String getVersionDesc() {
        return versionDesc;
    }

    public void setVersionDesc(String versionDesc) {
        this.versionDesc = versionDesc;
    }

    public String getVersionTime() {
        return versionTime;
    }

    public void setVersionTime(String versionTime) {
        this.versionTime = versionTime;
    }
}
