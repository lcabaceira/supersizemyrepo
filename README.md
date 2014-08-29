supersizemyrepo
===============

A new way to create lots of content in your alfresco repository.

This tool enable you to create (bulk-import-ready) content and metadata for your alfresco repository.


Types of documents created 
-------
<ul>
<li>
MS Word Documents (.doc) </li>
<ul>
<li>Average size of 1024k </li>
<li>Correspondent Meta-Data xml properties files</li>
</ul>

<li>MS Excel Documents(.xls)</li>
<ul>
<li>Average size of 800k </li>
<li>Correspondent Meta-Data xml properties files</li>
</ul>

<li>Pdf documents(.pdf)</li>
<ul>
<li>Average size of 10MB </li>
<li>Correspondent Meta-Data xml properties files</li>
</ul>

<li>MS PowerPoint Presentation Documents(.ppt)</li>
<ul>
<li>Average size of 5MB </li>
<li>Correspondent Meta-Data xml properties files</li>
</ul>

<li>Jpeg images(.jpg)</li>
<ul>
<li>Average size of 2MB </li>
<li>Correspondent Meta-Data xml properties files</li>
</ul>

</ul>

Pre-Requirements
-------
<b>NOTE :</b>Runing the compiled version (available in the uiJars folder) has no pre-requirements appart from having java installed on your laptop. <br/><br/>
If you wish to build the tool and use the standalone (properties file based) version you will need the following:

<b>1 - Software requirements</b><br/>
<ul>
<li>JDK 1.7 </li>
<li>Apache Maven 3.0.4+</li>
</ul>

<b>2 - Configuration requirements</b><br/><br/>
During the installation of maven, a new file name settings.xml was created. This file is our entry point to the your local maven settings configuration, including the remote maven repositories.
Edit your settings.xml file and update the server’s section including the alfresco server id and your credentials.

Note that the root pom.xml references 2 different repositories : <b>alfresco-private</b>, <b>alfresco-private-snapshots</b> . The id of each repository must match with a server id on your settings.xml (where you specify your credentials for that server).

Section from configuration settings.xml

```xml
...
        <server>
            <id>alfresco-public</id>
            <username>YOUR_USERNAME</username>
            <password>YOUR_PASSWORD</password>
        </server>
        <server>
            <id>alfresco-public-snapshots</id>
            <username>YOUR_USERNAME</username>
            <password>YOUR_PASSWORD</password>
        </server>
        
 ...
```

Section from the root pom.xml maven build file

```xml
...
        <repository>
            <id>alfresco-public</id>
            <url>https://artifacts.alfresco.com/nexus/content/groups/public</url>
        </repository>
        <repository>
            <id>alfresco-public-snapshots</id>
            <url>https://artifacts.alfresco.com/nexus/content/groups/public-snapshots</url>
        </repository>
 ...
```

<b>3 - Location/Path Where to create the files </b><br/><br/>

Edit the src/main/java/super-size-my-repo.properties and configure your deployment location and the images location.

<b>files_deployment_location</b> : Shoud be a in a place inside your contentStore. This will be the root for the in-place bulkImport.<br/><br/>
<b>images_location</b> : The tool randomly chooses from a folder of local images to include on the various document types. You need to set the images_location to a folder where you have <b>jpg</b> images. You can use the sample images by pointing the images_location to your <project location>/images. The bigger your images are, the bigger your target documents will be. For the sizes of the documents considered we expect jpg images with aprox <b>1.5MB</b>


Tool Configuration files and options
-------

You find the tool configuration file under src/main/java/super-size-my-repo.properties
This configuration file contains the following pre-explanatory properties.

```xml
...
       files_deployment_location=<PATH_WHERE_THE_FILES_WILL_BE_CREATED>
       images_location=<DEFAULT_LOCATION_FOR_BASE_IMAGES>
       num_Threads=<NUMBER_OF_THREADS_TO_EXECUTE>
       threadPoolSize=<SIZE_OF_THE_THREAD_POOL>
       max_files_per_folder=<NUMBER_OF_MAX_FILES_IN_A_SINGLE_FOLDER>
 ...
```

The only 2 properties that are mandatory to adjust are files_deployment_location and images_location
All of the other properties have default running values.


How to run with maven ?
-------
Issue the following maven command to generate the targets (executable jar) from the project root.

P.S. - Don't forget to configure your properties file.

<b>mvn clean install</b> <br/>

This will build and generate the executable jar on the target directory.

To run this jar, just type 

```xml
...
java -jar super-size-my-repo-<YOUR_VERSION>-SNAPSHOT-jar-with-dependencies.jar
 ...
```

Running with the UI ?
-------

![Screenshot of User Interface Version](https://github.com/lcabaceira/supersizemyrepo/blob/master/images/ui.png)


If you wish to run the tool without having to build it and you want to use the nice UI (many thanks to Alex Strachan), just pick up one the jars available in the folder uiJars and run them with java -jar.

<b>Note for MacOs users</b> : If you are using MAC you need to run the jar as follows:
```xml
...
java -XstartOnFirstThread -jar ./ssmr-ui-0.0.2-osx-20140813.193121-1-jar-with-dependencies.jar
 ...
```


Next Steps ?
-------
After running the tool, you will have lots of documents to import using the Alfresco bulk importer. To perform a in-place import, you need to define the files_deployment_location to a location inside your contentstore.

Now you can execute the in-place-bulk import action to add all the documents and correspondant 
meta-data to a target Alfresco repository.

The Streaming bulk import url on your alfresco is : <a href="http://localhost:8080/alfresco/service/bulkfsimport">http://localhost:8080/alfresco/service/bulkfsimport</a> 

The in-place bulk import url on your alfresco is  : 
<a href="http://localhost:8080/alfresco/service/bulkfsimport/inplace">http://localhost:8080/alfresco/service/bulkfsimport/inplace</a> 

Note that you may need to adjust localhost and the 8080 port with your server details if you not running alfresco locally or you're not running alfresco on the default 8080 port.

Check http://wiki.alfresco.com/wiki/Bulk_Importer for more details. 





