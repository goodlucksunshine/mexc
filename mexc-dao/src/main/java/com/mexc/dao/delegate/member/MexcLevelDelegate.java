package com.mexc.dao.delegate.member;

import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.dao.member.MexcLevelDAO;
import com.mexc.dao.delegate.AbstractDelegate;
import com.mexc.dao.model.member.MexcLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by huangxinguang on 2017/12/15 上午10:27.
 */
@Transactional
@Component
public class MexcLevelDelegate extends AbstractDelegate<MexcLevel, String> {
    @Resource
    MexcLevelDAO mexcLevelDAO;


    @Autowired
    @Override
    public void setBaseMapper(IBaseDAO<MexcLevel, String> baseMapper) {
        super.setBaseMapper(baseMapper);
    }


    public MexcLevel queryByLevel(Integer level) {
        MexcLevel mexcLevel = new MexcLevel();
        mexcLevel.setLevel(level);
        return mexcLevelDAO.selectOne(mexcLevel);
    }

}
