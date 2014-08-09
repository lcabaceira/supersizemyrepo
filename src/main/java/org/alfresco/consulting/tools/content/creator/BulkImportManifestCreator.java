package org.alfresco.consulting.tools.content.creator;

import org.alfresco.consulting.locator.PropertiesLocator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

public class BulkImportManifestCreator
{
    private static Properties props = PropertiesLocator.getProperties("alfresco-consulting.properties");
    private static String files_deployment_location = props.getProperty("files_deployment_location");
    /** No-arguments constructor. */
    public BulkImportManifestCreator() {}

    /**
     * Get traditional properties in name=value format.
     *
     * @param filePathAndName Path and name of properties file (without the
     *    .properties extension).
     * @return Properties read in from provided file.
     */
    public Properties loadTraditionalProperties(
            final String filePathAndName)
    {
        final Properties properties = new Properties();
        try
        {
            final FileInputStream in = new FileInputStream(filePathAndName);
            properties.load(in);
            in.close();
        }
        catch (FileNotFoundException fnfEx)
        {
            System.err.println("Could not read properties from file " + filePathAndName);
        }
        catch (IOException ioEx)
        {
            System.err.println(
                    "IOException encountered while reading from " + filePathAndName);
        }
        return properties;
    }


    /**
     * Get traditional properties in name=value format.
     *
     * @param SSMR_file  name of the file target for meta-data manifest creation
     * @return String execution log
     */
    public static String createBulkManifest(final String SSMR_file)
    {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String date = dateFormat.format(cal.getTime());
        final Properties properties = new Properties();
        properties.setProperty("type", "cm:content");
        properties.setProperty("aspects", "cm:versionable,cm:dublincore");
        properties.setProperty("cm:title", "Daily Report document : " + date );
        properties.setProperty("cm:description", "");
        properties.setProperty("cm:created", "Today");
        properties.setProperty("cm:author", "SuperSizeMyRepo");
        properties.setProperty("cm:publisher", "SuperSizeMyRepo");
        properties.setProperty("cm:contributor", "SuperSizeMyRepo");
        properties.setProperty("cm:type", "default_plus_dubincore_aspect");
        properties.setProperty("cm:identifier", SSMR_file);
        properties.setProperty("cm:source", "SuperSizeMyRepo");
        properties.setProperty("cm:coverage", "General");
        properties.setProperty("cm:rights", "");
        properties.setProperty("cm:subject", "Metadata file created with SSMR");
        FileOutputStream outStream = null;
        String metaDatafileName =  SSMR_file + ".metadata.properties.xml";
        String metaDatafilePath = files_deployment_location + "/" + metaDatafileName;
        try {
            outStream = new FileOutputStream(metaDatafilePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        createMetaDataXmlFile(properties,outStream);
        return "Created Manifest for " + SSMR_file + ": " + files_deployment_location + "/" + metaDatafileName;

    }

    public static void  createMetaDataXmlFile(final Properties sourceProperties,final OutputStream out)
    {

        try
        {
            sourceProperties.storeToXML(out, "To use with Alfresco in-place-bulk importer");
        }
        catch (IOException ioEx)
        {
            System.err.println("ERROR trying to store properties in XML!");
        }
    }

    /**
     * Store provided properties in XML format.
     *
     * @param sourceProperties Properties to be stored in XML format.
     * @param out OutputStream to which to write XML formatted properties.
     */
    public void storeXmlProperties(final Properties sourceProperties,final OutputStream out)
    {
        try
        {
            sourceProperties.storeToXML(out, "This is easy!");
        }
        catch (IOException ioEx)
        {
            System.err.println("ERROR trying to store properties in XML!");
        }
    }

    /**
     * Store provided properties in XML format to provided file.
     *
     * @param sourceProperties Properties to be stored in XML format.
     * @param pathAndFileName Path and name of file to which XML-formatted
     *    properties will be written.
     */
    public void storeXmlPropertiesToFile(
            final Properties sourceProperties,
            final String pathAndFileName)
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(pathAndFileName);
            storeXmlProperties(sourceProperties, fos);
            fos.close();
        }
        catch (FileNotFoundException fnfEx)
        {
            System.err.println("ERROR writing to " + pathAndFileName);
        }
        catch (IOException ioEx)
        {
            System.err.println(
                    "ERROR trying to write XML properties to file " + pathAndFileName);
        }
    }

    /**
     * Runs main examples.
     *
     * @param arguments Command-line arguments; none anticipated.
     */
    public static void main(final String[] arguments)
    {
        //final BulkImportManifestCreator me = new BulkImportManifestCreator();
        //final Properties inputProperties = me.loadTraditionalProperties();
        //me.storeXmlPropertiesToFile(inputProperties, "examples-xml.properties");
    }
}
