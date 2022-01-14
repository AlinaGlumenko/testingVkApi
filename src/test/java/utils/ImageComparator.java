package utils;

import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageComparator {

    private static final int RGB_CHANNELS_COUNT = 3;
    private static final int MAX_CHANNEL_VALUE = 255;
    private static final int ALLOWABLE_ERROR = 3;

    public static boolean compare(File image1, File image2) throws Exception {
        double percentage;

        BufferedImage img1 = null;
        BufferedImage img2 = null;
        try {
            img1 = ImageIO.read(image1);
            img2 = ImageIO.read(image2);
        } catch (IOException e) {
            Logger.getRootLogger().error(e);
            e.printStackTrace();
        }

        int w1 = img1.getWidth();
        int w2 = img2.getWidth();
        int h1 = img1.getHeight();
        int h2 = img2.getHeight();
        if ((w1 != w2) || (h1 != h2)) {
            throw new Exception(String.format("Both images should have same dimensions: first image [%dx%d], second image [%dx%d]", w1, h1, w2, h2));
        } else {
            long diff = 0;
            for (int j = 0; j < h1; j++) {
                for (int i = 0; i < w1; i++) {
                    //Getting the RGB values of a pixel
                    int pixel1 = img1.getRGB(i, j);
                    Color color1 = new Color(pixel1, true);
                    int r1 = color1.getRed();
                    int g1 = color1.getGreen();
                    int b1 = color1.getBlue();
                    int pixel2 = img2.getRGB(i, j);
                    Color color2 = new Color(pixel2, true);
                    int r2 = color2.getRed();
                    int g2 = color2.getGreen();
                    int b2 = color2.getBlue();

                    //sum of differences of RGB values of the two images
                    long data = Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
                    diff = diff + data;
                }
            }
            double avg = diff / (w1 * h1 * RGB_CHANNELS_COUNT);
            percentage = (avg / MAX_CHANNEL_VALUE) * 100;
        }

        return percentage < ALLOWABLE_ERROR;
    }
}


