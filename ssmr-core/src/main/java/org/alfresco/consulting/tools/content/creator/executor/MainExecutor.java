package org.alfresco.consulting.tools.content.creator.executor;


import org.alfresco.consulting.tools.content.creator.executor.parameters.ExecutorParameters;
import org.alfresco.consulting.tools.content.creator.executor.parameters.ParameterGenerator;
import org.alfresco.consulting.tools.content.creator.executor.parameters.PropertyParameterGenerator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MainExecutor {
    private static final Log logger = LogFactory.getLog(MainExecutor.class);

    public static void main(String[] args) {
        logger.info("### not called by the UI");
        performExecution(new PropertyParameterGenerator());
    }

    private static void performExecution(ParameterGenerator parameterGenerator) {
        final ExecutorParameters executorParameters = parameterGenerator.generateParameters();

        execute(executorParameters);
    }

    public static void execute(ExecutorParameters executorParameters) {
        new SuperSizeExecutor(executorParameters).performExecution();
    }
}
