package com.mexc.dao.delegate.common;

import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.delegate.AbstractDelegate;
import com.mexc.dao.model.common.MexcLoginLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by huangxinguang on 2017/12/18 下午2:23.
 */
@Component
@Transactional
public class MexcLoginLogDelegate extends AbstractDelegate<MexcLoginLog,String> {

    @Autowired
    @Override
    public void setBaseMapper(IBaseDAO<MexcLoginLog, String> baseMapper) {
        super.setBaseMapper(baseMapper);
    }
}
