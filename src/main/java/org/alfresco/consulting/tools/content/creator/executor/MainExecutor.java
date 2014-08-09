package org.alfresco.consulting.tools.content.creator.executor;


import org.alfresco.consulting.locator.PropertiesLocator;
import org.alfresco.consulting.tools.content.creator.agents.*;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainExecutor {


    private static Properties props = PropertiesLocator.getProperties("alfresco-consulting.properties");
    private static String num_Threads = props.getProperty("num_Threads");
    private static String threadPoolSize = props.getProperty("threadPoolSize");

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(Integer.valueOf(threadPoolSize));
        for (int i = 0; i < Integer.valueOf(num_Threads); i++) {
            Runnable workerppt = new MSPowerPointAgent();
            Runnable workerPdf = new PdfAgent();
            Runnable workerxls = new MSExcelAgent();
            Runnable workerdoc = new MSWordAgent();
            Runnable workerjpg = new JpgAgent();

            executor.execute(workerppt);
            executor.execute(workerPdf);
            executor.execute(workerxls);
            executor.execute(workerdoc);
            executor.execute(workerjpg);
        }
        // This will make the executor accept no new threads
        // and finish all existing threads in the queue
        executor.shutdown();
        // Wait until all threads are finish

        try {
            executor.awaitTermination(1, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Finished all threads");
    }
}
