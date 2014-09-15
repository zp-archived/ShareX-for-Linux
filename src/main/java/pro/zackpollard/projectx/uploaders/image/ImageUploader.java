package pro.zackpollard.projectx.uploaders.image;

import lombok.Setter;
import pro.zackpollard.projectx.io.NetworkRequest;
import pro.zackpollard.projectx.uploaders.UploadStatus;
import pro.zackpollard.projectx.uploaders.Uploader;
import pro.zackpollard.projectx.utils.Regex;

import java.io.File;

public class ImageUploader extends Uploader {

    @Setter
    private String imageResponse;

    @Setter
    private String thumbResponse;

    @Setter
    private String deleteResponse;

    public ImageUploader(String name) {
        super(name, Type.IMAGE);
    }

    /**
     * This method is called in order to upload a File using the selected class.
     *
     * @param file The image file that will be uploaded.
     * @return A string that will resemble a URL that will then be dealt with
     * post-capture as selected by the user in the config.
     */
    public String upload(File file) {
        String response = NetworkRequest.createRequest(this, file).run();
        if (response == null) {
            return null;
        }
        for (Regex filter : this.getResponseFilter()) {
            response = filter.replace(response);
        }
        return this.imageResponse.replace("%response%", response);
    }

    @Override
    public UploadStatus testConnection() {
        return null;
    }

    @Override
    public boolean isSetup() {
        return false;
    }

    @Override
    public void runSetup() {

    }
}
