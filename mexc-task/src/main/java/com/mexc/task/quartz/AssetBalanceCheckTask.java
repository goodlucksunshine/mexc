package com.mexc.task.quartz;

import com.alibaba.fastjson.JSON;
import com.laile.esf.common.util.DateUtil;
import com.laile.esf.common.util.MathUtil;
import com.laile.esf.common.util.SpringUtils;
import com.laile.esf.common.util.TrackUtil;
import com.mexc.common.util.ThressDescUtil;
import com.mexc.common.util.jedis.RedisUtil;
import com.mexc.dao.delegate.member.MemberAssetDelegate;
import com.mexc.dao.delegate.vcoin.VCoinDelegate;
import com.mexc.dao.dto.asset.AssetBalanceCheckDto;
import com.mexc.dao.model.vcoin.MexcVCoin;
import com.mexc.dao.model.vcoin.MexcVcoinConfig;
import com.mexc.vcoin.TokenEnum;
import com.mexc.vcoin.WalletHolder;
import com.mexc.vcoin.bitcoin.BitServerUtil;
import com.mexc.vcoin.eth.ERC20TokenUtil;
import com.mexc.web.core.model.request.UserWalletToClodRequest;
import com.mexc.web.core.service.asset.impl.AssetServiceImpl;
import jdk.nashorn.internal.parser.Token;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.web3j.utils.Numeric;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.mexc.common.constant.RedisKeyConstant.CHECK_MAX_SEQ;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2017/12/30
 * Time: 上午11:41
 */
public class AssetBalanceCheckTask {
    private static final Logger logger = LoggerFactory.getLogger("balanceCheckTask");
    private static boolean checkLock = true;
    @Autowired
    AssetServiceImpl assetService;

    @Autowired
    private VCoinDelegate vCoinDelegate;

