package com.mexc.dao.dao.wallet;

import com.laile.esf.common.util.Page;
import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.dto.asset.AssetCashDto;
import com.mexc.dao.model.wallet.MexcAssetCash;
import com.mexc.dao.model.wallet.MexcAssetCashVcoin;
import com.mexc.dao.vo.asset.AssetCashHistoryVo;
import com.mexc.dao.vo.asset.AssetCashVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface MexcAssetCashDAO extends IBaseDAO<MexcAssetCash, String> {

    int cashFail(@Param("cashId") String cashId);

    int cashSuccess(@Param("cashId") String cashId);

    int cashAutoFailed(@Param("cashId") String cashId);

    int cashConfirm(@Param("cashId") String cashId);

    int resendCash(@Param("cashId") String cashId);

    List<MexcAssetCash> queryAssetCash(@Param("memberId") String memberId);

    List<MexcAssetCashVcoin> queryAssetCashVcoin(@Param("memberId") String memberId);

    /**
     * 查询用户每个币种的提现历史总额度
     * @param memberId
     * @return
     */
    List<AssetCashHistoryVo> checkLevelCashLimit(@Param("memberId")String memberId);

    /**
     * 提现查询
     * @param assetCashDto
     * @param page
     * @return
     */
    List<AssetCashVo> queryAssetCashPage(@Param("page")Page<AssetCashVo> page,@Param("query") AssetCashDto assetCashDto);

    List<AssetCashVo> queryAssetCashPageByCondition(@Param("page")Page<AssetCashVo> page,@Param("query") AssetCashDto assetCashDto);


    int updateCashHash(@Param("cashId")String cashId,@Param("txid")String txid);
}