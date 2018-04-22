package com.mexc.admin.controller.auth;

import com.laile.esf.common.util.Page;
import com.mexc.admin.controller.WebController;
import com.mexc.admin.core.service.auth.resource.IResourceService;
import com.mexc.common.util.R;
import com.mexc.dao.model.auth.resource.Resource;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * Created by huangxinguang on 2017/9/27 下午2:55.
 */
@Controller
@RequestMapping("/resource")
public class ResourceController extends WebController {

    @Autowired
    private IResourceService resourceService;

    @ResponseBody
    @RequestMapping(value = "/menuTree",method = RequestMethod.GET)
    public Object menuTree() {
         return resourceService.getMenuTree(getSessionUser().getId());
    }

    @ResponseBody
    @RequestMapping(value = "/selectedRoleTree",method = RequestMethod.GET)
    public Object selectedRoleTree(Integer id)  {
        return resourceService.getSelectedRoleTree(id);
    }

    @ResponseBody
    @RequestMapping(value = "/resourceTree",method = RequestMethod.GET)
    public Object resourceTree()  {
        return resourceService.getRoleTree();
    }

    @ResponseBody
    @RequestMapping(value = "/selectedResourceTree",method = RequestMethod.GET)
    public Object selectedResourceTree(Integer id)  {
        return resourceService.getSelectedResourceTree(id);
    }

    @RequiresPermissions("resource:list")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView list(Integer currentPage,Integer showCount,String searchKey) {
        ModelAndView mav = getModelAndView();
        Page<Resource> page = resourceService.queryResourceListPage(currentPage,showCount,searchKey);
        mav.addObject("page",page);
        mav.setViewName("/auth/resource/list");
        return mav;
    }

    @RequiresPermissions("resource:add")
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public ModelAndView add() {
        ModelAndView mav = getModelAndView();
        mav.setViewName("/auth/resource/add");
        return mav;
    }

    @RequiresPermissions("resource:edit")
    @RequestMapping(value = "/edit",method = RequestMethod.GET)
    public ModelAndView edit(Integer id) {
        ModelAndView mav = getModelAndView();
        Resource resource = resourceService.selectByPrimaryKey(id);
        mav.addObject("resource",resource);
        mav.setViewName("/auth/resource/edit");
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/saveOrUpdate")
    public Object saveOrUpdate(Resource resource) {
        if(resource.getId() == null) {
            resource.setParentId(resource.getParentId());
            resource.setCreateBy(getSessionUser().getAdminName());
            resource.setCreateTime(new Date());
            resource.setStatus(1);
        }else {
            resource.setParentId(resource.getParentId());
            resource.setUpdateBy(getSessionUser().getAdminName());
            resource.setUpdateTime(new Date());
        }
        resourceService.saveOrUpdate(resource);
        return R.ok();
    }
}
