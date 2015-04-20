package io.sharex.io.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SaveImage {

    public static void saveImage(BufferedImage image, File output, ImageFormat format) {

        output.mkdirs();

        try {
            javax.imageio.ImageIO.write(image, format.name().toLowerCase(), output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
