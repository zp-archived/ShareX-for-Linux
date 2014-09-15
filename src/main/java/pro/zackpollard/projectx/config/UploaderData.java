package pro.zackpollard.projectx.config;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import pro.zackpollard.projectx.io.NetworkRequest;
import pro.zackpollard.projectx.uploaders.Uploader;
import pro.zackpollard.projectx.uploaders.image.ImageUploader;
import pro.zackpollard.projectx.utils.ParserData;
import pro.zackpollard.projectx.utils.Regex;

import java.util.Iterator;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @author DarkSeraphim
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UploaderData extends ConfigurableData<Uploader> {

    private static UploaderData instance;

    private static UploaderData getInstance() {
        return instance != null ? instance : (instance = new UploaderData());
    }

    public static ParserData<Uploader> loadAPI(JSONObject object) {
        return getInstance().load(object);
    }

    public Uploader parse(JSONObject json) {
        String name = json.getString("name");
        Uploader.Type type = Uploader.Type.getType(json.getString("type"));
        if (type == Uploader.Type.UNKNOWN) {
            this.throwError(new IllegalArgumentException("Invalid API type"));
            return null;
        }

        Uploader uploader = null;
        switch (type) {
            case IMAGE:
                uploader = new ImageUploader(name);
                this.loadImageUploader((ImageUploader) uploader, json);
                break;
            default:
                this.throwError(new IllegalArgumentException("Unsupported API type"));
                return null;
        }

        uploader.setUrl(getString(json, "url"));
        uploader.setMethod(NetworkRequest.Method.getMethod(getString(json, "method")));
        uploader.setFile(getString(json, "file"));

        addFilters(uploader, json);

        JSONObject optHeaders = json.getJSONObject("optional-headers");
        if (optHeaders != null) {
            Object key;
            Object value;
            for (Iterator it = optHeaders.keys(); it.hasNext(); ) {
                key = it.next();
                if (key instanceof String && (value = optHeaders.getString((String) key)) != null) {
                    uploader.addOptionalHeader((String) key, (String) value);
                }
            }
        }

        JSONObject optParams = json.getJSONObject("optional-parameters");
        if (optHeaders != null) {
            Object key;
            Object value;
            for (Iterator it = optParams.keys(); it.hasNext(); ) {
                key = it.next();
                if (key instanceof String && (value = optParams.getString((String) key)) != null) {
                    uploader.addOptionalParam((String) key, (String) value);
                }
            }
        }

        return uploader;
    }

    private void loadImageUploader(ImageUploader uploader, JSONObject object) {
        JSONObject formats = object.getJSONObject("response-format");
        if (formats == null) {
            return;
        }

        String image, thumb, delete;

        if ((image = formats.optString("image")) != null) {
            uploader.setImageResponse(image);
        } else {
            this.issueWarning("No image response format set. This might lead to unexpected output.");
        }

        if ((thumb = formats.optString("thumb")) != null) {
            uploader.setThumbResponse(thumb);
        } else {
            this.issueWarning("No thumb response format set. This might lead to unexpected output.");
        }

        if ((delete = formats.optString("delete")) != null) {
            uploader.setDeleteResponse(delete);
        } else {
            this.issueWarning("No delete response format set. This might lead to unexpected output.");
        }
    }

    private String getString(JSONObject json, String key) {
        String value = json.getString(key);
        if (value == null) {
            this.throwError(new IllegalArgumentException(String.format("Invalid/missing option '%s'.", key)));
        }
        return value;
    }

    private void addFilters(Uploader uploader, JSONObject json) {
        JSONArray array = json.getJSONArray("response-filters");
        if (array == null || array.length() == 0) {
            return;
        }
        Pattern pattern;
        String regex, replacement;
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            if ((regex = object.optString("regex")) != null && (replacement = object.optString("replacement")) != null) {
                try {
                    pattern = Pattern.compile(regex);
                    uploader.addFilter(new Regex(pattern, replacement));
                } catch (PatternSyntaxException ex) {
                    this.throwError(ex);
                }
            } else {
                this.throwError(new IllegalArgumentException("Invalid filter: missing regex and/or replacement."));
            }
        }
    }

}