package com.weatherreport.weatherreport.service;

import com.weatherreport.weatherreport.model.email.EmailProperties;
import com.weatherreport.weatherreport.model.email.EmailProps;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.SneakyThrows;

import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

import static jakarta.mail.internet.InternetAddress.parse;


public class EmailSenderService implements EmailProperties {
    private static EmailSenderService emailSender;

    private EmailSenderService() {}
    public static synchronized EmailSenderService getEmailSenderInstance() {
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
    private BodyPart setMessagePart(String quote) {
        BodyPart messageBodyPart = new MimeBodyPart();
        Optional<String> html = ZenQuotesService.getQuotesInstance().getHTML(quote);
        if(html.isPresent()) {
            messageBodyPart.setContent(html.get(),"text/html; charset=utf-8");
            return messageBodyPart;
        }
        return null;
    }
    @SneakyThrows
    protected BodyPart setImagePart(String path) {
        BodyPart imageBodyPart = new MimeBodyPart();
        DataSource dataSource = new FileDataSource(path);
        imageBodyPart.setDataHandler(new DataHandler(dataSource));
        imageBodyPart.setHeader("Content-ID","<image>");
        return imageBodyPart;
    }
    @SneakyThrows
    public void shareByEmail(String[] addresses, String imagePath, String quote) {
        Message message = headerFieldSetter(addresses);
        MimeMultipart multipart = new MimeMultipart();
        multipart.addBodyPart(setMessagePart(quote));
        multipart.addBodyPart(setImagePart(imagePath));
        message.setContent(multipart);
        Transport.send(message);
        System.out.println("Email sent!");
    }

}
