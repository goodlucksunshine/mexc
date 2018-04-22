package com.mexc.dao.vo.admin;


import com.mexc.dao.model.auth.admin.Admin;

/**
 * Created by huangxinguang on 2017/10/13 下午7:02.
 */
public class AdminVo extends Admin {

    private String roleCode;

    private String roleName;

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
