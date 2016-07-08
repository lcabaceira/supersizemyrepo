package org.alfresco.consulting.tools.content.creator.executor;

public class ExecutionConfiguration {
    private final String maxFiles;
    private final int numThreads;
    private final int threadPoolSize;
    private final String deployPath;
    private final String images;

    public ExecutionConfiguration(String maxFiles, int numThreads, int threadPoolSize, String deployPath, String images) {
        this.maxFiles = maxFiles;
        this.numThreads = numThreads;
        this.threadPoolSize = threadPoolSize;
        this.deployPath = deployPath;
        this.images = images;
    }

    public String getMaxFiles() {
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
