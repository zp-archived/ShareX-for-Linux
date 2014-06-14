package pro.zackpollard.projectx.managers;

import pro.zackpollard.projectx.ProjectX;
import pro.zackpollard.projectx.uploaders.Uploader;
import pro.zackpollard.projectx.uploaders.image.ImageUploader;

public class ImageUploadManager {

    private ProjectX instance;
    private ImageUploader selectedUploader;

    public ImageUploadManager(ProjectX instance) {

        this.instance = instance;
        //TODO: Get selected uploader from config.
    }

    /**
     * This will change the current uploader to the new uploader selected by the user.
     *
     * @param uploader The uploader that will be used.
     */
    public void setUploader(ImageUploader uploader) {

        this.selectedUploader = uploader;
        //TODO: Save new uploader back to config
    }

    public Uploader getUploader() {


    }
}
