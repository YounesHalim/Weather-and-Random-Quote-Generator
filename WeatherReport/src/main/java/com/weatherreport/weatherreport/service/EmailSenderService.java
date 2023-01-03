package com.weatherreport.weatherreport.service;
import com.weatherreport.weatherreport.model.email.EmailProps;
import com.weatherreport.weatherreport.model.email.EmailProperties;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.SneakyThrows;

import java.awt.*;
import java.util.Objects;
import java.util.Properties;

import static jakarta.mail.internet.InternetAddress.parse;


public class EmailSenderService implements EmailProperties {
    private static EmailSenderService emailSender;
    private BodyPart imageBodyPart, messageBodyPart;
    private EmailSenderService() {}
    public static EmailSenderService getEmailSenderInstance() {
        if(emailSender == null) {
            emailSender = new EmailSenderService();
        }
        return emailSender;
    }
    @Override
    public Properties gmailProperties() {
        return EmailProperties.super.gmailProperties();
    }

    @Override
    public Properties setProperties() {
        return null;
    }

    public static void main(String[] args) throws MessagingException {
//        EmailSender emailSender = new EmailSender();
//        Session session = setSession(emailSender);
//
//        Message message = new MimeMessage(session);
//        message.setFrom(new InternetAddress(new EmailProps().getEmail()));
//        message.setRecipients(
//                Message.RecipientType.TO,
//                InternetAddress.parse("younes.halim@gmail.com"));
//        message.setSubject("This is a test");
//        message.setText("This is a test of an email sent via IntelliJ");
//        Transport.send(message);
//        System.out.println("Sent");
    }

    private Session setSession(Properties props) {
        return Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(new EmailProps().getEmail(),
                        new EmailProps().getPassword());
            }
        });
    }
    @SneakyThrows
    public Message defaultSender(EmailProps email) {
        Message message = new MimeMessage(setSession(gmailProperties()));
        message.setFrom(new InternetAddress(Objects.requireNonNullElseGet(email, EmailProps::new).getEmail()));
        return message;
    }

    private Message senderSetter() {
        return getEmailSenderInstance().defaultSender(null);
    }
    @SneakyThrows
    private Message headerFieldSetter(String[] addresses) {
        Message message = senderSetter();
        message.setRecipients(Message.RecipientType.TO,parse(String.join(",", addresses)));
        message.setSubject("Quote of the day");
        return message;
    }

    @SneakyThrows
    protected BodyPart setImagePart(String path) {
        imageBodyPart = new MimeBodyPart();
        DataSource dataSource = new FileDataSource(path);
        imageBodyPart.setDataHandler(new DataHandler(dataSource));
        imageBodyPart.setHeader("Content-ID","<image>");
        return imageBodyPart;
    }
    @SneakyThrows
    public void shareByEmail(String[] addresses, String imagePath) {
        Message message = headerFieldSetter(addresses);
        MimeMultipart multipart = new MimeMultipart();
        multipart.addBodyPart(setImagePart(imagePath));
        message.setContent(multipart);
        Transport.send(message);
        System.out.println("Email sent!");
    }
    private Boolean emailChecker(TextField email) {
        String mail = (email.getText().isEmpty() || email.getText().isBlank() || !email.getText().contains("@"))
                ? null : email.getText().toLowerCase().trim();
        return mail != null;
    }



}
