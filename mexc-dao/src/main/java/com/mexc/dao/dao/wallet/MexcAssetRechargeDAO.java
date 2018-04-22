package com.mexc.dao.dao.wallet;

import com.laile.esf.common.util.Page;
import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.dto.asset.AssetRechargeDto;
import com.mexc.dao.model.wallet.MexcAssetRecharge;
import com.mexc.dao.vo.asset.AssetRechargeVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by huangxinguang on 2018/1/16 下午9:21.
 */
@Repository
public interface MexcAssetRechargeDAO extends IBaseDAO<MexcAssetRecharge,String> {

    List<MexcAssetRecharge> queryAssetRecharge(@Param("memberId") String memberId);

    /**
     * 查询充值记录
     */
    List<AssetRechargeVo> queryAssetRechargePage(@Param("page")Page<AssetRechargeVo> page, @Param("query") AssetRechargeDto rechargeDto);

    List<AssetRechargeVo> queryAssetRechargeByCondition(@Param("page")Page<AssetRechargeVo> page, @Param("query") AssetRechargeDto rechargeDto);
}
