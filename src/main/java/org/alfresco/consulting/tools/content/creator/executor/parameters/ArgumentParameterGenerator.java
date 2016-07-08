package org.alfresco.consulting.tools.content.creator.executor.parameters;

import org.alfresco.consulting.tools.content.creator.executor.ExecutionConfiguration;

import java.util.Properties;

public class ArgumentParameterGenerator implements ParameterGenerator {
    private String[] args;
    private Properties propsUI;

    public ArgumentParameterGenerator(String[] args, Properties propsUI) {
        this.args = args;
        this.propsUI = propsUI;
    }

    private static ExecutionConfiguration getExecutionConfigurationFrom(String[] args) {
        String maxFilesPerFolder = args[0];
        int numThreads = Integer.parseInt(args[1]);
        int threadPoolSize = Integer.parseInt(args[2]);
        String filesDeploymentLocation = args[3];
        String imagesLocation = args[4];

        return new ExecutionConfiguration(maxFilesPerFolder, numThreads, threadPoolSize, filesDeploymentLocation, imagesLocation);
    }

    private static TypesToInclude getTypesToInclude(String[] args) {
        Boolean includePDF = args[5].equals("true");
        Boolean includePPT = args[6].equals("true");
        Boolean includeXLS = args[7].equals("true");
        Boolean includeDOC = args[8].equals("true");
        Boolean includeJPEG = args[9].equals("true");

        return new TypesToInclude(includePDF, includePPT, includeXLS, includeDOC, includeJPEG);
    }

    @Override
    public ExecutorParameters generateParameters() {
        return new ExecutorParameters(getExecutionConfigurationFrom(args), getTypesToInclude(args),
                new AgentExecutionInfo(isCreateSmallFiles(), getDocumentProperties()));
    }

    private boolean isCreateSmallFiles() {
        return false;
    }

    private Properties getDocumentProperties() {
        return !propsUI.isEmpty() ? propsUI : null;
    }
}
