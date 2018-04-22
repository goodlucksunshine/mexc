package com.mexc.dao.dao.quartz;

import com.laile.esf.common.util.Page;
import com.mexc.dao.model.quartz.JobConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface JobConfigReadDao{
    /**
     * 获取所有的数据
     */
     List<JobConfig> selectAll(@Param(value = "shopIds") List<String> shopIds);

    /**
     * 根据指定条件获取数据
     */
     List<JobConfig> select(@Param(value = "jobConfig") JobConfig jobConfig, Page page, @Param(value = "shopIds") List<String> shopIds);

    /**
     * 根据名称和分组获取一个
     * @param name
     * @param group
     * @return
     */
    JobConfig  selectByNameAndGroup(@Param("jobName") String name, @Param("jobGroup") String group, @Param(value = "shopIds") List<String> shopIds);
    /**
     * 根据id获取一个
     * @param id
     * @return
     */
    JobConfig  selectById(@Param("id") Integer id, @Param(value = "shopIds") List<String> shopIds);
    /**
     * 根据key(name+group) 获取一组数据
     * @param keyList
     * @return
     */
    List<JobConfig>  selectByKey(@Param("list") List<String> keyList, @Param(value = "shopIds") List<String> shopIds);

    /**
     * 查询该条是否存在
     */
    int selectIsHave(@Param("jobName") String jobName, @Param("jobGroup") String jobGroup, @Param(value = "shopIds") List<String> shopIds);
}
