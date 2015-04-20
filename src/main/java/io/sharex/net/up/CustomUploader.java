package io.sharex.net.up;

import io.sharex.net.NetworkRequest;

public final class CustomUploader implements Uploader {
    private final NetworkRequest request;
    private boolean started;

    public CustomUploader(NetworkRequest request) {
        this.request = request;
    }

    @Override
    public boolean started() {
        return started;
    }

    @Override
    public void start() {
        started = true;
        request.post();
    }
}
