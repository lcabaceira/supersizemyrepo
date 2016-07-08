package org.alfresco.consulting.tools.content.creator.executor.parameters;

import org.alfresco.consulting.tools.content.creator.executor.ExecutionConfiguration;

public class ExecutorParameters {
    private final ExecutionConfiguration executionConfiguration;
    private final TypesToInclude typesToInclude;
    private final AgentExecutionInfo agentExecutionInfo;

    ExecutorParameters(ExecutionConfiguration executionConfiguration, TypesToInclude typesToInclude, AgentExecutionInfo agentExecutionInfo) {
        this.executionConfiguration = executionConfiguration;
        this.typesToInclude = typesToInclude;
        this.agentExecutionInfo = agentExecutionInfo;
    }

    public ExecutionConfiguration getExecutionConfiguration() {
        return executionConfiguration;
    }

    public TypesToInclude getTypesToInclude() {
        return typesToInclude;
    }

    public AgentExecutionInfo getAgentExecutionInfo() {
        return agentExecutionInfo;
    }
}
