package com.mexc.task.quartz;

import com.laile.esf.common.util.SpringUtils;
import com.laile.esf.common.util.TrackUtil;
import com.mexc.dao.delegate.wallet.WalletDelegate;
import com.mexc.vcoin.WalletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2018/1/6
 * Time: 下午12:23
 */
public class BlockMaxIdenUpdate {
    private static Logger logger = LoggerFactory.getLogger(BlockMaxIdenUpdate.class);
    @Autowired
    private WalletDelegate walletDelegate;

    /**
     * 同步区块给用户入账
     */
    public void sync() {
        MDC.put("exeNo", TrackUtil.generateChannelFlowNo("BMI"));
        logger.info("更新区块同步状况");
        long pre = WalletHolder.getParityClient().getPreSync();
        long sync = WalletHolder.getParityClient().getBlockSync();
        if (sync > pre) {
            logger.info("eth 上次更新同步状况发生变化 pre:{} sync:{}", pre, sync);
            int result = walletDelegate.syncEthBlock(String.valueOf(sync));
            if (result < 1) {
                logger.error("更新ETH钱包区块同步异常");
            } else {
                logger.info("eth更新区块同步失败");
            }
        } else {
            logger.info("eth 上次更新同步未发生变化 pre:{} sync:{} 直接返回", pre, sync);
            return;
        }

        logger.info("btc更新区块同步状况");
        BigInteger offset = WalletHolder.getBtcdClient().getLastSyncTransOffset();
        BigInteger preOffset = WalletHolder.getBtcdClient().getLastSyncTransOffset();
        if (offset.longValue() > preOffset.longValue()) {
            logger.info("btc 更新区块同步 位置发生变化pre:{} sync:{}", preOffset.longValue(), offset.longValue());
            int btcResult = walletDelegate.syncBtcBlock(offset.toString());
            if (btcResult < 1) {
                logger.error("更新BTC钱包区块同步异常");
            } else {
                logger.info("btc更新区块同步状况成功");
            }
        } else {
            logger.info("btc 上次更新同步未发生变化 pre:{} sync:{} 直接返回", preOffset.longValue(), offset.longValue());
            return;
        }

    }
}
