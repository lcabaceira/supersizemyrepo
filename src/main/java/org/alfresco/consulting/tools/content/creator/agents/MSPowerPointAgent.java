package org.alfresco.consulting.tools.content.creator.agents;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.Random;

import org.alfresco.consulting.locator.PropertiesLocator;
import org.alfresco.consulting.tools.content.creator.BulkImportManifestCreator;
import org.alfresco.consulting.words.RandomWords;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xslf.usermodel.*;

public class MSPowerPointAgent extends Thread implements Runnable {
    /**
     * @param args
     * @throws java.io.IOException
     */

    private static String files_deployment_location;
    private static String images_location;

    public MSPowerPointAgent(String _files_deployment_location, String _images_location) {
        this.files_deployment_location = _files_deployment_location;
        this.images_location = _images_location;
      }


	public void run()
    {
        RandomWords.init();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        XMLSlideShow ppt = new XMLSlideShow();
        File imagesFolder = new File(images_location);
        File[] files =   imagesFolder.listFiles();
        int size = files.length;

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

        XSLFSlide slide[]  = {ppt.createSlide(),ppt.createSlide(),ppt.createSlide(),ppt.createSlide(),ppt.createSlide(),ppt.createSlide()};
        // random image
        for (int i=0;i<6;i++) {
            Random rand = new Random();
            int number = rand.nextInt(size);
            File randomImage = files[number];
            byte[] pictureData = new byte[0];
            try {
                pictureData = IOUtils.toByteArray(new FileInputStream(randomImage));
            } catch (IOException e) {
                e.printStackTrace();
            }
            int idx = ppt.addPicture(pictureData, XSLFPictureData.PICTURE_TYPE_PNG);
            XSLFPictureShape pic = slide[i].createPicture(idx);
        }
        FileOutputStream outStream = null;

        try {
            double x = Math.random();
            String fileName =  cal.getTimeInMillis() +"_MSpowerpointSSMR.ppt";
            String filePath = files_deployment_location + "/" + fileName;
            // Creating the metadata file
            BulkImportManifestCreator.createBulkManifest(fileName);
            FileOutputStream out = new FileOutputStream(filePath);
            ppt.write(out);
            out.close();

        } catch (Exception e) {
            System.out.println("First Catch");
            e.printStackTrace();
        }
    }

}
