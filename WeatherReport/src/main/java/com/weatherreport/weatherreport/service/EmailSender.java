package com.weatherreport.weatherreport.service;
import com.weatherreport.weatherreport.model.email.EmailProps;
import com.weatherreport.weatherreport.model.email.EmailProperties;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailSender implements EmailProperties {
    @Override
    public Properties gmailProperties() {
        return EmailProperties.super.gmailProperties();
    }

    @Override
    public Properties setProperties() {
        return null;
    }

//    public static void main(String[] args) throws MessagingException {
//        EmailSender emailSender = new EmailSender();
//        Session session = setSession(emailSender);
//
//        Message message = new MimeMessage(session);
//        message.setFrom(new InternetAddress(new EmailProps().getEmail()));
//        message.setRecipients(
//                Message.RecipientType.TO,
//                InternetAddress.parse("younes.halim@gmail.com, younes.halim@outlook.com"));
//        message.setSubject("This is a test");
//        message.setText("This is a test of an email sent via IntelliJ");
//        Transport.send(message);
//        System.out.println("Sent");
//    }

    private static Session setSession(EmailSender senderObject) {
        return Session.getInstance(senderObject.gmailProperties(), new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(new EmailProps().getEmail(),
                        new EmailProps().getPassword());
            }
        });
    }



}
