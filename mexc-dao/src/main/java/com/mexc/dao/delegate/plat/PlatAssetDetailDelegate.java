package com.mexc.dao.delegate.plat;

import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.delegate.AbstractDelegate;
import com.mexc.dao.model.plat.MexcPlatAssetDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by huangxinguang on 2018/1/2 下午8:18.
 */
@Component
@Transactional
public class PlatAssetDetailDelegate extends AbstractDelegate<MexcPlatAssetDetail,String>{

    @Autowired
    @Override
    public void setBaseMapper(IBaseDAO<MexcPlatAssetDetail, String> baseMapper) {
        super.setBaseMapper(baseMapper);
    }
}
