package com.mexc.task.quartz;

import com.alibaba.fastjson.JSON;
import com.laile.esf.common.util.TrackUtil;
import com.mexc.common.util.ThressDescUtil;
import com.mexc.common.util.UUIDUtil;
import com.mexc.common.util.jedis.RedisUtil;
import com.mexc.dao.delegate.common.TSysDictDelegate;
import com.mexc.dao.delegate.member.MemberAssetDelegate;
import com.mexc.dao.delegate.vcoin.VCoinDelegate;
import com.mexc.dao.delegate.wallet.MexcAssetTransDelegate;
import com.mexc.dao.model.common.TSysDict;
import com.mexc.dao.model.member.MexcAssetTrans;
import com.mexc.dao.model.member.MexcMemberAsset;
import com.mexc.dao.model.vcoin.MexcVCoin;
import com.mexc.dao.model.vcoin.MexcVcoinConfig;
import com.mexc.dao.model.wallet.MexcAssetRecharge;
import com.mexc.dao.util.mail.MailSender;
import com.mexc.task.AddressLibContext;
import com.mexc.vcoin.TokenEnum;
import com.mexc.vcoin.WalletHolder;
import com.mexc.vcoin.bitcoin.BitServerUtil;
import com.neemre.btcdcli4j.core.domain.Payment;
import com.neemre.btcdcli4j.core.domain.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2018/1/6
 * Time: 下午12:23
 */
public class BtcAssetRechargeBlockSync {
    private static Logger logger = LoggerFactory.getLogger("btcBlockSync");
    private static boolean syncLock = false;
    @Autowired
    private MemberAssetDelegate memberAssetDelegate;
    @Autowired
    private MexcAssetTransDelegate mexcAssetTransDelegate;
    @Autowired
    private VCoinDelegate vCoinDelegate;
    /*@Autowired
    private TSysDictDelegate tSysDictDelegate;*/
    private int btcBlock = 3;
    private Executor executor = Executors.newFixedThreadPool(50);

    /**
     * 同步区块给用户入账
     */
    public void sync() {
        if (!AddressLibContext.getInitStatus()) {
            logger.info("地址库未加载完成,任务延迟");
            return;
        }
        MDC.put("exeNo", TrackUtil.generateChannelFlowNo("BRB"));
        if (!AddressLibContext.getInitStatus()) {
            logger.info("地址库未加载完成,任务延迟");
            return;
        }
        logger.info("btc区块同步开始");
        /** 如果上一次还在执行 则直接返回 **/
        if (syncLock) {
            logger.info("btc 区块同步上一次暂未执行完");
            return;
        }
        syncLock = true;
        BigInteger nextSync = WalletHolder.getBtcdClient().getLastSyncTransOffset();
        long begin = nextSync.longValue();
        int step = 100;
        //获取虚拟币配置
        Map<String, String> configMap = RedisUtil.hgetall("sys_dict");
        MexcVcoinConfig config = new MexcVcoinConfig();
        config.setThresholdHotToCold("2");
        config.setSysCashBlock(15);
        config.setSysRechargeBlock(3);
        while (true) {
            try {
                logger.info("同步账单 begin:{} 步长:{}", begin, step);
                List<Payment> paymentList = BitServerUtil.listReceiveTrans(step, begin);
                logger.info("btc资产充值记录同步 size:{}", paymentList.size());
                if (paymentList == null || paymentList.size() == 0) {
                    break;
                } else {
                    begin += paymentList.size();
                }
                for (Payment payment : paymentList) {
                    String txid = payment.getTxId().trim();
                    String address = payment.getAddress();
                    logger.info("check btc address exists:{}", address);
                    if (!AddressLibContext.checkAddress(address.toLowerCase())) {
                        //未找到此地址 直接跳过
                        logger.info("check btc address not exists:{}", address);
                        continue;
                    }
                    logger.info("check btc txid exists:{}", txid);
                    //check交易是否已经存在了
                    boolean exit = mexcAssetTransDelegate.tranExists(txid);
                    if (exit) {
                        logger.info("check btc txid is exists:{} continue", txid);
                        continue;
                    }
                    //check交易是否已经存在了
                    MexcMemberAsset mexcMemberAsset = memberAssetDelegate.checkAddress(ThressDescUtil.encodeAssetAddress(address), TokenEnum.BIT_COIN.getCode());
                    try {
                        boolean checkResult = BitServerUtil.checkAddress(address);
                        if (!checkResult) {
                            logger.error("btc 充值校验,用户资产非钱包地址");
                            MailSender.sendWarningMail("btc 充值校验,用户资产非钱包地址 用户:" + mexcMemberAsset.getMemberId());
                            continue;
                        }
                    } catch (Exception e) {
                        logger.error("BTC 充值校验地址是否存在于钱包内异常", e);
                    }
                    if (!configMap.isEmpty() && configMap.size() > 0) {
                        String json = configMap.get(mexcMemberAsset.getVcoinId());
                        config = JSON.parseObject(json, MexcVcoinConfig.class);
                    }
                    blockConfirm(address, txid, mexcMemberAsset, config);
                }
            } catch (Exception e) {
                logger.info("btc区块同步异常", e);
            }
        }
        logger.info("已检查完本地记录");
        if (begin > nextSync.longValue()) {
            logger.info("begin 大于上次同步的位置则更新");
            WalletHolder.getBtcdClient().updateLastSyncTransOffset(nextSync);
        }
        //这次同步完之后解锁
        syncLock = false;
    }


