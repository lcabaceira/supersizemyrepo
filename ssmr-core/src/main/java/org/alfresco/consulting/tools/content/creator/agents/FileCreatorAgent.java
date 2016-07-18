package org.alfresco.consulting.tools.content.creator.agents;

import org.alfresco.consulting.tools.content.creator.BulkImportManifestCreator;
import org.alfresco.consulting.tools.content.creator.FolderManager;
import org.alfresco.consulting.tools.content.creator.executor.parameters.AgentExecutionInfo;
import org.apache.commons.logging.Log;

import java.util.Properties;

public abstract class FileCreatorAgent extends Thread implements Runnable {
    private String fileName;
    private String folderLocation;

    protected abstract Log getLogger();

    public final void run() {
        createFile();

        CompletionTracker.registerCompletion();
    }

    protected abstract void createFile();

    void populateFileInformation() {
        fileName = FolderManager.createFileName(getFileNameSuffix());
        folderLocation = FolderManager.getFolderLocation();
    }

    String getPathOfFile() {
        return getFolderLocation() + "/" + getFileName();
    }

    void createBulkManifest() {
        BulkImportManifestCreator.createBulkManifest(getFileName(), getFolderLocation(), getDocumentProperties());
    }

    protected abstract String getFileNameSuffix();

    private Properties getDocumentProperties() {
        return AgentExecutionInfo.getDefaultInstance().getDocumentProperties();
    }

    String getFileName() {
        return fileName;
    }

    boolean isCreatingSmallerFiles() {
        return AgentExecutionInfo.getDefaultInstance().isCreatingSmallerFiles();
    }

    String getFolderLocation() {
        return folderLocation;
    }
}
