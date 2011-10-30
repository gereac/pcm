package com.gcsf.pcm.dialogs;

import java.util.HashMap;

import org.eclipse.core.databinding.AggregateValidationStatus;
import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.core.databinding.observable.AbstractObservable;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.databinding.swt.ISWTObservable;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.gcsf.pcm.gui.ContactsView;
import com.gcsf.pcm.model.User;

public class UserDetailsDialog extends Dialog {

  private static HashMap<Control, ControlDecoration> decoratorMap = new HashMap<Control, ControlDecoration>();

  private static final String ADD_SHELL_TITLE = " Add user - dialog ";

  private static final String EDIT_SHELL_TITLE = " Edit user - dialog ";

  private static final String ADD_GROUP_TITLE = " Add user ";

  private static final String EDIT_GROUP_TITLE = " Edit user ";

  private AbstractObservable myAbstractObservable = null;

  private Label validationErrorLabel = null;

  private User user = new User();

  private Text nameText = null;

  private Text phoneText = null;

  private Text emailText = null;

  private boolean validationResult = true;

  private DataBindingContext dataBindingContext = new DataBindingContext();

  public UserDetailsDialog(Shell parentShell) {
    super(parentShell);
  }

  public UserDetailsDialog(Shell parentShell,
      AbstractObservable aAbstractObservable) {
    super(parentShell);
    this.myAbstractObservable = aAbstractObservable;
  }

  public UserDetailsDialog(Shell parentShell, User aUser) {
    super(parentShell);
    this.user = aUser;
  }

  protected void configureShell(Shell newShell) {
    super.configureShell(newShell);
    newShell.setText(myAbstractObservable != null ? ADD_SHELL_TITLE
        : EDIT_SHELL_TITLE);
    newShell.setSize(350, 200);
  }

  @Override
  protected Control createContents(Composite parent) {

    Composite composite = new Composite(parent, SWT.NULL);
    composite.setLayout(new GridLayout());
    GridData gridData0 = new GridData(SWT.FILL, SWT.FILL, true, false);
    gridData0.widthHint = SWT.DEFAULT;
    gridData0.heightHint = SWT.DEFAULT;
    composite.setLayoutData(gridData0);

    Group dialogGroup = new Group(composite, SWT.NONE);
    dialogGroup.setText(myAbstractObservable != null ? ADD_GROUP_TITLE
        : EDIT_GROUP_TITLE);

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
    nameText.setText(user.getUserName());
    nameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    createControlDecoration(nameText, "Please enter your name");

    Label phoneLabel = new Label(dialogGroup, SWT.LEFT);
    phoneLabel.setText("User Phone: ");
    phoneText = new Text(dialogGroup, SWT.BORDER);
    phoneText.setText(user.getUserPhone());
    phoneText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    createControlDecoration(phoneText, "Please enter your phone");

    Label emailLabel = new Label(dialogGroup, SWT.LEFT);
    emailLabel.setText("User Email: ");
    emailText = new Text(dialogGroup, SWT.BORDER);
    emailText.setText(user.getUserEmail());
    emailText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    createControlDecoration(emailText, "Please enter your e-mail");

    validationErrorLabel = new Label(composite, SWT.NONE);
    validationErrorLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

    GridDataFactory.swtDefaults().hint(150, SWT.DEFAULT)
        .applyTo(validationErrorLabel);

    return super.createContents(parent);
  }

  @Override
  protected void okPressed() {
    if (dataBindingContext != null) {
      dataBindingContext.dispose();
      dataBindingContext = new DataBindingContext();
    }
    dataBindingContext.bindValue(SWTObservables.observeText(nameText,
        SWT.Modify), PojoObservables.observeValue(user, "userName"),
        new UpdateValueStrategy()
            .setAfterConvertValidator(new StringRequiredValidator(
                "Please enter name")), null);
    dataBindingContext.bindValue(SWTObservables.observeText(phoneText,
        SWT.Modify), PojoObservables.observeValue(user, "userPhone"),
        new UpdateValueStrategy()
            .setAfterConvertValidator(new StringRequiredValidator(
                "Please enter phone")), null);
    dataBindingContext.bindValue(SWTObservables.observeText(emailText,
        SWT.Modify), PojoObservables.observeValue(user, "userEmail"),
        new UpdateValueStrategy()
            .setAfterConvertValidator(new StringRequiredValidator(
                "Please enter email")), null);

    AggregateValidationStatus aggregateValidationStatus = new AggregateValidationStatus(
        dataBindingContext.getBindings(),
        AggregateValidationStatus.MAX_SEVERITY);

    aggregateValidationStatus.addChangeListener(new IChangeListener() {
      public void handleChange(ChangeEvent event) {
        setValidationResult(true);
        for (Object o : dataBindingContext.getBindings()) {
          Binding binding = (Binding) o;
          IStatus status = (IStatus) binding.getValidationStatus().getValue();
          Control control = null;
          if (binding.getTarget() instanceof ISWTObservable) {
            ISWTObservable swtObservable = (ISWTObservable) binding.getTarget();
            control = (Control) swtObservable.getWidget();
          }
          ControlDecoration decoration = decoratorMap.get(control);
          if (decoration != null) {
            if (status.isOK()) {
              decoration.hide();
            } else {
              setValidationResult(false);
              decoration.setDescriptionText(status.getMessage());
              decoration.show();
            }
          }
        }
      }
    });

    dataBindingContext.bindValue(
        SWTObservables.observeText(validationErrorLabel),
        aggregateValidationStatus, null, null);
    if (getValidationResult() && myAbstractObservable != null) {
      ((WritableList) myAbstractObservable).add(new User(nameText.getText(),
          phoneText.getText(), emailText.getText()));
      ((ContactsView) PlatformUI.getWorkbench().getActiveWorkbenchWindow()
          .getActivePage().findView(ContactsView.VIEW_ID)).updateStatusBar();
      super.okPressed();
    }
  }

  private void setValidationResult(boolean aValidationResult) {
    validationResult = aValidationResult;
  }

  private boolean getValidationResult() {
    return validationResult;
  }

  private static void createControlDecoration(Control control, String hoverText) {
    ControlDecoration controlDecoration = new ControlDecoration(control,
        SWT.LEFT | SWT.TOP);
    controlDecoration.setDescriptionText(hoverText);
    FieldDecoration fieldDecoration = FieldDecorationRegistry.getDefault()
        .getFieldDecoration(FieldDecorationRegistry.DEC_ERROR);
    controlDecoration.setImage(fieldDecoration.getImage());
    controlDecoration.hide();
    decoratorMap.put(control, controlDecoration);
  }

  @Override
  protected boolean isResizable() {
    return true;
  }

}
