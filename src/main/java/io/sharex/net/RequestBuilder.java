package io.sharex.net;

import java.io.File;

public final class RequestBuilder {
    private NetworkRequest building = new NetworkRequest();

    public RequestBuilder() {
    }

    public RequestBuilder url(String url) {
        building.url = url;
        return this;
    }

    public RequestBuilder agent(String agent) {
        building.agent = agent;
        return this;
    }

    public RequestBuilder header(String key, String value) {
        building.headers.put(key, value);
        return this;
    }

    public RequestBuilder fields(String key, Object value) {
        building.fields.put(key, value);
        return this;
    }

    public RequestBuilder fields(String key, File value) {
        building.fields.put(key, value);
        return this;
    }

    public NetworkRequest build() {
        if (building == null) {
            throw new UnsupportedOperationException();
        }

        return building;
    }
}
