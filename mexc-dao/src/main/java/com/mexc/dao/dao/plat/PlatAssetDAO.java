package com.mexc.dao.dao.plat;

import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.model.plat.MexcPlatAsset;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * Created by huangxinguang on 2018/1/2 下午8:14.
 */
@Repository
public interface PlatAssetDAO extends IBaseDAO<MexcPlatAsset,String> {

    /**
     * 进账
     * @param platAssetId 平台资产ID
     * @param incomeValue 收入金额
     * @return
     */
    int assetIncome(@Param("platAssetId") String platAssetId, @Param("incomeValue") BigDecimal incomeValue);

    /**
     * 出账
     * @param platAssetId 平台资产ID
     * @param outcomeValue 出账金额
     * @return
     */
    int assetOutcome(@Param("platAssetId") String platAssetId, @Param("outcomeValue") BigDecimal outcomeValue);
}
