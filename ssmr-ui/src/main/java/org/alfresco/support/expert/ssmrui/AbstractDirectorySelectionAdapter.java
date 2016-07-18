package org.alfresco.support.expert.ssmrui;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;

abstract class AbstractDirectorySelectionAdapter extends SelectionAdapter {
    private final Shell shell;
    private final String message;

    AbstractDirectorySelectionAdapter(Shell shell, String message) {
        this.shell = shell;
        this.message = message;
    }

    @Override
    public void widgetSelected(SelectionEvent e) {
        DirectoryDialog dialog = new DirectoryDialog(shell);

        dialog.setFilterPath(getInitialFilterPath());
        dialog.setMessage(message);

        String selectedDir = dialog.open();
        if (selectedDir != null) {
            onDirectorySelected(selectedDir);
        }
    }

    protected abstract String getInitialFilterPath();

    protected abstract void onDirectorySelected(String selectedDir);
}
