package com.gcsf.pcm.splashHandlers;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.splash.AbstractSplashHandler;

/**
 * @since 3.3
 * 
 */
public class InteractiveSplashHandler extends AbstractSplashHandler {

  private final static int F_LABEL_HORIZONTAL_INDENT = 175;

  private final static int F_BUTTON_WIDTH_HINT = 80;

  private final static int F_TEXT_WIDTH_HINT = 175;

  private final static int F_COLUMN_COUNT = 3;

  private Composite fCompositeLogin;

  private Text fTextUsername;

  private Text fTextPassword;

  private Button fButtonOK;

  private Button fButtonCancel;

  private boolean fAuthenticated;

  private Label progressLabel;

  private ProgressBar progressBar;

  private volatile int loginStatus = -1;

  /**
	 * 
	 */
  public InteractiveSplashHandler() {
    fCompositeLogin = null;
    fTextUsername = null;
    fTextPassword = null;
    fButtonOK = null;
    fButtonCancel = null;
    fAuthenticated = false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.eclipse.ui.splash.AbstractSplashHandler#init(org.eclipse.swt.widgets
   * .Shell)
   */
  public void init(final Shell splash) {
    // Store the shell
    super.init(splash);
    // Configure the shell layout
    configureUISplash();
    // Create UI
    createUI();
    // Create UI listeners
    createUIListeners();
    // Force the splash screen to layout
    splash.layout(true);
    // Keep the splash screen visible and prevent the RCP application from
    // loading until the close button is clicked.
    doEventLoop();
  }

  /**
	 * 
	 */
  private void doEventLoop() {
    Shell splash = getSplash();
    while (fAuthenticated == false) {
      if (splash.getDisplay().readAndDispatch() == false) {
        if (loginStatus == 1) {
          loginSuccess();
        } else if (loginStatus == 2) {
          loginFailure();
        }

        splash.getDisplay().sleep();
      }
    }
  }

  private void loginSuccess() {
    toggelCheckProgress(false);
    fCompositeLogin.setVisible(false);
    fAuthenticated = true;
    loginStatus = -1;
  }

  private void loginFailure() {
    toggelCheckProgress(false);
    loginStatus = -1;
    MessageDialog.openError(getSplash(), "Authentification failed",
        "Your username or password was wrong");
  }

  // private void handleButtonOKWidgetSelected() {
  // final String username = fTextUsername.getText();
  // final String password = fTextPassword.getText();
  //
  // toggelCheckProgress(true);
  // Thread t = new Thread() {
  // public void run() {
  // if( login(username,password) ) {
  // loginStatus = 1;
  // } else {
  // loginStatus = 2;
  // }
  // }
  // }
  // t.start();
  // }

  private void toggelCheckProgress(boolean state) {
    progressLabel.setVisible(state);
    progressBar.setVisible(state);
    fCompositeLogin.layout();
  }

  /**
	 * 
	 */
  private void createUIListeners() {
    // Create the OK button listeners
    createUIListenersButtonOK();
    // Create the cancel button listeners
    createUIListenersButtonCancel();
  }

