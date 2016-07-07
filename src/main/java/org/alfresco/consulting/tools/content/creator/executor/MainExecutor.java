package org.alfresco.consulting.tools.content.creator.executor;


import org.alfresco.consulting.locator.PropertiesLocator;
import org.alfresco.consulting.tools.content.creator.FolderManager;
import org.alfresco.consulting.tools.content.creator.ImageManager;
import org.alfresco.consulting.tools.content.creator.agents.*;
import org.alfresco.consulting.words.RandomWords;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainExecutor {

    private static String threadPoolSize;
    private static String num_Threads;
    private static String files_deployment_location;
    private static String images_location;
    private static String max_files_per_folder;
    private static Properties properties;

    private static Boolean pdf = true;
    private static Boolean ppt = true;
    private static Boolean xls = true;
    private static Boolean doc = true;
    private static Boolean jpg = true;


    public static void main(String[] args) {

        System.out.println("### not called by the UI");
        Properties props = PropertiesLocator.getProperties("super-size-my-repo.properties");
        num_Threads = props.getProperty("num_Threads");
        threadPoolSize = props.getProperty("threadPoolSize");
        files_deployment_location = props.getProperty("files_deployment_location");
        images_location = props.getProperty("images_location");
        max_files_per_folder = props.getProperty("max_files_per_folder");

        Boolean createSmallFiles = getBooleanValue(props, "small_files", false);

        pdf = !getBooleanValue(props, "excludePDF", false);
        ppt = !getBooleanValue(props, "excludePPT", false);
        xls = !getBooleanValue(props, "excludeXLS", false);
        doc = !getBooleanValue(props, "excludeDOC", false);
        jpg = !getBooleanValue(props, "excludeJPEG", false);

        properties = PropertiesLocator.getProperties("document-properties.properties");

        doWorkWithMaxFiles(max_files_per_folder, num_Threads, threadPoolSize, files_deployment_location,
                images_location, pdf, ppt, xls, doc, jpg, createSmallFiles);
    }

    private static Boolean getBooleanValue(Properties props, String propName, Boolean defaultValue) {
        return props.containsKey(propName) ? Boolean.valueOf(props.getProperty(propName)) : defaultValue;
    }

    // called by the UI
    public static void main(String[] args, Properties propsUI) {

        if (!propsUI.isEmpty()) {
            properties = propsUI;
        }


        if (args.length > 1) {

            max_files_per_folder = args[0];
            num_Threads = args[1];
            threadPoolSize = args[2];
            files_deployment_location = args[3];
            images_location = args[4];

            if (!args[5].equals("true")) {
                pdf = false;
            }
            if (!args[6].equals("true")) {
                ppt = false;
            }
            if (!args[7].equals("true")) {
                xls = false;
            }
            if (!args[8].equals("true")) {
                doc = false;
            }
            if (!args[9].equals("true")) {
                jpg = false;
            }
            System.out.println("### called by UI");
            doWorkWithMaxFiles(max_files_per_folder, num_Threads, threadPoolSize, files_deployment_location, images_location, pdf, ppt, xls, doc, jpg, false);
        }
    }

    public static void doWorkWithMaxFiles(String maxFiles, String num_Threads, String threadPoolSize, String deployPath, String images, Boolean pdf, Boolean ppt, Boolean xls, Boolean doc, Boolean jpg, Boolean createSmallFiles) {
        final long startTime = System.currentTimeMillis();

        FolderManager.getInstance().setMaxFilesPerFolder(Integer.valueOf(maxFiles));
        FolderManager.getInstance().setRootDeploymentLocation(deployPath);

        ImageManager.initializeImageManager(images);

        RandomWords.init();

        System.out.println("### maxFiles: " + maxFiles);
        System.out.println("### num_Threads: " + num_Threads);
        System.out.println("### threadPoolSize: " + threadPoolSize);
        System.out.println("### deployPath: " + deployPath);
        System.out.println("### images: " + images);

        System.out.println("### pdf: " + pdf);
        System.out.println("### ppt: " + ppt);
        System.out.println("### xls: " + xls);
        System.out.println("### doc: " + doc);
        System.out.println("### jpg: " + jpg);

        ExecutorService executor = Executors.newFixedThreadPool(Integer.valueOf(threadPoolSize));
        int totalExecutions = 0;

        for (int i = 0; i < Integer.valueOf(num_Threads); i++) {
            if (ppt) {
                executor.execute(new MSPowerPointAgent(properties, createSmallFiles));
                totalExecutions++;
            }
            if (pdf) {
                executor.execute(new PdfAgent(properties, createSmallFiles));
                totalExecutions++;
            }
            if (xls) {
                executor.submit(new MSExcelAgent(properties, createSmallFiles));
                totalExecutions++;
            }
            if (doc) {
                executor.submit(new MSWordAgent(properties, createSmallFiles));
                totalExecutions++;
            }
            if (jpg) {
                executor.submit(new JpgAgent(properties));
                totalExecutions++;
            }
        }

        // This will make the executor accept no new threads
        // and finish all existing threads in the queue
        executor.shutdown();

        // Wait until all threads are finish
        try {
            while (!executor.isTerminated()) {
                executor.awaitTermination(5000, TimeUnit.MILLISECONDS);
                System.out.println("Completed " + CompletionService.getNumberOfCompletions() + " of " + totalExecutions);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Finished all threads");
        final long endTime = System.currentTimeMillis();

        final long duration = endTime - startTime;
        System.out.println("Total duration is " + duration + " milliseconds.");
        System.out.println("Average time per document is " + duration/totalExecutions + " milliseconds.");
    }
}
