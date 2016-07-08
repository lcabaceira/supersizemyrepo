package org.alfresco.consulting.tools.content.creator.agents;

import org.alfresco.consulting.tools.content.creator.BulkImportManifestCreator;
import org.alfresco.consulting.tools.content.creator.CustomXWPFDocument;
import org.alfresco.consulting.tools.content.creator.FolderManager;
import org.alfresco.consulting.tools.content.creator.ImageManager;
import org.alfresco.consulting.tools.content.creator.executor.AgentExecutionInfo;
import org.alfresco.consulting.words.RandomWords;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

public class MSWordAgent extends Thread implements Runnable {
    private static final Log logger = LogFactory.getLog(MSWordAgent.class);

    public void run() {
        RandomWords.init();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String date = dateFormat.format(cal.getTime());
        // Create a document file
        CustomXWPFDocument document = null;
        try {
            document = new CustomXWPFDocument();
        } catch (IOException e) {
            logger.error("Unable to create empty word document", e);
        }

        // insert doc details
        // Createa a para -1
        assert document != null;
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
        paragraphSixRunOne.addBreak();
        paragraphSixRunOne.setBold(true);
        paragraphSixRunOne.setText("     ");
        if (!isCreatingSmallerFiles()) {
            // Adding a file
            try {
                // Working addPicture Code below...
                XWPFParagraph paragraphX = document.createParagraph();
                paragraphX.setAlignment(ParagraphAlignment.CENTER);
                File randomImage = ImageManager.getImageManager().getRandomImage();
                // adding local random image
                String blipId;
                blipId = paragraphX.getDocument().addPictureData(new FileInputStream(randomImage), Document.PICTURE_TYPE_JPEG);
                document.createPicture(blipId, document.getNextPicNameNumber(Document.PICTURE_TYPE_JPEG), 800, 600);
            } catch (InvalidFormatException | FileNotFoundException e) {
                logger.error("Unable to add image to word document", e);
            }
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

        XWPFParagraph paragraphY = document.createParagraph();
        paragraphY.setAlignment(ParagraphAlignment.CENTER);

        FileOutputStream outStream = null;
        try {
            String fileName = cal.getTimeInMillis() + "_MSWordSSMR.docx";
            String folderLocation = FolderManager.getFolderLocation();
            outStream = new FileOutputStream(folderLocation + "/" + fileName);
            BulkImportManifestCreator.createBulkManifest(fileName, folderLocation, getDocumentProperties());
        } catch (FileNotFoundException e) {
            logger.error("Unable to create manifest file", e);
        }

        try {
            if (outStream != null) {
                document.write(outStream);
                outStream.close();
            }
        } catch (IOException e) {
            logger.error("Unable to save Word document", e);
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
