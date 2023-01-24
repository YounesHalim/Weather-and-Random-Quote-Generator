package com.weatherreport.weatherreport.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.weatherreport.weatherreport.service.EmailSenderService.getEmailSenderInstance;
import static com.weatherreport.weatherreport.service.ZenQuotesService.getQuotes;

public class ShareController implements Initializable {
    @FXML
    private AnchorPane shareContainerLayout;
    @FXML
    private Button openButton, sendButton;
    @FXML
    private Text imagePath;
    @FXML
    private TextField emailTextField;
    @FXML
    private ProgressIndicator spinnerIndicator;
    @FXML  private HTMLEditor editor;
    private StringBuilder pathBuilder = new StringBuilder();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        applyCursor();
        HTMLTextSetter();
        openButton.setOnAction(actionEvent -> setImagePath());
        sendButton.setOnAction(actionEvent -> sendEmailExecutioner());
    }

    private void sendEmailExecutioner() {
        try {
            spinnerIndicator.setVisible(true);
            setSendButton();
            spinnerIndicator.setVisible(false);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private void HTMLTextSetter() {
        editor.setHtmlText(QuoteController.setFormattedHTMLQuote(getQuotes()[QuoteController.POS]));
    }

    private void applyCursor() {
        openButton.setCursor(Cursor.HAND);
        sendButton.setCursor(Cursor.HAND);
    }

    private void setImagePath() {
        FileChooser fileChooser = new QuoteController().getFileChooser();
        File file = fileChooser.showOpenDialog(new QuoteController().getWindow());
        if (file != null) {
            imagePath.setText(file.getName().toUpperCase());
            pathBuilder.append(file.getAbsolutePath());
            System.out.println(imagePath.getText());
        }
    }

    private void setSendButton() throws InterruptedException {
        Optional<String> path = emailTextField.getText().describeConstable();
        if (path.isPresent()) {
            String emails = path.get();
            String[] emailArray = emails.split("[,;]");
            for (String email : emailArray) {
                email = email.toLowerCase().trim();
                if (!email.matches("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid email address");
                    alert.setContentText("Please enter a valid email address");
                    alert.showAndWait();
                } else {
                    Thread thread = new Thread(() -> getEmailSenderInstance().shareByEmail(emailArray, pathBuilder.toString(), editor.getHtmlText()));
                    thread.start();
                    successMessage();
                    thread.join();
                }
            }
        }
    }

    private void successMessage() {
        Alert successDialog = new Alert(Alert.AlertType.INFORMATION);
        successDialog.setTitle("Success");
        successDialog.setHeaderText("Operation Successful");
        successDialog.setContentText("The operation was completed successfully.");
        Optional<ButtonType> resultType = successDialog.showAndWait();
        if (resultType.get() == (ButtonType.OK)) {
            Stage stage = (Stage) shareContainerLayout.getScene().getWindow();
            stage.close();
        }
    }
}
