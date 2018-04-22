package com.mexc.dao.delegate.auth.resource;

import com.laile.esf.common.util.Page;
import com.mexc.common.plugin.SqlCondition;
import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.dao.auth.resource.ResourceDAO;
import com.mexc.dao.delegate.AbstractDelegate;
import com.mexc.dao.model.auth.resource.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

/**
 * Created by huangxinguang on 2017/9/26 下午3:06.
 */
@Transactional
@Component
public class ResourceDelegate extends AbstractDelegate<Resource,Integer> {

    @Autowired
    private ResourceDAO resourceDAO;

    @Autowired
    @Override
    public void setBaseMapper(IBaseDAO<Resource, Integer> baseMapper) {
        super.setBaseMapper(baseMapper);
    }


    public List<Resource> getAllMenuList() {
        SqlCondition condition = new SqlCondition();
        condition.put(" where type=",1);
        condition.put(" order by sort");
        List<Resource> resourceList = resourceDAO.selectByCondition(condition);
        return resourceList;
    }

    public Set<String> queryResourceCodes(Integer id) {
        if(id.intValue() == 1) {//超级管理员
            return resourceDAO.queryAllResourceCodes();
        }else {
            return resourceDAO.queryResourceCodes(id);
        }
    }

    public List<Resource> queryResource(Integer id) {
        return resourceDAO.queryResource(id);
    }

    public Page<Resource> queryResourceListPage(Integer currentPage, Integer showCount, String searchKey) {
        if(currentPage == null) {
            currentPage = 1;
        }
        Page<Resource> page = new Page<>(currentPage,showCount);
        SqlCondition condition = new SqlCondition();

        if(!StringUtils.isEmpty(searchKey)) {
            condition.put(" where name like ","'%"+searchKey+"%'");
        }
        condition.put(" order by code");
        resourceDAO.selectPagination(page,condition);
        return page;
    }

}
