package com.mexc.task;

import com.alibaba.fastjson.JSON;
import com.laile.esf.common.util.SpringUtils;
import com.mexc.common.util.ThressDescUtil;
import com.mexc.common.util.jedis.RedisUtil;
import com.mexc.dao.delegate.vcoin.MexcAddressLibDelegate;
import com.mexc.dao.delegate.vcoin.VCoinDelegate;
import com.mexc.dao.model.vcoin.MexcAddressLib;
import com.mexc.dao.model.vcoin.MexcVCoin;
import com.mexc.task.quartz.AddressUsePercentCheck;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.mexc.common.constant.RedisKeyConstant.*;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2018/1/19
 * Time: 下午2:45
 */
public class AddressLibContext {
    final static Logger logger = LoggerFactory.getLogger(AddressLibContext.class);
    static MexcAddressLibDelegate mexcAddressLibDelegate;
    static VCoinDelegate vCoinDelegate;
    static boolean initStatus = false;

    {
        mexcAddressLibDelegate = (MexcAddressLibDelegate) SpringUtils.getBean("mexcAddressLibDelegate");
        vCoinDelegate = (VCoinDelegate) SpringUtils.getBean("VCoinDelegate");
        putAddressToRedis(0, 100);
        logger.info("资产地址存放成功");
        putContractToRedis();
        logger.info("合约地址存放成功");
        initStatus = true;
        logger.info("更新状态");
        AddressUsePercentCheck addressUsePercentCheck = (AddressUsePercentCheck) SpringUtils.getBean("addressCreate");
        addressUsePercentCheck.addressUseCheck();
    }


    public static boolean getInitStatus() {
        return initStatus;
    }


    /**
     * 分页查询资产地址库，将资产库添加进redis
     *
     * @param max
     * @param limit
     */
    public static void putAddressToRedis(Integer max, Integer limit) {
        logger.info("查询资产库并存放redis");
        List<MexcAddressLib> list = mexcAddressLibDelegate.queryAddressLib(max, limit);
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        Set<String> addressAll = new HashSet<>();
        Map<String, Set<MexcAddressLib>> addressMap = new HashMap<>();
        logger.info("分批查询地址库");
        for (MexcAddressLib addressLib : list) {
            Set<MexcAddressLib> addressNotUsed = addressMap.get(addressLib.getType());
            if (addressNotUsed == null) {
                addressNotUsed = new HashSet<>();
            }
            if (1 != addressLib.getIsuse()) {
                logger.info("未使用的加入缓存对应额key");
                addressNotUsed.add(addressLib);
            }
            addressAll.add(ThressDescUtil.decodeAssetAddress(addressLib.getAddress()));
            addressMap.put(addressLib.getType().toUpperCase(), addressNotUsed);
            logger.info("已使用的全部解析出地址加入缓存");
        }
        logger.info("addressMap:{}", JSON.toJSONString(addressMap));
        Iterator<String> addressIte = addressAll.iterator();
        while (addressIte.hasNext()) {
            RedisUtil.sadd(ADDRESS_LIB, addressIte.next().toLowerCase());
        }
        Iterator iterator = addressMap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            String redisKey = key + "_" + ADDRESS_LIB_NOT_USED;
            logger.info("准备存放redis key:{}", redisKey);
            Set<MexcAddressLib> libSet = addressMap.get(key);
            Iterator iteratorSet = libSet.iterator();
            while (iteratorSet.hasNext()) {
                RedisUtil.sadd(redisKey, JSON.toJSONString(iteratorSet.next()));
            }
        }
        max = list.get(list.size() - 1).getId();
        putAddressToRedis(max, limit);
    }


    public static void putContractToRedis() {
        List<MexcVCoin> vCoins = vCoinDelegate.queryContract();
        if (CollectionUtils.isEmpty(vCoins)) {
            return;
        }
        for (MexcVCoin vCoin : vCoins) {
            if (StringUtils.isNotEmpty(vCoin.getContractAddress())) {
                RedisUtil.sadd(CONTRACT_LIB, vCoin.getContractAddress().toLowerCase());
            }
        }

    }


    public static Long srem(String type, String value) {
        return RedisUtil.srem(type + "_" + ADDRESS_LIB_NOT_USED, value);
    }

    public static String popAddress(String type) {
        return RedisUtil.spop(type + "_" + ADDRESS_LIB_NOT_USED);
    }

    public static String sRandMember(String type) {
        return RedisUtil.srandmember(type + "_" + ADDRESS_LIB_NOT_USED);
    }


    public static void appendNotUsed(String type, Set<MexcAddressLib> set) {
        if (set == null) {
            return;
        }
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            RedisUtil.sadd(type + "_" + ADDRESS_LIB_NOT_USED, JSON.toJSONString(iterator.next()));
        }
    }


    public static void appendAll(Set<String> set) {
        if (set == null) {
            return;
        }
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            RedisUtil.sadd(ADDRESS_LIB, ThressDescUtil.decodeAssetAddress(iterator.next()).toLowerCase());
        }

    }


    public static boolean checkAddress(String address) {
        return RedisUtil.sismember(ADDRESS_LIB, address.toLowerCase());
    }

    public static boolean checkContract(String contract) {
        return RedisUtil.sismember(CONTRACT_LIB, contract);
    }
}
