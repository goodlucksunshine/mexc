package com.mexc.admin.core.service.level.impl;

import com.mexc.admin.core.service.impl.AbstractService;
import com.mexc.admin.core.service.level.ILevelService;
import com.mexc.dao.delegate.AbstractDelegate;
import com.mexc.dao.model.member.MexcLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by huangxinguang on 2017/12/15 上午10:10.
 */
@Service
public class LevelService extends AbstractService<MexcLevel,String> implements ILevelService {

    @Autowired
    @Override
    public void setBaseDelegate(AbstractDelegate<MexcLevel, String> baseDelegate) {
        super.setBaseDelegate(baseDelegate);
    }
}
