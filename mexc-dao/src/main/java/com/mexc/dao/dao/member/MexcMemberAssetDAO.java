package com.mexc.dao.dao.member;

import com.laile.esf.common.util.Page;
import com.mexc.dao.dao.IBaseDAO;
import com.mexc.dao.dto.asset.AssetBalanceCheckDto;
import com.mexc.dao.dto.asset.AssetDto;
import com.mexc.dao.dto.asset.AssetQueryDto;
import com.mexc.dao.model.member.MexcMemberAsset;
import com.mexc.dao.vo.wallet.MemberAssetCheckVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by huangxinguang on 2017/11/22 下午4:55.
 */
@Repository
public interface MexcMemberAssetDAO extends IBaseDAO<MexcMemberAsset, String> {

    List<MemberAssetCheckVo> queryMemberAssetCheck();

    List<AssetDto> queryMemberAsset(@Param("query") AssetQueryDto assetQueryDto);

    List<AssetDto> queryMemberAssetPage(Page<AssetDto> assetDtoPage, @Param("query") AssetQueryDto assetQueryDto);

    List<AssetBalanceCheckDto> queryAssetFromMemberSeqLimit(@Param("memberSeq") Integer memberSeq, @Param("limit") Integer limit);

    int assetIncome(@Param("assetId") String assetId, @Param("inComeValue") BigDecimal incomeValue);

    int assetOutcome(@Param("assetId") String assetId, @Param("outComeValue") BigDecimal outValue);

    int frozenAmount(@Param("assetId") String assetId, @Param("frozenAmount") BigDecimal frozenAmount);

    int unfrozenAmount(@Param("assetId") String assetId, @Param("frozenAmount") BigDecimal frozenAmount);

    int cashSuccess(@Param("assetId") String assetId, @Param("frozenAmount") BigDecimal frozenAmount);

    int cashFail(@Param("assetId") String assetId, @Param("frozenAmount") BigDecimal frozenAmount);

    MexcMemberAsset checkExists(@Param("contract") String contract, @Param("address") String address, @Param("token") String token);

}
