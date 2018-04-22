package com.mexc.web.core.service.banner.impl;

import com.mexc.dao.delegate.banner.BannerDelegate;
import com.mexc.dao.model.banner.MexcBanner;
import com.mexc.web.core.service.banner.IBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangxinguang on 2018/1/25 下午5:40.
 */
@Service
public class BannerService implements IBannerService {

    @Autowired
    private BannerDelegate bannerDelegate;

    @Override
    public List<MexcBanner> queryBannerLimit(Integer limit) {
        return bannerDelegate.queryBannerLimit(limit);
    }
}
