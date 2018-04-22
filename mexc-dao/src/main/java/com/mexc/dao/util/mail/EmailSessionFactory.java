package com.mexc.dao.util.mail;

import com.alibaba.fastjson.JSON;
import com.laile.esf.common.util.SpringUtils;
import com.mexc.common.constant.CommonConstant;
import com.mexc.common.util.SpringContextUtils;
import com.mexc.common.util.jedis.RedisUtil;
import com.mexc.dao.delegate.common.MexcEmailPoolDelegate;
import com.mexc.dao.delegate.vcoin.MexcAddressLibDelegate;
import com.mexc.dao.model.common.MexcEmailPool;
import com.mexc.dao.util.mail.entity.EmailEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.*;

/**
 * Created by huangxinguang on 2018/3/5 下午5:30.
 * 邮箱session工厂类
 */
public class EmailSessionFactory {

    public static EmailEntity getEmailEntity() {
        MexcEmailPoolDelegate mexcEmailPoolDelegate = (MexcEmailPoolDelegate)SpringContextUtils.getBean("mexcEmailPoolDelegate");
        List<MexcEmailPool> emailList = mexcEmailPoolDelegate.selectAll();
        EmailEntity emailEntity = new EmailEntity();
        if(!CollectionUtils.isEmpty(emailList)) {
            Random random = new Random();
            Integer index = random.nextInt(emailList.size());
            MexcEmailPool emailPool = emailList.get(index);
            BeanUtils.copyProperties(emailPool, emailEntity);
        }
        return emailEntity;
    }
    /**
     * 从缓存中获取邮箱获取邮箱session
     * @return
     */
    public static Session getEmailSession(EmailEntity emailEntity) {
        Session session = null;
        if(1 == emailEntity.getType()) {
            session = createGmailSession(emailEntity);
        }else if(2 == emailEntity.getType()) {
            session = createQQSession(emailEntity);
        }
        return session;
    }

    /**
     * 创建Session对象，此时需要配置传输的协议，是否身份认证
     */
    private static Session createQQSession(EmailEntity emailEntity) {
        // 创建Properties 类用于记录邮箱的一些属性
        Properties props = new Properties();
        // 表示SMTP发送邮件，必须进行身份验证
        props.put("mail.smtp.auth", "true");
        //此处填写SMTP服务器
        props.put("mail.smtp.host", "smtp.qq.com");
        //端口号，QQ邮箱给出了两个端口，但是另一个我一直使用不了，所以就给出这一个587
        props.put("mail.smtp.port", "587");
        // 此处填写你的账号
        props.put("mail.user", emailEntity.getEmailUser());
        // 此处的密码就是前面说的16位STMP口令
        props.put("mail.password", emailEntity.getEmailPassword());
        // 构建授权信息，用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
                //String userName = props.getProperty("mail.user");
                //String password = props.getProperty("mail.password");
                return new PasswordAuthentication(emailEntity.getEmailUser(), emailEntity.getEmailPassword());
            }
        };
        // 使用环境属性和授权信息，创建邮件会话
        Session mailSession = Session.getInstance(props, authenticator);
        return mailSession;
    }

    /**
     * 创建gmail
     * @return
     */
    private static Session createGmailSession(EmailEntity emailEntity) {
        // 创建Properties 类用于记录邮箱的一些属性
        Properties props = new Properties();
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        props.put("mail.debug", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        //props.put("mail.smtp.ssl.enable", "true");
        //  props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.put("mail.smtp.port", "587");
        // props.put("mail.smtp.socketFactory.port", "465");
        // props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // 此处填写你的账号
        props.put("mail.user", emailEntity.getEmailUser());
        // 此处的密码就是前面说的16位STMP口令
        props.put("mail.password", emailEntity.getEmailPassword());
        // 构建授权信息，用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
               // String userName = props.getProperty("mail.user");
                //String password = props.getProperty("mail.password");
                //String userName = "hxguang666@gmail.com";
                //String password = "HelloWorld1234";
                return new PasswordAuthentication(emailEntity.getEmailUser(), emailEntity.getEmailPassword());
            }
        };
        // 使用环境属性和授权信息，创建邮件会话
        Session mailSession = Session.getDefaultInstance(props, authenticator);
        return mailSession;
    }
}
