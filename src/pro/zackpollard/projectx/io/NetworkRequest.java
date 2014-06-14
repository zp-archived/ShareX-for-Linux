package pro.zackpollard.projectx.io;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NetworkRequest {

    private final String USER_AGENT;
    private final String url;
    private final MultipartEntityBuilder urlParameters = MultipartEntityBuilder.create();


    public NetworkRequest(String userAgent, String url) {

        this.USER_AGENT = userAgent;
        this.url = url;
    }

    /**
     * This will execute the POST request and return the requested responses.
     * This Class object should be cleared from memory after this method has been exectued
     *
     * @return This will return a Map<Key, Returned Value> of the responses
     * which will relate to the values returned by the POST statement.
     */
    public Map<String, String> execute() {

        HttpClientBuilder buildClient = HttpClientBuilder.create();
        HttpClient client = buildClient.build();
        HttpPost post = new HttpPost(url);

        post.setHeader("User-Agent", USER_AGENT);
        post.setEntity(urlParameters.build());
        HttpResponse response = null;

        try {
            response = client.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, String> responses = new HashMap<String, String>();

        if(response != null) {
            for (Header header : response.getAllHeaders()) {
                responses.put(header.getName(), header.getValue());
            }

            return responses;
        }

        return null;
    }

    /**
     * This method is called to add a string value against a key to the POST request.
     *
     * @param key The key for that string value.
     * @param value The string value you want to send.
     */
    public void addPostString(String key, String value) {

        urlParameters.addTextBody(key, value);
    }

    /**
     * This method is called to add a file against a key to the POST request.
     *
     * @param key The string key for that file value.
     * @param file The file you would like to POST.
     */
    public void addPostFile(String key, File file) {

        urlParameters.addBinaryBody(key, file);
    }
}