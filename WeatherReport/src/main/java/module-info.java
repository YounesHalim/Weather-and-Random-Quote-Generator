module com.weatherreport.weatherreport {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires lombok;
    requires io.github.cdimascio.dotenv.java;
    requires com.google.gson;
    requires java.desktop;
    requires javafx.swing;
    requires jakarta.mail;
    requires jakarta.activation;
    requires javafx.web;

    opens com.weatherreport.weatherreport to javafx.fxml;
    opens com.weatherreport.weatherreport.controllers to javafx.fxml;
    opens com.weatherreport.weatherreport.model.meteorology to com.google.gson;
    opens com.weatherreport.weatherreport.model.unsplash to com.google.gson;
    opens com.weatherreport.weatherreport.model.location to com.google.gson;
    opens com.weatherreport.weatherreport.model.Quotes to com.google.gson;

    exports com.weatherreport.weatherreport;
    exports com.weatherreport.weatherreport.controllers;
    exports com.weatherreport.weatherreport.model.location;
    exports com.weatherreport.weatherreport.model.Quotes;
    exports com.weatherreport.weatherreport.model.unsplash;
}