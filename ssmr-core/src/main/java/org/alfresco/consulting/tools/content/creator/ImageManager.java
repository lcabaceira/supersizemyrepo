package org.alfresco.consulting.tools.content.creator;

import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

public class ImageManager {
    private static ImageManager instance;

    private File[] imageFiles;

    private ImageManager(String imageLocation) {
        File imageFolder = new File(imageLocation);
        this.imageFiles = imageFolder.listFiles();
    }

    public static void initializeImageManager(String imageLocation) {
        instance = new ImageManager(imageLocation);
    }

    public static ImageManager getImageManager() {
        if (instance == null) throw new IllegalArgumentException("Image manager has not been initialized.");

        return instance;
    }

    public File getRandomImage() {
        return imageFiles[ThreadLocalRandom.current().nextInt(imageFiles.length)];
    }
}
