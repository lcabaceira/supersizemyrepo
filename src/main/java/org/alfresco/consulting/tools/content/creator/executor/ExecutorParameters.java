package org.alfresco.consulting.tools.content.creator.executor;

public class ExecutorParameters {
    private final ExecutionConfiguration executionConfiguration;
    private final TypesToInclude typesToInclude;
    private final AgentExecutionInfo agentExecutionInfo;

    public ExecutorParameters(ExecutionConfiguration executionConfiguration, TypesToInclude typesToInclude, AgentExecutionInfo agentExecutionInfo) {
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
