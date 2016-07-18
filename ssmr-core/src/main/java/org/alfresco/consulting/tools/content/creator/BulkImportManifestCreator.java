package org.alfresco.consulting.tools.content.creator;

import org.alfresco.consulting.locator.PropertiesLocator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

public class BulkImportManifestCreator {
    private static final Log logger = LogFactory.getLog(BulkImportManifestCreator.class);

    private static Properties props = PropertiesLocator.getProperties("super-size-my-repo.properties");
    private static String files_deployment_location = props.getProperty("files_deployment_location");
    private static Properties properties1 = new Properties();

    /**
     * No-arguments constructor.
     */
    public BulkImportManifestCreator() {
    }


    /**
     * Get traditional properties in name=value format.
     *
     * @param SSMR_file name of the file target for meta-data manifest creation
     * @return String execution log
     */
    public static String createBulkManifest(final String SSMR_file, String path, Properties properties) {
        if (path != null && !path.equals("")) {
            files_deployment_location = path;
        }

        // final Properties properties = new Properties();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String date = dateFormat.format(cal.getTime());

        FileOutputStream outStream = null;
        String metaDatafileName = SSMR_file + ".metadata.properties.xml";
        String metaDatafilePath = files_deployment_location + "/" + metaDatafileName;
        try {
            outStream = new FileOutputStream(metaDatafilePath);
        } catch (FileNotFoundException e) {
            logger.error("Unable to save metadata file: " + metaDatafileName, e);
        }

        if (!(properties == null)) {
            createMetaDataXmlFile(properties, outStream);
        } else {
            properties1.setProperty("type", "cm:content");
            properties1.setProperty("aspects", "cm:versionable,cm:dublincore");
            properties1.setProperty("cm:title", "Daily Report document : " + date);
            properties1.setProperty("cm:description", "");
            properties1.setProperty("cm:author", "SuperSizeMyRepo");
            properties1.setProperty("cm:publisher", "SuperSizeMyRepo");
            properties1.setProperty("cm:contributor", "SuperSizeMyRepo");
            properties1.setProperty("cm:type", "default_plus_dubincore_aspect");
            properties1.setProperty("cm:identifier", SSMR_file);
            properties1.setProperty("cm:source", "SuperSizeMyRepo");
            properties1.setProperty("cm:coverage", "General");
            properties1.setProperty("cm:rights", "");
            properties1.setProperty("cm:subject", "Metadata file created with SSMR");

            createMetaDataXmlFile(properties1, outStream);
        }

        return "Created Manifest for " + SSMR_file + ": " + files_deployment_location + "/" + metaDatafileName;

    }

    public static void createMetaDataXmlFile(final Properties sourceProperties, final OutputStream out) {

        try {
            sourceProperties.storeToXML(out, "To use with Alfresco in-place-bulk importer");
        } catch (IOException ioEx) {
            logger.error("ERROR trying to store properties in XML!", ioEx);
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                logger.error("Unable to properly close the metadata XML file", e);
            }
        }
    }

}
