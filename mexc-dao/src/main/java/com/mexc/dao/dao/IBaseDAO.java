package com.mexc.dao.dao;

import com.laile.esf.common.util.Page;
import com.mexc.common.plugin.SqlCondition;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;


/**
 * Class Describe
 * <p/>
 * User: yangguang Date: 17/6/10 Time: 下午1:30
 */
public interface IBaseDAO<T, PK extends Serializable> {

    int insertSelective(T record);

    T selectByPrimaryKey(PK id);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKeyWithBLOBs(T record);

    int updateByPrimaryKey(T record);

    /**
     * 按条件查询记录
     *
     * @param param
     *            查询条件参数，包括WHERE条件、分页条件、排序条件
     * @return 符合条件记录的实体对象的List
     */
    List<T> selectByCondition(@Param("map") SqlCondition param);

    /**
     * 查询整表总记录数
     *
     * @return 整表总记录数
     */
    Long countAll();

    /**
     * 新增实体
     * 
     * @param entity
     * @return 影响记录条数
     */
    int insert(T entity);

    /*******/

    /**
     * 修改符合条件的记录
     * <p>
     * 此方法特别适合于一次性把多条记录的某些字段值设置为新值（定值）的情况，比如修改符合条件的记录的状态字段
     * </p>
     * <p>
     * 此方法的另一个用途是把一条记录的个别字段的值修改为新值（定值），此时要把条件设置为该记录的主键
     * </p>
     * 
     * @param param
     *            用于产生SQL的参数值，包括WHERE条件、目标字段和新值等
     * @return 修改的记录个数，用于判断修改是否成功
     */
    int updateByParam(@Param("record") T record, @Param("map") SqlCondition param);

    /**
     * 按主键删除记录
     * 
     * @param primaryKey
     *            主键对象
     * @return 删除的对象个数，正常情况=1
     */
    int deleteByPrimaryKey(PK primaryKey);

    /**
     * 删除符合条件的记录
     * <p>
     * <strong>此方法一定要慎用，如果条件设置不当，可能会删除有用的记录！</strong>
     * </p>
     * 
     * @param param
     *            用于产生SQL的参数值，包括WHERE条件（其他参数内容不起作用）
     * @return
     */
    int deleteByParam(@Param("map") SqlCondition param);

    /**
     * 清空表，比delete具有更高的效率，而且是从数据库中物理删除（delete是逻辑删除，被删除的记录依然占有空间）
     * <p>
     * <strong>此方法一定要慎用！</strong>
     * </p>
     * 
     * @return
     */
    int truncate();

    /**
     * 查询符合条件的记录数
     * 
     * @param param
     *            查询条件参数，包括WHERE条件（其他参数内容不起作用）。此参数设置为null，则相当于count()
     * @return
     */
    Long countByParam(@Param("map") SqlCondition param);

    /**
     * 取全部记录
     * 
     * @return 全部记录实体对象的List
     */
    List<T> selectAll();

    /**
     * 按条件查询记录，并处理成分页结果
     * 
     * @param param
     *            查询条件参数，包括WHERE条件、分页条件、排序条件
     * @return PaginationResult对象，包括（符合条件的）总记录数、页实体对象List等
     */
    List<T> selectPagination(Page<T> param, @Param("map") SqlCondition condition);

    /**
     * @author liuxingmi
     * @datetime 2017年7月27日 下午10:18:47
     * @desc 根据model查询单个数据
     * @param record
     * @return T
     */
    T selectOne(@Param("record") T record);
    
    /**
     * @author liuxingmi
     * @datetime 2017年7月27日 下午10:18:57
     * @desc 查询多条数据
     * @param record
     * @return List<T>
     */
    List<T> selectList(@Param("record") T record);
    /**
     * 批量插入
     * 
     * @param list
     */
    int insertBatch(List<T> list);

}
