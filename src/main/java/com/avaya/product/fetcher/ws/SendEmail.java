package com.avaya.product.fetcher.ws;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.Address;
import jakarta.mail.Authenticator;
import jakarta.mail.BodyPart;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import utils.Config;

public class SendEmail {
	
	final String username = Config.getPropertyValue("email.senderId");
    final String password = Config.getPropertyValue("email.senderPassword");
     public void sendMail(String fileNameWithPath,String fileName) {
    	 
    	 Properties props = new Properties();
         props.put("mail.smtp.auth", "true");
         props.put("mail.smtp.starttls.enable", "true");
         props.put("mail.smtp.host", "outlook.office365.com");
         props.put("mail.smtp.port", "587");

         Authenticator auth = new Authenticator() {
             protected PasswordAuthentication getPasswordAuthentication() {
                 return new PasswordAuthentication(username, password);
             }
         };

         // Create a new Session instance with the SMTP server properties and authenticator
         Session session = Session.getInstance(props, auth);

         List<String> toList = Arrays.asList(Config.getPropertyValue("email.toList").split(","));
         List<String> ccList = Arrays.asList(Config.getPropertyValue("email.ccList").split(","));
         System.out.println("Sending mail to "+toList);
         
         try {
        	 Message message = new MimeMessage(session);
             message.setFrom(new InternetAddress(username));
             
             // Setting recipients' email addresses
             Address[] toAddresses = new InternetAddress[toList.size()];
             for (int i = 0; i < toList.size(); i++) {
                toAddresses[i] = new InternetAddress(toList.get(i));
             }
             message.setRecipients(Message.RecipientType.TO, toAddresses);
             System.out.println("Sending mail toAddresses "+toAddresses);
             // Setting CC email addresses
             Address[] ccAddresses = new InternetAddress[ccList.size()];
             for (int i = 0; i < ccList.size(); i++) {
                ccAddresses[i] = new InternetAddress(ccList.get(i));
             }
             message.setRecipients(Message.RecipientType.CC, ccAddresses);
             System.out.println("Sending mail ccAddresses "+ccAddresses);
             message.setSubject("Engineer Metric Report");
             
             MimeBodyPart messageBodyPart = new MimeBodyPart();
             messageBodyPart.setText("Hi Team,\n\nPlease find the attached Engineer Metric report\n\nThank you. ");
             
             MimeBodyPart attachmentPart = new MimeBodyPart();
             File attachment = new File(fileNameWithPath);
             attachmentPart.attachFile(attachment);
             attachmentPart.setFileName(fileName);
             
             MimeMultipart multipart = new MimeMultipart();
             multipart.addBodyPart(messageBodyPart);
             multipart.addBodyPart(attachmentPart);
             message.setContent(multipart);
             
             Transport transport = session.getTransport("smtp");
             transport.connect("outlook.office365.com",username,password);

             transport.sendMessage(message, message.getAllRecipients());
             transport.close();
             System.out.println("Sent message successfully....");
          } catch (Exception e) {
             e.getStackTrace();
         }
     }
 }