package com.mexc.admin.core.service.vcoin.impl;

import com.mexc.admin.core.service.impl.AbstractService;
import com.mexc.admin.core.service.vcoin.IVCoinFeeService;
import com.mexc.dao.delegate.AbstractDelegate;
import com.mexc.dao.delegate.vcoin.VCoinFeeDelegate;
import com.mexc.dao.model.vcoin.MexcVCoinFee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huangxinguang on 2017/11/27 上午11:02.
 */
@Service
public class VCoinFeeService extends AbstractService<MexcVCoinFee,String> implements IVCoinFeeService {

    @Autowired
    private VCoinFeeDelegate vCoinFeeDelegate;

    @Autowired
    @Override
    public void setBaseDelegate(AbstractDelegate<MexcVCoinFee, String> baseDelegate) {
        super.setBaseDelegate(baseDelegate);
    }

    @Override
    public MexcVCoinFee queryVCoinFee(String vcoinId) {
        MexcVCoinFee mexcVCoinFee = new MexcVCoinFee();
        mexcVCoinFee.setVcoinId(vcoinId);
        return vCoinFeeDelegate.selectOne(mexcVCoinFee);
    }

}
