package com.weatherreport.weatherreport.controllers;

import com.weatherreport.weatherreport.WeatherReportApplication;
import com.weatherreport.weatherreport.model.Quotes.Quote;
import com.weatherreport.weatherreport.service.UnsplashService;
import com.weatherreport.weatherreport.service.ZenQuotesService;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.weatherreport.weatherreport.controllers.MainController.interfaceLoader;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuoteController implements Initializable {
    @FXML
    private AnchorPane rootQuoteAnchorPane;
    @FXML
    private Text quoteTextField, imagePath;
    @FXML
    private Label authorName;
    @FXML
    private Button generateQuote, saveButton, shareButton, directShare;
    @FXML
    private ColorPicker textColorPicker;
    @FXML
    private ImageView imageContainer;
    @FXML
    private AnchorPane imagePane;
    @FXML
    private HBox optionBar;

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        applyCursor();
        generateQuote.setOnAction(actionEvent -> execution());
        saveButton.setOnAction(actionEvent -> {
            try {
                savePicture();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        shareButton.setOnAction(actionEvent -> Platform.runLater(this::shareLayoutOpener));
    }

    private void execution() {
        new Thread(this::getRandomQuote).start();
        optionBar.getChildren().forEach((button) -> button.setVisible(true));
    }

    private void getRandomQuote() {
        Quote[] quotes = ZenQuotesService.getQuotes();
        int randPOS = new Random().nextInt(0, quotes.length);
        Platform.runLater(() -> setRandomQuote(quotes[randPOS]));
        setRandomBG();
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

    private void setRandomBG() {
        List<String> urls = UnsplashService.getListOfURLs();
        int size = urls.size();
        int rand = new Random().nextInt(0, size);
        Platform.runLater(() -> imageContainer.setImage(new Image(urls.get(rand), 574, 349, false, false)));
    }

    @SneakyThrows
    private void saveImage(String path) {
        WritableImage writableImage = imagePane.snapshot(null, null);
        File file = new File(path);
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(writableImage, null);
        ImageIO.write(bufferedImage, "png", file);
    }

    private void savePicture() throws IOException {
        FileChooser fileChooser = getFileChooser();
        File file = fileChooser.showSaveDialog(getWindow());
        if (file != null) {
            Platform.runLater(() -> saveImage(file.getAbsolutePath()));
        }
    }

    protected FileChooser getFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.setTitle("Save image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpeg", "*.gif", "*.jpeg"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        return fileChooser;
    }

    @SneakyThrows
    protected Window getWindow() {
        FXMLLoader loader = new FXMLLoader(WeatherReportApplication.class.getResource(interfaceLoader.WEATHER_INTERFACE.getInterface()));
        Callable<Window> windowCallable = () -> new Scene(loader.load()).getWindow();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Window> windowFuture = executorService.submit(windowCallable);
        executorService.shutdown();
        return windowFuture.get();
    }

    private void applyCursor() {
        ObservableList<Node> list = optionBar.getChildren();
        list.forEach((button) -> button.setCursor(Cursor.HAND));
    }
    @SneakyThrows
    private void shareLayoutOpener() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(WeatherReportApplication.class.getResource(interfaceLoader.SHARE_INTERFACE.getInterface()));
        Parent parent = loader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(parent));
        stage.show();
    }

}