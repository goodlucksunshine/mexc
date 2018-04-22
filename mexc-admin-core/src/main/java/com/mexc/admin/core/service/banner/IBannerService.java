package com.mexc.admin.core.service.banner;

import com.laile.esf.common.util.Page;
import com.mexc.dao.model.banner.MexcBanner;

/**
 * Created by huangxinguang on 2018/1/25 下午4:12.
 */
public interface IBannerService {
    /**
     * banner列表
     * @param currentPage
     * @param showCount
     * @param searchKey
     * @return
     */
    Page<MexcBanner> queryBannerPage(Integer currentPage, Integer showCount, String searchKey);

    MexcBanner queryBanner(String bannerId);

    int saveOrUpdate(MexcBanner banner);

    int delete(String bannerId);
}
