package org.alfresco.consulting.tools.content.creator.executor;

import com.google.common.base.Stopwatch;
import org.alfresco.consulting.tools.content.creator.FolderManager;
import org.alfresco.consulting.tools.content.creator.ImageManager;
import org.alfresco.consulting.tools.content.creator.agents.*;
import org.alfresco.consulting.tools.content.creator.executor.parameters.AgentExecutionInfo;
import org.alfresco.consulting.tools.content.creator.executor.parameters.ExecutorParameters;
import org.alfresco.consulting.words.RandomWords;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class SuperSizeExecutor {
    private static Log logger = LogFactory.getLog(MainExecutor.class);

    private final ExecutorParameters executorParameters;
    private Stopwatch stopwatch;
    private ExecutorService executor;

    SuperSizeExecutor(ExecutorParameters executorParameters) {
        this.executorParameters = executorParameters;
    }

    void performExecution() {
        stopwatch = Stopwatch.createStarted();

        initializeSharedServices();
        displayStartMessages();

        executeInThreads();

        stopwatch.stop();

        displayTiming();
    }

    private void executeInThreads() {
        executor = Executors.newFixedThreadPool(getThreadPoolSize());

        poolThreadsForExecution();
        stopAcceptingNewThreadsInPool();
        waitForThreadsToFinish();

        logger.info("Finished all threads");
    }

    private void waitForThreadsToFinish() {
        try {
            while (!executor.isTerminated()) {
                executor.awaitTermination(5, TimeUnit.SECONDS);
                displayStatusMessage();
            }
        } catch (InterruptedException e) {
            logger.error("Error executing threads.", e);
        }
    }

    private void displayStatusMessage() {
        final int numberOfCompletions = CompletionTracker.getNumberOfCompletions();
        if (numberOfCompletions > 0)
            logger.info("Completed " + numberOfCompletions + " of " + getNumThreads() +
                    " at a rate of about " + stopwatch.elapsed(TimeUnit.MILLISECONDS) / numberOfCompletions +
                    " milliseconds per document.");
        else
            logger.debug("No items completed yet.");
    }

    private void stopAcceptingNewThreadsInPool() {
        executor.shutdown();
    }

    private void poolThreadsForExecution() {
        List<AgentExecutor> agentExecutors = getSelectedAgentExecutors();

        for (int i = 0; i < getNumThreads(); i++) {
            final int index = i % agentExecutors.size();
            agentExecutors.get(index).executeAgent();
        }
    }

    private List<AgentExecutor> getSelectedAgentExecutors() {
        List<AgentExecutor> agentExecutors = new ArrayList<>(5);

        if (isIncludePPT()) agentExecutors.add(new PPTAgentExecutor());
        if (isIncludePDF()) agentExecutors.add(new PDFAgentExecutor());
        if (isIncludeXLS()) agentExecutors.add(new XLSAgentExecutor());
        if (isIncludeDOC()) agentExecutors.add(new DOCAgentExecutor());
        if (isIncludeJPEG()) agentExecutors.add(new JPEGAgentExecutor());

        return agentExecutors;
    }

    private void displayTiming() {
        logger.info("Total runtime is " + stopwatch + ".");
        final long avgTimePerDocInMillis = stopwatch.elapsed(TimeUnit.MILLISECONDS) / getNumThreads();
        logger.info("Average time per document is " + avgTimePerDocInMillis + " milliseconds.");
    }

    private void displayStartMessages() {
        logger.info("### maxFiles: " + getMaxFiles());
        logger.info("### num_Threads: " + getNumThreads());
        logger.info("### threadPoolSize: " + getThreadPoolSize());
        logger.info("### deployPath: " + getDeployPath());
        logger.info("### images: " + getImages());

        logger.info("### pdf: " + isIncludePDF());
        logger.info("### ppt: " + isIncludePPT());
        logger.info("### xls: " + isIncludeXLS());
        logger.info("### doc: " + isIncludeDOC());
        logger.info("### jpg: " + isIncludeJPEG());
    }

    private void initializeSharedServices() {
        AgentExecutionInfo.setDefaultInstance(executorParameters.getAgentExecutionInfo());
        FolderManager.getInstance().setMaxFilesPerFolder(Integer.valueOf(getMaxFiles()));
        FolderManager.getInstance().setRootDeploymentLocation(getDeployPath());
        ImageManager.initializeImageManager(getImages());

        initializeRandomWords();
    }

    private void initializeRandomWords() {
        if (executorParameters.getAgentExecutionInfo().isCreatingSmallerFiles())
            RandomWords.useFewerWords();

        RandomWords.init();
    }

    private String getMaxFiles() {
        return executorParameters.getExecutionConfiguration().getMaxFiles();
    }

    private int getNumThreads() {
        return executorParameters.getExecutionConfiguration().getNumThreads();
    }

    private int getThreadPoolSize() {
        return executorParameters.getExecutionConfiguration().getThreadPoolSize();
    }

    private String getDeployPath() {
        return executorParameters.getExecutionConfiguration().getDeployPath();
    }

    private String getImages() {
        return executorParameters.getExecutionConfiguration().getImages();
    }

    private Boolean isIncludePDF() {
        return executorParameters.getTypesToInclude().getPdf();
    }

    private Boolean isIncludePPT() {
        return executorParameters.getTypesToInclude().getPpt();
    }

    private Boolean isIncludeXLS() {
        return executorParameters.getTypesToInclude().getXls();
    }

    private Boolean isIncludeDOC() {
        return executorParameters.getTypesToInclude().getDoc();
    }

    private Boolean isIncludeJPEG() {
        return executorParameters.getTypesToInclude().getJpg();
    }

    private class PPTAgentExecutor implements AgentExecutor {
        @Override
        public void executeAgent() {
            executor.execute(new MSPowerPointAgent());
        }
    }

    private class PDFAgentExecutor implements AgentExecutor {
        @Override
        public void executeAgent() {
            executor.execute(new PdfAgent());
        }
    }

    private class XLSAgentExecutor implements AgentExecutor {
        @Override
        public void executeAgent() {
            executor.execute(new MSExcelAgent());
        }
    }

    private class DOCAgentExecutor implements AgentExecutor {
        @Override
        public void executeAgent() {
            executor.execute(new MSWordAgent());
        }
    }

    private class JPEGAgentExecutor implements AgentExecutor {
        @Override
        public void executeAgent() {
            executor.execute(new JpgAgent());
        }
    }
}
