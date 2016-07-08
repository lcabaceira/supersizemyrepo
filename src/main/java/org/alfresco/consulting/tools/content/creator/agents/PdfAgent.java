package org.alfresco.consulting.tools.content.creator.agents;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.alfresco.consulting.tools.content.creator.BulkImportManifestCreator;
import org.alfresco.consulting.tools.content.creator.FolderManager;
import org.alfresco.consulting.tools.content.creator.ImageManager;
import org.alfresco.consulting.tools.content.creator.executor.AgentExecutionInfo;
import org.alfresco.consulting.words.RandomWords;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Properties;


public class PdfAgent extends Thread implements Runnable {
    private static final Log logger = LogFactory.getLog(PdfAgent.class);

    private static Font catFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

    public void run() {
        try {
            String fileName = FolderManager.createFileName("_PdfSSMR.pdf");
            String filePath = FolderManager.getFolderLocation();

            RandomWords.init();

            Document document = new Document();
            // Creating the metadata file
            BulkImportManifestCreator.createBulkManifest(fileName, filePath, getDocumentProperties());
            PdfWriter.getInstance(document, new FileOutputStream(filePath + "/" + fileName));
            document.open();

            addMetaData(document);
            addTitlePage(document);
            addContent(document);

            //Random local image
            if (!isCreatingSmallerFiles()) {
                File randomImage = ImageManager.getImageManager().getRandomImage();

                String randomFilePath = randomImage.getAbsolutePath();
                Image localimage1 = Image.getInstance(randomFilePath);
                document.add(localimage1);


                File randomImage2 = ImageManager.getImageManager().getRandomImage();
                String randomFilePath2 = randomImage2.getAbsolutePath();
                Image localimage2 = Image.getInstance(randomFilePath2);
                document.add(localimage2);


                File randomImage3 = ImageManager.getImageManager().getRandomImage();
                String randomFilePath3 = randomImage3.getAbsolutePath();
                Image localimage3 = Image.getInstance(randomFilePath3);
                document.add(localimage3);

                File randomImage4 = ImageManager.getImageManager().getRandomImage();
                String randomFilePath4 = randomImage4.getAbsolutePath();
                Image localimage4 = Image.getInstance(randomFilePath4);
                document.add(localimage4);

                File randomImage5 = ImageManager.getImageManager().getRandomImage();
                String randomFilePath5 = randomImage5.getAbsolutePath();
                Image localimage5 = Image.getInstance(randomFilePath5);
                document.add(localimage5);

                File randomImage6 = ImageManager.getImageManager().getRandomImage();
                String randomFilePath6 = randomImage6.getAbsolutePath();
                Image localimage6 = Image.getInstance(randomFilePath6);
                document.add(localimage6);

                File randomImage7 = ImageManager.getImageManager().getRandomImage();
                String randomFilePath7 = randomImage7.getAbsolutePath();
                Image localimage7 = Image.getInstance(randomFilePath7);
                document.add(localimage7);

                File randomImage8 = ImageManager.getImageManager().getRandomImage();
                String randomFilePath8 = randomImage8.getAbsolutePath();
                Image localimage8 = Image.getInstance(randomFilePath8);
                document.add(localimage8);

                File randomImage9 = ImageManager.getImageManager().getRandomImage();
                String randomFilePath9 = randomImage9.getAbsolutePath();
                Image localimage9 = Image.getInstance(randomFilePath9);
                document.add(localimage9);

                File randomImage10 = ImageManager.getImageManager().getRandomImage();
                String randomFilePath10 = randomImage10.getAbsolutePath();
                Image localimage10 = Image.getInstance(randomFilePath10);
                document.add(localimage10);

                File randomImage11 = ImageManager.getImageManager().getRandomImage();
                String randomFilePath11 = randomImage11.getAbsolutePath();
                Image localimage11 = Image.getInstance(randomFilePath11);
                document.add(localimage11);

                File randomImage12 = ImageManager.getImageManager().getRandomImage();
                String randomFilePath12 = randomImage12.getAbsolutePath();
                Image localimage12 = Image.getInstance(randomFilePath12);
                document.add(localimage12);
            }

            document.close();
        } catch (Exception e) {
            logger.error("Unable to create PDF document", e);
        }

        CompletionTracker.registerCompletion();
    }

