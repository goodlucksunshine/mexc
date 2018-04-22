package com.mexc.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by huangxinguang on 2018/1/13 下午1:27.
 */
public class ListGroupUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(ListGroupUtil.class);
    /**
     * 将List<V>按照V的某个方法返回值（返回值必须为K类型）分组，合入到Map<K, List<V>>中<br>
     * 要保证入参的method必须为V的某一个有返回值的方法，并且该返回值必须为K类型
     *
     * @param list 待分组的列表
     * @param map 存放分组后的map
     * @param method 方法
     */
    @SuppressWarnings("unchecked")
    private static <K, V> void listGroup2Map(List<V> list, Map<K, List<V>> map, Method method) {
        // 入参非法行校验
        if (null == list || null == map || null == method) {
            LOGGER.error("CommonUtils.listGroup2Map 入参错误，list：" + list + " ；map：" + map
                    + " ；method：" + method);
            return;
        }

        try {
            // 开始分组
            Object key;
            List<V> listTmp;
            for (V val : list) {
                key = method.invoke(val);
                listTmp = map.get(key);
                if (null == listTmp) {
                    listTmp = new ArrayList<V>();
                    map.put((K) key, listTmp);
                }
                listTmp.add(val);
            }
        } catch (Exception e) {
            LOGGER.error("分组失败！", e);
        }
    }

    /**
     * 根据类和方法名，获取方法对象
     *
     * @param clazz
     * @param methodName
     * @return
     */
    private static Method getMethodByName(Class<?> clazz, String methodName) {
        Method method = null;
        // 入参不能为空
        if (null == clazz || StringUtils.isBlank(methodName)) {
            LOGGER.error("CommonUtils.getMethodByName 入参错误，clazz：" + clazz + " ；methodName："
                    + methodName);
            return method;
        }

        try {
            method = clazz.getDeclaredMethod(methodName);
        } catch (Exception e) {
            LOGGER.error("类获取方法失败！", e);
        }

        return method;
    }

    /**
     * 将List<V>按照V的methodName方法返回值（返回值必须为K类型）分组，合入到Map<K, List<V>>中<br>
     * 要保证入参的method必须为V的某一个有返回值的方法，并且该返回值必须为K类型
     *  @param list 待分组的列表
     * @param map 存放分组后的map
     * @param clazz 泛型V的类型
     * @param methodName 方法名
     */
    public static <K, V> void listGroup2Map(List<V> list, Map<K, List<V>> map, Class<V> clazz, String methodName) {
        // 入参非法行校验
        if (null == list || null == map || null == clazz || StringUtils.isBlank(methodName)) {
            LOGGER.error("CommonUtils.listGroup2Map 入参错误，list：" + list + " ；map：" + map
                    + " ；clazz：" + clazz + " ；methodName：" + methodName);
            return;
        }

        // 获取方法
        Method method = getMethodByName(clazz, methodName);
        // 非空判断
        if (null == method) {
            return;
        }

        // 正式分组
        listGroup2Map(list, map, method);
    }
}
