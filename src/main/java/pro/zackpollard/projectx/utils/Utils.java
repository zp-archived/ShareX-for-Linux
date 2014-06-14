package pro.zackpollard.projectx.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String generateFileName(String prefix) {

        SimpleDateFormat sdfDate = new SimpleDateFormat("ddmmyyyy-hhmmss");
        Date date = new Date();
        String fileName = sdfDate.format(date) + "." + prefix;
        return fileName;
    }
}
