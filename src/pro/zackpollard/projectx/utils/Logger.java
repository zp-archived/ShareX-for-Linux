package pro.zackpollard.projectx.utils;

public class Logger {

    private boolean debug;

    public Logger() {
    }

    public enum LoggerLevel {

        INFO,
        WARNING,
        ALERT,
        ERROR,
        FATAL,
        DEBUG
    }

    public void log(LoggerLevel level, String message) {

        this.log(level, message, null);
    }

    public void log(LoggerLevel level, String message, Exception e) {

        switch (LoggerLevel.values()) {
            case INFO:
                System.out.println("INFO: " + message);
            case WARNING:
                System.out.println("WARNING: " + message);
            case ALERT:
                System.out.println("ALERT: " + message);
            case ERROR:
                this.printException(e);
                System.out.println("ERROR: " + message);
            case FATAL:
                this.printException(e);
                System.out.println("FATAL: " + message);
            case DEBUG:
                this.printException(e);
                System.out.println("DEBUG: " + message);
        }
    }

    public void printException(Exception e) {

        if(e != null) {

            e.printStackTrace();
        }
    }

    public boolean toggleDebug() {

        if(this.debug) {

            debug = false;
        } else {

            debug = true;
        }
    }

    public boolean getDebug() {

        return debug;
    }
}
