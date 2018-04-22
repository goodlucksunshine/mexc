package com.mexc.dao.dao.member;

import com.laile.esf.common.util.Page;
import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.dto.asset.AssetQueryDto;
import com.mexc.dao.model.member.MexcAssetTrans;
import com.mexc.dao.vo.asset.AssetTransVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MexcAssetTransDAO extends IBaseDAO<MexcAssetTrans, String> {

    /**
     * 历史记录
     * @param account
     * @return
     */
    List<MexcAssetTrans> queryAssetTransHistory(@Param("account") String account) ;

    /**
     * 查询交易流水
     * @param queryDto
     * @param page
     * @return
     */
    List<AssetTransVo> queryAssetTransList(@Param("page") Page<AssetTransVo> page, @Param("query") AssetQueryDto queryDto);
}