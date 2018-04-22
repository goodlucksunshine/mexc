package com.mexc.dao.util.mail;


import com.laile.esf.common.util.DateUtil;
import com.laile.esf.common.util.MD5Util;
import com.mexc.common.util.LogUtil;
import com.mexc.dao.util.mail.entity.EmailEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * 定义发送邮件验证
 *
 * @author shirong.fan Date:2016/7/1 Time:9:40
 */
public class MailSender {
    private static final Logger logger = LoggerFactory.getLogger(MailSender.class);
    public static String MAIL_AUTH_CODE = "<p>&nbsp;&nbsp;验证码：${code}</p>";
    public static String MAIL_MODEL = "<p>请点击&nbsp;&nbsp;<u><a href='${url}'>完成注册</a></u>&nbsp;&nbsp;,如非本人操作请忽略此次邮件</p></br>如网页未成功跳转,请复制链接地址在浏览器中打开:${url}";
    public static String CASH_MODEL = "<p>点击&nbsp;&nbsp;<u><a href='${url}'>确认提现操作</a></u>&nbsp;&nbsp;,如非本人操作请尽快修改密码，加强认证</p></br>如网页未成功跳转,请复制链接地址在浏览器中打开:${url}";


    /**
     * 传入Session、MimeMessage对象，创建 Transport 对象发送邮件
     */
    public static void sendMail(Session session, MimeMessage msg) throws Exception {
        // 由 Session 对象获得 Transport 对象
        Transport.send(msg, msg.getRecipients(Message.RecipientType.TO));
    }

    /**
     * 发送邮件
     *
     * @param mailContent 邮件内容实体
     *
     * @throws Exception
     */
    public static void sendSingleMail(MailContent mailContent) {
        MailSender sender = new MailSender();

        EmailEntity emailEntity = EmailSessionFactory.getEmailEntity();
        mailContent.setUserForm(emailEntity.getEmailUser());
        // 指定使用SMTP协议来创建Session对象
        Session session = EmailSessionFactory.getEmailSession(emailEntity);
        try {
            // 使用邮件创建类获得Message对象
            MimeMessage mail = new WithAttachmentMessage().createMessage(session, mailContent);
            // 发送邮件
            sender.sendMail(session, mail);

        } catch (Exception e) {
            logger.info("error", e);
        }
    }

    public static void sendWarningMail(String message) {
        MailContent mailContent = new MailContent();
        mailContent.setUserTo(new String[]{});
        mailContent.setTitle(message);
        mailContent.setBody(message);
        MailSender sender = new MailSender();
        // 指定使用SMTP协议来创建Session对象
        EmailEntity emailEntity = EmailSessionFactory.getEmailEntity();
        mailContent.setUserForm(emailEntity.getEmailUser());
        Session session = EmailSessionFactory.getEmailSession(emailEntity);
        try {
            // 使用邮件创建类获得Message对象
            MimeMessage mail = new WithAttachmentMessage().createMessage(session, mailContent);
            // 发送邮件
            sender.sendMail(session, mail);

        } catch (Exception e) {
            logger.info("error", e);
        }
    }
    

    /**
     * 队列发送邮件
     *
     * @param mailContent 邮件内容实体
     *
     * @throws Exception
     */
    public static boolean senderQueueMail(MailContent mailContent) throws Exception {
        MailSender sender = new MailSender();
        // 判断邮件是否发送成功
        boolean flag = true;
        // 指定使用SMTP协议来创建Session对象
        EmailEntity emailEntity = EmailSessionFactory.getEmailEntity();
        mailContent.setUserForm(emailEntity.getEmailUser());
        Session session = EmailSessionFactory.getEmailSession(emailEntity);
        try {
            // 使用邮件创建类获得Message对象
            MimeMessage mail = new WithAttachmentMessage().createMessage(session, mailContent);
            // 发送邮件
            sender.sendMail(session, mail);
        } catch (Exception e) {
            flag = false;
            logger.info("send email error", e);
        }
        return flag;
    }
}
