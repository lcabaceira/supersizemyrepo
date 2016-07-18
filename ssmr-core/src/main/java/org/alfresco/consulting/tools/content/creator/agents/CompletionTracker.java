package org.alfresco.consulting.tools.content.creator.agents;

public class CompletionTracker {
    private static final CompletionTracker INSTANCE = new CompletionTracker();
    private int completionCount = 0;

    private CompletionTracker() {}

    public static void initializeTracker() {
        INSTANCE.completionCount = 0;
    }

    static void registerCompletion() {
        INSTANCE.incrementCompletionCount();
    }

    public static int getNumberOfCompletions() {
        return INSTANCE.completionCount;
    }

    // ensure this method is not inlined for two reasons: 1) for readability and 2) it is synchronized on updating the
    // count
    private synchronized void incrementCompletionCount() {
        completionCount++;
    }
}
