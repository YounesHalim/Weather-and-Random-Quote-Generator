package com.weatherreport.weatherreport.controllers;

import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.text.MessageFormat;
import java.util.ResourceBundle;

public class SearchBarController implements Initializable {
    @FXML
    private TextField searchBar;
    private String[] Test = {"Hello", "Halim", "Hero","Younes","Sparta", "Words"};
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchBar
                .textProperty()
                .addListener((observable, oldValue, newValue)
                        -> System.out.println(MessageFormat.format("Old value: {0}\nNew value: {1}", oldValue, newValue)));
        TextFields.bindAutoCompletion(searchBar, Test);
    }
}
