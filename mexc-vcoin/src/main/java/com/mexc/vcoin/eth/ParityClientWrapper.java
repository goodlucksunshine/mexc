package com.mexc.vcoin.eth;

import com.alibaba.fastjson.JSON;
import com.laile.esf.common.util.StringUtil;
import com.mexc.common.constant.RedisKeyConstant;
import com.mexc.common.util.jedis.RedisUtil;
import com.mexc.dao.dto.wallet.EthWallInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.parity.Parity;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2017/12/29
 * Time: 上午11:17
 */
public class ParityClientWrapper {
    private static Logger logger = LoggerFactory.getLogger(ParityClientWrapper.class);
    /**
     * 钱包客户端
     */
    private Parity parity;
    /**
     * web3j client
     */
    private Web3j web3j;
    /**
     * 钱包同步到的区块
     */
    private long blockSync;

    private long preSync;

    public Parity getParity() {
        return parity;
    }

    public void setParity(Parity parity) {
        this.parity = parity;
    }

    public Web3j getWeb3j() {
        return web3j;
    }

    public void setWeb3j(Web3j web3j) {
        this.web3j = web3j;
    }

    public long getBlockSync() {
        return blockSync;
    }

    public long getPreSync() {
        return preSync;
    }

    public void initBlock(long init) {
        preSync = init;
        blockSync = init;
    }

    public void updateBlock(long last) {
        synchronized (this) {
            preSync = blockSync;
            blockSync = last;
        }
    }


    public  EthWallInfo getEthWallInfo() {
        String ethWalletInfo = RedisUtil.get(RedisKeyConstant.ETH_WALLET_INFO);
        if (StringUtil.isEmpty(ethWalletInfo)) {
            logger.warn("btc冷热钱包地址为空");
        }
        EthWallInfo ethWallInfo = JSON.parseObject(ethWalletInfo, EthWallInfo.class);
        return ethWallInfo;
    }
}
