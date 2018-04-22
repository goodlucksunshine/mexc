package com.mexc.task.quartz;

import com.alibaba.fastjson.JSON;
import com.mexc.common.util.jedis.RedisUtil;
import com.mexc.dao.delegate.member.MemberAssetDelegate;
import com.mexc.dao.delegate.vcoin.VCoinDelegate;
import com.mexc.dao.delegate.wallet.MexcAssetCashDelegate;
import com.mexc.dao.model.member.MexcMemberAsset;
import com.mexc.dao.model.vcoin.MexcVCoin;
import com.mexc.dao.model.vcoin.MexcVcoinConfig;
import com.mexc.dao.model.wallet.MexcAssetCash;
import com.mexc.vcoin.TokenEnum;
import com.mexc.vcoin.bitcoin.BitServerUtil;
import com.mexc.vcoin.eth.ERC20TokenUtil;
import com.mexc.web.core.service.asset.IAssetService;
import com.neemre.btcdcli4j.core.domain.Transaction;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2018/3/13
 * Time: 下午5:15
 */
public class CashStatusSync {

    private static Logger logger = LoggerFactory.getLogger(EntrustOrderReSendCheck.class);
    private static boolean lock = false;
    @Autowired
    private MexcAssetCashDelegate cashDelegate;
    @Resource
    VCoinDelegate vCoinDelegate;
    @Resource
    MemberAssetDelegate assetDelegate;
    @Resource
    IAssetService assetService;

    private Executor executor = Executors.newFixedThreadPool(50);

    public void cashStatusSync() {
        if (lock) {
            logger.info("上一次同步还未完成,直接返回");
            return;
        }
        lock = true;
        List<MexcAssetCash> cashList = cashDelegate.queryProcessingCashList(50);
        if (CollectionUtils.isEmpty(cashList)) {
            return;
        }
        for (MexcAssetCash cash : cashList) {
            if (cash.getCashStatus() == 1) {
                continue;
            }
            MexcMemberAsset asset = assetDelegate.selectByPrimaryKey(cash.getAssetId());
            MexcVCoin vCoin = vCoinDelegate.selectByPrimaryKey(asset.getVcoinId());
            if (vCoin.getVcoinToken().equalsIgnoreCase(TokenEnum.ETH_COIN.getCode())) {
                confirmEthCashBlock(cash, cash.getTxHash(), vCoin.getId());
            } else if (vCoin.getVcoinToken().equalsIgnoreCase(TokenEnum.BIT_COIN.getCode())) {
                confirmBtcCashBlock(cash, cash.getTxHash(), vCoin.getId());
            }
        }
        lock = false;
    }

    public void confirmBtcCashBlock(MexcAssetCash cash, String txid, String vcoindId) {
        executor.execute(() -> {
            Transaction transaction = null;
            transaction = BitServerUtil.getReceipt(txid);
            logger.info("btc 区块确认 :{}" + transaction.getConfirmations());
            MexcVcoinConfig config = getConfig(vcoindId);
            if (transaction.getConfirmations() > config.getSysRechargeBlock()) {
                cash.setTxGaslimit(transaction.getFee());
                cash.setTxGasprice(BigDecimal.ZERO);
                cash.setTxHash(transaction.getTxId());
                cash.setTxApplyStatus(1);
                cash.setTxReceipt(JSON.toJSONString(transaction));
                cash.setVcoinId(vcoindId);
                assetService.cashSuccess(cash);
                logger.info("队列处理用户提现成功:{}", JSON.toJSONString(cash));
            }
        });
    }

    public void confirmEthCashBlock(MexcAssetCash cash, String txid, String vcoindId) {
        executor.execute(() -> {
            TransactionReceipt transaction = ERC20TokenUtil.getTransReceipt(txid);
            Long lastBlock = ERC20TokenUtil.getEthLastBlock();
            Long tranConfirm = transaction.getBlockNumber().longValue();
            if (lastBlock - tranConfirm > getConfig(vcoindId).getSysCashBlock()) {
                cash.setTxGaslimit(new BigDecimal(transaction.getGasUsed()));
                cash.setTxGasprice(new BigDecimal(ERC20TokenUtil.getGasPrice()));
                cash.setTxApplyStatus(1);
                cash.setVcoinId(vcoindId);
                boolean result = assetService.cashSuccess(cash);
                if (!result) {
                    logger.warn("提现状态更改异常:{}", JSON.toJSONString(cash));
                }
                logger.info("队列处理用户提现成功:{}", JSON.toJSONString(cash));
            }
        });
    }


    private MexcVcoinConfig getConfig(String vcoindId) {
        Map<String, String> configMap = RedisUtil.hgetall("sys_dict");
        MexcVcoinConfig config = new MexcVcoinConfig();
        config.setThresholdHotToCold("2");
        config.setSysCashBlock(15);
        config.setSysRechargeBlock(3);
        if (!configMap.isEmpty() && configMap.size() > 0) {
            String json = configMap.get(vcoindId);
            config = JSON.parseObject(json, MexcVcoinConfig.class);
        }
        return config;
    }
}
