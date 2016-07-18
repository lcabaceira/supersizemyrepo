supersizemyrepo
===============

A new way to create lots of content in your alfresco repository.  It 
has two modes of running; standard and small file.
<ul>
    <li>Standard
        <ul>
            <li>Creates relatively large files</li>
            <li>All files have image content</li>
            <li>Uses a large word base from which words are randomly 
            chosen</li>
            <li>Adds a large number of words to documents</li>
            <li>Will create a very large full text index</li>
            <li>Best for testing large file system usage</li>
        </ul>
    </li>
    <li>Small Files
        <ul>
            <li>Creates small files</li>
            <li>Runs extremely quickly (around 10 ms a document 
            depending on the environment being run in)</li>
            <li>Uses a reduced word base from which words are randomly 
            chosen</li>
            <li>Creates significantly smaller full text index</li>
            <li>Best for testing large numbers of documents without
            taxing the file system or full text engine as much</li>
        </ul>
    </li>
</ul>

This tool enable you to create (bulk-import-ready) content and metadata for your alfresco repository.


Types of documents created 
-------
<ul>
<li>
MS Word Documents (.doc) </li>
<ul>
<li>Average size of 1024k </li>
<li>Average size of 4k (small file option specified)</li>
<li>Correspondent Meta-Data xml properties files</li>
</ul>

<li>MS Excel Documents(.xls)</li>
<ul>
<li>Average size of 800k </li>
<li>Average size of 22k (small file option specified)</li>
<li>Correspondent Meta-Data xml properties files</li>
</ul>

<li>Pdf documents(.pdf)</li>
<ul>
<li>Average size of 10MB </li>
<li>Average size of 15k (small file option specified)</li>
<li>Correspondent Meta-Data xml properties files</li>
</ul>

<li>MS PowerPoint Presentation Documents(.ppt)</li>
<ul>
<li>Average size of 5MB </li>
<li>Average size of 25k (small file option specified)</li>
<li>Correspondent Meta-Data xml properties files</li>
</ul>

<li>Jpeg images(.jpg)</li>
<ul>
<li>Average size using supplied image folder is 2MB </li>
<li>Folder with smaller images should be specified when the small file 
option is specified</li>
<li>Correspondent Meta-Data xml properties files</li>
</ul>

</ul>

Pre-Requirements
-------
<b>NOTE :</b>Runing the compiled version (available in the uiJars folder) has no pre-requirements apart from having java installed on your laptop. <br/><br/>
If you wish to build the tool and use the standalone (properties file based) version you will need the following:

<ol>
<li><b>Software requirements</b><br/>
<ul>
<li>JDK 1.7 </li>
<li>Apache Maven 3.0.4+</li>
</ul>
</li>

<li><b>Location/Path Where to create the files </b><br/><br/>

Edit the src/main/java/super-size-my-repo.properties and configure your deployment location and the images location.

<b>files_deployment_location</b> : Shoud be a in a place inside your contentStore. This will be the root for the in-place bulkImport.<br/><br/>
<b>images_location</b> : The tool randomly chooses from a folder of local images to include on the various document types. You need to set the images_location to a folder where you have <b>jpg</b> images. You can use the sample images by pointing the images_location to your <project location>/images. The bigger your images are, the bigger your target documents will be. For the sizes of the documents considered we expect jpg images with aprox <b>1.5MB</b>
</li>
</ol>

Tool Configuration files and options
-------

You find the tool configuration file under ssmr-core/src/main/resources/super-size-my-repo.properties
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

You can also remove or modify the ssmr-core/src/main/resources/document-properties.properties file.
This is a standard properties file that defines the properties for a document.  The property name
corresponds to an Alfresco property, just remember to escape the colon.  In addition you must
specify a 'type' property to define the Alfresco object type of the document.


How to run with maven ?
-------
Issue the following maven command to generate the targets (executable jar) from the project root.

P.S. - Don't forget to configure your properties file if you will be running without the UI.

<b>mvn -P<environment> clean install</b> <br/>
where environment is one of the following:
<dl>
    <dt>lnx</dt>
    <dd>Any flavor of Linux</dd>
    <dt>osx</dt>
    <dd>Mac OS X</dd>
    <dt>win64</dt>
    <dd>64-bit Windows (refers to Java not necessarily OS)</dd>
    <dt>winx86</dt>
    <dd>32-bit Windows (refers to Java not necessarily OS)</dd>
</dl>

This will build and generate the executable jar in the target directory of the ssmr-core project.

To run this jar, just type 

```xml
...
java -jar super-size-my-repo-<YOUR_VERSION>-SNAPSHOT-jar-with-dependencies.jar
 ...
```

You can also run the UI from the command line using the instructions below noting that the UI JAR is built into the uiJars folder under the root project.

Running with the UI ?
-------

![Screenshot of User Interface Version](https://github.com/eamell/supersizemyrepo/blob/master/images/ui.png)


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

Now you can execute the in-place-bulk import action to add all the documents and correspondent 
meta-data to a target Alfresco repository.

The Streaming bulk import url on your alfresco is : <a href="http://localhost:8080/alfresco/service/bulkfsimport">http://localhost:8080/alfresco/service/bulkfsimport</a> 

The in-place bulk import url on your alfresco is  : 
<a href="http://localhost:8080/alfresco/service/bulkfsimport/inplace">http://localhost:8080/alfresco/service/bulkfsimport/inplace</a> 

Note that you may need to adjust localhost and the 8080 port with your server details if you not running alfresco locally or you're not running alfresco on the default 8080 port.

Check http://wiki.alfresco.com/wiki/Bulk_Importer for more details. 





