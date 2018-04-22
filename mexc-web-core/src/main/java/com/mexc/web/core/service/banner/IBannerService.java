package com.mexc.web.core.service.banner;

import com.mexc.dao.model.banner.MexcBanner;

import java.util.List;

/**
 * Created by huangxinguang on 2018/1/25 下午5:40.
 */
public interface IBannerService {

    List<MexcBanner> queryBannerLimit(Integer limit);
}
