package io.sharex.uploaders;


public abstract class Uploader {

    public abstract UploadStatus testConnection();

    public abstract boolean isSetup();

    public abstract void runSetup();
}
