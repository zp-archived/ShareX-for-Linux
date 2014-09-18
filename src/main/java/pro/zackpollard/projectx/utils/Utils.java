package pro.zackpollard.projectx.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    /**
     * This generates a file name based on the data and time of generation.
     * It also appends the provided suffix to the generated fileName.
     *
     * @param suffix This is the String of text to be appended to the generated fileName.
     * @return The entire generated string, suffix included.
     */

    public static String generateFileName(String suffix) {

        SimpleDateFormat sdfDate = new SimpleDateFormat("ddmmyyyy-hhmmss");
        Date date = new Date();
        String fileName = sdfDate.format(date) + "." + suffix;
        return fileName;
    }
}
