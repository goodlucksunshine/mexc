package com.mexc.dao.delegate.vcoin;

import com.mexc.common.plugin.SqlCondition;
import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.dao.vcoin.MexcVCoinFeeDAO;
import com.mexc.dao.delegate.AbstractDelegate;
import com.mexc.dao.model.vcoin.MexcVCoinFee;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by huangxinguang on 2017/11/27 上午11:07.
 */
@Component
@Transactional
public class VCoinFeeDelegate extends AbstractDelegate<MexcVCoinFee, String> {
    @Resource
    MexcVCoinFeeDAO mexcVCoinFeeDAO;

    @Autowired
    @Override
    public void setBaseMapper(IBaseDAO<MexcVCoinFee, String> baseMapper) {
        super.setBaseMapper(baseMapper);
    }


    public MexcVCoinFee queryVcoinFeeByVcoinId(String vcoinId) {
        SqlCondition condition = new SqlCondition();
        condition.put("where vcoin_id=", vcoinId);
        List<MexcVCoinFee> vCoinFees = mexcVCoinFeeDAO.selectByCondition(condition);
        if (CollectionUtils.isNotEmpty(vCoinFees)) {
            return vCoinFees.get(0);
        } else {
            return null;
        }
    }

}
