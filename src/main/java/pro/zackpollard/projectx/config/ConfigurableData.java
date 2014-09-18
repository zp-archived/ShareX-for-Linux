package pro.zackpollard.projectx.config;

import org.json.JSONObject;
import pro.zackpollard.projectx.uploaders.Uploader;
import pro.zackpollard.projectx.utils.Logger;

/**
 * @author DarkSeraphim
 */
public abstract class ConfigurableData {

    private boolean header = true;

    private boolean success = true;

	private Logger logger;

	public boolean load(JSONObject object, Uploader uploader, Logger logger) {
        this.header = true;
        this.success = true;
        this.logger = logger;
		parse(object, uploader);
        this.logger = null;
		return this.success;
	}

	protected abstract void parse(JSONObject object, Uploader uploader);

	protected final void throwError(Exception ex) {
        this.logger.log(Logger.LoggerLevel.WARNING, ex.getLocalizedMessage());
	}

	protected final void issueWarning(String warning) {
        this.logger.log(Logger.LoggerLevel.WARNING, warning);
	}

    protected final void printHeader() {
        if(this.header) {
            this.logger.log(Logger.LoggerLevel.WARNING, "Error(s) and/or warning(s) occurred while parsing the config");
            this.header = false;
        }
    }

}
