package com.mexc.dao.delegate.common;

import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.delegate.AbstractDelegate;
import com.mexc.dao.model.common.MexcEmailPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by huangxinguang on 2018/3/5 下午5:51.
 */
@Component
@Transactional
public class MexcEmailPoolDelegate extends AbstractDelegate<MexcEmailPool,String> {

    @Autowired
    @Override
    public void setBaseMapper(IBaseDAO<MexcEmailPool, String> baseMapper) {
        super.setBaseMapper(baseMapper);
    }

}
