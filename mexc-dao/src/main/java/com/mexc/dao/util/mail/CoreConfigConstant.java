package com.mexc.dao.util.mail;

import com.laile.esf.common.config.PropertyPlaceholderConfigurer;

/**
 * Class Describe
 * <p>
 * User: yangguang Date: 17/8/1 Time: 下午8:46
 */
public class CoreConfigConstant {

    public static String EMAIL_USER = PropertyPlaceholderConfigurer.getProperty("EMAIL_USER");

    public static String EMAIL_PWD = PropertyPlaceholderConfigurer.getProperty("EMAIL_PWD");

    public static String EMAIL_URL = PropertyPlaceholderConfigurer.getProperty("EMAIL_URL");


    public static String WARNING_EMAIL = PropertyPlaceholderConfigurer.getProperty("WARING_EMAIL");

}
