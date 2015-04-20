package io.sharex.image;

import java.awt.*;
import java.io.IOException;

public interface ImageProvider {
    public Image snapImage(Rectangle rect) throws IOException;
}
