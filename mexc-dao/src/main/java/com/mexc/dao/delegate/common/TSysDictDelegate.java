package com.mexc.dao.delegate.common;

import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.dao.common.TSysDictDao;
import com.mexc.dao.delegate.AbstractDelegate;
import com.mexc.dao.model.common.TSysDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Transactional
@Component
public class TSysDictDelegate extends AbstractDelegate<TSysDict,Integer> {

    @Resource
    private TSysDictDao tSysDictMapper;

    @Autowired
    @Override
    public void setBaseMapper(IBaseDAO<TSysDict, Integer> baseMapper) {
        super.setBaseMapper(baseMapper);
    }

    @Override
    public List<TSysDict> selectAll() {

        List<TSysDict> res =  tSysDictMapper.selectAll();
        return res;
    }
}
