package com.linCu.view.backingBeans;

import java.util.Properties;    
import javax.mail.*;    
import javax.mail.internet.*;    
public class MailTest {
    public MailTest() {
        super();
    }

    public static void main(String[] args) {
//                    final String username = "dbmstestmail@gmail.com";
//                    final String password = "dbmstestmail123";
                    
                    final String username = "support@lincultd.com";
                    final String password = "Lincu@321";
                    
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
                        message.setFrom(new InternetAddress("dbmstestmail@gmail.com"));
                        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("dileeprongaliprojects@gmail.com"));
                        message.setSubject("Testing Subject");
                        message.setText("Dear User," + "\n\n You are added to LinCU system as Admin, You can access system using below credentials.\n\n Login Name: "+username+"\n\n Password: "+password);
            
                        Transport.send(message);
            
                        System.out.println("Done");
            
                    } catch (MessagingException e) {
                        throw new RuntimeException(e);
                    }

    }
    
    public static void send(String from,String password,String to,String sub,String msg){  
              //Get properties object    
              Properties props = new Properties();    
//              props.put("mail.smtp.host", "webmail.sirma.com");    
//              props.put("mail.smtp.socketFactory.port", "465");    
//              props.put("mail.smtp.socketFactory.class",    
//                        "javax.net.ssl.SSLSocketFactory");    
//              props.put("mail.smtp.auth", "true");    
//              props.put("mail.smtp.port", "465");   
              props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
              //get Session   
              Session session = Session.getDefaultInstance(props,    
               new javax.mail.Authenticator() {    
               protected PasswordAuthentication getPasswordAuthentication() {    
               return new PasswordAuthentication(from,password);  
               }    
              });    
              //compose message    
              try {    
               MimeMessage message = new MimeMessage(session);    
               message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));    
               message.setSubject(sub);    
               message.setText(msg);    
               //send message  
               Transport.send(message);    
               System.out.println("message sent successfully");    
              } catch (MessagingException e) {throw new RuntimeException(e);}    
                 
        }  
      
}
