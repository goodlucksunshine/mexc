package com.mexc.admin.core.service.asset;

import com.laile.esf.common.util.Page;
import com.mexc.dao.dto.asset.AssetRechargeDto;
import com.mexc.dao.model.wallet.MexcAssetRecharge;
import com.mexc.dao.vo.asset.AssetRechargeVo;

/**
 * Created by huangxinguang on 2018/1/22 下午5:11.
 */
public interface IAssetRechargeService {

    Page<AssetRechargeVo> queryAssetRechargePage(AssetRechargeDto rechargeDto);

    Page<AssetRechargeVo> queryAssetRechargeByCondition(AssetRechargeDto rechargeDto);
}
