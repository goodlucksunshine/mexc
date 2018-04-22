package com.mexc.dao.delegate.member;

import com.mexc.common.plugin.SqlCondition;
import com.mexc.common.util.UUIDUtil;
import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.dao.member.MexcAddressMarkDAO;
import com.mexc.dao.delegate.AbstractDelegate;
import com.mexc.dao.model.member.MexcAddressMark;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2018/1/12
 * Time: 下午4:06
 */
@Component
public class AddressMarkDelegate extends AbstractDelegate<MexcAddressMark, String> {

    @Resource
    MexcAddressMarkDAO addressMarkDAO;

    @Autowired
    @Override
    public void setBaseMapper(IBaseDAO<MexcAddressMark, String> baseMapper) {
        super.setBaseMapper(baseMapper);
    }


    public List<MexcAddressMark> assetMarkList(String assetId, String memberId) {
        SqlCondition sqlCondition = new SqlCondition();
        sqlCondition.put("where asset_id=", assetId);
        sqlCondition.put(" and member_id=", memberId);
        return addressMarkDAO.selectByCondition(sqlCondition);
    }

    public void saveHisAddress(String memberId, String address_tab, String assetId, String address) {
        SqlCondition sqlCondition = new SqlCondition();
        sqlCondition.put(" where asset_id=", assetId);
        sqlCondition.put(" and address=", address);
        List list = selectByCondition(sqlCondition);
        if (CollectionUtils.isNotEmpty(list)) {
            return;
        }
        MexcAddressMark mark = new MexcAddressMark();
        mark.setId(UUIDUtil.get32UUID());
        mark.setAddressTab(address_tab);
        mark.setMemberId(memberId);
        mark.setAddress(address);
        mark.setAssetId(assetId);
        addressMarkDAO.insertSelective(mark);
    }
}
