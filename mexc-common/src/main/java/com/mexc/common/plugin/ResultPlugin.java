package com.mexc.common.plugin;

import com.laile.esf.common.util.Page;
import com.laile.esf.common.util.ReflectHelper;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Mybatis - 通用分页拦截器
 *
 * @author liuzh/abel533/isea
 *         Created by liuzh on 14-4-15.
 */
@Intercepts({@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})})
public class ResultPlugin implements Interceptor {
    private static final Logger logger = LoggerFactory.getLogger(ResultPlugin.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        ResultSetHandler resultSetHandler = (ResultSetHandler) invocation.getTarget();
        ParameterHandler parameterHandler = (ParameterHandler) ReflectHelper.getValueByFieldName(resultSetHandler, "parameterHandler");
        Object objParam = parameterHandler.getParameterObject();
        List list = (List) invocation.proceed();
        if (objParam instanceof Page) {
            Page page = (Page) objParam;
            page.setResultList(list);
        }else if (objParam instanceof Map) {
            for (Map.Entry<String, Object> e : ((Map<String, Object>) objParam).entrySet()) {
                if (e.getValue() instanceof Page) {
                    Page page = (Page) e.getValue();
                    page.setResultList(list);
                }
            }
        }
        return list;
    }

    /**
     * 只拦截这两种类型的
     * <br>StatementHandler
     * <br>ResultSetHandler
     *
     * @param target
     * @return
     */
    @Override
    public Object plugin(Object target) {
        if (target instanceof ResultSetHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
