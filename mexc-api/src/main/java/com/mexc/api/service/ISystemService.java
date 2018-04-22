package com.mexc.api.service;

import com.mexc.api.vo.sys.FlashImageResponse;
import com.mexc.api.vo.sys.VersionResponse;

/**
 * Created by huangxinguang on 2018/2/6 下午7:26.
 */
public interface ISystemService {
    /**
     * 获取版本信息
     * @return
     */
    VersionResponse getVersion();

    /**
     * 获取闪屏图片
     * @return
     */
    FlashImageResponse getFlashImage();
}
