package com.weatherreport.weatherreport.model.email;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class EmailProps {
    private String email, password;
    public EmailProps() {
        this.email = Dotenv.load().get("EMAIL");
        this.password = Dotenv.load().get("PASSWORD");
    }
}
