package pro.zackpollard.projectx.uploaders.image;

import pro.zackpollard.projectx.ProjectX;
import pro.zackpollard.projectx.uploaders.UploadStatus;

/**
 * Created by Zack on 17/09/2014.
 */
public class CustomImageUploader extends ImageUploader {

	public CustomImageUploader(ProjectX projectX, String name) {
		super(projectX, name);
	}

	@Override
	public UploadStatus testConnection() {

		//TODO: This runs a test upload and checks if the image uploaded successfully.

		return null;
	}

	@Override
	public boolean isSetup() {

		//TODO: This needs to check if the config contains settings for this uploader.

		return false;
	}

	@Override
	public void runSetup() {
		//TODO: Once GUI is implemented do a setup run if this is enabled.
	}
}
