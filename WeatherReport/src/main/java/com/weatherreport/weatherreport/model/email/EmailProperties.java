package com.weatherreport.weatherreport.model.email;

import java.util.Properties;

public interface EmailProperties {
    default Properties gmailProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        return props;
    }
    Properties setProperties();
}