  /**
	 * 
	 */
  private void createUIListenersButtonCancel() {
    fButtonCancel.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent e) {
        handleButtonCancelWidgetSelected();
      }
    });
  }

  /**
	 * 
	 */
  private void handleButtonCancelWidgetSelected() {
    // Abort the loading of the RCP application
    getSplash().getDisplay().close();
    System.exit(0);
  }

  /**
	 * 
	 */
  private void createUIListenersButtonOK() {
    fButtonOK.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent e) {
        handleButtonOKWidgetSelected();
      }
    });
  }

  /**
	 * 
	 */
  private void handleButtonOKWidgetSelected() {
    String username = fTextUsername.getText();
    String password = fTextPassword.getText();
    // Aunthentication is successful if a user provides any username and
    // any password
    if ((username.length() > 0) && (password.length() > 0)) {
      if (username.equalsIgnoreCase("catalin")
          && password.equalsIgnoreCase("gerea")) {
        fAuthenticated = true;
      }
    } else {
      MessageDialog.openError(getSplash(), "Authentication Failed", //$NON-NLS-1$
          "A username and password must be specified to login."); //$NON-NLS-1$
    }
  }

  /**
	 * 
	 */
  private void createUI() {
    // Create the login panel
    createUICompositeLogin();
    // Create the blank spanner
    createUICompositeBlank();
    // Create the user name label
    createUILabelUserName();
    // Create the user name text widget
    createUITextUserName();
    // Create the password label
    createUILabelPassword();
    // Create the password text widget
    createUITextPassword();
    // Create the blank label
    createUILabelBlank();
    // Create the OK button
    createUIButtonOK();
    // Create the cancel button
    createUIButtonCancel();
    // Create progress
    createProgressInfo();
  }

  private void createProgressInfo() {
    progressLabel = new Label(fCompositeLogin, SWT.NONE);
    progressLabel.setText("Ueberpreufung laeuft");
    GridData data = new GridData();
    data.horizontalIndent = F_LABEL_HORIZONTAL_INDENT - 50;
    progressLabel.setLayoutData(data);
    progressLabel.setVisible(false);

    progressBar = new ProgressBar(fCompositeLogin, SWT.NONE | SWT.INDETERMINATE);
    data = new GridData(SWT.NONE, SWT.NONE, false, false);
    data.widthHint = F_TEXT_WIDTH_HINT;
    data.horizontalSpan = 2;
    progressBar.setLayoutData(data);
    progressBar.setVisible(false);
  }

  /**
	 * 
	 */
  private void createUIButtonCancel() {
    // Create the button
    fButtonCancel = new Button(fCompositeLogin, SWT.PUSH);
    fButtonCancel.setText("Cancel"); //$NON-NLS-1$
    // Configure layout data
    GridData data = new GridData(SWT.NONE, SWT.NONE, false, false);
    data.widthHint = F_BUTTON_WIDTH_HINT;
    data.verticalIndent = 10;
    fButtonCancel.setLayoutData(data);
  }

  /**
	 * 
	 */
  private void createUIButtonOK() {
    // Create the button
    fButtonOK = new Button(fCompositeLogin, SWT.PUSH);
    fButtonOK.setText("OK"); //$NON-NLS-1$
    // Configure layout data
    GridData data = new GridData(SWT.NONE, SWT.NONE, false, false);
    data.widthHint = F_BUTTON_WIDTH_HINT;
    data.verticalIndent = 10;
    fButtonOK.setLayoutData(data);
    fCompositeLogin.getShell().setDefaultButton(fButtonOK);
  }

  /**
	 * 
	 */
  private void createUILabelBlank() {
    Label label = new Label(fCompositeLogin, SWT.NONE);
    label.setVisible(false);
  }

  /**
	 * 
	 */
  private void createUITextPassword() {
    // Create the text widget
    int style = SWT.PASSWORD | SWT.BORDER;
    fTextPassword = new Text(fCompositeLogin, style);
    // Configure layout data
    GridData data = new GridData(SWT.NONE, SWT.NONE, false, false);
    data.widthHint = F_TEXT_WIDTH_HINT;
    data.horizontalSpan = 2;
    fTextPassword.setLayoutData(data);
    fTextPassword.setEchoChar('*');
  }

  /**
	 * 
	 */
  private void createUILabelPassword() {
    // Create the label
    Label label = new Label(fCompositeLogin, SWT.NONE);
    label.setText("&Password:"); //$NON-NLS-1$
    // Configure layout data
    GridData data = new GridData();
    data.horizontalIndent = F_LABEL_HORIZONTAL_INDENT;
    label.setLayoutData(data);
  }

  /**
	 * 
	 */
  private void createUITextUserName() {
    // Create the text widget
    fTextUsername = new Text(fCompositeLogin, SWT.BORDER);
    // Configure layout data
    GridData data = new GridData(SWT.NONE, SWT.NONE, false, false);
    data.widthHint = F_TEXT_WIDTH_HINT;
    data.horizontalSpan = 2;
    fTextUsername.setLayoutData(data);
    fTextUsername.setEchoChar('*');
  }

  /**
	 * 
	 */
  private void createUILabelUserName() {
    // Create the label
    Label label = new Label(fCompositeLogin, SWT.NONE);
    label.setText("&User Name:"); //$NON-NLS-1$
    // Configure layout data
    GridData data = new GridData();
    data.horizontalIndent = F_LABEL_HORIZONTAL_INDENT;
    label.setLayoutData(data);
  }

  /**
	 * 
	 */
  private void createUICompositeBlank() {
    Composite spanner = new Composite(fCompositeLogin, SWT.NONE);
    GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
    data.horizontalSpan = F_COLUMN_COUNT;
    spanner.setLayoutData(data);
  }

  /**
	 * 
	 */
  private void createUICompositeLogin() {
    // Create the composite
    fCompositeLogin = new Composite(getSplash(), SWT.BORDER);
    GridLayout layout = new GridLayout(F_COLUMN_COUNT, false);
    fCompositeLogin.setLayout(layout);
  }

  /**
	 * 
	 */
  private void configureUISplash() {
    // Configure layout
    FillLayout layout = new FillLayout();
    getSplash().setLayout(layout);
    // Force shell to inherit the splash background
    getSplash().setBackgroundMode(SWT.INHERIT_DEFAULT);
  }

}
