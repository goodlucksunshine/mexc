package com.mexc.task.servlet;

import com.mexc.task.AddressLibContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2018/1/20
 * Time: 下午5:03
 */
public class InitServlet implements ServletContextListener {
    private static Logger logger= LoggerFactory.getLogger(InitServlet.class);


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("servlet 参数初始化");
//        AddressLibContext.init();
        logger.info("servlet 参数初始化 完成");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        logger.info("servlet 参数销毁");
    }
}
