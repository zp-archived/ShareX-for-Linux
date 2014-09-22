package pro.zackpollard.projectx.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    /**
     * This generates a file name based on the data and time of generation.
     * It also appends the provided suffix to the generated fileName.
     *
     * @param suffix This is the String of text to be appended to the generated fileName
     * @return The entire generated string, suffix included
     */

    public static String generateFileName(String suffix) {

        SimpleDateFormat sdfDate = new SimpleDateFormat("ddmmyyyy-hhmmss");
        Date date = new Date();
        String fileName = sdfDate.format(date) + "." + suffix;
        return fileName;
    }

    /**
     * This is used to find out the type of operating system that the program
     * is running on in order to specialise some methods that don't work on certain
     * operating systems.
     *
     * @return OSType which specifies the operating system type otherwise returns OSType.OTHER
     * @see OSType
     */

    public static OSType getOS() {

        String os = System.getProperty("os.name").toLowerCase();

        if (os.startsWith("win")) {

            return OSType.WINDOWS;
        } else if (os.startsWith("mac")) {

            return OSType.MAC;
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {

            return OSType.UNIX;
        } else if (os.startsWith("sunos")) {

            return OSType.SOLARIS;
        } else {

            return OSType.OTHER;
        }
    }
}
