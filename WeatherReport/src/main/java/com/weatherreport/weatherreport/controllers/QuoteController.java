package com.weatherreport.weatherreport.controllers;

import com.weatherreport.weatherreport.model.Quotes.Quote;
import com.weatherreport.weatherreport.service.ApiUnsplashService;
import com.weatherreport.weatherreport.service.ApiZenQuotesService;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.imageio.ImageIO;
import java.awt.font.LineBreakMeasurer;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
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
    private Button generateQuote, saveButton;
    @FXML
    private ColorPicker textColorPicker, bgColorPicker;
    @FXML
    private ImageView imageContainer;

    private static int POS = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setContainerColor();
        generateQuote.setCursor(Cursor.HAND);
        generateQuote.setOnAction(actionEvent -> execution());
        saveButton.setOnAction(actionEvent -> new Thread(
                () -> {
                    try {
                        extractImage();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .start());
    }

    private void execution() {
        new Thread(this::getRandomQuote).start();

        //new Thread(this::setContainerColor).start();
    }

    private void getRandomQuote() {
        Quote[] quotes = ApiZenQuotesService.getQuotes();
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

    private void setContainerColor() {
        bgColorPicker.setOnAction(actionEvent
                -> Platform.runLater(()
                -> imageContainer.setImage(new ImagePattern(new Image(String.valueOf(bgColorPicker.getValue()))).getImage())));
    }

    private void setRandomBG() {
        List<String> urls = ApiUnsplashService.getListOfURLs();
        int size = urls.size();
        int rand = new Random().nextInt(0, size);
        POS = rand;
        Platform.runLater(() -> imageContainer.setImage(new Image(urls.get(rand), 458, 261, false, false)));
    }

    private void extractImage() throws IOException {
        Canvas canvas = new Canvas(1080 , 720);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFont(new Font(quoteTextField.getFont().toString(),25));
        Image fetchedImage = new Image(ApiUnsplashService.getListOfURLs().get(POS), 1080, 720, false, false);
        graphicsContext.drawImage(fetchedImage, 0, 0);
        authorQuote(canvas, graphicsContext);
        authorWatermark(canvas, graphicsContext);
        WritableImage writableImage = new WritableImage(1080, 720);

        Platform.runLater(() -> {
            canvas.snapshot(null, writableImage);
            File file = new File("icos/output.jpeg");
            try {
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(bufferedImage, "jpeg", file.getAbsoluteFile());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void authorQuote(Canvas canvas, GraphicsContext graphicsContext) {
        graphicsContext.setLineWidth(300);
        graphicsContext.setTextAlign(TextAlignment.CENTER);
        graphicsContext.setTextBaseline(VPos.CENTER);
        graphicsContext.setFill(quoteTextField.getFill());
        graphicsContext.fillText(quoteTextField.getText(), canvas.getWidth() /2 , canvas.getHeight() / 2, 1080);
    }

    private void authorWatermark(Canvas canvas, GraphicsContext graphicsContext) {
        graphicsContext.setTextAlign(TextAlignment.RIGHT);
        graphicsContext.setTextBaseline(VPos.BOTTOM);
        graphicsContext.fillText(authorName.getText(), canvas.getWidth(),700);
    }


    private void shareQuote(Byte[] imageData) {

    }

}


//        WritableImage writableImage = new WritableImage((int) newImage.getWidth(), (int) newImage.getHeight());
//        PixelWriter pixelWriter = writableImage.getPixelWriter();


// Write the pixels of the Image to the WritableImage
//        pixelWriter.setPixels(0, 0, (int) newImage.getWidth(), (int) newImage.getHeight(), PixelFormat.getByteBgraInstance(),
//                newImage.getPixelReader().getPixels(0, 0,
//                        (int) newImage.getWidth(),
//                        (int) newImage.getHeight(),
//                        PixelFormat.getByteBgraInstance(),
//                        null), 0, (int) newImage.getWidth() * 4);


//        // Create a FileOutputStream for the output file
//        FileOutputStream fos = new FileOutputStream("image.png");
//
//        // Get a FileChannel for the FileOutputStream
//        FileChannel channel = fos.getChannel();
//
//        // Create a ByteBuffer with the size of the WritableImage
//        ByteBuffer buffer = ByteBuffer.allocate((int) (writableImage.getWidth() * writableImage.getHeight() * 4));
//
//        // Write the pixels of the WritableImage to the ByteBuffer
//        pixelWriter.setPixels(0, 0, (int) writableImage.getWidth(), (int) writableImage.getHeight(), PixelFormat.getByteBgraInstance(), buffer, (int) writableImage.getWidth() * 4);
//
//        // Write the ByteBuffer to the FileChannel
//        channel.write(buffer);
//
//        // Close the FileOutputStream and FileChannel
//        fos.close();
//        channel.close();