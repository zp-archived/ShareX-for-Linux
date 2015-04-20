package io.sharex.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String generateFileName(String suffix) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("ddmmyyyy-hhmmss");
        return sdfDate.format(new Date()) + "." + suffix;
    }
}
