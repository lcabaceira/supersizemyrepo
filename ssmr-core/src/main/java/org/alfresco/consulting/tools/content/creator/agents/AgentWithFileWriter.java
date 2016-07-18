package org.alfresco.consulting.tools.content.creator.agents;

import java.io.FileOutputStream;
import java.io.IOException;

abstract class AgentWithFileWriter extends FileCreatorAgent {
    void writeOutFiles() {
        try {
            populateFileInformation();
            FileOutputStream out = new FileOutputStream(getPathOfFile());
            createBulkManifest();
            performWriteOfFile(out);
            out.close();
        } catch (IOException e) {
            getLogger().error("Unable to save document: " + getPathOfFile(), e);
        }
    }

    protected abstract void performWriteOfFile(FileOutputStream out) throws IOException;
}
