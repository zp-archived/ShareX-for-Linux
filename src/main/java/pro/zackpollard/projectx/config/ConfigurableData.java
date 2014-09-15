package pro.zackpollard.projectx.config;

import org.json.JSONObject;
import pro.zackpollard.projectx.utils.ParserData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DarkSeraphim
 */
public abstract class ConfigurableData<Result> {

    private final List<String> warnings = new ArrayList<String>();

    private final List<Exception> exceptions = new ArrayList<Exception>();

    public ParserData<Result> load(JSONObject object) {
        this.warnings.clear();
        this.exceptions.clear();
        Result result = parse(object);
        if (!this.exceptions.isEmpty()) {
            result = null;
        }
        return new ParserData<Result>(result, this.warnings, this.exceptions);
    }

    protected abstract Result parse(JSONObject object);

    protected final void throwError(Exception ex) {
        this.exceptions.add(ex);
    }

    protected final void issueWarning(String warning) {
        this.warnings.add(warning);
    }

}
