package org.alfresco.consulting.tools.content.creator.agents;


import org.alfresco.consulting.tools.content.creator.ImageManager;
import org.alfresco.consulting.words.RandomWords;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;

import java.io.*;

public class MSExcelAgent extends AgentWithFileWriter implements Runnable {
    private static final Log logger = LogFactory.getLog(MSExcelAgent.class);
    private HSSFWorkbook workbook;

    protected Log getLogger() {
        return logger;
    }

    protected void createFile() {
        workbook = new HSSFWorkbook();

        addBannerSheetToWorkbook();

        // create a new sheet
        Sheet s = workbook.createSheet();
        // declare a row object reference
        Row r;
        // declare a cell object reference
        Cell c;
        // create 3 cell styles
        CellStyle cs = workbook.createCellStyle();
        CellStyle cs2 = workbook.createCellStyle();
        CellStyle cs3 = workbook.createCellStyle();
        DataFormat df = workbook.createDataFormat();
        // create 2 fonts objects
        Font f = workbook.createFont();
        Font f2 = workbook.createFont();

        //set font 1 to 12 point type
        f.setFontHeightInPoints((short) 12);
        //make it blue
        f.setColor((short) 0xc);
        // make it bold
        //arial is the default font
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        //set font 2 to 10 point type
        f2.setFontHeightInPoints((short) 10);
        //make it red
        f2.setColor(Font.COLOR_RED);
        //make it bold
        f2.setBoldweight(Font.BOLDWEIGHT_BOLD);

        f2.setStrikeout(true);

        //set cell stlye
        cs.setFont(f);
        //set the cell format
        cs.setDataFormat(df.getFormat("#,##0.0"));

        //set a thin border
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        //fill w fg fill color
        cs2.setFillPattern(CellStyle.SOLID_FOREGROUND);
        //set the cell format to text see DataFormat for a full list
        cs2.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));

        // set the font
        cs2.setFont(f2);

        // set the sheet name in Unicode
        workbook.setSheetName(0, "SuperSizeMyRepo ");

        // create a sheet with 30 rows (0-29)
        int rownum;
        for (rownum = (short) 0; rownum < 30; rownum++) {
            // create a row
            r = s.createRow(rownum);
            // on every other row
            if ((rownum % 2) == 0) {
                // make the row height bigger  (in twips - 1/20 of a point)
                r.setHeight((short) 0x249);
            }

            //r.setRowNum(( short ) rownum);
            // create 10 cells (0-9) (the += 2 becomes apparent later
            for (short cellnum = (short) 0; cellnum < 10; cellnum += 2) {
                // create a numeric cell
                c = r.createCell(cellnum);
                // do some goofy math to demonstrate decimals
                c.setCellValue(rownum * 10000 + cellnum
                        + (((double) rownum / 1000)
                        + ((double) cellnum / 10000)));

                // create a string cell (see why += 2 in the
                c = r.createCell((short) (cellnum + 1));

                // on every other row
                if ((rownum % 2) == 0) {
                    // set this cell to the first cell style we defined
                    c.setCellStyle(cs);
                    // set the cell's string value to "Test"
                    c.setCellValue(RandomWords.getWords(10));
                } else {
                    c.setCellStyle(cs2);
                    // set the cell's string value to "\u0422\u0435\u0441\u0442"
                    c.setCellValue(RandomWords.getWords(5));
                }

                // make this column a bit wider
                s.setColumnWidth((short) (cellnum + 1), (short) ((50 * 8) / ((double) 1 / 20)));
            }
        }

        //draw a thick black border on the row at the bottom using BLANKS
        // advance 2 rows
        rownum++;
        rownum++;

        r = s.createRow(rownum);

        // define the third style to be the default
        // except with a thick black border at the bottom
        cs3.setBorderBottom(CellStyle.BORDER_THICK);

        //create 50 cells
        for (short cellnum = (short) 0; cellnum < 50; cellnum++) {
            //create a blank type cell (no value)
            c = r.createCell(cellnum);
            // set it to the thick black border style
            c.setCellStyle(cs3);
        }
        //end draw thick black border

        writeOutFiles();
    }

    @Override
    protected void performWriteOfFile(FileOutputStream out) throws IOException {
        workbook.write(out);
    }

    private void addBannerSheetToWorkbook() {
        if (!isCreatingSmallerFiles()) {
            HSSFSheet my_sheet = workbook.createSheet("SuperSizeMyRepoBanner");
         /* Read the input image into InputStream */
            File randomImage = ImageManager.getImageManager().getRandomImage();

            int my_picture_id = 0;
            try {
                InputStream my_banner_image = new FileInputStream(randomImage);
                /* Convert Image to byte array */
                byte[] bytes = IOUtils.toByteArray(my_banner_image);
                /* Add Picture to workbook and get a index for the picture */
                my_picture_id = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
                /* Close Input Stream */
                my_banner_image.close();
            } catch (IOException e) {
                getLogger().error("Unable to copy image " + randomImage + " into Excel document", e);
            }
            try {
                /* Create the drawing container */
                HSSFPatriarch drawing = my_sheet.createDrawingPatriarch();
                /* Create an anchor point */
                ClientAnchor my_anchor = new HSSFClientAnchor();
                /* Define top left corner, and we can resize picture suitable from there */
                my_anchor.setCol1(2);
                my_anchor.setRow1(1);
                /* Invoke createPicture and pass the anchor point and ID */
                HSSFPicture my_picture = drawing.createPicture(my_anchor, my_picture_id);
                /* Call resize method, which resizes the image */
                my_picture.resize();
            } catch (Exception e) {
                getLogger().error("Unable to edit the image from " + randomImage + ".", e);
            }
        /* Write changes to the workbook */
        }
    }

    protected String getFileNameSuffix() {
        return "_MSExcelSSMR.xls";
    }
}
