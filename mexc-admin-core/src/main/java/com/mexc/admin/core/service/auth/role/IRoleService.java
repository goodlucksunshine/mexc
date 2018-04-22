package com.mexc.admin.core.service.auth.role;


import com.laile.esf.common.util.Page;
import com.mexc.admin.core.service.IBaseService;
import com.mexc.dao.model.auth.role.Role;

import java.util.Set;

/**
 * Created by huangxinguang on 2017/9/26 下午3:15.
 */
public interface IRoleService extends IBaseService<Role,Integer> {

    Page<Role> queryRoleListPage(Integer currentPage, Integer showCount, String searchKey);

    void saveOrUpdate(Role role, Integer[] menuIds);

    void delete(Integer id);
}
