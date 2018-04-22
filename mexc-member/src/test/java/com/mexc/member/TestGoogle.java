package com.mexc.member;

import com.mexc.common.util.ThressDescUtil;
import com.mexc.common.util.google.GoogleAuthenticator;
import org.junit.Test;


/**
 * google授权测试
 * Created by huangxinguang on 2017/12/13 下午4:23.
 */
public class TestGoogle {

    @Test
    public void genSecretTest() {
        String secret = GoogleAuthenticator.generateSecretKey();
        String url = GoogleAuthenticator.getQRBarcodeURL("testuser", "laile.com", secret);
        System.out.println("Please register " + url);
        System.out.println("Secret key is " + secret);
    }

    //生成的密匙，保存到数据库中
    static String savedSecret = "WJ3SGMBBMQ3Z7FTH";

    @Test
    public void authTest() {
        //手机上的google码，过期时间内有效
        long code = 714125;
        GoogleAuthenticator ga = new GoogleAuthenticator();
        ga.setWindowSize(5); //should give 5 * 30 seconds of grace...
        boolean r = ga.checkCode(savedSecret, code);
        System.out.println("Check code = " + r);
    }


    public static void main(String[] args) {
        System.out.println(ThressDescUtil.decodeAssetAddress("HLSaDkQkaS2SNDjeBnAMxHlzHAq3idm6FipjIJn9CqVpQ4QlXAcvbA=="));
    }
}
