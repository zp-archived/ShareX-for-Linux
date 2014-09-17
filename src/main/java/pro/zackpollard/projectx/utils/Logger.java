package pro.zackpollard.projectx.utils;

public class Logger {

	private boolean debug = false;

	/**
	 * Log with the required level and can provide a message.
	 *
	 * @param level   The LoggerLevel of that message, will be reflected in the logger output.
	 * @param message The message that should accompany the logger level in the output.
	 */
	public void log(LoggerLevel level, String message) {

		this.log(level, message, null);
	}

	/**
	 * Log with the required level and can provide a message.
	 *
	 * @param level   The LoggerLevel of that message, will be reflected in the logger output.
	 * @param message The message that should accompany the logger level in the output.
	 * @param e       The exception that was provided with that error, is not required.
	 */
	public void log(LoggerLevel level, String message, Exception e) {

		switch (level) {
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
				if (this.getDebug()) {
					this.printException(e);
					System.out.println("DEBUG: " + message);
				}
		}
	}

	/**
	 * Prints a stacktrace for the provided exception if that exception object is not null.
	 *
	 * @param e The exception that is to be handled and printed, is nullable.
	 */
	private void printException(Exception e) {

		if (e != null) {

			e.printStackTrace();
		}
	}

	/**
	 * Toggles the debug state in the logger.
	 *
	 * @return the new debug state.
	 */
	public boolean toggleDebug() {

		return this.debug = !this.debug;
	}

	/**
	 * Used to get whether the logger is in debug state or not.
	 *
	 * @return the current debug state.
	 */
	public boolean getDebug() {

		return debug;
	}

	public void setDebug(boolean debug) {

		this.debug = debug;
	}

	/**
	 * Enum of the various levels the logs can be sent at.
	 */
	public enum LoggerLevel {

		INFO,
		WARNING,
		ALERT,
		ERROR,
		FATAL,
		DEBUG
	}
}