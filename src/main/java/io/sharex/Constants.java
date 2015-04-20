package io.sharex;

import java.io.File;

public final class Constants {
    public static final File HOME = new File(System.getProperty("user.dir"));
    public static final String OS = System.getProperty("os.name").toLowerCase();
    public static final boolean IS_UNIX = OS.contains("nix") || OS.contains("nux") || OS.contains("mac");
    public static final boolean IS_WINDOWS = !IS_UNIX; // assume windows if not unix
}
