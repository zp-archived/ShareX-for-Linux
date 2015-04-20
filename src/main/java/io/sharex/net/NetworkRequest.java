package io.sharex.net;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

import java.util.Map;

public class NetworkRequest {
    Map<String, String> headers;
    Map<String, Object> fields;
    String agent;
    String url;

    public NetworkRequest(String agent, String url,
                          Map<String, String> headers,
                          Map<String, Object> fields) {
        this.agent = agent;
        this.url = url;
        this.headers = headers;
        this.fields = fields;
    }

    NetworkRequest() { // for gson and RequestBuilder
    }

    public String agent() {
        return agent;
    }

    public String url() {
        return url;
    }

    public Map<String, String> headers() {
        return headers;
    }

    public Map<String, Object> fields() {
        return fields;
    }

    public JSONObject post() {
        try {
            return Unirest.post(url)
                    .headers(headers)
                    .header("User-Agent", agent)
                    .fields(fields)
                    .asJson()
                    .getBody()
                    .getObject();
        } catch (UnirestException ex) {
            // TODO deal with exception
            return null;
        }
    }
}
