module com.weatherreport.weatherreport {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires lombok;
    requires io.github.cdimascio.dotenv.java;
    requires com.google.gson;

    opens com.weatherreport.weatherreport to javafx.fxml;
    exports com.weatherreport.weatherreport;
    exports com.weatherreport.weatherreport.controllers;
    exports com.weatherreport.weatherreport.model.location;
    opens com.weatherreport.weatherreport.controllers to javafx.fxml;
    opens com.weatherreport.weatherreport.model.meteorology to com.google.gson;
    opens com.weatherreport.weatherreport.model.news to com.google.gson;
    opens com.weatherreport.weatherreport.model.location to com.google.gson;
}