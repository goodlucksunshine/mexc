package com.mexc.dao.dto.admin;


import com.mexc.dao.model.auth.admin.Admin;

/**
 * Created by huangxinguang on 2017/10/14 上午10:24.
 */
public class AdminDto extends Admin {

    private Integer roleId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
