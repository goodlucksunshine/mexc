package redis.test;

import java.security.GeneralSecurityException;
import java.security.Security;
import java.util.Date;
import java.util.Properties;


import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class JavaMailDemo {
    public static void main(String[] args) {
// qqSender();
        gmailSender();
    }

    //gmail邮箱SSL方式
    private static void gmailssl(Properties props) {
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        props.put("mail.debug", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
// props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.auth", "true");
    }

    /*
     * 通过gmail邮箱发送邮件
     */
    public static void gmailSender() {

// Get a Properties object
        Properties props = new Properties();
//选择ssl方式
        gmailssl(props);

        final String username = "hxguang666@gmail.com";
        final String password = "HelloWorld1234";
        Session session = Session.getDefaultInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });


// -- Create a new message --
        Message msg = new MimeMessage(session);


// -- Set the FROM and TO fields --
        try {
            msg.setFrom(new InternetAddress(username));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("396928069@qq.com"));
            msg.setSubject("test gmail");
            msg.setText("How are you");
            msg.setSentDate(new Date());
            Transport.send(msg);
        } catch (AddressException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MessagingException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }


        System.out.println("Message sent.");
    }

}
