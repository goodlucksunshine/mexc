package com.mexc.admin.core.service.asset;

import com.laile.esf.common.util.Page;
import com.mexc.dao.dto.asset.AssetQueryDto;
import com.mexc.dao.vo.asset.AssetTransVo;

import java.util.List;

/**
 * Created by huangxinguang on 2018/1/22 下午3:58.
 */
public interface IAssetTransService {

    Page<AssetTransVo> queryAsserTransList(AssetQueryDto queryDto);
}
