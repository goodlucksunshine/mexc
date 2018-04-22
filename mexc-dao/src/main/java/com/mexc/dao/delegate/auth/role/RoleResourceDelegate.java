package com.mexc.dao.delegate.auth.role;

import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.delegate.AbstractDelegate;
import com.mexc.dao.model.auth.role.RoleResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by huangxinguang on 2017/9/26 下午3:06.
 */
@Transactional
@Component
public class RoleResourceDelegate extends AbstractDelegate<RoleResource,Integer> {

    @Autowired
    @Override
    public void setBaseMapper(IBaseDAO<RoleResource, Integer> baseMapper) {
        super.setBaseMapper(baseMapper);
    }
}
