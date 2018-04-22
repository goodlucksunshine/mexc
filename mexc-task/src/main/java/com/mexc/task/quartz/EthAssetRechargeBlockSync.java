package com.mexc.task.quartz;

import com.alibaba.fastjson.JSON;
import com.laile.esf.common.util.TrackUtil;
import com.mexc.common.util.ThressDescUtil;
import com.mexc.common.util.UUIDUtil;
import com.mexc.dao.delegate.member.MemberAssetDelegate;
import com.mexc.dao.delegate.vcoin.VCoinDelegate;
import com.mexc.dao.delegate.wallet.MexcAssetTransDelegate;
import com.mexc.dao.model.member.MexcAssetTrans;
import com.mexc.dao.model.member.MexcMemberAsset;
import com.mexc.dao.model.vcoin.MexcVCoin;
import com.mexc.dao.model.wallet.MexcAssetRecharge;
import com.mexc.task.AddressLibContext;
import com.mexc.vcoin.WalletHolder;
import com.mexc.vcoin.eth.ERC20TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Numeric;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2018/1/6
 * Time: 下午12:23
 */
public class EthAssetRechargeBlockSync {
    private static Logger logger = LoggerFactory.getLogger("ethBlockSync");
    private static boolean lock = false;
    @Autowired
    private VCoinDelegate VCoinDelegate;
    @Autowired
    private MemberAssetDelegate memberAssetDelegate;
    @Autowired
    private MexcAssetTransDelegate mexcAssetTransDelegate;

    private Executor executor = Executors.newFixedThreadPool(50);

    private static int synctime = 0;

    /**
     * 同步区块给用户入账
     */
    public void sync() {
        if (!AddressLibContext.getInitStatus()) {
            logger.info("地址库未加载完成,任务延迟");
            return;
        }
        MDC.put("exeNo", TrackUtil.generateChannelFlowNo("ERB"));
        if (!AddressLibContext.getInitStatus()) {
            logger.info("地址库未加载完成,任务延迟");
            return;
        }
        logger.info("eth 区块同步开始");
        /** 如果上一次还在执行 则直接返回 **/
        if (lock) {
            logger.info("eth 区块同步上一次暂未执行完");
            return;
        }
        lock = true;
        long nextSync = WalletHolder.getParityClient().getBlockSync();
        logger.info("同步区块nextSync:{}", nextSync);
        while (ERC20TokenUtil.getEthLastBlock() > nextSync) {
            BigInteger receiptCount = ERC20TokenUtil.eth_getBlockTransactionCountByNumber(nextSync);
            logger.info("同步区块nextSync:{} --->count:{}", nextSync, receiptCount);
            for (int i = 0; i < receiptCount.longValue(); i++) {
                try {
                    logger.info("同步区块 index:{}", i);
                    Transaction transaction = ERC20TokenUtil.getBlockTransByNumberValue(nextSync, i);
                    logger.info("transaction:{}", JSON.toJSONString(transaction));
                    String to = transaction.getTo();
                    String transValue;
                    String assetAddress;
                    String contract = "";
                    if (StringUtils.isEmpty(to)) {
                        logger.info("创建合约交易,直接返回");
                        continue;
                    }
                    //判断是否合约交易
                    if (AddressLibContext.checkContract(to)) {
                        logger.info("本地存在的合约  contract:{}", to);
                        contract = to;
                        String input = transaction.getInput();
                        logger.info("合约交易 input:{}", input);
                        if (input.length() < 5) {
                            logger.info("合约交易拿不到input信息 直接跳过");
                            continue;
                        }
                        assetAddress = "0x" + input.substring(34, 74);
                        String valueHex = input.substring(74, 138);
                        logger.info("合约交易assetAddress:{}", valueHex);
                        transValue = "0x"+valueHex;
                    } else {
                        assetAddress = to;
                        transValue = transaction.getValue().toString();
                    }
                    logger.info("交易解析 assetAddress:{},amount:{}", assetAddress, transValue);
                    if (!AddressLibContext.checkAddress(assetAddress)) {
                        logger.info("资产不存在跳过 assetAddress:{}", assetAddress);
                        continue;
                    }

                    //check交易是否已经存在了
                    logger.info("检查资产交易是否存在 assetAddress:{}", assetAddress);
                    boolean result = mexcAssetTransDelegate.tranExists(transaction.getHash());
                    logger.info("检查资产交易是否存在 transhash:{}", transaction.getHash());
                    if (result) {
                        logger.info("transhash 已存在 跳过:{}", transaction.getHash());
                        continue;
                    }
                    logger.info("查询对应币种小数位");
                    MexcVCoin mexcVCoin;
                    if (StringUtils.isNotEmpty(contract)) {
                        mexcVCoin = VCoinDelegate.queryByContract(contract);
                        logger.info("查询到平台合约");
                        if (mexcVCoin == null) {
                            logger.error("redis查到合约,但是数据库中未查到对应合约币种:{}", contract);
                        }
                    } else {
                        mexcVCoin = VCoinDelegate.queryByEth();
                    }
                    MexcMemberAsset asset = memberAssetDelegate.queryAsset(ThressDescUtil.encodeAssetAddress(assetAddress), mexcVCoin.getId());
                    try {
                        boolean checkResult = ERC20TokenUtil.checkEthAddress(assetAddress, ThressDescUtil.decodeAssetPwd(asset.getAccountPwd(),asset.getIv()));
                        if (!checkResult) {
                            logger.error("eth 资产地址:{} 不在钱包内", assetAddress);
                            continue;
                        }
                    } catch (Exception e) {
                        logger.error("eth 资产地址钱包校验异常", e);
                    }
                    blockConfirm(transaction.getHash(), assetAddress, mexcVCoin, transValue, asset);
                } catch (Exception e) {
                    logger.error("eth区块同步异常:{}", e);
                }
            }
            nextSync += 1;
            synctime += 1;
            if (synctime > 10) {
                WalletHolder.getParityClient().updateBlock(nextSync);
                synctime = 0;
            }
        }
        //这次同步完之后解锁
        lock = false;
    }

