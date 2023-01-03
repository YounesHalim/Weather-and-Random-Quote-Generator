package com.weatherreport.weatherreport.controllers;

import com.weatherreport.weatherreport.model.Quotes.Quote;
import com.weatherreport.weatherreport.service.ApiUnsplashService;
import com.weatherreport.weatherreport.service.ApiZenQuotesService;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.transform.Transform;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.imageio.ImageIO;
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
    private Button generateQuote, saveButton, shareButton;
    @FXML
    private ColorPicker textColorPicker;
    @FXML
    private ImageView imageContainer;
    @FXML
    private AnchorPane imagePane;

    private static int POS = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateQuote.setCursor(Cursor.HAND);
        generateQuote.setOnAction(actionEvent -> execution());
        saveButton.setOnAction(actionEvent -> new Thread(
                () -> {
                    try {
                        saveImage();
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

    private void setRandomBG() {
        List<String> urls = ApiUnsplashService.getListOfURLs();
        int size = urls.size();
        int rand = new Random().nextInt(0, size);
        POS = rand;
        Platform.runLater(() -> imageContainer.setImage(new Image(urls.get(rand),574,349,false,false)));
    }

    private void saveImage() throws IOException {

        Platform.runLater(() -> {
            WritableImage writableImage = imagePane.snapshot(null,null);
            File file = new File("src/main/resources/com/weatherreport/weatherreport/output/quote.jpeg");
            try {
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(bufferedImage, "png", file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }finally {
                //getEmailSenderInstance().shareByEmail();
            }
        });

    }
    public  WritableImage pixelScaleAwareCanvasSnapshot(Canvas canvas, double pixelScale) {
        WritableImage writableImage = new WritableImage((int)Math.rint(pixelScale*canvas.getWidth()), (int)Math.rint(pixelScale*canvas.getHeight()));
        SnapshotParameters spa = new SnapshotParameters();
        spa.setTransform(Transform.scale(pixelScale, pixelScale));
        return canvas.snapshot(spa, writableImage);
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



// Another method to write an image
//    ImageWriter writer = ImageIO.getImageWritersByFormatName("png").next();
//    IIOImage iioImage = new IIOImage(bufferedImage,null,null);
//    ImageWriteParam param = writer.getDefaultWriteParam();
//                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//                        param.setCompressionQuality(1.0f);
//                        ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(file);
//                        writer.setOutput(imageOutputStream);
//                        writer.write(null,iioImage,param);
//                        writer.dispose();
//                        imageOutputStream.close();