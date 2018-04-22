package com.mexc.dao.dao.banner;

import com.laile.esf.common.util.Page;
import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.model.banner.MexcBanner;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by huangxinguang on 2018/1/25 下午4:00.
 */
@Repository
public interface BannerDAO extends IBaseDAO<MexcBanner,String> {

    List<MexcBanner> queryBannerPage(@Param("page")Page<MexcBanner> page,@Param("searchKey") String searchKey);

    List<MexcBanner> queryBannerLimit(@Param("limit") Integer limit);

}
