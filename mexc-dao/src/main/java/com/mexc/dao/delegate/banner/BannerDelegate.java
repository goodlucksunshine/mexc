package com.mexc.dao.delegate.banner;

import com.laile.esf.common.util.Page;
import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.dao.banner.BannerDAO;
import com.mexc.dao.delegate.AbstractDelegate;
import com.mexc.dao.model.banner.MexcBanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by huangxinguang on 2018/1/25 下午4:02.
 */
@Transactional
@Component
public class BannerDelegate extends AbstractDelegate<MexcBanner,String> {

    @Autowired
    private BannerDAO bannerDAO;

    @Autowired
    @Override
    public void setBaseMapper(IBaseDAO<MexcBanner, String> baseMapper) {
        super.setBaseMapper(baseMapper);
    }

    public Page<MexcBanner> queryBannerPage(Integer currentPage,Integer showCount,String searchKey) {
        Page<MexcBanner> page = new Page<>(currentPage,showCount);
        List<MexcBanner> list = bannerDAO.queryBannerPage(page,searchKey);
        page.setResultList(list);
        return page;
    }

    public List<MexcBanner> queryBannerLimit(Integer limit) {
        return bannerDAO.queryBannerLimit(limit);
    }
}
