package com.mexc.dao.dao.quartz;

import com.mexc.dao.model.quartz.JobConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JobConfigWriteDao {

    /**
     * 插入一条记录
     * 
     * @param jobConfig
     * @return
     */
    int insert(JobConfig jobConfig);

    /**
     * 根据名称和分组更新一条记录
     * 
     * @param jobConfig
     * @return
     */
    int updateByNameAndGroup(JobConfig jobConfig);

    /**
     * 根据名称和分组删除一条记录
     * 
     * @param name
     * @param group
     * @return
     */
    int deleteByNameAndGroup(@Param("name") String name, @Param("group") String group);
}
