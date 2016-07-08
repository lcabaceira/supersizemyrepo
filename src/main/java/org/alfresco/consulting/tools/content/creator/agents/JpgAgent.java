package org.alfresco.consulting.tools.content.creator.agents;

import org.alfresco.consulting.tools.content.creator.BulkImportManifestCreator;
import org.alfresco.consulting.tools.content.creator.FolderManager;
import org.alfresco.consulting.tools.content.creator.ImageManager;
import org.alfresco.consulting.tools.content.creator.executor.parameters.AgentExecutionInfo;
import org.alfresco.consulting.words.RandomWords;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.util.IOUtils;

import java.io.*;
import java.util.Properties;

public class JpgAgent extends Thread implements Runnable {
    private static final Log logger = LogFactory.getLog(JpgAgent.class);

    public void run() {
        RandomWords.init();

        try {
            File randomImage = ImageManager.getImageManager().getRandomImage();
            InputStream is = new FileInputStream(randomImage);
            try {
                String fileName = FolderManager.createFileName("_JpegImageSSMR.jpg");
                String folderLocation = FolderManager.getFolderLocation();
                FileOutputStream out = new FileOutputStream(folderLocation + "/" + fileName);
                BulkImportManifestCreator.createBulkManifest(fileName, folderLocation, getDocumentProperties());
                IOUtils.copy(is, out);
                out.close();
                is.close();
            } catch (IOException e) {
                logger.error("Unable to copy image.", e);
            }
        } catch (Exception e) {
            logger.error("First catch", e);
        }

        CompletionTracker.registerCompletion();
    }

    private Properties getDocumentProperties() {
        return AgentExecutionInfo.getDefaultInstance().getDocumentProperties();
    }
}
