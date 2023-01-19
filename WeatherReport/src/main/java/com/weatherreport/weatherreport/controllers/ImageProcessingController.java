package com.weatherreport.weatherreport.controllers;


import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class ImageProcessingController {
    public enum Filters {
        GRAYSCALE, SEPIA, INVERTED, ORIGINAL
    }

    protected void applyFilter(Filters filter, PixelWriter pixelWriter, PixelReader pixelReader, int i, int j) {
        switch (filter) {
            case GRAYSCALE -> grayscale(pixelWriter, pixelReader, i, j);
            case INVERTED -> inverted(pixelWriter, pixelReader,i,j);
        }
    }
    private void grayscale(PixelWriter pixelWriter, PixelReader pixelReader, int i, int j) {
        Color color = pixelReader.getColor(j, i);
        double avg = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
        color = new Color(avg, avg, avg, color.getOpacity());
        pixelWriter.setColor(j, i, color);
    }

    private void inverted(PixelWriter pixelWriter, PixelReader pixelReader, int i, int j) {
        Color color = pixelReader.getColor(j, i);
        color = new Color(1 - color.getRed(), 1 - color.getGreen(), 1 - color.getBlue(), color.getOpacity());
        pixelWriter.setColor(j, i, color);
    }
}
