package com.mexc.admin.core.service.banner.impl;

import com.laile.esf.common.util.Page;
import com.laile.esf.common.util.StringUtil;
import com.mexc.admin.core.service.banner.IBannerService;
import com.mexc.common.util.UUIDUtil;
import com.mexc.dao.delegate.banner.BannerDelegate;
import com.mexc.dao.model.banner.MexcBanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huangxinguang on 2018/1/25 下午4:13.
 */
@Service
public class BannerService implements IBannerService {

    @Autowired
    private BannerDelegate bannerDelegate;

    @Override
    public Page<MexcBanner> queryBannerPage(Integer currentPage, Integer showCount, String searchKey) {
        return bannerDelegate.queryBannerPage(currentPage,showCount,searchKey);
    }

    @Override
    public MexcBanner queryBanner(String bannerId) {
        return bannerDelegate.selectByPrimaryKey(bannerId);
    }

    @Override
    public int saveOrUpdate(MexcBanner banner) {
        if(StringUtil.isEmpty(banner.getId())) {
            banner.setId(UUIDUtil.get32UUID());
            return bannerDelegate.insert(banner);
        }else {
            return bannerDelegate.updateByPrimaryKeySelective(banner);
        }
    }

    @Override
    public int delete(String bannerId) {
        return bannerDelegate.deleteByPrimaryKey(bannerId);
    }
}
