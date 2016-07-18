package org.alfresco.consulting.tools.content.creator.agents;

import org.alfresco.consulting.tools.content.creator.ImageManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.util.IOUtils;

import java.io.*;

public class JpgAgent extends AgentWithFileWriter {
    private static final Log logger = LogFactory.getLog(JpgAgent.class);
    private InputStream randomImageStream;

    @Override
    protected Log getLogger() {
        return logger;
    }

    @Override
    protected void createFile() {
        try {
            randomImageStream = new FileInputStream(getRandomImage());
            writeOutFiles();
            randomImageStream.close();
        } catch (IOException e) {
            getLogger().error("Unable to read image file", e);
        }
    }

    private File getRandomImage() {
        return ImageManager.getImageManager().getRandomImage();
    }

    @Override
    protected void performWriteOfFile(FileOutputStream out) throws IOException {
        IOUtils.copy(randomImageStream, out);
    }

    @Override
    protected String getFileNameSuffix() {
        return "_JpegImageSSMR.jpg";
    }

}
