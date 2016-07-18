package org.alfresco.support.expert.ssmrui;

import org.alfresco.consulting.tools.content.creator.executor.ExecutionConfiguration;
import org.alfresco.consulting.tools.content.creator.executor.MainExecutor;
import org.alfresco.consulting.tools.content.creator.executor.parameters.AgentExecutionInfo;
import org.alfresco.consulting.tools.content.creator.executor.parameters.ExecutorParameters;
import org.alfresco.consulting.tools.content.creator.executor.parameters.TypesToInclude;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.*;
import org.eclipse.wb.swt.SWTResourceManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;
import java.util.Properties;

/*
 * Author - astrachan, Alfresco Expert Support
 * Version 0.0.1 -- initial version 13-08-2014
 * Based on the super-size-my-repo utility developed by Luis Cabaceira (https://github.com/lcabaceira/supersizemyrepo)
 * This mavenized class brings in the 
 *
 */

public class MainUI extends Shell {

    private static String version = "0.0.2";
//	private static Shell shell;

    // properties to be passed through to MainExecutor
    private Text txtDeploy;
    private Text txtImages;
    private Text txtThreads;
    private Text txtThreadPoolSize;

    // our check boxes
    private Button btnPdf;
    private Button btnPpt;
    private Button btnXls;
    private Button btnDoc;
    private Button btnJpg;

    private Button btnCreateSmallFiles;

    // buttons
    private Button btnGo;
    private Button btnExit;
    private Button btnDeploy;
    private Button btnImages;
    private Button btnReset;

    // directories
    private String deployDir;
    private String imagesDir;

    // properties
    private Text txtKey1;
    private Text txtVal1;
    private Text txtKey2;
    private Text txtKey3;
    private Text txtKey4;
    private Text txtKey5;
    private Text txtVal2;
    private Text txtVal3;
    private Text txtVal4;
    private Text txtVal5;
    private Text txtVal6;
    private Text txtVal7;
    private Text txtVal8;
    private Text txtVal14;
    private Text txtVal15;
    private Text txtVal16;
    private Text txtKey9;
    private Text txtKey10;
    private Text txtKey11;
    private Text txtKey12;
    private Text txtKey13;
    private Text txtVal9;
    private Text txtVal10;
    private Text txtVal11;
    private Text txtVal12;
    private Text txtVal13;
    private Text txtKey6;
    private Text txtKey7;
    private Text txtKey8;
    private Text txtKey14;
    private Text txtKey15;
    private Text txtKey16;
    private Label label_10;
    private Label label_11;
    private Label label_12;
    private Label label_13;
    private Label label_14;
    private Label label_15;

    private Properties properties;


    private DateFormat dateFormat;
    private Calendar cal;
    private String date;


