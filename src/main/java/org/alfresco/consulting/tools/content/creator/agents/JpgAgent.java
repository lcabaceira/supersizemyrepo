package org.alfresco.consulting.tools.content.creator.agents;

import org.alfresco.consulting.tools.content.creator.BulkImportManifestCreator;
import org.alfresco.consulting.tools.content.creator.FolderManager;
import org.alfresco.consulting.tools.content.creator.ImageManager;
import org.alfresco.consulting.words.RandomWords;
import org.apache.poi.util.IOUtils;

import java.io.*;
import java.util.Properties;

public class JpgAgent extends Thread implements Runnable {
    private static Properties properties;

    public JpgAgent(Properties _properties) {
        properties = _properties;
    }
    public void run() {
        RandomWords.init();

        try {
            File randomImage = ImageManager.getImageManager().getRandomImage();
            InputStream is = new FileInputStream(randomImage);
            try {
                String fileName = FolderManager.createFileName("_JpegImageSSMR.jpg");
                String folderLocation = FolderManager.getFolderLocation();
                FileOutputStream out = new FileOutputStream(folderLocation + "/" + fileName);
                BulkImportManifestCreator.createBulkManifest(fileName, folderLocation, properties);
                IOUtils.copy(is, out);
                out.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            System.out.println("First Catch");
            e.printStackTrace();
        }

        CompletionService.registerCompletion();
    }
}
