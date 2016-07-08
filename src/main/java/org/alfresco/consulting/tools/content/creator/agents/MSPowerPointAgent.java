package org.alfresco.consulting.tools.content.creator.agents;

import org.alfresco.consulting.tools.content.creator.BulkImportManifestCreator;
import org.alfresco.consulting.tools.content.creator.FolderManager;
import org.alfresco.consulting.tools.content.creator.ImageManager;
import org.alfresco.consulting.tools.content.creator.executor.AgentExecutionInfo;
import org.alfresco.consulting.words.RandomWords;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xslf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class MSPowerPointAgent extends Thread implements Runnable {
    private static final Log logger = LogFactory.getLog(MSPowerPointAgent.class);

    public void run() {
        RandomWords.init();
        XMLSlideShow ppt = new XMLSlideShow();

        // there can be multiple masters each referencing a number of layouts
        // for demonstration purposes we use the first (default) slide master
        XSLFSlideMaster defaultMaster = ppt.getSlideMasters()[0];

        // title and content
        XSLFSlideLayout titleBodyLayout = defaultMaster.getLayout(SlideLayout.TITLE_AND_CONTENT);
        XSLFSlide slide2 = ppt.createSlide(titleBodyLayout);
        XSLFTextShape title2 = slide2.getPlaceholder(0);
        title2.setText("ppt document created by SSMR");

        XSLFTextShape body2 = slide2.getPlaceholder(1);
        body2.clearText(); // unset any existing text
        body2.addNewTextParagraph().addNewTextRun().setText(RandomWords.getWords(20));
        body2.addNewTextParagraph().addNewTextRun().setText(RandomWords.getWords(20));
        body2.addNewTextParagraph().addNewTextRun().setText(RandomWords.getWords(20));

        XSLFSlide slide[] = {ppt.createSlide(), ppt.createSlide(), ppt.createSlide(), ppt.createSlide(), ppt.createSlide(), ppt.createSlide()};

        // random image
        if (!isCreatingSmallerFiles()) {
            for (int i = 0; i < 6; i++) {
                File randomImage = ImageManager.getImageManager().getRandomImage();
                byte[] pictureData = new byte[0];
                try {
                    pictureData = IOUtils.toByteArray(new FileInputStream(randomImage));
                } catch (IOException e) {
                    logger.error("Unable to get bytes of " + randomImage, e);
                }
                int idx = ppt.addPicture(pictureData, XSLFPictureData.PICTURE_TYPE_PNG);
                slide[i].createPicture(idx);
            }
        }

        String fileName = FolderManager.createFileName("_MSpowerpointSSMR.ppt");

        try {
            final String folderLocation = FolderManager.getFolderLocation();
            FileOutputStream out = new FileOutputStream(folderLocation + "/" + fileName);
            BulkImportManifestCreator.createBulkManifest(fileName, folderLocation, getDocumentProperties());
            ppt.write(out);
            out.close();
        } catch (IOException e) {
            logger.error("Unable to save PowerPoint document: " + fileName, e);
        }

        CompletionTracker.registerCompletion();
    }

    private boolean isCreatingSmallerFiles() {
        return AgentExecutionInfo.getDefaultInstance().isCreatingSmallerFiles();
    }

    private Properties getDocumentProperties() {
        return AgentExecutionInfo.getDefaultInstance().getDocumentProperties();
    }

}
