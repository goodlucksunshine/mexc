package com.mexc.admin.core.service.asset;

import com.laile.esf.common.util.Page;
import com.mexc.dao.dto.asset.AssetCashDto;
import com.mexc.dao.vo.asset.AssetCashVo;

/**
 * Created by huangxinguang on 2018/1/22 下午5:55.
 */
public interface IAssetCashService {

    Page<AssetCashVo> queryAssetCashPage(AssetCashDto assetCashDto);

    Page<AssetCashVo> queryAssetCashPageByCondition(AssetCashDto assetCashDto);

    String cashReSend(String cashId);
}
