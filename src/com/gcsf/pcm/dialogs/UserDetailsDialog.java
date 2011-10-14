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
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
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

import com.gcsf.pcm.model.User;

public class UserDetailsDialog extends Dialog {

  private static HashMap<Control, ControlDecoration> decoratorMap = new HashMap<Control, ControlDecoration>();

  private String ADD_SHELL_TITLE = " Add user - dialog";

  private String EDIT_SHELL_TITLE = " Edit user - dialog";

  private AbstractObservable myAbstractObservable = null;

  private User user = new User();

  private Text nameText = null;

  private Text phoneText = null;

  private Text emailText = null;

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
//    if (user == null) {
      newShell.setText(ADD_SHELL_TITLE);
//    } else {
//      newShell.setText(EDIT_SHELL_TITLE);
//    }
  }

  @Override
  protected Control createContents(Composite parent) {

    Composite composite = new Composite(parent, SWT.NULL);
    composite.setLayout(new GridLayout());

    Group dialogGroup = new Group(composite, SWT.NONE);
//    if (user == null) {
      dialogGroup.setText(ADD_SHELL_TITLE);
//    } else {
//      dialogGroup.setText(EDIT_SHELL_TITLE);
//    }
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
//    if (user == null) {
      nameText.setText("");
//    } else {
//      nameText.setText(user.getUserName());
//    }
    nameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    createControlDecoration(nameText, "Please enter your name");

    Label phoneLabel = new Label(dialogGroup, SWT.LEFT);
    phoneLabel.setText("User Phone: ");
    phoneText = new Text(dialogGroup, SWT.BORDER);
//    if (user == null) {
      phoneText.setText("");
//    } else {
//      phoneText.setText(user.getUserName());
//    }
    phoneText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    createControlDecoration(phoneText, "Please enter your phone");

    Label emailLabel = new Label(dialogGroup, SWT.LEFT);
    emailLabel.setText("User Email: ");
    emailText = new Text(dialogGroup, SWT.BORDER);
//    if (user == null) {
      emailText.setText("");
//    } else {
//      emailText.setText(user.getUserName());
//    }
    emailText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    createControlDecoration(emailText, "Please enter your e-mail");

    Label validationErrorLabel = new Label(composite, SWT.NONE);
    validationErrorLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

    GridDataFactory.swtDefaults().hint(150, SWT.DEFAULT)
        .applyTo(validationErrorLabel);

    final DataBindingContext dataBindingContext = new DataBindingContext();
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

    return super.createContents(parent);
  }

  @Override
  protected void okPressed() {
    ((WritableList) myAbstractObservable).add(new User(nameText.getText(),
        phoneText.getText(), emailText.getText()));
    super.okPressed();
  }

  private static void createControlDecoration(Control control, String hoverText) {
    ControlDecoration controlDecoration = new ControlDecoration(control,
        SWT.LEFT | SWT.TOP);
    controlDecoration.setDescriptionText(hoverText);
    FieldDecoration fieldDecoration = FieldDecorationRegistry.getDefault()
        .getFieldDecoration(FieldDecorationRegistry.DEC_ERROR);
    controlDecoration.setImage(fieldDecoration.getImage());
    decoratorMap.put(control, controlDecoration);
  }

  private static class StringRequiredValidator implements IValidator {

    private final String errorText;

    public StringRequiredValidator(String errorText) {
      super();
      this.errorText = errorText;
    }

    public IStatus validate(Object value) {
      if (value instanceof String) {
        String text = (String) value;
        if (text.trim().length() == 0) {
          return ValidationStatus.error(errorText);
        }
      }
      return Status.OK_STATUS;
    }
  }

}