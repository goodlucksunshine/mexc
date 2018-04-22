package com.mexc.dao.delegate.vcoin;

import com.mexc.common.plugin.SqlCondition;
import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.dao.vcoin.MexcAddressLibDAO;
import com.mexc.dao.delegate.AbstractDelegate;
import com.mexc.dao.model.vcoin.MexcAddressLib;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2018/1/19
 * Time: 上午11:28
 */
@Transactional
@Component
public class MexcAddressLibDelegate extends AbstractDelegate<MexcAddressLib, String> {

    @Resource
    MexcAddressLibDAO mexcAddressLibDAO;

    @Autowired
    @Override
    public void setBaseMapper(IBaseDAO<MexcAddressLib, String> baseMapper) {
        super.setBaseMapper(baseMapper);
    }

    public List<MexcAddressLib> queryAddressLib(Integer max, Integer limit) {
        SqlCondition sqlCondition = new SqlCondition();
        sqlCondition.put(" where id>", max);
        sqlCondition.put(" limit", limit);
        return mexcAddressLibDAO.selectByCondition(sqlCondition);
    }

    public List<Map> queryUsePercent() {
        return mexcAddressLibDAO.queryUsePercent();
    }

}
