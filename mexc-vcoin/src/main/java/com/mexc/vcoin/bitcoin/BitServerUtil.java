package com.mexc.vcoin.bitcoin;

import com.laile.esf.common.exception.ResultCode;
import com.laile.esf.common.exception.SystemException;
import com.mexc.dao.util.mail.MailSender;
import com.mexc.vcoin.WalletHolder;
import com.neemre.btcdcli4j.core.BitcoindException;
import com.neemre.btcdcli4j.core.CommunicationException;
import com.neemre.btcdcli4j.core.domain.AddressInfo;
import com.neemre.btcdcli4j.core.domain.Payment;
import com.neemre.btcdcli4j.core.domain.Transaction;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2018/1/5
 * Time: 下午2:25
 */
public class BitServerUtil {
    private static Logger logger = LoggerFactory.getLogger(BitServerUtil.class);


    public static String getNewAddress() {
        return "";
    }


    public static String createAddress(String account) {
        try {
            return WalletHolder.getBtcdClient().getBtcdClient().getNewAddress(account);
        } catch (Exception e) {
            logger.error("创建比特币地址异常", e);
            MailSender.sendWarningMail("创建比特币地址异常 account:" + account + ",异常信息:" + e.getMessage());
            return "";
        }
    }

    public static String createAddress() {
        try {
            return WalletHolder.getBtcdClient().getBtcdClient().getNewAddress();
        } catch (Exception e) {
            logger.error("创建比特币地址异常", e);
            MailSender.sendWarningMail("创建比特币地址异常,异常信息:" + e.getMessage());
            return "";
        }
    }

    /**
     * 设置钱包地址账户
     *
     * @param address 钱包地址
     * @param account 钱包标签（账户）
     *
     * @return
     */
    public static boolean setAccount(String address, String account) {
        try {
            WalletHolder.getBtcdClient().getBtcdClient().setAccount(address, account);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public static BigDecimal getBalance() {
        try {
            return WalletHolder.getBtcdClient().getBtcdClient().getBalance();
        } catch (Exception e) {
            logger.error("查询用户余额异常", e);
            throw new SystemException(ResultCode.COMMON_ERROR, "查询用户余额异常");
        }
    }

    public static BigDecimal getBalance(String address) {
        try {
            return WalletHolder.getBtcdClient().getBtcdClient().getBalance(address);
        } catch (Exception e) {
            logger.error("查询用户余额异常", e);
            throw new SystemException(ResultCode.COMMON_ERROR, "查询用户余额异常");
        }
    }

    public static String sendTo(String to, BigDecimal amount) {
        BitCoreClientWrapper coreClientWrapper = WalletHolder.getBtcdClient();
        try {
//            coreClientWrapper.getBtcdClient().walletPassphrase(coreClientWrapper.getHotPwd(), 30);
            return coreClientWrapper.getBtcdClient().sendToAddress(to, amount);
        } catch (Exception e) {
            logger.error("比特币发送交易失败,from:钱包 to:" + to + ",amount:" + amount, e);
            MailSender.sendWarningMail("比特币发送交易失败,from:主钱包  to:" + to + ",amount:" + amount);
            return "";
        } finally {
//            try {
//                coreClientWrapper.getBtcdClient().walletLock();
//            } catch (Exception e) {
//                logger.error("加锁比特币钱包失败", e);
//                throw new SystemException(ResultCode.COMMON_ERROR, "加锁比特币钱包失败");
//            }
        }
    }


    public static String sendFromAddress(String from, String to, BigDecimal amount) {
        BitCoreClientWrapper coreClientWrapper = WalletHolder.getBtcdClient();
        try {
//            coreClientWrapper.getBtcdClient().walletPassphrase(coreClientWrapper.getHotPwd(), 30);
            return coreClientWrapper.getBtcdClient().sendFrom(from, to, amount);
        } catch (Exception e) {
            logger.error("比特币发送交易失败,from:钱包 to:" + to + ",amount:" + amount, e);
            MailSender.sendWarningMail("比特币发送交易失败,from:主钱包  to:" + to + ",amount:" + amount);
            return "";
        } finally {
//            try {
//                coreClientWrapper.getBtcdClient().walletLock();
//            } catch (Exception e) {
//                logger.error("加锁比特币钱包失败", e);
//                throw new SystemException(ResultCode.COMMON_ERROR, "加锁比特币钱包失败");
//            }
        }
    }


    public static Transaction getReceipt(String txid) {
        try {
            return WalletHolder.getBtcdClient().getBtcdClient().getTransaction(txid);
        } catch (Exception e) {
            logger.error("获取交易详情失败" + txid, e);
            MailSender.sendWarningMail("获取交易详情失败" + txid);
            return null;
        }
    }


    public static boolean move(String from, String to, BigDecimal amount) {
        try {
            return WalletHolder.getBtcdClient().getBtcdClient().move(from, to, amount);
        } catch (Exception e) {
            logger.error("btc余额转移异常", e);
            return false;
        }
    }

    public static List<Payment> listtransactionsValue(String account, long count, long from) {
        try {
            if (StringUtils.isEmpty(account)) {
                account = "*";
            }
            List<Payment> list = WalletHolder.getBtcdClient().getBtcdClient().listTransactions(account, count, from);
            if (CollectionUtils.isNotEmpty(list)) {
                for (int i = 0; i < list.size(); i++) {
                    Payment payment = list.get(i);
                    if (!payment.getCategory().getName().equals("receive")) {
                        list.remove(payment);
                        i -= 1;
                    }
                }
            }
            return list;
        } catch (Exception e) {
            logger.error("同步钱包用户交易列表异常", e);
            throw new SystemException(ResultCode.COMMON_ERROR, "同步钱包用户交易列表异常" + e.getMessage());
        }
    }


    public static List<Payment> listReceiveTrans(long count, long from) {
        return listtransactionsValue("*", count, from);
    }


    public static boolean checkAddress(String address) throws BitcoindException, CommunicationException {
        if (StringUtils.isEmpty(address)) {
            return false;
        }
        return WalletHolder.getBtcdClient().getBtcdClient().checkAddress(address);
    }

}
