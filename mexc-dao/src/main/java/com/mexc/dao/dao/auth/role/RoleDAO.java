package com.mexc.dao.dao.auth.role;

import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.model.auth.role.Role;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Created by huangxinguang on 2017/9/26 下午2:40.
 */
@Repository
public interface RoleDAO extends IBaseDAO<Role,Integer> {
    /**
     * 查询管理角色编码
     * @param id
     * @return
     */
    Set<String> queryAdminRoleCodes(Integer id);
}
