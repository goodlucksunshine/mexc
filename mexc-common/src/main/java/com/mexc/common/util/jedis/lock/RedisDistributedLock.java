package com.mexc.common.util.jedis.lock;

import com.mexc.common.util.jedis.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by huangxinguang on 2018/3/8 上午11:48.
 */
public class RedisDistributedLock {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisDistributedLock.class);
    private int lockExpireSecs;
    private int lockWaitTimeOutSecs;
    private int retryAquireLockFrequence;
    private String key;
    private static final long SETNX_SUCCESS = 1L;
    private boolean isLock;

    public RedisDistributedLock(String productCode, Map<String, String> keyMap) {
        this.lockExpireSecs = 8000;
        this.lockWaitTimeOutSecs = 10000;
        this.retryAquireLockFrequence = 100;
        StringBuilder sb = new StringBuilder();
        sb.append(productCode);
        Iterator i$ = keyMap.entrySet().iterator();

        while(i$.hasNext()) {
            Map.Entry entry = (Map.Entry)i$.next();
            sb.append((String)entry.getValue());
        }

        this.key = sb.toString();
    }

    public RedisDistributedLock(String productCode, Map<String, String> keyMap, int lockExpireSecs, int lockWaitTimeOutSecs) {
        this(productCode, keyMap);
        this.lockExpireSecs = lockExpireSecs;
        this.lockWaitTimeOutSecs = lockWaitTimeOutSecs;
    }

    public RedisDistributedLock(String productCode, Map<String, String> keyMap, int lockExpireSecs, int lockWaitTimeOutSecs, int retryAquireLockFrequence) {
        this(productCode, keyMap, lockExpireSecs, lockWaitTimeOutSecs);
        this.retryAquireLockFrequence = retryAquireLockFrequence;
    }

    public boolean lock() {
        try {
            for(int t = this.lockWaitTimeOutSecs; t >= 0; Thread.sleep((long)this.retryAquireLockFrequence)) {
                long expires = System.currentTimeMillis() + (long)this.lockExpireSecs;
                String expiresStr = String.valueOf(expires);
                long resValue = RedisUtil.setnx(this.key, expiresStr);
                if(1L == resValue) {
                    LOGGER.info("RedisDistributedLock key={} get lock success", this.key);
                    this.isLock = true;
                    return true;
                }

                String currentVauleStr = RedisUtil.get(this.key);
                if(null != currentVauleStr && System.currentTimeMillis() > Long.parseLong(currentVauleStr)) {
                    String oldVauleStr = RedisUtil.getset(this.key, expiresStr);
                    if(null != oldVauleStr && oldVauleStr.equals(currentVauleStr)) {
                        LOGGER.info("RedisDistributedLock key={} get lock success after previous thread timeout", this.key);
                        this.isLock = true;
                        return true;
                    }
                }

                t -= this.retryAquireLockFrequence;
                if(t % 1000 == 0) {
                    LOGGER.info("RedisDistributedLock key={} get lock fail,timeout={},let me sleep for a while", this.key, Integer.valueOf(t));
                }
            }

            LOGGER.info("RedisDistributedLock key={} count not get the lock", this.key);
            return false;
        } catch (Throwable var9) {
            LOGGER.error("try to get lock using Redis error");
            return true;
        }
    }

    public void unlock() {
        try {
            if(this.isLock) {
                String t = RedisUtil.get(this.key);
                if(System.currentTimeMillis() < Long.parseLong(t)) {
                    RedisUtil.del(this.key);
                    this.isLock = false;
                    LOGGER.info("RedisDistributedLock key={} release lock", this.key);
                }
            }
        } catch (Throwable var2) {
            LOGGER.error("try to get unlock using Redis error");
        }

    }
}
