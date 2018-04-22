package com.mexc.dao.delegate.wallet;

import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.dao.wallet.MexcEthHotTransDAO;
import com.mexc.dao.delegate.AbstractDelegate;
import com.mexc.dao.model.wallet.MexcEthHotTrans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2017/12/30
 * Time: 下午10:54
 */
@Transactional
@Component
public class MexcEthHotTransDelegate extends AbstractDelegate<MexcEthHotTrans, String> {


    @Autowired
    MexcEthHotTransDAO mexcEthHotTransDAO;

    @Autowired
    @Override
    public void setBaseMapper(IBaseDAO<MexcEthHotTrans, String> baseMapper) {
        super.setBaseMapper(baseMapper);
    }
}
