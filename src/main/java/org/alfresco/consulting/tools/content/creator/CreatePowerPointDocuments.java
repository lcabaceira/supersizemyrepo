package org.alfresco.consulting.tools.content.creator;


import org.alfresco.consulting.benchmark.locator.PropertiesLocator;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.usermodel.SlideShow;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import java.io.*;


public class CreatePowerPointDocuments extends Thread {

    private static int iteration;
    private static int threadCnt;
    private static Properties props = PropertiesLocator.getProperties("alfresco-consulting.properties");
    private static String files_deployment_location = props.getProperty("files_deployment_location");
    public static void main(String[] args) {
        String noThread = args[0];
        String noIteration = args[1];
        threadCnt = Integer.parseInt(noThread);
        iteration=Integer.parseInt(noIteration);

        SlideShow ppt = new SlideShow();
        //add first slide
        Slide s1 = ppt.createSlide();
        //add second slide
        Slide s2 = ppt.createSlide();
        //save changes in a file
        try {
            FileOutputStream out = new FileOutputStream(files_deployment_location + "/slideshow.ppt");
            ppt.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        for(int i=0; i<threadCnt ; i++)
        {
            CreatePowerPointDocuments executer = new CreatePowerPointDocuments();
            executer.start();


        }

    }

    public static void Execute(String numTreads,String numIterations)throws IOException
    {

        String noThread = numTreads;
        String noIteration = numIterations;
        threadCnt = Integer.parseInt(noThread);
        iteration=Integer.parseInt(noIteration);
        SlideShow ppt = new SlideShow();
        //add first slide
        Slide s1 = ppt.createSlide();
        //add second slide
        Slide s2 = ppt.createSlide();
        //save changes in a file
        FileOutputStream out = new FileOutputStream(files_deployment_location + "/slideshow.ppt");
        ppt.write(out);
        out.close();

        for(int i=0; i<threadCnt ; i++)
        {
            CreatePowerPointDocuments executer = new CreatePowerPointDocuments();
            executer.start();
        }


    }
}
