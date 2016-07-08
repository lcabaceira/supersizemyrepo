package org.alfresco.consulting.tools.content.creator.executor.parameters;

import java.util.Properties;

public class AgentExecutionInfo {
    private static AgentExecutionInfo defaultInstance;

    private final boolean createSmallFiles;
    private final Properties documentProperties;

    AgentExecutionInfo(boolean createSmallFiles, Properties documentProperties) {
        this.createSmallFiles = createSmallFiles;
        this.documentProperties = documentProperties;
    }

    public static AgentExecutionInfo getDefaultInstance() {
        return defaultInstance;
    }

    public static void setDefaultInstance(AgentExecutionInfo defaultInstance) {
        AgentExecutionInfo.defaultInstance = defaultInstance;
    }

    public boolean isCreatingSmallerFiles() {
        return createSmallFiles;
    }

    public Properties getDocumentProperties() {
        return documentProperties;
    }


}
