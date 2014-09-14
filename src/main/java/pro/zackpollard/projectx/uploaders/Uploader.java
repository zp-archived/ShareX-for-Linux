package pro.zackpollard.projectx.uploaders;


import java.io.File;

public abstract class Uploader {

    public abstract UploadStatus testConnection();

    public abstract boolean isSetup();

    public abstract void runSetup();
}
