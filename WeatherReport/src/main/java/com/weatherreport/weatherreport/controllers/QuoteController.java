package com.weatherreport.weatherreport.controllers;

import com.weatherreport.weatherreport.model.Quotes.Quote;
import com.weatherreport.weatherreport.service.ApiZenQuotesService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuoteController implements Initializable {
    @FXML
    private AnchorPane rootQuoteAnchorPane;
    @FXML
    private Text quoteTextField;
    @FXML
    private Label authorName;
    @FXML
    private Button generateQuote;
    @FXML
    private ColorPicker textColorPicker, bgColorPicker;
    @FXML
    private Rectangle imageContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setContainerColor();
        generateQuote.setCursor(Cursor.HAND);
        generateQuote.setOnAction(actionEvent -> execution());
    }

    private void execution() {
        new Thread(this::getRandomQuote).start();
        new Thread(this::setContainerColor).start();
    }

    private void getRandomQuote() {
        Quote[] quotes = ApiZenQuotesService.getQuotes();
        int randPOS = new Random().nextInt(0, quotes.length);
        Platform.runLater(() -> setRandomQuote(quotes[randPOS]));
        textColorPicker.setOnAction(actionEvent -> Platform.runLater(this::setQuoteColor));
    }

    private void setRandomQuote(Quote quotes) {
        quoteTextField.setText(quotes.getQ());
        authorName.setText(quotes.getA());
    }

    private void setQuoteColor() {
        quoteTextField.setFill(textColorPicker.getValue());
        authorName.setTextFill(textColorPicker.getValue());
    }

    private void setContainerColor() {
        bgColorPicker.setOnAction(actionEvent
                -> Platform.runLater(()
                -> imageContainer.setFill(bgColorPicker.getValue())));
    }


}
