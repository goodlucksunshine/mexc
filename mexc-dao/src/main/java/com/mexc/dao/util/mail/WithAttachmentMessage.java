package com.mexc.dao.util.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;

public class WithAttachmentMessage {

    /**
     * 根据传入的 Seesion对象创建MIME消息
     */
    public MimeMessage createMessage(Session session, MailContent mailContent) throws Exception {
        MimeMessage mailMessage = new MimeMessage(session);
        Address from = new InternetAddress(mailContent.getUserForm());
        mailMessage.setFrom(from);
        // 创建邮件的接收者地址，并设置到邮件消息中             
        Address[] tos = null;
        String[] receivers = mailContent.getUserTo();
        if (receivers != null) {
            // 为每个邮件接收者创建一个地址 
            tos = new InternetAddress[receivers.length];
            for (int i = 0; i < receivers.length; i++) {
                tos[i] = new InternetAddress(receivers[i]);
            }
        } else {
            tos = new InternetAddress[1];
            tos[0] = new InternetAddress(mailContent.getUserForm());
        }

        //设置自定义发件人昵称
        String nick = javax.mail.internet.MimeUtility.encodeText("MEXC");

        // 将所有接收者地址都添加到邮件接收者属性中
        mailMessage.setFrom(new InternetAddress(nick + "<" + from + ">"));
        mailMessage.setRecipients(Message.RecipientType.TO, tos);
        mailMessage.setSubject(mailContent.getTitle());
        mailMessage.setSentDate(new Date());
        Multipart mainPart = new MimeMultipart();

        BodyPart html = new MimeBodyPart();
        html.setContent(mailContent.getBody() +
                "<br/><br/><hr style='float:left; width:21%; border:1px dashed black;' /><br/>" +
                "<p style='float:left;margin-top: -3px;'>客服电话：4006-898-895<br/>" +
                "MEXC</p>", "text/html;charset=gbk");

        mainPart.addBodyPart(html);
        mailMessage.setContent(mainPart);
        return mailMessage;
    }
}