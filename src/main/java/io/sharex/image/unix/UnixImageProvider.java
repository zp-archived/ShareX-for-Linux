package io.sharex.image.unix;

import io.sharex.Constants;
import io.sharex.image.ImageProvider;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class UnixImageProvider implements ImageProvider {

    @Override
    public Image snapImage(Rectangle rect) throws IOException {
        StringBuilder command = new StringBuilder("ffmpeg -f x11grab -vframes 1 ")
                .append("-video_size ")
                .append(rect.getWidth())
                .append('x')
                .append(rect.getHeight());

        command.append(" -i :0.0+")
                .append(rect.getX())
                .append(',')
                .append(rect.getY())
                .append(",0")
                .append(" tmp.png");

        Process process = new ProcessBuilder(command.toString())
                .directory(Constants.HOME)
                .start();

        try {
            process.waitFor();
        } catch (InterruptedException ignored) {
        }

        return ImageIO.read(new File(Constants.HOME, "tmp.png"));
    }
}
