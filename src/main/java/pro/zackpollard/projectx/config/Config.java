package pro.zackpollard.projectx.config;

import org.json.JSONException;
import org.json.JSONObject;
import pro.zackpollard.projectx.ProjectX;
import pro.zackpollard.projectx.uploaders.Uploader;
import pro.zackpollard.projectx.utils.Logger;

import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author DarkSeraphim
 */
public class Config {

    private static final UploaderData data = new UploaderData();

    //TODO: Currently one config for all uploaders, this should be changed to one config per uploader in the future.

    private final ProjectX projectX;

    private final File configFile;

    private Map<String, JSONObject> unparsed;

    public Config(ProjectX instance, File configFile) {
        this.projectX = instance;
        this.configFile = configFile;
        init();
    }

    private final void init() {
        saveDefaultConfig();
        load();
    }

    public void load() {
        String json = loadConfigurationAsString();
        JSONObject object;
        try {
            object = new JSONObject(json);
        } catch (JSONException ex) {
            throw new IllegalArgumentException("Invalid JSON file.", ex);
        }
        Iterator<String> keys = object.keys();
        String key;
        JSONObject data;
        while (keys.hasNext()) {
            key = keys.next();
            data = object.optJSONObject(key);
            if (data == null) {
                // TODO: log this
                continue;
            }
            this.unparsed.put(key, data);
        }
    }

    private final String loadConfigurationAsString() {
        StringBuilder jsonString = new StringBuilder();
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                // Ignore comments
                if (line.startsWith("#")) {
                    continue;
                }
                jsonString.append(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
        return jsonString.toString();
    }

    public Set<String> getAPIs() {
        return this.unparsed.keySet();
    }

    public void saveDefaultConfig() {
        if (configFile.exists()) {
            return;
        }

        try {
            if (!configFile.createNewFile()) {
                throw new IOException("Failed to create Custom-Image.json");
            }
        } catch (IOException ex) {
            // Well fuck it, we need that config file
            // The error should give them plenty of
            // information to fix it
            projectX.getLogger().log(Logger.LoggerLevel.DEBUG, "There was an error when saving the default config.", ex);
            System.exit(-1);
        }

        InputStream in = this.getClass().getClassLoader().getResourceAsStream("Custom-Image.json");
        if (in == null) {
            return;
        }

        String line;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in));
             BufferedWriter writer = new BufferedWriter(new FileWriter(this.configFile))) {
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException ex) {
            this.configFile.delete();
            ex.printStackTrace();
            System.exit(-1);
        }
    }

    public boolean loadAPI(String api, Uploader uploader, Logger logger) {

        JSONObject object = this.unparsed.get(api);
        if (object == null) {
            throw new IllegalArgumentException("API name not found");
        }
        return data.load(object, uploader, logger);
    }
}