    private boolean isCreatingSmallerFiles() {
        return AgentExecutionInfo.getDefaultInstance().isCreatingSmallerFiles();
    }

    private Properties getDocumentProperties() {
        return AgentExecutionInfo.getDefaultInstance().getDocumentProperties();
    }

    // iText allows to add metadata to the PDF which can be viewed in your Adobe Reader under File -> Properties
    private static void addMetaData(Document document) {
        document.addTitle("SSMR Generated PDF");
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, iText, Alfresco, BulkImport");
        document.addAuthor("Super Size My Repo");
        document.addCreator("Luis Cabaceira");
        document.addCreationDate();
    }

    private static void addTitlePage(Document document)
            throws DocumentException {
        Paragraph preface = new Paragraph();
        // We add one empty line
        addEmptyLine(preface, 1);
        // Lets write a big header
        preface.add(new Paragraph(RandomWords.getWords(4), catFont));
        addEmptyLine(preface, 1);
        // Will create: Report generated by: _name, _date
        preface.add(new Paragraph("Report generated by: " + System.getProperty("user.name") + ", " + new Date(), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                smallBold));
        addEmptyLine(preface, 3);
        preface.add(new Paragraph(RandomWords.getWords(500), smallBold));
        addEmptyLine(preface, 8);
        preface.add(new Paragraph("This document was created with the supersizemyrepo tool, beta version.", redFont));
        document.add(preface);
        // Start a new page
        document.newPage();
    }

    private static void addContent(Document document) throws DocumentException {
        Anchor anchor = new Anchor(RandomWords.getWords(2), catFont);
        anchor.setName(RandomWords.getWords(2));
        // Second parameter is the number of the chapter
        Chapter catPart = new Chapter(new Paragraph(anchor), 1);
        Paragraph subPara = new Paragraph(RandomWords.getWords(2), subFont);
        Section subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph(RandomWords.getWords(500)));

        subPara = new Paragraph("Subcategory 2", subFont);
        subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph(RandomWords.getWords(20)));
        subCatPart.add(new Paragraph(RandomWords.getWords(40)));
        subCatPart.add(new Paragraph(RandomWords.getWords(600)));

        // add a list
        createList(subCatPart);
        Paragraph paragraph = new Paragraph();
        addEmptyLine(paragraph, 5);
        subCatPart.add(paragraph);
        // add a table
        createTable(subCatPart);
        // now add all this to the document
        document.add(catPart);
        // Next section
        anchor = new Anchor(RandomWords.getWords(2), catFont);
        anchor.setName(RandomWords.getWords(4));
        // Second parameter is the number of the chapter
        catPart = new Chapter(new Paragraph(anchor), 1);
        subPara = new Paragraph(RandomWords.getWords(1), subFont);
        subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph(RandomWords.getWords(300)));
        // now add all this to the document
        document.add(catPart);

    }

    private static void createTable(Section subCatPart)
            throws BadElementException {
        PdfPTable table = new PdfPTable(3);

        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);

        PdfPCell c1 = new PdfPCell(new Phrase(RandomWords.getWords(3)));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase(RandomWords.getWords(3)));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase(RandomWords.getWords(3)));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);

        table.addCell("1.0");
        table.addCell("1.1");
        table.addCell("1.2");
        table.addCell("2.1");
        table.addCell("2.2");
        table.addCell("2.3");

        subCatPart.add(table);

    }

    private static void createList(Section subCatPart) {
        List list = new List(true, false, 10);
        list.add(new ListItem(RandomWords.getWords(2)));
        list.add(new ListItem(RandomWords.getWords(2)));
        list.add(new ListItem(RandomWords.getWords(2)));
        subCatPart.add(list);
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
