package com.mexc.dao.delegate.plat;

import com.mexc.common.plugin.SqlCondition;
import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.dao.plat.PlatAssetDAO;
import com.mexc.dao.delegate.AbstractDelegate;
import com.mexc.dao.model.plat.MexcPlatAsset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by huangxinguang on 2018/1/2 下午8:16.
 */
@Component
@Transactional
public class PlatAssetDelegate extends AbstractDelegate<MexcPlatAsset,String> {

    @Autowired
    private PlatAssetDAO platAssetDAO;

    @Autowired
    @Override
    public void setBaseMapper(IBaseDAO<MexcPlatAsset, String> baseMapper) {
        super.setBaseMapper(baseMapper);
    }

    public MexcPlatAsset queryPlatAsset(String vcoinId) {
        SqlCondition condition = new SqlCondition();
        condition.put(" where vcoin_id=",vcoinId);
        List<MexcPlatAsset> list = platAssetDAO.selectByCondition(condition);
        if(!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }




    public int assetIncome(String platAssetId, BigDecimal amount) {
        return platAssetDAO.assetIncome(platAssetId,amount);
    }

    public int assetOutcome(String platAssetId,BigDecimal amount) {
        return platAssetDAO.assetOutcome(platAssetId,amount);
    }
}
