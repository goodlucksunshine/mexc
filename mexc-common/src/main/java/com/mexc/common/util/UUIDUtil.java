package com.mexc.common.util;

import java.util.UUID;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2018/1/4
 * Time: 上午11:56
 */
public class UUIDUtil {

    public UUIDUtil() {
    }

    public static String get32UUID() {
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return uuid;
    }
}
