package com.mexc.api.service.impl;

import com.mexc.api.service.ISystemService;
import com.mexc.api.vo.sys.FlashImageResponse;
import com.mexc.api.vo.sys.VersionResponse;
import org.springframework.stereotype.Service;

/**
 * Created by huangxinguang on 2018/2/6 下午7:27.
 */
@Service
public class SystemService implements ISystemService {
    @Override
    public VersionResponse getVersion() {
        return null;
    }

    @Override
    public FlashImageResponse getFlashImage() {
        return null;
    }
}
