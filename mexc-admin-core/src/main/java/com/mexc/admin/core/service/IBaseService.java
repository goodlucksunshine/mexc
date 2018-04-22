package com.mexc.admin.core.service;

import java.io.Serializable;
import java.util.List;

/**
 * Class Describe
 * <p>
 * User: yangguang Date: 17/7/21 Time: 下午3:50
 */
public interface IBaseService<T, PK extends Serializable> {
    T selectByPrimaryKey(PK id);

    int deleteByPrimaryKey(PK id);

    int insertSelective(T record);

    List<T> selectAll();

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKeyWithBLOBs(T record);

    int updateByPrimaryKey(T record);

}
