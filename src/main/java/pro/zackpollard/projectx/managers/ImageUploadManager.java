package pro.zackpollard.projectx.managers;

import pro.zackpollard.projectx.ProjectX;
import pro.zackpollard.projectx.config.Config;
import pro.zackpollard.projectx.uploaders.image.CustomImageUploader;
import pro.zackpollard.projectx.uploaders.image.ImageUploader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ImageUploadManager {

	private ProjectX instance;

    private Config config;

	private ImageUploader selectedUploader;

	private Map<String, ImageUploader> uploaderMap = new HashMap<>();

	public ImageUploadManager(ProjectX instance) {

		this.instance = instance;
		//TODO: Get selected uploader from config.
        this.config = new Config(instance, new File("Custom-Image.json"));
	}

	public ImageUploader getUploader() {

		return this.selectedUploader;
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


	/**
	 * When a new ImageUploader is created, it should be added here with the name that it should have in the GUI. This makes it available for selection.
	 */

	public Map<String, ImageUploader> getImageUploaders() {

		return this.uploaderMap;
	}

    public ImageUploader getImageUploader(String name) {
        ImageUploader uploader = this.uploaderMap.get(name);
        if(uploader == null) {
            uploader = new CustomImageUploader(instance, name);
            this.config.loadAPI(name, uploader, instance.getLogger());
            this.uploaderMap.put(name, uploader);
        }
        return uploader;
    }

	public void registerUploader(ImageUploader uploader) {

		this.uploaderMap.put(uploader.getName(), uploader);
	}
}
