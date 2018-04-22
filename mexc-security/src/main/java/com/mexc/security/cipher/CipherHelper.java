package com.mexc.security.cipher;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * Class Describe
 * <p/>
 * User: yangguang
 * Date: 17/6/23
 * Time: 下午6:39
 */
public class CipherHelper {
    private static String algorithmName = "md5";
    private static final int hashIterations = 1;

    public static String encryptPassword(String password, String salt) {
        String newPassword = new SimpleHash(
                algorithmName,
                password,
                ByteSource.Util.bytes(salt),
                hashIterations).toHex();
        return newPassword;
    }
}
