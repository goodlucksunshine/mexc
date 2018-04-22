package com.mexc.web.core.service.asset;

import com.mexc.dao.dto.asset.AssetDto;
import com.mexc.dao.dto.asset.AssetQueryDto;
import com.mexc.dao.model.member.MexcAddressMark;
import com.mexc.dao.model.member.MexcMemberAsset;
import com.mexc.dao.model.vcoin.MexcVCoin;
import com.mexc.dao.model.wallet.MexcAssetCash;
import com.mexc.web.core.model.request.MemberAssetCashRequest;
import com.mexc.web.core.model.request.UserWalletToClodRequest;
import com.mexc.web.core.model.response.MemberAssetCashResponse;
import com.neemre.btcdcli4j.core.domain.Transaction;

import java.math.BigDecimal;
import java.util.List;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 17/12/14
 * Time: 下午5:55
 */
public interface IAssetService {
    List<AssetDto> queryMemberAsset(AssetQueryDto queryDto);

    int addAsset(MexcMemberAsset asset);

    int addAsset(List<MexcMemberAsset> asset);

    boolean userWalletToClodWallet(UserWalletToClodRequest request);

    MemberAssetCashResponse memberAssetCash(MemberAssetCashRequest request);

    void cashConfirm(String s_t, String t_k, String sign, String c_id);

    String ethCash(MexcVCoin vCoin, MexcMemberAsset asset, String cashAddress, BigDecimal cashWei);

    Transaction btcCash(MexcVCoin vCoin, MexcMemberAsset asset, String cashAddress, BigDecimal cashWei);

    boolean cashSuccess(MexcAssetCash cash);

    boolean cashAutoFailed(MexcAssetCash cash);

    boolean cashFailture(MexcAssetCash cash);

    boolean googleCodeCheck(String account, String code);

    List<MexcAddressMark> queryMarkList(String account, String assetId);


    MemberAssetCashResponse assetCashCheck(String assetId, String amount);

    BigDecimal assetEstimateBtc();

    MexcMemberAsset queryMemberAsset(String memberId, String vcoinId);

    BigDecimal getTxFee();

    int updateCashHash(String cashId,String txid);
}
