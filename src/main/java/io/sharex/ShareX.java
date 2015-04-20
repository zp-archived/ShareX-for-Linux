package io.sharex;

import io.sharex.image.ImageProvider;
import io.sharex.image.unix.UnixImageProvider;
import io.sharex.utils.Logger;

import java.awt.*;

public class ShareX {
    private static ShareX instance;
    private ImageProvider provider;
    private Robot robot;

    ShareX() {
        instance = this;

        if (Constants.IS_UNIX) {
            provider = new UnixImageProvider();
        } else {
            // TODO windows provider
        }

        try {
            robot = new Robot();
        } catch (AWTException ex) {
            System.out.println("Unable to start up robot!");
            ex.printStackTrace();
            return;
        }

        // TODO open up GUI
    }

    public Logger logger() {
        return null; // no
    }

    public Robot robot() {
        return robot;
    }

    public static ShareX instance() {
        return instance;
    }
}
