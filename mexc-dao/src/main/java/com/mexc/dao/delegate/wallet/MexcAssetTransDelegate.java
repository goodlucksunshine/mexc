package com.mexc.dao.delegate.wallet;

import com.laile.esf.common.util.Page;
import com.mexc.common.plugin.SqlCondition;
import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.dao.member.MexcAssetTransDAO;
import com.mexc.dao.delegate.AbstractDelegate;
import com.mexc.dao.dto.asset.AssetQueryDto;
import com.mexc.dao.model.member.MexcAssetTrans;
import com.mexc.dao.vo.asset.AssetTransVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2018/1/2
 * Time: 上午9:19
 */
@Transactional
@Component
public class MexcAssetTransDelegate extends AbstractDelegate<MexcAssetTrans, String> {
    @Autowired
    private MexcAssetTransDAO assetTransDAO;


       @Autowired
    @Override
    public void setBaseMapper(IBaseDAO<MexcAssetTrans, String> baseMapper) {
        super.setBaseMapper(baseMapper);
    }

    public boolean tranExists(String transhash) {
        SqlCondition condition = new SqlCondition();
        condition.put(" where trans_no=", transhash);
        List<MexcAssetTrans> list = assetTransDAO.selectByCondition(condition);
        if (CollectionUtils.isNotEmpty(list)) {
            return true;
        } else {
            return false;
        }
    }

    public List<MexcAssetTrans> queryAssetTransHistory(String account) {
        return assetTransDAO.queryAssetTransHistory(account);
    }
    
    public Page<AssetTransVo> queryAsserTransList(AssetQueryDto queryDto) {
        Page<AssetTransVo> page = new Page<>(queryDto.getCurrentPage(),queryDto.getShowCount());
        List<AssetTransVo> list = assetTransDAO.queryAssetTransList(page,queryDto);
        page.setResultList(list);
        return page;
    }
}
