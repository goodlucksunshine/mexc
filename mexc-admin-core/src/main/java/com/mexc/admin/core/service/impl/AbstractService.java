package com.mexc.admin.core.service.impl;

import com.laile.esf.common.annotation.validator.Validator;
import com.laile.esf.common.util.Page;
import com.mexc.admin.core.service.IBaseService;
import com.mexc.common.plugin.SqlCondition;
import com.mexc.dao.delegate.AbstractDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;

/**
 * Class Describe
 * <p/>
 * User: yangguang Date: 17/6/21 Time: 下午2:35
 */
public abstract class AbstractService<T, PK extends Serializable> implements IBaseService<T,PK> {
    protected Logger logger= LoggerFactory.getLogger(this.getClass());


    private AbstractDelegate<T, PK> baseDelegate;

    public void setBaseDelegate(AbstractDelegate<T, PK> baseDelegate) {
        this.baseDelegate = baseDelegate;
    }


    public int deleteByPrimaryKey(PK id) {
        return baseDelegate.deleteByPrimaryKey(id);
    }

    public int insertSelective(T record) {
        Validator.valid(record);
        return baseDelegate.insertSelective(record);
    }

    public T selectByPrimaryKey(PK id) {
        return baseDelegate.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(T record) {
        return baseDelegate.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKeyWithBLOBs(T record) {
        return baseDelegate.updateByPrimaryKeyWithBLOBs(record);
    }

    public int updateByPrimaryKey(T record) {
        Validator.valid(record);
        return baseDelegate.updateByPrimaryKey(record);
    }

    public int insert(T record) {
        Validator.valid(record);
        return baseDelegate.insert(record);
    }

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
    public int updateParam(T record, SqlCondition param) {
        Validator.valid(record);
        return baseDelegate.updateParam(record, param);
    }

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
    public int deleteParam(SqlCondition param) {
        return baseDelegate.deleteParam(param);
    }

    /**
     * 清空表，比delete具有更高的效率，而且是从数据库中物理删除（delete是逻辑删除，被删除的记录依然占有空间）
     * <p>
     * <strong>此方法一定要慎用！</strong>
     * </p>
     *
     * @return
     */
    public int truncate() {
        return baseDelegate.truncate();
    }

    /**
     * 查询整表总记录数
     *
     * @return 整表总记录数
     */
    public Long countAll() {
        return baseDelegate.countAll();
    }

    /**
     * 查询符合条件的记录数
     *
     * @param sqlCondtion
     *            查询条件参数，包括WHERE条件（其他参数内容不起作用）。此参数设置为null，则相当于count()
     * @return
     */
    public Long count(SqlCondition sqlCondtion) {
        return baseDelegate.count(sqlCondtion);
    }

    /**
     * 取全部记录
     *
     * @return 全部记录实体对象的List
     */
    public List<T> selectAll() {
        return baseDelegate.selectAll();
    }

    /**
     * 按条件查询记录
     *
     * @param param
     *            查询条件参数，包括WHERE条件、分页条件、排序条件
     * @return 符合条件记录的实体对象的List
     */
    public List<T> selectByCondition(SqlCondition param) {
        return baseDelegate.selectByCondition(param);
    }

    /**
     * 按条件查询记录，并处理成分页结果
     *
     * @Param page
     *            分页对象
     * @param condition
     *            查询条件参数，包括WHERE条件、分页条件、排序条件
     * @return PaginationResult对象，包括（符合条件的）总记录数、页实体对象List等
     */
    public void selectPagination(Page<T> page, SqlCondition condition) {
        baseDelegate.selectPagination(page,condition);
    }

    /**
     * 批量插入
     *
     * @param list
     */
    public int insertBatch(final List<T> list) {
        return baseDelegate.insertBatch(list);
    }


}
