package com.mexc.dao.dao.auth.resource;

import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.model.auth.resource.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Created by huangxinguang on 2017/9/26 下午2:41.
 */
@Repository
public interface ResourceDAO extends IBaseDAO<Resource,Integer> {

    /**
     * 查询用户权限资源
     * @param id
     * @return
     */
    Set<String> queryResourceCodes(Integer id);

    /**
     * 查询所有资源编码
     * @return
     */
    Set<String> queryAllResourceCodes();

    /**
     * 通过用户ID查询
     * @param id
     * @return
     */
    List<Resource> queryResource(Integer id);
}
