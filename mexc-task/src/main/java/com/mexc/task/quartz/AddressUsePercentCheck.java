package com.mexc.task.quartz;

import com.laile.esf.common.util.RandomUtil;
import com.laile.esf.common.util.TrackUtil;
import com.mexc.common.util.ThressDescUtil;
import com.mexc.dao.delegate.vcoin.MexcAddressLibDelegate;
import com.mexc.dao.model.vcoin.MexcAddressLib;
import com.mexc.task.AddressLibContext;
import com.mexc.vcoin.TokenEnum;
import com.mexc.vcoin.bitcoin.BitServerUtil;
import com.mexc.vcoin.eth.ERC20TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.web3j.crypto.WalletUtils;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2018/1/20
 * Time: 下午1:21
 */
public class AddressUsePercentCheck {
    private static Logger logger = LoggerFactory.getLogger("addressCheck");
    @Value("${eth_path}")
    private String eth_path;
    @Value("${coin_list}")
    private String coin_list;
    private static boolean lock = false;
    @Autowired
    private MexcAddressLibDelegate mexcAddressLibDelegate;


    public void addressUseCheck() {
        MDC.put("exeNo", TrackUtil.generateChannelFlowNo("ACK"));
        if (!AddressLibContext.getInitStatus()) {
            logger.info("地址库未加载完成,任务延迟");
            return;
        }
        if (lock) {
            logger.info("上一次生产地址尚未完成");
            return;
        }
        lock = true;
        //查询地址库使用率
        List<Map> list = mexcAddressLibDelegate.queryUsePercent();
        Map<String, Integer> usePercent = new HashMap<>();
        //计算使用率百分比
        String[] coinType = coin_list.split(",");
        //初始化为100%
        for (String type : coinType) {
            usePercent.put(type, 100);
        }
        //查询数据库百分比 以免漏掉
        for (Map map : list) {
            BigDecimal usedCount = new BigDecimal((Long) map.get("used"));
            BigDecimal notUsed = new BigDecimal((Long) map.get("not_use"));
            String type = (String) map.get("type");
            BigDecimal percent = usedCount.divide(usedCount.add(notUsed)).multiply(new BigDecimal(100));
            usePercent.put(type, percent.intValue());
        }
        Iterator iterator = usePercent.keySet().iterator();
        while (iterator.hasNext()) {
            String type = (String) iterator.next();
            Integer percent = usePercent.get(type);
            //百分比大于80%则补充地址库
            if (percent > 80) {
                genAddress(type);
            }
        }
        lock = false;
    }


    private void genAddress(String type) {
        switch (TokenEnum.forCode(type)) {
            case BIT_COIN:
                genBtc();
                break;
            case ETH_COIN:
                try {
                    genEth();
                } catch (Exception e) {
                    logger.error("生成以太坊钱包地址异常");
                }
                break;
            default:
                break;
        }
    }

    private void genEth() {
        Set<MexcAddressLib> addressLibs = new HashSet<>();
        Set<String> notUsed = new HashSet<>();
        Date now = new Date();
        for (int i = 0; i < 10; i++) {
            String pwd = RandomUtil.randomStr(24);
            String iv = RandomUtil.randomStr(8);
            String address = ERC20TokenUtil.createWalletAccountBySecret(pwd);
            String encodAddress = ThressDescUtil.encodeAssetAddress(address);
            notUsed.add(encodAddress);
            notUsed.add(address);
            MexcAddressLib lib = new MexcAddressLib();
            lib.setType(TokenEnum.ETH_COIN.getCode());
            lib.setAddress(encodAddress);
            lib.setCreateTime(now);
            lib.setIsuse(0);
            lib.setIv(iv);
            lib.setPwd(ThressDescUtil.encodeAssetPwd(pwd, iv));
            lib.setFilepath("");
            addressLibs.add(lib);
        }
        mexcAddressLibDelegate.insertBatch(new ArrayList<>(addressLibs));
        //刷新redis缓存全部地址及未使用地址
        AddressLibContext.appendAll(notUsed);
        AddressLibContext.appendNotUsed(TokenEnum.ETH_COIN.getCode(), addressLibs);
    }


    private String createEthFile(String pwd) {
        String relFilePath = RandomUtil.randomStr(1, RandomUtil.NUM_CHAR) + File.separator;
        try {
            File file = new File(eth_path + relFilePath + "/");
            if (!file.exists()) {
                file.mkdirs();
            }
            logger.info("生成虚拟币资产 生成对应文件");
            String fileName = WalletUtils.generateFullNewWalletFile(pwd, file);
            logger.info("生成虚拟币资产 生成对应文件文件地址==>" + file);
            return file.getPath() + "/" + fileName;
        } catch (Exception e) {
            logger.error("生成钱包文件异常", e);
            return "";
        }
    }


    private void genBtc() {
        Set<MexcAddressLib> addressLibs = new HashSet<>();
        Set<String> notUsed = new HashSet<>();
        Date now = new Date();
        for (int i = 0; i < 10; i++) {
            String pwd = RandomUtil.randomStr(24);
            String iv = RandomUtil.randomStr(8);
            MexcAddressLib lib = new MexcAddressLib();
            lib.setType(TokenEnum.BIT_COIN.getCode());
            String address = BitServerUtil.createAddress();
            //设置地址标签
            BitServerUtil.setAccount(address,address);
            String encodAddress = ThressDescUtil.encodeAssetAddress(address);
            notUsed.add(encodAddress);
            lib.setAddress(encodAddress);
            lib.setCreateTime(now);
            lib.setIsuse(0);
            lib.setIv(iv);
            lib.setPwd(ThressDescUtil.encodeAssetPwd(pwd, iv));
            addressLibs.add(lib);
        }
        mexcAddressLibDelegate.insertBatch(new ArrayList<>(addressLibs));
        //刷新redis缓存全部地址及未使用地址
        AddressLibContext.appendAll(notUsed);
        AddressLibContext.appendNotUsed(TokenEnum.BIT_COIN.getCode(), addressLibs);
    }

}