    private void blockConfirm(String txid, String address, MexcVCoin mexcVCoin, String transValue, MexcMemberAsset asset) {
        executor.execute(() -> {
            TransactionReceipt transaction;
            logger.info("eth 区块确认开始");
            while (true) {
                transaction = ERC20TokenUtil.getTransReceipt(txid);
                Long lastBlock = ERC20TokenUtil.getEthLastBlock();
                Long tranConfirm = transaction.getBlockNumber().longValue();
                logger.info("eth 区块确认开始 last block:{},tranConfirm:{}", lastBlock, tranConfirm);
                if (lastBlock - tranConfirm < 3) {
                    continue;
                } else {
                    break;
                }
            }
            BigDecimal convertValue = ERC20TokenUtil.parseAmount(mexcVCoin.getScale(), transValue);
            MexcAssetTrans mexcAssetTrans = new MexcAssetTrans();
            mexcAssetTrans.setId(UUIDUtil.get32UUID());
            mexcAssetTrans.setTransType(1);
            mexcAssetTrans.setTradeType("1");
            mexcAssetTrans.setTransReceipt(JSON.toJSONString(transaction));
            mexcAssetTrans.setTransNo(transaction.getTransactionHash());
            mexcAssetTrans.setTransTime(new Date());
            mexcAssetTrans.setAssetId(asset.getId());
            mexcAssetTrans.setTransAmount(convertValue);
            MexcAssetRecharge recharge = new MexcAssetRecharge();
            recharge.setId(transaction.getTransactionHash());
            recharge.setVcoinId(mexcVCoin.getId());
            recharge.setTxReceipt(mexcAssetTrans.getTransReceipt());
            recharge.setReceiptTime(mexcAssetTrans.getTransTime());
            recharge.setTxHash(transaction.getTransactionHash());
            recharge.setTxToken(mexcVCoin.getVcoinToken());
            recharge.setMemberId(asset.getMemberId());
            recharge.setAssetAddress(address);
            recharge.setAssetId(asset.getId());
            recharge.setRechargeAddress(transaction.getFrom());
            recharge.setRechargeValue(convertValue);
            memberAssetDelegate.recharge(mexcAssetTrans, recharge);
            logger.info("用户资产入账成功transhash:{}", transaction.getTransactionHash());
        });
    }


}
