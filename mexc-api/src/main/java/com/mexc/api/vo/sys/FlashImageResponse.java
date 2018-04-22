package com.mexc.api.vo.sys;

import com.mexc.api.vo.BaseResponse;

/**
 * Created by huangxinguang on 2018/2/6 下午7:30.
 */
public class FlashImageResponse extends BaseResponse {

    private String flashImagePath;

    public String getFlashImagePath() {
        return flashImagePath;
    }

    public void setFlashImagePath(String flashImagePath) {
        this.flashImagePath = flashImagePath;
    }
}
