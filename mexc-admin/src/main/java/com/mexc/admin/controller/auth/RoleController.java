package com.mexc.admin.controller.auth;

import com.laile.esf.common.util.Page;
import com.mexc.admin.controller.WebController;
import com.mexc.admin.core.service.auth.role.IRoleService;
import com.mexc.common.util.R;
import com.mexc.dao.model.auth.role.Role;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * Created by huangxinguang on 2017/10/13 下午1:42.
 */
@Controller
@RequestMapping(value = "/role")
public class RoleController extends WebController {

    @Autowired
    private IRoleService roleService;

    @RequiresPermissions("role:list")
    @RequestMapping(value = "/list")
    public ModelAndView list(Integer currentPage, Integer showCount, String searchKey) {
        ModelAndView mav = getModelAndView();
        Page<Role> page = roleService.queryRoleListPage(currentPage, showCount, searchKey);
        mav.addObject("page", page);
        mav.addObject("searchKey", searchKey);
        mav.setViewName("/auth/role/list");
        return mav;
    }

    @RequiresPermissions("role:add")
    @RequestMapping(value = "/add")
    public ModelAndView add() {
        ModelAndView mav = getModelAndView();
        mav.setViewName("/auth/role/add");
        return mav;
    }

    @RequiresPermissions("role:edit")
    @RequestMapping(value = "/edit")
    public ModelAndView edit(Integer id) {
        ModelAndView mav = getModelAndView();
        Role role = roleService.selectByPrimaryKey(id);
        mav.addObject("role", role);
        mav.setViewName("/auth/role/edit");
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/saveOrUpdate")
    public Object saveOrUpdate(Role role, Integer[] menuIds) {
        if (role.getId() == null) {
            role.setCreateBy(getSessionUser().getAdminName());
            role.setCreateTime(new Date());
            role.setStatus(1);
        } else {
            role.setUpdateBy(getSessionUser().getAdminName());
            role.setUpdateTime(new Date());
        }
        roleService.saveOrUpdate(role, menuIds);
        return R.ok();
    }

    @RequiresPermissions("role:del")
    @ResponseBody
    @RequestMapping(value = "/del")
    public Object del(Integer id) {
        roleService.delete(id);
        return R.ok();
    }
}
