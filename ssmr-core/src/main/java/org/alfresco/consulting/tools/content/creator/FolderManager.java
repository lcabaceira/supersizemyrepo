package org.alfresco.consulting.tools.content.creator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.util.Calendar;

public class FolderManager {
    private static final Log logger = LogFactory.getLog(FolderManager.class);

    private static final FolderManager _instance = new FolderManager();
    private String rootDeploymentLocation;
    private Integer maxFilesPerFolder;
    private String folderLocation;
    private Integer maxPathLength;

    private FolderManager() {
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().startsWith("windows")) {
            maxPathLength = 180;
        }
    }

    public static FolderManager getInstance() {
        return _instance;
    }

    public static String getFolderLocation() {
        return getInstance().getCurrentFolderLocation();
    }

    public static String createFileName(String suffix) {
        return createUniqueName() + suffix;
    }

    private synchronized String getCurrentFolderLocation() {
        if (folderLocation == null) {
            folderLocation = getRootDeploymentLocation();
        }
        else {
            File deploymentFolder = new File(folderLocation);
            File[] deploymentFiles = deploymentFolder.listFiles();
            assert deploymentFiles != null;
            int total_deployment_size = deploymentFiles.length;
            // checking if the deployment location is full (more than max_files_per_folder files)
            if (total_deployment_size > maxFilesPerFolder) {
                final String uniqueName = createUniqueName();
                folderLocation += "/" + uniqueName;
                if (maxPathLength != null && folderLocation.length() >= maxPathLength) {
                    folderLocation = rootDeploymentLocation + "/" + uniqueName;
                }
                boolean success = (new File(folderLocation)).mkdirs();
                if (!success) {
                    logger.warn("Failed to create directory " + folderLocation);
                }
            }
        }

        return folderLocation;
    }

    private static String createUniqueName() {
        Calendar calendar = Calendar.getInstance();

        return String.valueOf(calendar.get(Calendar.MINUTE)) + String.valueOf(calendar.get(Calendar.SECOND)) + String.valueOf(calendar.get(Calendar.MILLISECOND));
    }

    private String getRootDeploymentLocation() {
        return rootDeploymentLocation;
    }

    public void setRootDeploymentLocation(String rootDeploymentLocation) {
        this.rootDeploymentLocation = rootDeploymentLocation;
        folderLocation = null;
    }

    public void setMaxFilesPerFolder(Integer maxFilesPerFolder) {
        this.maxFilesPerFolder = maxFilesPerFolder;
    }
}
