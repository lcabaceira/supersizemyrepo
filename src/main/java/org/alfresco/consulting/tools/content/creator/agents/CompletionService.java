package org.alfresco.consulting.tools.content.creator.agents;

public class CompletionService {
    private static final CompletionService INSTANCE = new CompletionService();
    private int completionCount = 0;

    private CompletionService() {}

    static void registerCompletion() {
        INSTANCE.incrementCompletionCount();
    }

    public static int getNumberOfCompletions() {
        return INSTANCE.getCompletionCount();
    }

    private synchronized void incrementCompletionCount() {
        completionCount++;
    }

    private synchronized int getCompletionCount() {
        return completionCount;
    }
}
