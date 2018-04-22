/*
 * Copyright 2014 Qunar.com All right reserved. This software is the
 * confidential and proprietary information of Qunar.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Qunar.com.
 */
package com.mexc.common.plugin;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 实现描述：MySQL分页
 *
 * @author changjiang.tang
 * @version v1.0.0
 * @see 
 * @since 2015年7月28日 下午8:55:33
 */
public abstract class MySQLHelper {

    private static final String DELIMITER = ";";
    private static final Pattern STANDARD_TOKENIZER = Pattern.compile(
            "\\bselect\\b|\\bfrom\\b|\\bgroup\\s+by\\b|\\border\\s+by\\b|\\(|\\)", Pattern.CASE_INSENSITIVE);
    private static final Pattern COMPOSITE_TOKENIZER = Pattern.compile("union|intersect|except",
            Pattern.CASE_INSENSITIVE);

    public static String limit(String sql, int offset, int limit) {
        sql = normalize(sql);
        StringBuilder sb = new StringBuilder();
        sb.append(sql).append(" limit ").append(offset);
        if (limit > 0)
            sb.append(" , ").append(limit);
        return sb.toString();
    }

    private static String normalize(String sql) {
        sql = sql.trim();
        if (sql.endsWith(DELIMITER))
            sql = sql.substring(0, sql.length() - DELIMITER.length()).trim();
        return sql;
    }

    public static String count(String sql) {
        sql = normalize(sql);

        if (COMPOSITE_TOKENIZER.matcher(sql).find()) {
            // 包括union/intersect/except关键字，采用通用包装方案
            return String.format("select count(1) from (%s) __paging", sql);
        }

        // 基于语法分析生成
        String convertedSql = convert(sql);
        if (convertedSql != null)
            return convertedSql;

        // 其他情况，采用通用包装方案
        return String.format("select count(1) from (%s) __paging", sql);
    }

    private static String convert(String sql) {
        Matcher m = STANDARD_TOKENIZER.matcher(sql);

        // 第一个关键字一定要是select
        if (!m.find() || !m.group().toLowerCase().equals("select"))
            return null;

        Stack<String> stack = new Stack<String>();

        // 找from，跳过配对的括号
        int fromStart = -1;
        while (m.find()) {
            if (m.group().toLowerCase().equals("from")) {
                fromStart = m.start();
                break;
            }
            if (!balanceParentheses(m, stack))
                return null; // 括号不配对
        }
        if (fromStart < 0)
            return null; // 没找到from

        // 找order by，并判断是否有group by，跳过配对的括号
        boolean hasGroupBy = false;
        int orderByStart = -1;
        while (m.find()) {
            if (m.group().toLowerCase().startsWith("group")) { // group by
                hasGroupBy = true;
                continue;
            }
            if (m.group().toLowerCase().startsWith("order")) { // order by
                orderByStart = m.start();
                break;
            }
            if (!balanceParentheses(m, stack))
                return null; // 括号不配对
        }

        // 截去select部分和order by部分
        String partial;
        if (orderByStart < 0) {
            partial = sql.substring(fromStart);
        } else {
            partial = sql.substring(fromStart, orderByStart).trim();
        }

        // 根据有无group by，决定生成方式
        if (hasGroupBy)
            return String.format("select count(1) from (select 1 %s) __paging", partial);
        return String.format("select count(1) %s", partial);
    }

    private static boolean balanceParentheses(Matcher m, Stack<String> stack) {
        if (!m.group().equals("("))
            return true;

        stack.push("(");
        while (m.find()) {
            if (m.group().equals("(")) {
                stack.push("(");
            } else if (m.group().equals(")")) {
                if (stack.isEmpty())
                    return false;
                stack.pop();
                if (stack.isEmpty())
                    return true;
            }
            // ignore all other cases
        }
        return stack.isEmpty();
    }

}
