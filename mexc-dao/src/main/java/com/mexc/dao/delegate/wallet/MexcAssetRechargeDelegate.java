package com.mexc.dao.delegate.wallet;

import com.laile.esf.common.util.Page;
import com.laile.esf.common.util.StringUtil;
import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.dao.wallet.MexcAssetRechargeDAO;
import com.mexc.dao.delegate.AbstractDelegate;
import com.mexc.dao.dto.asset.AssetRechargeDto;
import com.mexc.dao.model.wallet.MexcAssetRecharge;
import com.mexc.dao.vo.asset.AssetRechargeVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by huangxinguang on 2018/1/16 下午9:24.
 */
@Component
@Transactional
public class MexcAssetRechargeDelegate extends AbstractDelegate<MexcAssetRecharge,String>{

    @Autowired
    private MexcAssetRechargeDAO mexcAssetRechargeDAO;

    @Autowired
    @Override
    public void setBaseMapper(IBaseDAO<MexcAssetRecharge, String> baseMapper) {
        super.setBaseMapper(baseMapper);
    }

    public List<MexcAssetRecharge> queryAssetRecharge(String memberId) {
        return mexcAssetRechargeDAO.queryAssetRecharge(memberId);
    }

    public Page<AssetRechargeVo> queryAssetRechargePage(AssetRechargeDto rechargeDto) {
        Page<AssetRechargeVo> page = new Page<>(rechargeDto.getCurrentPage(),rechargeDto.getShowCount());
        List<AssetRechargeVo> list = mexcAssetRechargeDAO.queryAssetRechargePage(page,rechargeDto);
        page.setResultList(list);
        return page;
    }

    public Page<AssetRechargeVo> queryAssetRechargeByCondition(AssetRechargeDto rechargeDto) {
        Page<AssetRechargeVo> page = new Page<>(rechargeDto.getCurrentPage(),rechargeDto.getShowCount());
        List<AssetRechargeVo> list = mexcAssetRechargeDAO.queryAssetRechargeByCondition(page,rechargeDto);
        if(!StringUtil.isEmpty(rechargeDto.getAccount())){
            if(null != list && list.size()>0){
                for (AssetRechargeVo a:list) {
                    a.setAccount(rechargeDto.getAccount());
                }
            }
        }
        page.setResultList(list);
        return page;
    }
}
