package com.mexc.vcoin;

import com.alibaba.fastjson.JSON;
import com.laile.esf.common.exception.ResultCode;
import com.laile.esf.common.exception.SystemException;
import com.mexc.common.constant.RedisKeyConstant;
import com.mexc.common.util.SpringContextUtils;
import com.mexc.common.util.jedis.RedisUtil;
import com.mexc.dao.delegate.wallet.WalletDelegate;
import com.mexc.dao.dto.wallet.BtcWalletInfo;
import com.mexc.dao.dto.wallet.EthWallInfo;
import com.mexc.vcoin.bitcoin.BitCoreClientWrapper;
import com.mexc.vcoin.eth.ParityClientWrapper;
import com.neemre.btcdcli4j.core.client.BtcdClientImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.parity.Parity;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2017/12/29
 * Time: 下午2:51
 */
public class WalletHolder {
    private static Logger logger = LoggerFactory.getLogger(WalletHolder.class);
    private static BitCoreClientWrapper btcdClientWrapper = null;
    private static ParityClientWrapper parityClientWrapper = null;

    private static void loadBitCoinCore() {
        WalletInfo bitCoreInfo;
        String bitCoreServer = RedisUtil.get("bitCoreServer");
        if (StringUtils.isEmpty(bitCoreServer)) {
            //加载parity钱包信息
            WalletDelegate walletDelegate = (WalletDelegate) SpringContextUtils.getBean("walletDelegate");
            List<Map> maps = walletDelegate.queryWalletByType(TokenEnum.BIT_COIN.getCode());
            Map map = maps.get(0);
            if (map == null) {
                logger.error("BTC钱包未找到配置");
                return;
            }
            String jsonStr = com.alibaba.fastjson.JSON.toJSONString(map);
            bitCoreInfo = JSON.parseObject(jsonStr, WalletInfo.class);
        } else {
            bitCoreInfo = JSON.parseObject(bitCoreServer, WalletInfo.class);
        }
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        CloseableHttpClient httpProvider = HttpClients.custom().setConnectionManager(cm).build();
        Properties nodeConfig = new Properties();
        nodeConfig.setProperty("node.bitcoind.rpc.protocol", "http");
        nodeConfig.setProperty("node.bitcoind.rpc.host", bitCoreInfo.getIp());
        nodeConfig.setProperty("node.bitcoind.rpc.user", bitCoreInfo.getUser());
        nodeConfig.setProperty("node.bitcoind.rpc.password", bitCoreInfo.getPwd());
        nodeConfig.setProperty("node.bitcoind.notification.block.port", bitCoreInfo.getPort());
        nodeConfig.setProperty("node.bitcoind.notification.wallet.port", "5160");
        nodeConfig.setProperty("node.bitcoind.notification.alert.port", "5158");
        try {
            BtcdClientImpl btcdClient = new BtcdClientImpl(httpProvider, nodeConfig);
            btcdClientWrapper = new BitCoreClientWrapper();
            btcdClientWrapper.setBtcdClient(btcdClient);
            BtcWalletInfo btcWalletInfo = new BtcWalletInfo();
            btcWalletInfo.setHotAddress(bitCoreInfo.getHotAddress());
            btcWalletInfo.setColdAddress(bitCoreInfo.getColdAddress());
            RedisUtil.set(RedisKeyConstant.BTC_WALLET_INFO, JSON.toJSONString(btcWalletInfo));
//            btcdClientWrapper.setColdAddress(bitCoreInfo.getColdAddress());
//            btcdClientWrapper.setHotAddress(bitCoreInfo.getHotAddress());
//            btcdClientWrapper.setHotFile(bitCoreInfo.getHotFile());
//            btcdClientWrapper.setHotPwd(bitCoreInfo.getHotPwd());
            if (StringUtils.isEmpty(bitCoreInfo.getLastTransOffset())) {
                bitCoreInfo.setLastTransOffset("0");
            }
            btcdClientWrapper.init(new BigInteger(bitCoreInfo.getLastTransOffset()));
        } catch (Exception e) {
            logger.error("比特币客户端链接失败", e);
            throw new SystemException(ResultCode.COMMON_ERROR, "链接比特币客户端异常");
        }
    }

    private static void loadParity() {
        WalletInfo walletInfo;
        String walletServer = RedisUtil.get("walletServer");
        if (StringUtils.isEmpty(walletServer)) {
            //加载parity钱包信息
            WalletDelegate walletDelegate = (WalletDelegate) SpringContextUtils.getBean("walletDelegate");
            List<Map> maps = walletDelegate.queryWalletByType(TokenEnum.ETH_COIN.getCode());
            Map map = maps.get(0);
            if (map == null) {
                logger.error("未找到ETH钱包");
                return;
            }
            String jsonStr = com.alibaba.fastjson.JSON.toJSONString(map);
            walletInfo = JSON.parseObject(jsonStr, WalletInfo.class);

        } else {
            walletInfo = JSON.parseObject(walletServer, WalletInfo.class);
        }
        logger.info("parity walletInfo:{}", JSON.toJSON(walletInfo));
        String parityAddress = "http://" + walletInfo.getIp() + ":" + walletInfo.getPort() + "/";
        Parity parity = Parity.build(new HttpService(parityAddress));
        ParityClientWrapper walletClientWrapper = new ParityClientWrapper();
        walletClientWrapper.setParity(parity);
        Web3j web3j = Web3j.build(new HttpService(parityAddress));
        walletClientWrapper.setWeb3j(web3j);
        walletClientWrapper.initBlock(Long.valueOf(walletInfo.getLastSync()));
        EthWallInfo ethWallInfo = new EthWallInfo();
        ethWallInfo.setHotAddress(walletInfo.getHotAddress());
        ethWallInfo.setColdAddress(walletInfo.getColdAddress());
        ethWallInfo.setHotAddressPwd(walletInfo.getHotPwd());
        RedisUtil.set(RedisKeyConstant.ETH_WALLET_INFO, JSON.toJSONString(ethWallInfo));
//        walletClientWrapper.setHotAddress(walletInfo.getHotAddress());
//        walletClientWrapper.setColdAddress(walletInfo.getColdAddress());
//        walletClientWrapper.setHotFile(walletInfo.getHotFile());
//        walletClientWrapper.setHotPwd(walletInfo.getHotPwd());
        parityClientWrapper = walletClientWrapper;
    }

    public static void reload() {
        loadBitCoinCore();
        loadParity();
    }


    /**
     * 私有构造方法 进制用户创建此对象
     */
    private WalletHolder() {
    }


    public static ParityClientWrapper getParityClient() {
        if (parityClientWrapper == null) {
            loadParity();
        }
        return parityClientWrapper;
    }


    public static BitCoreClientWrapper getBtcdClient() {
        if (btcdClientWrapper == null) {
            loadBitCoinCore();
        }
        return btcdClientWrapper;
    }
}
