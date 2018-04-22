package com.mexc.admin.core.service.asset.impl;

import com.laile.esf.common.util.Page;
import com.mexc.admin.core.service.asset.IAssetRechargeService;
import com.mexc.dao.delegate.wallet.MexcAssetRechargeDelegate;
import com.mexc.dao.dto.asset.AssetRechargeDto;
import com.mexc.dao.model.wallet.MexcAssetRecharge;
import com.mexc.dao.vo.asset.AssetRechargeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huangxinguang on 2018/1/22 下午5:11.
 */
@Service
public class AssetRechargeService implements IAssetRechargeService {

    @Autowired
    private MexcAssetRechargeDelegate mexcAssetRechargeDelegate;

    @Override
    public Page<AssetRechargeVo> queryAssetRechargePage(AssetRechargeDto rechargeDto) {
        return mexcAssetRechargeDelegate.queryAssetRechargePage(rechargeDto);
    }

    @Override
    public Page<AssetRechargeVo> queryAssetRechargeByCondition(AssetRechargeDto rechargeDto) {
        if(null!=rechargeDto&&null!=rechargeDto.getSearchMethod()){
            if(rechargeDto.getSearchMethod().equals("2")){
                rechargeDto.setMonth("");
            }
            if(rechargeDto.getSearchMethod().equals("1")){
                rechargeDto.setStartTime(null);
                rechargeDto.setEndTime(null);
            }
        }
        return mexcAssetRechargeDelegate.queryAssetRechargeByCondition(rechargeDto);
    }
}
