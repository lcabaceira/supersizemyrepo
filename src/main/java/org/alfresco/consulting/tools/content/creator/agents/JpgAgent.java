package org.alfresco.consulting.tools.content.creator.agents;

import java.io.*;
import java.util.Calendar;
import java.util.Properties;
import java.util.Random;

import org.alfresco.consulting.locator.PropertiesLocator;
import org.alfresco.consulting.tools.content.creator.BulkImportManifestCreator;
import org.alfresco.consulting.words.RandomWords;
import org.apache.poi.util.IOUtils;

public class JpgAgent extends Thread implements Runnable {
    /**
     * @param args
     * @throws java.io.IOException
     */

    private static String files_deployment_location;
    private static String images_location;
    private static Properties properties;

    public JpgAgent(String _files_deployment_location, String _images_location, Properties _properties) {
        this.files_deployment_location = _files_deployment_location;
        this.images_location = _images_location;
        this.properties = _properties;
      }


    public void run() {

        RandomWords.init();
        Calendar cal = Calendar.getInstance();

        try {
            File imagesFolder = new File(images_location);
            File[] files =   imagesFolder.listFiles();
            int size = files.length;
            Random rand = new Random();
            int number = rand.nextInt(size);
            File randomImage = files[number];


            //InputStream is =new URL("http://lorempixel.com/g/800/600/").openStream();
            InputStream is = new FileInputStream(randomImage);
            FileOutputStream outStream = null;


            String fileName =  cal.getTimeInMillis() +"_JpegImageSSMR.jpg";
            String filePath = files_deployment_location + "/" + fileName;
            // Creating the metadata file
            BulkImportManifestCreator.createBulkManifest(fileName, files_deployment_location, properties);
            FileOutputStream out = new FileOutputStream(filePath);
            IOUtils.copy(is,out);
            out.close();


        } catch (Exception e) {
            System.out.println("First Catch");
            e.printStackTrace();
        }

    }

}
