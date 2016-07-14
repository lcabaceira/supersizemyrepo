package org.alfresco.consulting.tools.content.creator.agents;

import org.alfresco.consulting.tools.content.creator.ImageManager;
import org.alfresco.consulting.words.RandomWords;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xslf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MSPowerPointAgent extends AgentWithFileWriter {
    private static final Log logger = LogFactory.getLog(MSPowerPointAgent.class);
    private XMLSlideShow ppt;

    protected Log getLogger() {
        return logger;
    }

    protected void createFile() {
        ppt = new XMLSlideShow();

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

        // random image
        if (!isCreatingSmallerFiles()) {
            createSlidesWithImages();
        }

        writeOutFiles();
    }

    private void createSlidesWithImages() {
        XSLFSlide slide[] = {ppt.createSlide(), ppt.createSlide(), ppt.createSlide(), ppt.createSlide(),
                ppt.createSlide(), ppt.createSlide()};

        for (XSLFSlide aSlide : slide) {
            File randomImage = ImageManager.getImageManager().getRandomImage();
            byte[] pictureData = new byte[0];
            try {
                pictureData = IOUtils.toByteArray(new FileInputStream(randomImage));
            } catch (IOException e) {
                getLogger().error("Unable to get bytes of " + randomImage, e);
            }
            int idx = ppt.addPicture(pictureData, XSLFPictureData.PICTURE_TYPE_PNG);
            aSlide.createPicture(idx);
        }
    }

    protected void performWriteOfFile(FileOutputStream out) throws IOException {
        ppt.write(out);
    }

    protected String getFileNameSuffix() {
        return "_MSpowerpointSSMR.ppt";
    }
}
