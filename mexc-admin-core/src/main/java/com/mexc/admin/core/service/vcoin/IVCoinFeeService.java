package com.mexc.admin.core.service.vcoin;

import com.mexc.admin.core.service.IBaseService;
import com.mexc.dao.model.vcoin.MexcVCoinFee;

/**
 * Created by huangxinguang on 2017/11/27 上午11:02.
 */
public interface IVCoinFeeService extends IBaseService<MexcVCoinFee,String> {

    MexcVCoinFee queryVCoinFee(String vcoinId);
}
