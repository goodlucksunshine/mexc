package com.mexc.common.util.jedis;

import org.apache.commons.lang3.StringUtils;

import redis.clients.jedis.Jedis;

/**
 * Created by 23626 on 2016/10/25.
 */
public class JedisLock {

    private static final int LOCKKEY_EXPIRE_TIME = 3;

    /**
     * 加锁
     *
     * @param key
     * @param timeout
     * @param jedis
     * @return
     */
    public boolean lock(String key, long timeout, Jedis jedis) {
        boolean lockSuccess = false;
        try {
            long start = System.currentTimeMillis();
            String lockKey = "lock_" + key;
            do {
                long result = jedis.setnx(lockKey, String.valueOf(System.currentTimeMillis() + LOCKKEY_EXPIRE_TIME * 1000 + 1));
                if (result == 1) {
                    lockSuccess = true;
                    break;
                } else {
                    String lockTimeStr = jedis.get(lockKey);
                    if (StringUtils.isNumeric(lockTimeStr)) {//如果key存在，锁存在
                        long lockTime = Long.valueOf(lockTimeStr);
                        if (lockTime < System.currentTimeMillis()) {//锁已过期
                            String originStr = jedis.getSet(lockKey, String.valueOf(System.currentTimeMillis() + LOCKKEY_EXPIRE_TIME * 1000 + 1));
                            if (!StringUtils.isEmpty(originStr) && originStr.equals(lockTimeStr)) {//表明锁由该线程获得
                                lockSuccess = true;
                                break;
                            }
                        }
                    }
                }
                //如果不等待，则直接返回
                if (timeout == 0) {
                    break;
                }
                //等待300ms继续加锁
                Thread.sleep(300);
            } while ((System.currentTimeMillis() - start) < timeout);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return lockSuccess;
    }

    public void unLock(String key, Jedis jedis) {
        try {
            String lockKey = "lock_" + key;
            jedis.del(lockKey);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }
    public JedisLock() {
//        this.jedis = jedis;
    }
//
//    String lockKey;
//
//    int expireMsecs = 60 * 1000;
//
//    int timeoutMsecs = 10 * 1000;
//
//    boolean locked = false;
//
//    public JedisLock(Jedis jedis, String lockKey) {
//        this.jedis = jedis;
//        this.lockKey = lockKey;
//    }
//
//
//    public JedisLock(Jedis jedis, String lockKey, int timeoutMsecs) {
//        this(jedis, lockKey);
//        this.timeoutMsecs = timeoutMsecs;
//    }
//
//    public JedisLock(Jedis jedis, String lockKey, int timeoutMsecs, int expireMsecs) {
//        this(jedis, lockKey, timeoutMsecs);
//        this.expireMsecs = expireMsecs;
//    }
//
//    public JedisLock(String lockKey) {
//        this(null, lockKey);
//    }
//
//    public JedisLock(String lockKey, int timeoutMsecs) {
//        this(null, lockKey, timeoutMsecs);
//    }
//
//    public JedisLock(String lockKey, int timeoutMsecs, int expireMsecs) {
//        this(null, lockKey, timeoutMsecs, expireMsecs);
//    }
//
//    public String getLockKey() {
//        return lockKey;
//    }

//    public synchronized boolean acquire() throws InterruptedException {
//        return acquire(jedis);
//    }
//
//    public synchronized boolean acquire(Jedis jedis) throws InterruptedException {
//        int timeout = timeoutMsecs;
//        while (timeout >= 0) {
//            long expires = System.currentTimeMillis() + expireMsecs + 1;
//            String expiresStr = String.valueOf(expires);
//            //成功
//            if (jedis.setnx(lockKey, expiresStr) == 1) {
//                locked = true;
//                return true;
//            }
//            //key
//            String currentValueStr = jedis.get(lockKey);
//            if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
//                // lock is expired
//
//                String oldValueStr = jedis.getSet(lockKey, expiresStr);
//                if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
//                    // lock acquired
//                    locked = true;
//                    return true;
//                }
//            }
//
//            timeout -= 100;
//            Thread.sleep(100);
//        }
//
//        return false;
//    }

//    public synchronized boolean acquire(Jedis jedis, String... keysvalues) throws InterruptedException {
//        int timeout = timeoutMsecs;
//        while (timeout >= 0) {
//
//            if (jedis.msetnx(keysvalues) == 1) {
//                // lock acquired
//                locked = true;
//                return true;
//            }
//
////            List<String> list = jedis.mget(keysvalues);
////            if (list != null && list.size() != 0) {
////                // lock is expired
////                String listStr = JSONArray.fromObject(list).toString();
////                List<String> oldlist = jedis.mget(keysvalues);
////
////                String oldValueStr = null;
////                JSONArray.fromObject(oldlist).toString();
////
////                System.out.println(listStr);
////                System.out.println(oldValueStr);
////                if (oldValueStr != null && oldValueStr.equals(listStr)) {
////                    // lock acquired
////                    locked = true;
////                    return true;
////                }
////            }
//
//            timeout -= 100;
//            Thread.sleep(100);
//        }
//
//        return false;
//    }

//    public synchronized void release() {
//        release(jedis);
//    }

//    public synchronized void release(Jedis jedis) {
//        if (locked) {
//            jedis.del(lockKey);
//            locked = false;
//        }
//    }

}