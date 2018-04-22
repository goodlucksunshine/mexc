package com.mexc.dfs.constant;


import com.laile.esf.common.config.PropertyPlaceholderConfigurer;

/**
 * Created by huangxinguang on 2017/10/31 下午2:29.
 */
public class DFSConstant {

    public static String FILE_SERVER = PropertyPlaceholderConfigurer.getProperty("fileServer");

    public static String COMMON_PATH = PropertyPlaceholderConfigurer.getProperty("commonPath");
}