    public static void main(String args[]) {
        try {
            Display display = Display.getDefault();

            MainUI shell = new MainUI(display);
            shell.open();
            shell.layout();
            while (!shell.isDisposed()) {
                if (!display.readAndDispatch()) {
                    display.sleep();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the shell.
     *
     * @param display
     */
    private MainUI(final Display display) {
        super(display, SWT.SHELL_TRIM);
        Group grpConfiguration = new Group(this, SWT.NONE);
        grpConfiguration.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        grpConfiguration.setText("Configuration");
        grpConfiguration.setBounds(10, 10, 633, 416);

        btnDeploy = new Button(grpConfiguration, SWT.NONE);
        btnDeploy.setToolTipText("Click here to select the folder that the files for importing will be created");
        btnDeploy.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        btnDeploy.addSelectionListener(new DeployDirSelectionAdapter(this));
        btnDeploy.setBounds(10, 95, 120, 25);
        btnDeploy.setText("Deployment");

        txtDeploy = new Text(grpConfiguration, SWT.BORDER);
        txtDeploy.setToolTipText("Deployment path");
        txtDeploy.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        txtDeploy.setEnabled(false);
        txtDeploy.setEditable(false);
        txtDeploy.setBounds(141, 97, 481, 25);

        btnImages = new Button(grpConfiguration, SWT.NONE);
        btnImages.setToolTipText("Click here to seelct the folder where images are stored (these images will be embedded in the documents)");
        btnImages.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        btnImages.addSelectionListener(new ImagesDirSelectionAdapter(this));
        btnImages.setBounds(10, 126, 120, 25);
        btnImages.setText("Images");

        txtImages = new Text(grpConfiguration, SWT.BORDER);
        txtImages.setToolTipText("Images path");
        txtImages.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        txtImages.setEnabled(false);
        txtImages.setEditable(false);
        txtImages.setBounds(141, 128, 481, 25);

        txtThreads = new Text(grpConfiguration, SWT.BORDER);
        txtThreads.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        txtThreads.setToolTipText("Enter the number of threads");
        txtThreads.setBounds(134, 27, 76, 21);

        Label lblNumberOfThreads = new Label(grpConfiguration, SWT.NONE);
        lblNumberOfThreads.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        lblNumberOfThreads.setBounds(10, 30, 120, 15);
        lblNumberOfThreads.setText("Number of threads");

        txtThreadPoolSize = new Text(grpConfiguration, SWT.BORDER);
        txtThreadPoolSize.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        txtThreadPoolSize.setToolTipText("Enter the thread pool size here, i.e. the number of documents per thread");
        txtThreadPoolSize.setBounds(134, 51, 76, 21);

        Label lblThreadPoolSize = new Label(grpConfiguration, SWT.NONE);
        lblThreadPoolSize.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        lblThreadPoolSize.setBounds(10, 54, 120, 15);
        lblThreadPoolSize.setText("Thread pool size");

        Composite mimeTypeContainer = new Composite(grpConfiguration, SWT.NONE);
        mimeTypeContainer.setToolTipText("Check the mimetype of the document(s) you'd like to produce");
        mimeTypeContainer.setBounds(294, 17, 328, 36);

        btnPdf = new Button(mimeTypeContainer, SWT.CHECK);
        btnPdf.setToolTipText("Select to create PDF files");
        btnPdf.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        btnPdf.setBounds(10, 10, 54, 16);
        btnPdf.setText("PDF");

        btnPpt = new Button(mimeTypeContainer, SWT.CHECK);
        btnPpt.setToolTipText("Select to create PPT (Powerpoint) files");
        btnPpt.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        btnPpt.setBounds(74, 10, 54, 16);
        btnPpt.setText("PPT");

        btnXls = new Button(mimeTypeContainer, SWT.CHECK);
        btnXls.setToolTipText("Select to create XLS (Excel) files");
        btnXls.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        btnXls.setBounds(134, 10, 54, 16);
        btnXls.setText("XLS");

        btnDoc = new Button(mimeTypeContainer, SWT.CHECK);
        btnDoc.setToolTipText("Select to create DOC (Document) files");
        btnDoc.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        btnDoc.setBounds(194, 10, 59, 16);
        btnDoc.setText("DOC");

        btnJpg = new Button(mimeTypeContainer, SWT.CHECK);
        btnJpg.setToolTipText("Select to create JPG (image) files");
        btnJpg.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        btnJpg.setBounds(259, 10, 54, 16);
        btnJpg.setText("JPG");

        btnCreateSmallFiles = new Button(grpConfiguration, SWT.CHECK);
        btnCreateSmallFiles.setToolTipText("Select to create smaller files.");
        btnCreateSmallFiles.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        btnCreateSmallFiles.setBounds(304, 54, 120, 16);
        btnCreateSmallFiles.setText("Small Files");

        txtKey1 = new Text(grpConfiguration, SWT.BORDER);
        txtKey1.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        txtKey1.setBounds(10, 194, 76, 21);

        Label label = new Label(grpConfiguration, SWT.NONE);
        label.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        label.setBounds(92, 197, 16, 15);
        label.setText(":");

        txtVal1 = new Text(grpConfiguration, SWT.BORDER);
        txtVal1.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        txtVal1.setBounds(114, 194, 186, 21);

        Label lblProperties = new Label(grpConfiguration, SWT.NONE);
        lblProperties.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        lblProperties.setBounds(10, 173, 568, 15);
        lblProperties.setText("Meta-data properties (manifest file will contain this information)");

        txtKey2 = new Text(grpConfiguration, SWT.BORDER);
        txtKey2.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        txtKey2.setBounds(10, 221, 76, 21);

        txtKey3 = new Text(grpConfiguration, SWT.BORDER);
        txtKey3.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        txtKey3.setBounds(10, 248, 76, 21);

        txtKey4 = new Text(grpConfiguration, SWT.BORDER);
        txtKey4.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        txtKey4.setBounds(10, 275, 76, 21);

        txtKey5 = new Text(grpConfiguration, SWT.BORDER);
        txtKey5.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        txtKey5.setBounds(10, 302, 76, 21);

        Label label_1 = new Label(grpConfiguration, SWT.NONE);
        label_1.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        label_1.setText(":");
        label_1.setBounds(92, 224, 16, 15);

        Label label_2 = new Label(grpConfiguration, SWT.NONE);
        label_2.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        label_2.setText(":");
        label_2.setBounds(92, 248, 16, 15);

        Label label_3 = new Label(grpConfiguration, SWT.NONE);
        label_3.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        label_3.setText(":");
        label_3.setBounds(92, 275, 16, 15);

        Label label_4 = new Label(grpConfiguration, SWT.NONE);
        label_4.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        label_4.setText(":");
        label_4.setBounds(92, 305, 16, 15);

        txtVal2 = new Text(grpConfiguration, SWT.BORDER);
        txtVal2.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        txtVal2.setBounds(114, 221, 186, 21);

        txtVal3 = new Text(grpConfiguration, SWT.BORDER);
        txtVal3.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        txtVal3.setBounds(114, 248, 186, 21);

        txtVal4 = new Text(grpConfiguration, SWT.BORDER);
        txtVal4.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        txtVal4.setBounds(114, 275, 186, 21);

        txtVal5 = new Text(grpConfiguration, SWT.BORDER);
        txtVal5.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        txtVal5.setBounds(114, 302, 186, 21);

        txtKey9 = new Text(grpConfiguration, SWT.BORDER);
        txtKey9.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        txtKey9.setBounds(332, 194, 76, 21);

        txtKey10 = new Text(grpConfiguration, SWT.BORDER);
        txtKey10.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        txtKey10.setBounds(332, 221, 76, 21);

        txtKey11 = new Text(grpConfiguration, SWT.BORDER);
        txtKey11.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        txtKey11.setBounds(332, 248, 76, 21);

        txtKey12 = new Text(grpConfiguration, SWT.BORDER);
        txtKey12.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        txtKey12.setBounds(332, 275, 76, 21);

        txtKey13 = new Text(grpConfiguration, SWT.BORDER);
        txtKey13.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        txtKey13.setBounds(332, 302, 76, 21);

        Label label_5 = new Label(grpConfiguration, SWT.NONE);
        label_5.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        label_5.setText(":");
        label_5.setBounds(414, 194, 16, 15);

        Label label_6 = new Label(grpConfiguration, SWT.NONE);
        label_6.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        label_6.setText(":");
        label_6.setBounds(414, 221, 16, 15);

        Label label_7 = new Label(grpConfiguration, SWT.NONE);
        label_7.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        label_7.setText(":");
        label_7.setBounds(414, 248, 16, 15);

        Label label_8 = new Label(grpConfiguration, SWT.NONE);
        label_8.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        label_8.setText(":");
        label_8.setBounds(414, 275, 16, 15);

        Label label_9 = new Label(grpConfiguration, SWT.NONE);
        label_9.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        label_9.setText(":");
        label_9.setBounds(414, 302, 16, 15);

        txtVal9 = new Text(grpConfiguration, SWT.BORDER);
        txtVal9.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        txtVal9.setBounds(436, 194, 186, 21);

        txtVal10 = new Text(grpConfiguration, SWT.BORDER);
        txtVal10.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        txtVal10.setBounds(436, 221, 186, 21);

        txtVal11 = new Text(grpConfiguration, SWT.BORDER);
        txtVal11.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        txtVal11.setBounds(437, 248, 186, 21);

        txtVal12 = new Text(grpConfiguration, SWT.BORDER);
        txtVal12.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        txtVal12.setBounds(437, 275, 186, 21);

        txtVal13 = new Text(grpConfiguration, SWT.BORDER);
        txtVal13.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        txtVal13.setBounds(437, 302, 186, 21);

        txtKey6 = new Text(grpConfiguration, SWT.BORDER);
        txtKey6.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        txtKey6.setBounds(10, 329, 76, 21);

        txtKey7 = new Text(grpConfiguration, SWT.BORDER);
        txtKey7.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        txtKey7.setBounds(10, 356, 76, 21);

        txtKey8 = new Text(grpConfiguration, SWT.BORDER);
        txtKey8.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        txtKey8.setBounds(10, 383, 76, 21);

        txtKey14 = new Text(grpConfiguration, SWT.BORDER);
        txtKey14.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        txtKey14.setBounds(332, 329, 76, 21);

        txtKey15 = new Text(grpConfiguration, SWT.BORDER);
        txtKey15.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        txtKey15.setBounds(332, 356, 76, 21);

        txtKey16 = new Text(grpConfiguration, SWT.BORDER);
        txtKey16.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        txtKey16.setBounds(332, 383, 76, 21);

        label_10 = new Label(grpConfiguration, SWT.NONE);
        label_10.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        label_10.setText(":");
        label_10.setBounds(92, 329, 16, 15);

        label_11 = new Label(grpConfiguration, SWT.NONE);
        label_11.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        label_11.setText(":");
        label_11.setBounds(92, 356, 16, 15);

        label_12 = new Label(grpConfiguration, SWT.NONE);
        label_12.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        label_12.setText(":");
        label_12.setBounds(92, 383, 16, 15);

        label_13 = new Label(grpConfiguration, SWT.NONE);
        label_13.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        label_13.setText(":");
        label_13.setBounds(414, 329, 16, 15);

        label_14 = new Label(grpConfiguration, SWT.NONE);
        label_14.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        label_14.setText(":");
        label_14.setBounds(414, 356, 16, 15);

        label_15 = new Label(grpConfiguration, SWT.NONE);
        label_15.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        label_15.setText(":");
        label_15.setBounds(414, 383, 16, 15);

        txtVal6 = new Text(grpConfiguration, SWT.BORDER);
        txtVal6.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        txtVal6.setBounds(114, 329, 186, 21);

        txtVal7 = new Text(grpConfiguration, SWT.BORDER);
        txtVal7.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        txtVal7.setBounds(114, 356, 186, 21);

        txtVal8 = new Text(grpConfiguration, SWT.BORDER);
        txtVal8.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        txtVal8.setBounds(114, 383, 186, 21);

        txtVal14 = new Text(grpConfiguration, SWT.BORDER);
        txtVal14.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        txtVal14.setBounds(436, 329, 186, 21);

        txtVal15 = new Text(grpConfiguration, SWT.BORDER);
        txtVal15.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        txtVal15.setBounds(436, 356, 186, 21);

        txtVal16 = new Text(grpConfiguration, SWT.BORDER);
        txtVal16.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        txtVal16.setBounds(436, 383, 186, 21);

        btnGo = new Button(this, SWT.NONE);
        btnGo.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        btnGo.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (checkValues()) {
                    System.out.println("### values passed test, calling MainExecutor()");

                    MainExecutor.execute(buildExecutorParameters());
                }
            }
        });
        btnGo.setBounds(10, 432, 118, 25);
        btnGo.setText("Go");

        btnExit = new Button(this, SWT.NONE);
        btnExit.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        btnExit.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                System.exit(0);
            }
        });
        btnExit.setBounds(525, 432, 118, 25);
        btnExit.setText("Exit");

        btnReset = new Button(this, SWT.NONE);
        btnReset.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.NORMAL));
        btnReset.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                reset();
            }
        });
        btnReset.setBounds(388, 432, 118, 25);
        btnReset.setText("Reset");

        createContents();
    }

    private ExecutorParameters buildExecutorParameters() {
        return new ExecutorParameters(getExecutionConfiguration(), getTypesToInclude(), getAgentExecutionInfo());
    }

    private AgentExecutionInfo getAgentExecutionInfo() {
        return new AgentExecutionInfo(btnCreateSmallFiles.getSelection(), generatePropsFromValues());
    }

    private TypesToInclude getTypesToInclude() {
        return new TypesToInclude(btnPdf.getSelection(), btnPpt.getSelection(),
                btnXls.getSelection(), btnDoc.getSelection(), btnJpg.getSelection());
    }

    private ExecutionConfiguration getExecutionConfiguration() {
        return new ExecutionConfiguration(Integer.parseInt(txtThreads.getText()),
                Integer.parseInt(txtThreadPoolSize.getText()), txtDeploy.getText(),
                txtImages.getText());
    }

    /**
     * Create contents of the shell.
     */
    private void createContents() {

        setSize(669, 499);
        setText("ssmr-ui | version " + version);

        // set the defaults
        reset();
        populateProperties();
    }

    private boolean checkValues() {
        return !((txtThreads.getText().length() == 0) || (Objects.equals(txtThreads.getText(), "")) ||
                (txtThreadPoolSize.getText().length() == 0) || (txtThreadPoolSize.getText() == null) ||
                (txtDeploy.getText().length() == 0) || (txtDeploy.getText() == null) ||
                (txtImages.getText().length() == 0) || (txtImages.getText() == null));
    }

    private void reset() {

        // reset UI
        txtThreads.setText("100");
        txtThreadPoolSize.setText("10");
        txtDeploy.setText("");
        txtImages.setText("");

        btnDeploy.setEnabled(true);
        btnImages.setEnabled(true);

        btnPdf.setSelection(true);
        btnPpt.setSelection(true);
        btnXls.setSelection(true);
        btnDoc.setSelection(true);
        btnJpg.setSelection(true);

        populateProperties();
    }

    private void populateProperties() {
        dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        cal = Calendar.getInstance();
        date = dateFormat.format(cal.getTime());

        // --------------------------------------------------
        txtKey1.setText("type");
        txtVal1.setText("cm:content");

        txtKey2.setText("aspects");
        txtVal2.setText("cm:versionable,cm:dublincore");

        txtKey3.setText("cm:title");
        txtVal3.setText("Daily Report document :" + date);

        txtKey4.setText("cm:description");
        txtVal4.setText("Document description");

        txtKey5.setText("cm:author");
        txtVal5.setText("SuperSizeMyRepo");

        txtKey6.setText("cm:publisher");
        txtVal6.setText("SuperSizeMyRepo");

        txtKey7.setText("cm:contributor");
        txtVal7.setText("SuperSizeMyRepo");

        txtKey8.setText("cm:type");
        txtVal8.setText("default_plus_dubincore_aspect");

        txtKey9.setText("cm:identifier");
        txtVal9.setText("");

        txtKey10.setText("cm:source");
        txtVal10.setText("SuperSizeMyRepo");

        txtKey11.setText("cm:coverage");
        txtVal11.setText("General");

        txtKey12.setText("cm:rights");
        txtVal12.setText("");

        txtKey13.setText("cm:subject");
        txtVal13.setText("Metadata file created with SSMR");

        txtKey14.setText("");
        txtVal14.setText("");

        txtKey15.setText("");
        txtVal15.setText("");

        txtKey16.setText("");
        txtVal16.setText("");

    }

    private Properties generatePropsFromValues() {
        properties = new Properties();

        if (!txtKey1.equals("")) {
            properties.setProperty(txtKey1.getText(), txtVal1.getText());
        }
        if (!txtKey2.equals("")) {
            properties.setProperty(txtKey2.getText(), txtVal2.getText());
        }
        if (!txtKey3.equals("")) {
            properties.setProperty(txtKey3.getText(), txtVal3.getText());
        }
        if (!txtKey4.equals("")) {
            properties.setProperty(txtKey4.getText(), txtVal4.getText());
        }
        if (!txtKey5.equals("")) {
            properties.setProperty(txtKey5.getText(), txtVal5.getText());
        }
        if (!txtKey6.equals("")) {
            properties.setProperty(txtKey6.getText(), txtVal6.getText());
        }
        if (!txtKey7.equals("")) {
            properties.setProperty(txtKey7.getText(), txtVal7.getText());
        }
        if (!txtKey8.equals("")) {
            properties.setProperty(txtKey8.getText(), txtVal8.getText());
        }
        if (!txtKey9.equals("")) {
            properties.setProperty(txtKey9.getText(), txtVal9.getText());
        }
        if (!txtKey10.equals("")) {
            properties.setProperty(txtKey10.getText(), txtVal10.getText());
        }
        if (!txtKey11.equals("")) {
            properties.setProperty(txtKey11.getText(), txtVal11.getText());
        }
        if (!txtKey12.equals("")) {
            properties.setProperty(txtKey12.getText(), txtVal12.getText());
        }
        if (!txtKey13.equals("")) {
            properties.setProperty(txtKey13.getText(), txtVal13.getText());
        }
        if (!txtKey14.equals("")) {
            properties.setProperty(txtKey14.getText(), txtVal14.getText());
        }
        if (!txtKey15.equals("")) {
            properties.setProperty(txtKey15.getText(), txtVal15.getText());
        }
        if (!txtKey16.equals("")) {
            properties.setProperty(txtKey16.getText(), txtVal16.getText());
        }
        return properties;
    }


    @Override
    protected void checkSubclass() {
        // disable the check that prevents subclassing of SWT components
    }

    private class DeployDirSelectionAdapter extends AbstractDirectorySelectionAdapter {
        DeployDirSelectionAdapter(Shell shell) {
            super(shell, "Please select a deployment directory and click OK");
        }

        @Override
        protected String getInitialFilterPath() {
            return deployDir;
        }

        @Override
        protected void onDirectorySelected(String selectedDir) {
            txtDeploy.setText(selectedDir);
            deployDir = selectedDir;
        }
    }

    private class ImagesDirSelectionAdapter extends AbstractDirectorySelectionAdapter {
        ImagesDirSelectionAdapter(Shell shell) {
            super(shell, "Please select the folder your images are in and click OK");
        }

        protected String getInitialFilterPath() {
            return imagesDir;
        }

        protected void onDirectorySelected(String selectedDir) {
            txtImages.setText(selectedDir);
            imagesDir = selectedDir;
        }
    }
}
