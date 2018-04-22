package com.mexc.common.plugin;

import com.laile.esf.common.util.Page;
import com.laile.esf.common.util.ReflectHelper;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.PropertyException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class PagePlugin implements Interceptor {
    Logger logger = LoggerFactory.getLogger(PagePlugin.class);
    private static String dialect = "";

    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler sh = (StatementHandler) invocation.getTarget();
        if (sh instanceof RoutingStatementHandler) {
            RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation
                    .getTarget();
            BaseStatementHandler delegate = (BaseStatementHandler) ReflectHelper
                    .getValueByFieldName(statementHandler, "delegate");
            MappedStatement mappedStatement = (MappedStatement) ReflectHelper
                    .getValueByFieldName(delegate, "mappedStatement");
            BoundSql boundSql = delegate.getBoundSql();
            Object parameterObject = boundSql.getParameterObject();
            if (parameterObject == null) {
                return invocation.proceed();
            }
            Page page = null;
            if (parameterObject instanceof Page) {
                page = (Page) parameterObject;
            } else if (parameterObject instanceof Map) {
                for (Map.Entry<String, Object> e : ((Map<String, Object>) parameterObject).entrySet()) {
                    if (e.getValue() instanceof Page) {
                        page = (Page) e.getValue();
                        break;
                    }
                }
            }
            if (page == null) {
                return invocation.proceed();
            }
            Connection connection = (Connection) invocation.getArgs()[0];
            String sql = boundSql.getSql();
            String countSql = MySQLHelper.count(sql);
            logger.info("总数sql 语句:" + countSql);
            PreparedStatement countStmt = connection
                    .prepareStatement(countSql);
            BoundSql countBS = new BoundSql(
                    mappedStatement.getConfiguration(), countSql,
                    boundSql.getParameterMappings(), parameterObject);
            for (ParameterMapping mapping : boundSql.getParameterMappings()) {
                String prop = mapping.getProperty();
                if (boundSql.hasAdditionalParameter(prop)) {
                    countBS.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
                }
            }
            setParameters(countStmt, countBS, mappedStatement, parameterObject);
            ResultSet rs = countStmt.executeQuery();
            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            countStmt.close();
            page.setTotalResult(count);
            String pageSql = generatePageSql(sql, page);
            System.out.println("page sql:" + pageSql);
            ReflectHelper.setValueByFieldName(boundSql, "sql", pageSql);
        }
        return invocation.proceed();
    }

    public void setParameters(PreparedStatement ps, BoundSql boundSql, MappedStatement mappedStatement, Object parameterObject) throws SQLException {
        ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
        Configuration configuration = mappedStatement.getConfiguration();
        TypeHandlerRegistry typeHandlerRegistry = configuration
                .getTypeHandlerRegistry();
        //获取所有参数
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings != null) {
            for (int i = 0; i < parameterMappings.size(); i++) {
                //获取某个参数
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value;
                    //参数的名字，属性
                    String propertyName = parameterMapping.getProperty();
                    //先从附加的
                    if (boundSql.hasAdditionalParameter(propertyName)) { // issue #448 ask first for additional params
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (parameterObject == null) {
                        value = null;
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                        //typeHandlerRegistry注册了某个类的处理
                        value = parameterObject;
                    } else {
                        //默认的MetaObject 的处理，根据参数获取值,这个将和boundSql一起解释
                        MetaObject metaObject = configuration.newMetaObject(parameterObject);
                        value = metaObject.getValue(propertyName);
                    }
                    //参数列的TypeHandler
                    TypeHandler typeHandler = parameterMapping.getTypeHandler();
                    //jdbcType的处理
                    JdbcType jdbcType = parameterMapping.getJdbcType();
                    if (value == null && jdbcType == null) jdbcType = configuration.getJdbcTypeForNull();
                    //见下面的分析，这里实现了 JDBC的 pst.setString(1,"Tom" + i);
                    typeHandler.setParameter(ps, i + 1, value, jdbcType);
                }
            }
        }
    }

    private String generatePageSql(String sql, Page page) {
        if (page != null && (dialect != null || !dialect.equals(""))) {
            if (page.getTotalPage() < page.getCurrentPage()) {
                page.setCurrentPage(page.getTotalPage());
            }
            StringBuffer pageSql = new StringBuffer();
            if ("mysql".equalsIgnoreCase(dialect)) {
                pageSql.append(sql);
                pageSql.append(" limit " + page.getCurrentResult() + ","
                        + page.getShowCount());
            } else if ("oracle".equalsIgnoreCase(dialect)) {
                pageSql.append("select * from (select tmp_tb.*,ROWNUM row_id from (");
                pageSql.append(sql);
                pageSql.append(")  tmp_tb where ROWNUM<=");
                pageSql.append(page.getCurrentResult() + page.getShowCount());
                pageSql.append(") where row_id>");
                pageSql.append(page.getCurrentResult());
            }
            return pageSql.toString();
        } else {
            return sql;
        }
    }

    public Object plugin(Object arg0) {
        return Plugin.wrap(arg0, this);
    }

    public void setProperties(Properties p) {
        dialect = p.getProperty("dialect");
        if (dialect == null || dialect.equals("")) {
            try {
                throw new PropertyException("dialect property is not found!");
            } catch (PropertyException e) {
                e.printStackTrace();
            }
        }
        if (dialect == null || dialect.equals("")) {
            try {
                throw new PropertyException("pageSqlId property is not found!");
            } catch (PropertyException e) {
                e.printStackTrace();
            }
        }
    }

}