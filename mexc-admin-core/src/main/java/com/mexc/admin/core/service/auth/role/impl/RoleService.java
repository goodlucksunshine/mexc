package com.mexc.admin.core.service.auth.role.impl;

import com.laile.esf.common.util.Page;
import com.mexc.admin.core.service.auth.role.IRoleService;
import com.mexc.admin.core.service.impl.AbstractService;
import com.mexc.common.exception.TipsException;
import com.mexc.dao.delegate.AbstractDelegate;
import com.mexc.dao.delegate.auth.role.RoleDelegate;
import com.mexc.dao.model.auth.role.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by huangxinguang on 2017/9/26 下午3:16.
 */
@Service
public class RoleService extends AbstractService<Role,Integer> implements IRoleService {
    @Autowired
    private RoleDelegate roleDelegate;

    @Override
    public Page<Role> queryRoleListPage(Integer currentPage, Integer showCount, String searchKey) {
        return roleDelegate.queryRoleListPage(currentPage,showCount,searchKey);
    }

    @Override
    public void saveOrUpdate(Role role, Integer[] menuIds) {
        if (role.getId() == null) {
            Boolean exist = roleDelegate.querRoleCodeExist(role.getRoleCode());
            if(exist) {
                throw new TipsException("角色编码已经存在");
            }
            roleDelegate.saveRole(role, menuIds);
        } else {
            roleDelegate.updateRole(role, menuIds);
        }
    }

    @Override
    public void delete(Integer id) {
        roleDelegate.delete(id);
    }

    @Autowired
    @Override
    public void setBaseDelegate(AbstractDelegate<Role, Integer> baseDelegate) {
        super.setBaseDelegate(baseDelegate);
    }
}
