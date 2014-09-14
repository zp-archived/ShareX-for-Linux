package pro.zackpollard.projectx.uploaders.image;

import pro.zackpollard.projectx.uploaders.UploadStatus;
import pro.zackpollard.projectx.uploaders.Uploader;

import java.io.File;

public abstract class ImageUploader extends Uploader {

    /**
     * This method is called in order to upload a File using the selected class.
     *
     * @param file The image file that will be uploaded.
     * @return A string that will resemble a URL that will then be dealt with
     * post-capture as selected by the user in the config.
     */
    public abstract String upload(File file);
}
