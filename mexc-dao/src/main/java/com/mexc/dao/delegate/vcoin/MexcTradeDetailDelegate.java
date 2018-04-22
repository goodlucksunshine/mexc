package com.mexc.dao.delegate.vcoin;

import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.delegate.AbstractDelegate;
import com.mexc.dao.model.vcoin.MexcTradeDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by huangxinguang on 2017/12/27 下午3:10.
 */
@Component
@Transactional
public class MexcTradeDetailDelegate extends AbstractDelegate<MexcTradeDetail,String> {

    @Autowired
    @Override
    public void setBaseMapper(IBaseDAO<MexcTradeDetail, String> baseMapper) {
        super.setBaseMapper(baseMapper);
    }

}
