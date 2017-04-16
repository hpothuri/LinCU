package com.linCu.view.utils;

import com.linCu.constants.LinCUConstants;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {
    public EmailUtil() {
        super();
    }
    
    public static void sendEmail(String sender, String recipient, String subject, String content){
        String username = LinCUConstants.EMAIL_USER;
        String password = LinCUConstants.EMAIL_PASSWORD;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
               protected PasswordAuthentication getPasswordAuthentication() {
                   return new PasswordAuthentication(username, password);
               }
           });
        
        try {
        
           Message message = new MimeMessage(session);
           message.setFrom(new InternetAddress(sender));
           message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
           message.setSubject(subject);
           message.setText(content);
        
           Transport.send(message);
        
           System.out.println("Done");
        
        } catch (MessagingException e) {
           throw new RuntimeException(e);
        }
    }
}
