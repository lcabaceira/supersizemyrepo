package org.alfresco.consulting.tools.content.creator.agents;
import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.Random;

import org.alfresco.consulting.locator.PropertiesLocator;
import org.alfresco.consulting.tools.content.creator.BulkImportManifestCreator;
import org.alfresco.consulting.tools.content.creator.CustomXWPFDocument;
import org.alfresco.consulting.words.RandomWords;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.*;

public class MSWordAgent extends Thread implements Runnable {
    /**
     * @param args
     * @throws IOException
     */

    private static String files_deployment_location;
    private static String images_location;

    public MSWordAgent(String _files_deployment_location, String _images_location) {
        this.files_deployment_location = _files_deployment_location;
        this.images_location = _images_location;
    }

    public void run(){

        RandomWords.init();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String date = dateFormat.format(cal.getTime());
        // Create a document file
        CustomXWPFDocument document = null;
        try {
            document = new CustomXWPFDocument();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // insert doc details
        // Createa a para -1
        XWPFParagraph paragraphOne = document.createParagraph();
        paragraphOne.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun paragraphOneRunOne = paragraphOne.createRun();
        paragraphOneRunOne.setBold(true);
        paragraphOneRunOne.setFontSize(20);
        paragraphOneRunOne.setFontFamily("Verdana");
        paragraphOneRunOne.setColor("000070");
        paragraphOneRunOne.setText("Daily Status Report");
        // Createa a para -2
        XWPFParagraph paragraphTwo = document.createParagraph();
        paragraphTwo.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun paragraphTwoRunOne = paragraphTwo.createRun();
        paragraphTwoRunOne.setFontSize(12);
        paragraphTwoRunOne.setFontFamily("Verdana");
        paragraphTwoRunOne.setColor("000070");
        paragraphTwoRunOne.setText(date);
        paragraphTwoRunOne.addBreak();
        // Createa a para -3
        XWPFParagraph paragraphThree = document.createParagraph();
        paragraphThree.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun paragraphThreeRunOne = paragraphThree.createRun();
        paragraphThreeRunOne.setFontSize(14);
        paragraphThreeRunOne.setFontFamily("Verdana");
        paragraphThreeRunOne.setColor("000070");

        paragraphThreeRunOne.setText(date);
        paragraphThreeRunOne.addBreak();
        // Createa a para -4
        XWPFParagraph paragraphFour = document.createParagraph();
        paragraphFour.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun paragraphFourRunOne = paragraphFour.createRun();
        paragraphFourRunOne.setBold(true);
        paragraphFourRunOne.setUnderline(UnderlinePatterns.SINGLE);
        paragraphFourRunOne.setFontSize(10);
        paragraphFourRunOne.setFontFamily("Verdana");
        paragraphFourRunOne.setColor("000070");
        paragraphFourRunOne.setText("Benchmark Document with Random Words");
        // insert doc details end
        XWPFParagraph paragraphFive = document.createParagraph();
        paragraphFive.setAlignment(ParagraphAlignment.RIGHT);
        XWPFRun paragraphFiveRunOne = paragraphFive.createRun();
        paragraphFiveRunOne.addBreak();
        paragraphFourRunOne.setBold(true);
        paragraphFourRunOne.setUnderline(UnderlinePatterns.SINGLE);
        paragraphFourRunOne.setFontSize(10);
        paragraphFourRunOne.setFontFamily("Verdana");
        paragraphFourRunOne.setColor("000070");
        paragraphFourRunOne.setText(" Created with SuperSizeMyRepo");

        XWPFParagraph paragraphSix = document.createParagraph();
        paragraphFive.setAlignment(ParagraphAlignment.RIGHT);
        XWPFRun paragraphSixRunOne = paragraphSix.createRun();
        paragraphFiveRunOne.addBreak();
        paragraphFourRunOne.setBold(true);
        paragraphFourRunOne.setText("     ");
        // Adding a file
        try {
            // Working addPicture Code below...
            XWPFParagraph paragraphX = document.createParagraph();
            paragraphX.setAlignment(ParagraphAlignment.CENTER);
            File imagesFolder = new File(images_location);
            File[] files =   imagesFolder.listFiles();
            int size = files.length;
            Random rand = new Random();
            int number = rand.nextInt(size);
            File randomImage = files[number];
            // adding local random image
            String blipId = null;
            blipId = paragraphX.getDocument().addPictureData(new FileInputStream(randomImage), Document.PICTURE_TYPE_JPEG);
            document.createPicture(blipId,document.getNextPicNameNumber(Document.PICTURE_TYPE_JPEG),800, 600);
        } catch (InvalidFormatException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }   catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        XWPFParagraph p3 = document.createParagraph();
        p3.setWordWrap(true);
        p3.setPageBreak(true);
        //p3.setAlignment(ParagraphAlignment.DISTRIBUTE);
        p3.setAlignment(ParagraphAlignment.BOTH);
        p3.setSpacingLineRule(LineSpacingRule.EXACT);

        p3.setIndentationFirstLine(20);
        XWPFRun r4 = p3.createRun();
        r4.setTextPosition(20);
        r4.setText(RandomWords.getWords(6000));
        r4.addBreak(BreakType.PAGE);
        r4.setText(RandomWords.getWords(6000));
        r4.setItalic(true);
        //This would imply that this break shall be treated as a simple line break, and break the line after that word:
        XWPFRun r5 = p3.createRun();
        r5.setTextPosition(-10);
        r5.setText("For what dreams may come");
        r5.addCarriageReturn();
        r5.setText(RandomWords.getWords(4000));
        r5.addBreak();
        r5.setText(RandomWords.getWords(4000));
        r5.addBreak(BreakClear.ALL);
        r5.setText(RandomWords.getWords(4000));

        try {
            // Working addPicture Code below...
            XWPFParagraph paragraphY = document.createParagraph();
            paragraphY.setAlignment(ParagraphAlignment.CENTER);
            // adding http image
            InputStream is = null;
            is = new URL("http://lorempixel.com/g/800/600/").openStream();
            String blipIdw = paragraphY.getDocument().addPictureData(is,Document.PICTURE_TYPE_JPEG);
            document.createPicture(blipIdw,document.getNextPicNameNumber(Document.PICTURE_TYPE_JPEG),800, 600);
        } catch (InvalidFormatException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        FileOutputStream outStream = null;

        try {
            double x = Math.random();
            String fileName =  cal.getTimeInMillis() +"_MSWordSSMR.docx";
            String filePath = files_deployment_location + "/" + fileName;

            BulkImportManifestCreator.createBulkManifest(fileName,files_deployment_location);
            outStream = new FileOutputStream(filePath);



        } catch (Exception e) {
            System.out.println("First Catch");
            e.printStackTrace();
        }
        try {
            document.write(outStream);
            outStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("Second Catch");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Third Catch");
            e.printStackTrace();
        }

    }

}
