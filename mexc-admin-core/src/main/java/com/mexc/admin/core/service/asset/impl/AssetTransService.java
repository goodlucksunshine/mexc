package com.mexc.admin.core.service.asset.impl;

import com.laile.esf.common.util.Page;
import com.mexc.admin.core.service.asset.IAssetTransService;
import com.mexc.dao.delegate.wallet.MexcAssetTransDelegate;
import com.mexc.dao.dto.asset.AssetQueryDto;
import com.mexc.dao.vo.asset.AssetTransVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by huangxinguang on 2018/1/22 下午3:58.
 */
@Service
public class AssetTransService implements IAssetTransService {

    @Autowired
    private MexcAssetTransDelegate mexcAssetTransDelegate;

    public Page<AssetTransVo> queryAsserTransList(AssetQueryDto queryDto) {
        return mexcAssetTransDelegate.queryAsserTransList(queryDto);
    }
}