    public void balanceCheck() throws JobExecutionException {
        MDC.put("exeNo", TrackUtil.generateChannelFlowNo("BCK"));
        logger.info("开始检查资产余额,date:" + DateUtil.format(new Date(), DateUtil.YYYYMMDDHHMMSS));
        if (!checkLock) {
            logger.info("上次检查尚未完成,date:" + DateUtil.format(new Date(), DateUtil.YYYYMMDDHHMMSS));
            return;
        }
        String maxSeq = RedisUtil.get("check_max_seq");
        logger.info("获取上次检查资产的最大编号:{}", maxSeq);
        /** 如果缓存中没有则存放一个1**/
        if (StringUtils.isEmpty(maxSeq)) {
            logger.info("资产编号为空创建第一次资产检查编号");
            maxSeq = "1";
            RedisUtil.set(CHECK_MAX_SEQ, maxSeq);
        }
        MemberAssetDelegate assetDelegate = (MemberAssetDelegate) SpringUtils.getBean("memberAssetDelegate");
        List<AssetBalanceCheckDto> checkList = assetDelegate.balanceCheckList(Integer.valueOf(maxSeq), 50);
        logger.info("资产检查列表:{}", JSON.toJSONString(checkList));
        /** 如果已经查不到结果说明一轮已经进行完了**/
        if (CollectionUtils.isEmpty(checkList)) {
            logger.info("上一轮检查资产已完成或者没有资产可检查默认设置seq为1");
            RedisUtil.set(CHECK_MAX_SEQ, "1");
            maxSeq = "1";
            checkList = assetDelegate.balanceCheckList(Integer.valueOf(maxSeq), 50);
        }
        //读取内存配置
        Map<String,String> configMap = RedisUtil.hgetall("sys_dict");

        //处理比特币资产
        logger.info("这次检查btc地址");
        //获取比特币配置
        MexcVcoinConfig config = new MexcVcoinConfig();
        config.setThresholdHotToCold("0.0005");
        if(!configMap.isEmpty()&&configMap.size()>0)
        {
            List<MexcVCoin> vCoinList =  vCoinDelegate.queryMainVCoinList();
            for(MexcVCoin vcoin :vCoinList)
            {
                if(vcoin.getVcoinToken().equalsIgnoreCase(TokenEnum.BIT_COIN.getCode()))
                {
                    String json = configMap.get(vcoin.getId());
                    config = JSON.parseObject(json,MexcVcoinConfig.class);
                }
            }
        }
        BigDecimal btcBalance = BitServerUtil.getBalance();
        logger.info("btc 地址 check return:{}", btcBalance);
        Boolean isBTCContinue = true;
        BigDecimal toColdBalance = btcBalance.subtract(new BigDecimal(config.getThresholdHotToCold())).setScale(8, BigDecimal.ROUND_DOWN);
        if (toColdBalance.compareTo(BigDecimal.ZERO) < 0) {
            logger.info("btc资产转入冷钱包,余额过小不处理");
            isBTCContinue = false;
        }
        if (toColdBalance.compareTo(AssetServiceImpl.btcTxFee) < 1) {
            logger.info("btc资产转入冷钱包 资产过小 不转入");
            isBTCContinue = false;
        }
        if(isBTCContinue){
            UserWalletToClodRequest btcRequest = new UserWalletToClodRequest();
            btcRequest.setAccount("");
            btcRequest.setAssetId("");
            btcRequest.setIv("");
            btcRequest.setMainCoin(1);
            btcRequest.setMemberId("");
            btcRequest.setUserWalletAddressSecret("*");
            btcRequest.setUserPwdSecret("");
            btcRequest.setMainToken("BTC");
            btcRequest.setUserWalletFile("");
            btcRequest.setTransDate(new Date());
            btcRequest.setValue(toColdBalance);
            btcRequest.setWalletId("");
            btcRequest.setScale(0);
            assetService.userWalletToClodWallet(btcRequest);
        }

        logger.info("开始循环检查");
        for (AssetBalanceCheckDto dto : checkList) {
            MDC.put("SENO", TrackUtil.generateChannelFlowNo("AAA"));
            logger.info("assetId:{} mainCoin:{}", dto.getAssetId(), dto.getMainCoin());
            if (StringUtils.isEmpty(dto.getWalletAddress())) {
                logger.info("资产钱包地址为空直接跳过assetId:{}", dto.getAssetId());
                continue;
            }
            //获取阀值
            MexcVcoinConfig vcoinConfig = new MexcVcoinConfig();
            vcoinConfig.setThresholdHotToCold("0.001");
            if(!configMap.isEmpty()&& configMap.size()>0){
                String json = configMap.get(dto.getVCoinId());
                vcoinConfig = JSON.parseObject(json,MexcVcoinConfig.class);
            }
            String address = ThressDescUtil.decodeAssetAddress(dto.getWalletAddress());
            if (dto.getVcoinToken().equalsIgnoreCase(TokenEnum.ETH_COIN.getCode())) {
                try {
                    BigInteger balance = BigInteger.ZERO;
                    BigDecimal balanceVcoin = BigDecimal.ZERO;
                    logger.info("这次检查eth地址:{}", address);
                    /** 主币 即 eth **/
                    if (dto.getMainCoin() == 1) {
                        logger.info(address + "主币检查");
                        balance = ERC20TokenUtil.queryBalance(address);
                        balanceVcoin = ERC20TokenUtil.parseAmount(dto.getScale(), balance.toString());
                        logger.info("eth 阀值:{}", vcoinConfig.getThresholdHotToCold());
                        if (balanceVcoin.compareTo(new BigDecimal(vcoinConfig.getThresholdHotToCold())) < 0) {
                            logger.info("eth资产转入冷钱包,余额过小不处理");
                            continue;
                        }
                        logger.info("check return:{}", balance);
                    } else {
                        logger.info("合约检查" + dto.getContractAddress());
                        String balanceStr = ERC20TokenUtil.queryTokenBalance(address, dto.getContractAddress());
                        logger.info("check return:{}", balanceStr);
                        if ("0x".equalsIgnoreCase(balanceStr)) {
                            logger.info("如果是0x估计是空的 直接返回");
                            continue;
                        }
                        if (StringUtils.isNotEmpty(balanceStr)) {
                            balance = Numeric.decodeQuantity(balanceStr);
                            balanceVcoin = ERC20TokenUtil.parseAmount(dto.getScale(), balance.toString());
                        } else {
                            logger.info("余额字符为空,跳过");
                            continue;
                        }
                        if (balanceVcoin.compareTo(new BigDecimal(vcoinConfig.getThresholdHotToCold())) < 0) {
                            logger.info("eth代币资产转入冷钱包,余额过小不处理");
                            continue;
                        }
                    }
                    logger.info("开始给用户入账:{} balance:{}", JSON.toJSONString(dto), balanceVcoin);
                    UserWalletToClodRequest request = new UserWalletToClodRequest();
                    request.setScale(dto.getScale());
                    request.setAccount(dto.getAccount());
                    request.setAssetId(dto.getAssetId());
                    request.setIv(dto.getIv());
                    request.setMainCoin(dto.getMainCoin());
                    request.setMemberId(dto.getMemberId());
                    request.setUserWalletAddressSecret(ThressDescUtil.encodeAssetAddress(address));
                    request.setUserPwdSecret(dto.getAccountPwd());
                    request.setMainToken(dto.getVcoinToken());
                    request.setUserWalletFile(dto.getFilePath());
                    request.setTransDate(new Date());
                    request.setValue(new BigDecimal(balance));
                    request.setWalletId(dto.getWalletId());
                    request.setContractAddress(dto.getContractAddress());
                    request.setScale(dto.getScale());
                    assetService.userWalletToClodWallet(request);
                } catch (Exception e) {
                    logger.error("ETH余额入账异常", e);
                }
            }
            if (Integer.parseInt(dto.getMemberSeq()) > Integer.parseInt(maxSeq)) {
                maxSeq = dto.getMemberSeq();
            }
        }
        Integer end = Integer.parseInt(maxSeq) + 1;
        RedisUtil.set(CHECK_MAX_SEQ, end.toString());
    }
}
