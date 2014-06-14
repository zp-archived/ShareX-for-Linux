package pro.zackpollard.projectx.io;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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
    private JSONObject run() {
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

        StringBuilder sb = new StringBuilder();
        Scanner scanner = null;
        try {
            scanner = new Scanner(response.getEntity().getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (scanner.hasNext() && scanner != null) {

            sb.append(scanner.next());
        }

        JSONObject jsonMap = new JSONObject(sb.toString());

        return jsonMap;
    }

    /**
     * This method is called to add a string value against a key to the POST request.
     *
     * @param key   The key for that string value.
     * @param value The string value you want to send.
     */
    public void addPostString(String key, String value) {

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
}