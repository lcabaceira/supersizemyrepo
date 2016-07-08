package org.alfresco.consulting.tools.content.creator.executor.parameters;

import org.alfresco.consulting.locator.PropertiesLocator;
import org.alfresco.consulting.tools.content.creator.executor.ExecutionConfiguration;

import java.util.Properties;

import static org.alfresco.consulting.PropertiesUtil.getBooleanValue;

public class PropertyParameterGenerator implements ParameterGenerator {
    private Properties executionProperties;

    public ExecutorParameters generateParameters() {
        executionProperties = PropertiesLocator.getProperties("super-size-my-repo.properties");

        return new ExecutorParameters(getExecutionConfigurationFrom(), getTypesToInclude(),
                getAgentExecutionInfoFromProperties());
    }

    private ExecutionConfiguration getExecutionConfigurationFrom() {
        int numThreads = Integer.parseInt(executionProperties.getProperty("num_Threads"));
        int threadPoolSize = Integer.parseInt(executionProperties.getProperty("threadPoolSize"));
        String filesDeploymentLocation = executionProperties.getProperty("files_deployment_location");
        String imagesLocation = executionProperties.getProperty("images_location");
        String maxFilesPerFolder = executionProperties.getProperty("max_files_per_folder");

        return new ExecutionConfiguration(maxFilesPerFolder, numThreads, threadPoolSize, filesDeploymentLocation,
                imagesLocation);
    }

    private TypesToInclude getTypesToInclude() {
        Boolean pdf = !getBooleanValue(executionProperties, "excludePDF", false);
        Boolean ppt = !getBooleanValue(executionProperties, "excludePPT", false);
        Boolean xls = !getBooleanValue(executionProperties, "excludeXLS", false);
        Boolean doc = !getBooleanValue(executionProperties, "excludeDOC", false);
        Boolean jpg = !getBooleanValue(executionProperties, "excludeJPEG", false);

        return new TypesToInclude(pdf, ppt, xls, doc, jpg);
    }

    private AgentExecutionInfo getAgentExecutionInfoFromProperties() {
        Boolean createSmallFiles = getBooleanValue(executionProperties, "small_files", false);
        Properties documentProperties = PropertiesLocator.getProperties("document-properties.properties");

        return new AgentExecutionInfo(createSmallFiles, documentProperties);
    }
}