    private void blockConfirm(String address, String txid, MexcMemberAsset mexcMemberAsset, MexcVcoinConfig config) {
        executor.execute(() ->
        {
            Transaction transaction = null;
            while (true) {
                transaction = BitServerUtil.getReceipt(txid);
                logger.info("btc 区块确认 :{}" + transaction.getConfirmations());
                if (transaction.getConfirmations() < config.getSysRechargeBlock()) {
                    continue;
                } else {
                    break;
                }
            }
            logger.info("btc 区块确认完成 开始入账");
            if (transaction.getConfirmations() >= config.getSysRechargeBlock()) {
                MexcAssetTrans mexcAssetTrans = new MexcAssetTrans();
                mexcAssetTrans.setId(UUIDUtil.get32UUID());
                mexcAssetTrans.setTransType(1);
                mexcAssetTrans.setTradeType("1");
                mexcAssetTrans.setTransReceipt(JSON.toJSONString(transaction));
                mexcAssetTrans.setTransNo(transaction.getTxId());
                mexcAssetTrans.setTransTime(new Date());
                mexcAssetTrans.setAssetId(mexcMemberAsset.getId());
                mexcAssetTrans.setTransAmount(transaction.getAmount());
                logger.info("用户资产入账成功transhash:{}", transaction.getTxId());
                MexcAssetRecharge recharge = new MexcAssetRecharge();
                recharge.setId(UUIDUtil.get32UUID());
                recharge.setVcoinId(mexcMemberAsset.getVcoinId());
                recharge.setTxReceipt(mexcAssetTrans.getTransReceipt());
                recharge.setReceiptTime(mexcAssetTrans.getTransTime());
                recharge.setTxHash(transaction.getTxId());
                recharge.setTxToken(TokenEnum.BIT_COIN.getCode());
                recharge.setRechargeAddress(address);
                recharge.setMemberId(mexcMemberAsset.getMemberId());
                recharge.setAssetAddress(address);
                //recharge.setRechargeAddress(transaction.getTo());
                recharge.setRechargeValue(transaction.getAmount());
                memberAssetDelegate.recharge(mexcAssetTrans, recharge);
                logger.info("用户资产入账成功transhash:{}", transaction.getTxId());
            }
        });
    }

    /**
     * 设置btc充值区块确认数
     */
    public void findBtcBlock() {

        // List<TSysDict> tSysDicts = tSysDictDelegate.selectAll();
        List<MexcVCoin> mexcVCoins = vCoinDelegate.selectAll();

        if (mexcVCoins == null || mexcVCoins.size() == 0) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        for (MexcVcoinConfig mexcVCoin : mexcVCoins) {

            map.put(mexcVCoin.getId(), JSON.toJSONString(mexcVCoin));

        }
        RedisUtil.hmset("sys_dict", map);
    }


}
