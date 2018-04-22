package com.mexc.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by huangxinguang on 2017/7/31 下午5:26.
 */
public class PropsUtil {
    private final static Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);

    /**
     * 加载配置文件
     * @param fileName
     * @return
     */
    public static Properties loadProps(String fileName) {
        if(fileName == null) {
            throw new NullPointerException("fileName is null");
        }
        InputStream is = null;
        Properties properties = null;
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (is == null) {
                throw new FileNotFoundException(fileName + " file is not found!");
            }

            properties = new Properties();
            properties.load(is);
        }catch (IOException e) {
            LOGGER.error("load properties file failure",e);
        }finally {
            if(is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.error("close input stream failure");
                }
            }
        }
        return properties;
    }

    /**
     * 获取string
     * @param properties
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getString(Properties properties,String key,String defaultValue) {
        String value = defaultValue;
        if(properties.containsKey(key)) {
            value = properties.get(key).toString();
        }
        return value;
    }

    public static String getString(Properties properties,String key) {
        return getString(properties,key,"");
    }

    /**
     * 获取int
     * @param properties
     * @param key
     * @param defaultValue
     * @return
     */
    public static int getInt(Properties properties,String key,int defaultValue) {
        int value = defaultValue;
        if(properties.containsKey(key)) {
            value = CastUtil.castInt(properties.get(key));
        }
        return value;
    }

    public static int getInt(Properties properties,String key) {
        return getInt(properties,key,0);
    }

    /**
     * 获取boolean
     * @param properties
     * @param key
     * @param defaultValue
     * @return
     */
    public static boolean getBoolean(Properties properties,String key,boolean defaultValue) {
        boolean value = defaultValue;
        if(properties.containsKey(key)) {
            value = CastUtil.castBoolean(properties.get(key));
        }
        return value;
    }

    public static boolean getBoolean(Properties properties,String key) {
        return getBoolean(properties,key,false);
    }
}
