package pro.zackpollard.projectx.io;

import org.apache.http.entity.mime.MultipartEntityBuilder;
import pro.zackpollard.projectx.uploaders.Uploader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public abstract class NetworkRequest {

    protected final MultipartEntityBuilder urlParameters = MultipartEntityBuilder.create();
    protected final Uploader uploader;

    public NetworkRequest(Uploader uploader) {
        this.uploader = uploader;
    }

    public static NetworkRequest createRequest(Uploader uploader, File file) {

        switch (uploader.getMethod()) {
            case POST:
                return new POSTRequest(uploader, file);
            default:
                throw new UnsupportedOperationException("Unsupported HTTP method");
        }
    }

    /**
     * This will execute the POST request and return the requested responses.
     * This Class object should be cleared from memory after this method has been exectued
     *
     * @return This will return a Map<Key, Returned Value> of the responses
     * which will relate to the values returned by the POST statement.
     */
    public abstract String run();

    /**
     * This method is called to add a string value against a key to the POST request.
     *
     * @param key   The key for that string value.
     * @param value The string value you want to send.
     */
    public void addKeyValuePair(String key, String value) {

        urlParameters.addTextBody(key, value);
    }

    /**
     * This method is called to add a file against a key to the POST request.
     *
     * @param key  The string key for that file value.
     * @param file The file you would like to POST.
     */
    public void addPostFile(String key, File file) {

        urlParameters.addBinaryBody(key, file);
    }

    public enum Method {
        GET,
        POST;

        private static final Map<String, Method> byName = new HashMap<String, Method>();

        static {
            for (Method m : Method.values()) {
                byName.put(m.name().toUpperCase(), m);
            }
        }

        public static Method getMethod(String name) {
            Method m = Method.byName.get(name.toUpperCase());
            return m != null ? m : GET;
        }
    }
}