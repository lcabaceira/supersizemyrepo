package org.alfresco.consulting.tools.content.creator.executor;

public class ExecutionConfiguration {
    private static final int DEFAULT_MAX_FILES_PER_FOLDER = 50;
    private final int maxFiles;
    private final int numThreads;
    private final int threadPoolSize;
    private final String deployPath;
    private final String images;

    public ExecutionConfiguration(int maxFiles, int numThreads, int threadPoolSize, String deployPath, String images) {
        this.maxFiles = maxFiles;
        this.numThreads = numThreads;
        this.threadPoolSize = threadPoolSize;
        this.deployPath = deployPath;
        this.images = images;
    }

    public ExecutionConfiguration(int numThreads, int threadPoolSize, String deployPath, String images) {
        this(DEFAULT_MAX_FILES_PER_FOLDER, numThreads, threadPoolSize, deployPath, images);
    }

    public int getMaxFiles() {
        return maxFiles;
    }

    public int getNumThreads() {
        return numThreads;
    }

    public int getThreadPoolSize() {
        return threadPoolSize;
    }

    public String getDeployPath() {
        return deployPath;
    }

    public String getImages() {
        return images;
    }
}
