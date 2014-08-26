package org.alfresco.consulting.tools.content.creator.executor;


import org.alfresco.consulting.locator.PropertiesLocator;
import org.alfresco.consulting.tools.content.creator.agents.*;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainExecutor {
	
	private static Properties props;

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


    // --astrachan - not called by the UI 
    public static void main(String[] args) {
 
            System.out.println("### not called by the UI");
            props = PropertiesLocator.getProperties("super-size-my-repo.properties");
            num_Threads = props.getProperty("num_Threads");
            threadPoolSize = props.getProperty("threadPoolSize");
            files_deployment_location = props.getProperty("files_deployment_location");
            images_location = props.getProperty("images_location");
            max_files_per_folder = props.getProperty("max_files_per_folder");
  
            doWorkWithMaxFiles(max_files_per_folder,num_Threads, threadPoolSize, files_deployment_location, images_location,true, true, true, true, true);
        }


    // called by the UI
    public static void main(String[] args, Properties propsUI) {
    	
    	if (!propsUI.isEmpty()){
    		properties = propsUI;
    	}
    	
   
    	if (args.length > 1){
	
	   			max_files_per_folder				= args[0];
	   			num_Threads 						= 	args[1];
	   			threadPoolSize						= 	args[2];
	   			files_deployment_location 	= 	args[3];
	   			images_location 					= 	args[4];
	   			
	   			if (!args[4].equals("true")){
	   				pdf = false;
	   			}
	 			if (!args[5].equals("true")){
	   				ppt = false;
	   			}
	 			if (!args[6].equals("true")){
	   				xls = false;
	   			}
	 			if (!args[7].equals("true")){
	   				doc = false;
	   			}
	 			if (!args[8].equals("true")){
	   				jpg = false;
	   			}
	 			System.out.println("### called by UI cakey");
	   			doWorkWithMaxFiles(max_files_per_folder,num_Threads, threadPoolSize, files_deployment_location, images_location, pdf, ppt, xls, doc, jpg);  
   			} 
    }

   public static void doWorkWithMaxFiles(String maxFiles, String num_Threads, String threadPoolSize, String deployPath, String images, Boolean pdf, Boolean ppt, Boolean xls, Boolean doc, Boolean jpg){

	   		System.out.println("### maxFiles: " + maxFiles);
			System.out.println("### num_Threads: " + num_Threads);
			System.out.println("### threadPoolSize: " + threadPoolSize);
			System.out.println("### deployPath: " + deployPath);
			System.out.println("### images: " + images);
	
			ExecutorService executor = Executors.newFixedThreadPool(Integer.valueOf(threadPoolSize));

			for (int i = 0; i < Integer.valueOf(num_Threads); i++) {

            Runnable workerppt = new MSPowerPointAgent(maxFiles,deployPath, images, properties);
            Runnable workerPdf = new PdfAgent(maxFiles,deployPath, images, num_Threads, properties);
            Runnable workerxls = new MSExcelAgent(maxFiles,deployPath, images, properties);
            Runnable workerdoc = new MSWordAgent(maxFiles,deployPath, images, properties);
            Runnable workerjpg = new JpgAgent(maxFiles,deployPath, images, properties);


            if (ppt) {executor.execute(workerppt);}
            if (pdf) {executor.execute(workerPdf);}
            if (xls) {executor.execute(workerxls);}
            if (doc) {executor.execute(workerdoc);}
            if (jpg) {executor.execute(workerjpg);}

        }
        // This will make the executor accept no new threads
        // and finish all existing threads in the queue
        executor.shutdown();
        // Wait until all threads are finish

        try {
            executor.awaitTermination(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Finished all threads");
   }
}
