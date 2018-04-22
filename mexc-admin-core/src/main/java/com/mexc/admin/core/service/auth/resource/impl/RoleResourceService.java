package com.mexc.admin.core.service.auth.resource.impl;


import com.mexc.admin.core.service.auth.resource.IRoleResourceService;
import com.mexc.admin.core.service.impl.AbstractService;
import com.mexc.dao.delegate.AbstractDelegate;
import com.mexc.dao.model.auth.role.RoleResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huangxinguang on 2017/9/26 下午3:18.
 */
@Service
public class RoleResourceService extends AbstractService<RoleResource,Integer> implements IRoleResourceService {

    @Autowired
    @Override
    public void setBaseDelegate(AbstractDelegate<RoleResource, Integer> baseDelegate) {
        super.setBaseDelegate(baseDelegate);
    }
}
