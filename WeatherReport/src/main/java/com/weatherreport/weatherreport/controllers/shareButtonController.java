package com.weatherreport.weatherreport.controllers;
import jakarta.mail.BodyPart;
import jakarta.mail.Message;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMultipart;
import lombok.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import static com.weatherreport.weatherreport.service.EmailSender.*;
import static jakarta.mail.internet.InternetAddress.parse;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class shareButtonController {
    private BodyPart imageBodyPart, messageBodyPart;
    private Message senderSetter() {
        return getEmailSenderInstance().defaultSender(null);
    }
    @SneakyThrows
    private void headerFieldSetter(TextField email) {
        if(!emailChecker(email)) return;
        senderSetter()
                .setRecipients(Message.RecipientType.TO,parse(email.getText().toLowerCase().trim()));
    }
    protected byte[] fromImageToByte(@NonNull BufferedImage image) {
        try(ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(image,"jpg",byteArrayOutputStream);
            byteArrayOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    @SneakyThrows
    protected BodyPart setImagePart(@NonNull BufferedImage image) {
        imageBodyPart = new MimeBodyPart();
        imageBodyPart.setHeader("Content-ID","<image>");
        imageBodyPart.setContent(fromImageToByte(image),"image/jpeg" );
        return imageBodyPart;
    }
    @SneakyThrows
    protected void shareByEmail(@NonNull BufferedImage image) {
        MimeMultipart multipart = new MimeMultipart();
        multipart.addBodyPart(setImagePart(image));
    }
    private Boolean emailChecker(TextField email) {
        String mail = (email.getText().isEmpty() || email.getText().isBlank() || !email.getText().contains("@"))
                ? null : email.getText().toLowerCase().trim();
        return mail != null;
    }

}
