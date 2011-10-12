package com.gcsf.pcm.dialogs;

import org.eclipse.core.databinding.observable.AbstractObservable;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.gcsf.pcm.model.User;

public class UserDetailsDialog extends Dialog {

  private String ADD_SHELL_TITLE = " Add user - dialog";
  
  private String EDIT_SHELL_TITLE = " Edit user - dialog";
  
  private AbstractObservable myAbstractObservable = null;
  
  User user = null;
  
  Text nameText = null;
  
  Text phoneText = null;
  
  Text emailText = null;

  public UserDetailsDialog(Shell parentShell) {
    super(parentShell);
  }
  
  public UserDetailsDialog(Shell parentShell, AbstractObservable aAbstractObservable) {
    super(parentShell);
    this.myAbstractObservable = aAbstractObservable;
  }
  
  public UserDetailsDialog(Shell parentShell, User aUser) {
    super(parentShell);
    this.user = aUser;
  }

  protected void configureShell(Shell newShell) {
    super.configureShell(newShell);
    if(user == null){
      newShell.setText(ADD_SHELL_TITLE);
    } else {
      newShell.setText(EDIT_SHELL_TITLE);
    }
  }

  @Override
  protected Control createContents(Composite parent) {

    Composite composite = new Composite(parent, SWT.NULL);
    composite.setLayout(new GridLayout());

    Group dialogGroup = new Group(parent, SWT.NONE);
    if(user == null){
      dialogGroup.setText(ADD_SHELL_TITLE);
    } else {
      dialogGroup.setText(EDIT_SHELL_TITLE);
    }
    GridLayout groupLayout = new GridLayout(2, false);
    dialogGroup.setLayout(groupLayout);
    GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
    gridData.widthHint = SWT.DEFAULT;
    gridData.heightHint = SWT.DEFAULT;
    dialogGroup.setLayoutData(gridData);
    dialogGroup.setLayoutData(gridData);

    Label nameLabel = new Label(dialogGroup, SWT.LEFT);
    nameLabel.setText("User Name: ");
    nameText = new Text(dialogGroup, SWT.BORDER);
    if(user == null){
      nameText.setText("");
    } else {
      nameText.setText(user.getUserName());
    }
    Label phoneLabel = new Label(dialogGroup, SWT.LEFT);
    phoneLabel.setText("User Phone: ");
    phoneText = new Text(dialogGroup, SWT.BORDER);
    if(user == null){
      phoneText.setText("");
    } else {
      phoneText.setText(user.getUserName());
    }
    Label emailLabel = new Label(dialogGroup, SWT.LEFT);
    emailLabel.setText("User Email: ");
    emailText = new Text(dialogGroup, SWT.BORDER);
    if(user == null){
      emailText.setText("");
    } else {
      emailText.setText(user.getUserName());
    }

    // Create a control decoration to indicate an error.
    ControlDecoration dec = new ControlDecoration(nameText, SWT.TOP | SWT.LEFT);
    FieldDecoration errorFieldIndicator = FieldDecorationRegistry.getDefault()
        .getFieldDecoration(FieldDecorationRegistry.DEC_ERROR);
    dec.setImage(errorFieldIndicator.getImage());
    dec.setDescriptionText(errorFieldIndicator.getDescription());
    dec.setMarginWidth(1);
    dec.hide();
    // Set the layout data
    GridData data = new GridData(IDialogConstants.ENTRY_FIELD_WIDTH,
        SWT.DEFAULT);
    data.horizontalIndent = FieldDecorationRegistry.getDefault()
        .getMaximumDecorationWidth();
    nameText.setLayoutData(data);

//    nameText.addPaintListener(new PaintListener() {
//      public void paintControl(PaintEvent evt) {
//        Point s = nameText.getSize();
//        Color c = evt.gc.getForeground();
//
//        evt.gc.setForeground(nameText.getDisplay()
//            .getSystemColor(SWT.COLOR_RED));
//        evt.gc.drawRectangle(0, 0, s.x - 5, s.y - 5);
//        evt.gc.setForeground(c);
//      }
//    });

    return super.createContents(parent);
  }

  @Override
  protected void okPressed() {
   ((WritableList)myAbstractObservable).add(new User(nameText.getText(), phoneText.getText(), emailText.getText()));
    super.okPressed();
  }
}
