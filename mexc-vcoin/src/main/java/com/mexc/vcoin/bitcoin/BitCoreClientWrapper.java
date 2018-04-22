package com.mexc.vcoin.bitcoin;

import com.alibaba.fastjson.JSON;
import com.laile.esf.common.util.StringUtil;
import com.mexc.common.constant.RedisKeyConstant;
import com.mexc.common.util.jedis.RedisUtil;
import com.mexc.dao.dto.wallet.BtcWalletInfo;
import com.neemre.btcdcli4j.core.client.BtcdClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2018/1/6
 * Time: 下午2:47
 */
public class BitCoreClientWrapper {
    private static Logger logger = LoggerFactory.getLogger(BitCoreClientWrapper.class);
    /**
     * 比特币客户端
     */
    private BtcdClient btcdClient;
    /**
     * 最后块同步的那个交易单号
     */
    private BigInteger lastSyncTransOffset;
    /**
     * 上次更新位置
     */
    private BigInteger preSyncTransOffset;

    public BtcdClient getBtcdClient() {
        return btcdClient;
    }

    public void setBtcdClient(BtcdClient btcdClient) {
        this.btcdClient = btcdClient;
    }

    public BigInteger getLastSyncTransOffset() {
        return lastSyncTransOffset;
    }

    public void init(BigInteger transOffset) {
        lastSyncTransOffset = transOffset;
        preSyncTransOffset = transOffset;
    }

    public void updateLastSyncTransOffset(BigInteger transOffset) {
        synchronized (this) {
            preSyncTransOffset = lastSyncTransOffset;
            lastSyncTransOffset = transOffset;
        }
    }


    public  BtcWalletInfo getBtcWallInfo() {
        String btcwalletInfo = RedisUtil.get(RedisKeyConstant.BTC_WALLET_INFO);
        if (StringUtil.isEmpty(btcwalletInfo)) {
            logger.warn("btc冷热钱包地址为空");
        }
        BtcWalletInfo btcWalletInfo = JSON.parseObject(btcwalletInfo, BtcWalletInfo.class);
        return btcWalletInfo;
    }




}
