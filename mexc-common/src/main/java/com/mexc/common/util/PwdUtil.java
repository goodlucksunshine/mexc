package com.mexc.common.util;

import com.laile.esf.common.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PwdUtil {

    public static String getMemberPwd(String account, String pwd) {
        try {
            return MD5Util.MD5(pwd + account);
        } catch (Exception e) {
            return pwd;
        }
    }
}
