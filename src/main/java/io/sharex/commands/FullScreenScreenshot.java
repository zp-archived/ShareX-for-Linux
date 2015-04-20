package io.sharex.commands;

import io.sharex.ShareX;
import io.sharex.io.image.ImageFormat;
import io.sharex.io.image.SaveImage;
import io.sharex.utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class FullScreenScreenshot extends Command {

    public FullScreenScreenshot(String name) {
        super(name);
    }

    @Override
    public void execute() {
        Robot robot = ShareX.instance().robot();
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        BufferedImage image = robot.createScreenCapture(new Rectangle(0, 0,
                (int) toolkit.getScreenSize().getWidth(),
                (int) toolkit.getScreenSize().getHeight()));

        //TODO: Make the image saving configurable
        //TODO: Make the image saving location configurable

        SaveImage.saveImage(image, new File("./images/" + Utils.generateFileName("png")), ImageFormat.PNG);

        //TODO: Push this upstream to a server (Configurable).
    }
}