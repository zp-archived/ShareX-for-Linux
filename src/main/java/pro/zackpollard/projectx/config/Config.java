package pro.zackpollard.projectx.config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pro.zackpollard.projectx.uploaders.Uploader;
import pro.zackpollard.projectx.utils.Logger;
import pro.zackpollard.projectx.utils.ParserData;

import java.io.*;
import java.util.*;

/**
 * @author DarkSeraphim
 */
public class Config {

    private final File configFile = new File("config.json");

    private Map<Uploader.Type, Collection<Uploader>> byType = new EnumMap<Uploader.Type, Collection<Uploader>>(Uploader.Type.class);

    private Map<String, Uploader> byName = new HashMap<String, Uploader>();

    private static String loadConfigurationAsString(File configFile) {
        StringBuilder jsonString = new StringBuilder();
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            while ((line = reader.readLine()) != null) {
                line = reader.readLine().trim();
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

    public void saveDefaultConfig() {
        if (configFile.exists()) {
            return;
        }

        try {
            if (!configFile.createNewFile()) {
                throw new IOException("Failed to create config.json");
            }
        } catch (IOException ex) {
            // Well fuck it, we need that config file
            // The error should give them plenty of
            // information to fix it
            ex.printStackTrace();
            System.exit(-1);
        }

        InputStream in = this.getClass().getClassLoader().getResourceAsStream("config.json");
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

    public void loadConfiguration(File configFile, Logger logger) {
        String json = loadConfigurationAsString(configFile);
        JSONObject object;
        try {
            object = new JSONObject(json);
        } catch (JSONException ex) {
            ex.printStackTrace();
            System.exit(-1);
            return;
        }
        JSONArray apis = object.optJSONArray("apis");
        if (apis == null || apis.length() == 0) {
            // No APIs? How do you want to handle that :P
            return;
        }

        boolean success = true;

        String header = "Error(s) and/or warning(s) occurred while parsing the config";
        ParserData<Uploader> pd;
        for (int i = 0; i < apis.length(); i++) {
            JSONObject apiData = apis.getJSONObject(i);
            pd = UploaderData.loadAPI(apiData);
            if (pd.wasSuccessful()) {
                if (!registerUploader(pd.getResult())) {
                    if (header != null) {
                        logger.log(Logger.LoggerLevel.INFO, header);
                        header = null;
                    }
                    logger.log(Logger.LoggerLevel.WARNING, String.format("Uploader '%s' already exists!", pd.getResult().getName()));
                }
            } else {
                success = false;
                for (Exception ex : pd.getExceptions()) {
                    if (header != null) {
                        logger.log(Logger.LoggerLevel.INFO, header);
                        header = null;
                    }
                    logger.log(Logger.LoggerLevel.ERROR, ex.getLocalizedMessage());
                }
            }
            if (pd.hasWarnings()) {
                for (String warning : pd.getWarnings()) {
                    if (header != null) {
                        logger.log(Logger.LoggerLevel.INFO, header);
                        header = null;
                    }
                    logger.log(Logger.LoggerLevel.WARNING, warning);
                }
            }
        }

        if (!success) {
            System.exit(-1);
        }
    }

    private boolean registerUploader(Uploader uploader) {

        if (this.byName.containsKey(uploader.getName())) {
            return false;
        }

        Collection<Uploader> uploaders = this.byType.get(uploader.getType());
        if (uploaders == null) {
            uploaders = new LinkedHashSet<Uploader>();
            this.byType.put(uploader.getType(), uploaders);
        }
        uploaders.add(uploader);
        this.byName.put(uploader.getName(), uploader);
        return true;
    }
}
