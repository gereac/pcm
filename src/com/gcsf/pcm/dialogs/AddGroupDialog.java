package com.gcsf.pcm.dialogs;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.gcsf.pcm.model.UserGroup;

public class AddGroupDialog extends TitleAreaDialog {

  private Text text1;

  private Text text2;

  private UserGroup userGroup;

  private Button button1;

  public UserGroup getUserGroup() {
    return userGroup;
  }

  public AddGroupDialog(Shell parentShell) {
    super(parentShell);
  }

  @Override
  protected Control createContents(Composite parent) {
    Control contents = super.createContents(parent);
    setTitle("Add a new User Group");
    setMessage("Please enter the data of the new user group",
        IMessageProvider.INFORMATION);
    return contents;
  }

  @Override
  protected Control createDialogArea(Composite parent) {
    GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    parent.setLayout(layout);
    Label label1 = new Label(parent, SWT.NONE);
    label1.setText("Group Name");
    text1 = new Text(parent, SWT.BORDER);
    Label label2 = new Label(parent, SWT.NONE);
    label2.setText("Group Description");
    text2 = new Text(parent, SWT.BORDER);
    GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_END);
    gd.horizontalSpan = 2;
    return parent;

  }

  @Override
  protected void createButtonsForButtonBar(Composite parent) {
    ((GridLayout) parent.getLayout()).numColumns++;

    Button button = new Button(parent, SWT.PUSH);
    button.setText("OK");
    button.setFont(JFaceResources.getDialogFont());
    button.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent e) {
        if (text1.getText().length() != 0 && text2.getText().length() != 0) {
          userGroup = new UserGroup();
          userGroup.setGroupName(text1.getText());
          userGroup.setGroupDescription(text2.getText());
          close();

        } else {
          setErrorMessage("Please enter all data");
        }
      }
    });
  }
}
