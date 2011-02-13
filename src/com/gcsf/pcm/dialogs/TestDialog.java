package com.gcsf.pcm.dialogs;

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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class TestDialog extends Dialog {

  private String SHELL_TITLE = "Add user";

  public TestDialog(Shell parentShell) {
    super(parentShell);
  }

  protected void configureShell(Shell newShell) {
    super.configureShell(newShell);
    newShell.setText(SHELL_TITLE);
  }

  @Override
  protected Control createContents(Composite parent) {

    Composite composite = new Composite(parent, SWT.NULL);
    composite.setLayout(new GridLayout());

    // // Sets up the toolkit.
    // FormToolkit toolkit = new FormToolkit(getShell().getDisplay());
    //
    // // Creates a form instance.
    // Form form = toolkit.createForm(composite);
    // form.setLayoutData(new GridData(GridData.FILL_BOTH));
    // ColorRegistry colorRegistry = JFaceResources.getColorRegistry();
    // form.setBackground(colorRegistry
    // .get(IWorkbenchThemeConstants.ACTIVE_TAB_HIGHLIGHT));
    //
    // // Sets title.
    // // form.setText("Composing an Email Message");
    //
    // // Adds body contents.
    // form.getBody().setLayout(new GridLayout(2, false));
    // Label label = toolkit.createLabel(form.getBody(), "Name: ", SWT.NULL);
    // Text textTo = toolkit.createText(form.getBody(), "", SWT.BORDER);
    // textTo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    //
    // label = toolkit.createLabel(form.getBody(), "Phone: ", SWT.NULL);
    // Text textSubject = toolkit.createText(form.getBody(), "", SWT.BORDER);
    // textSubject.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    //
    // label = toolkit.createLabel(form.getBody(), "Email: ", SWT.NULL);
    // Text textMessage = toolkit.createText(form.getBody(), "", SWT.BORDER);
    // textMessage.setLayoutData(new GridData(GridData.FILL_BOTH));
    //
    // Button buttonOK = toolkit.createButton(form.getBody(), "OK", SWT.PUSH);
    // GridData gridDataOK = new GridData();
    // // gridDataOK.horizontalSpan = 2;
    // gridDataOK.horizontalAlignment = GridData.END;
    // buttonOK.setLayoutData(gridDataOK);
    //
    // Button buttonCancel = toolkit.createButton(form.getBody(), "Cancel",
    // SWT.PUSH);
    // GridData gridDataCancel = new GridData();
    // // gridDataCancel.horizontalSpan = 2;
    // gridDataCancel.horizontalAlignment = GridData.END;
    // buttonCancel.setLayoutData(gridDataCancel);

    // Create a text field
    Text nameText = new Text(parent, SWT.BORDER);
    nameText.setText("some text");
    Text phoneText = new Text(parent, SWT.BORDER);
    phoneText.setText("some text");
    // Create a control decoration for the control.
    // ControlDecoration dec = new ControlDecoration(text, SWT.TOP | SWT.LEFT);
    // Specify the decoration image and description
    // Image image =
    // JFaceResources.getImage(ISharedImages.IMG_DEC_FIELD_WARNING);
    // dec.setImage(image);
    // dec.setDescriptionText("This field is special");
    // dec.setShowOnlyOnFocus(false);
    // Set the layout data to ensure there is enough space for the decoration
    // GridData data = new GridData(IDialogConstants.ENTRY_FIELD_WIDTH,
    // SWT.DEFAULT);
    // data.horizontalIndent = image.getBounds().width;
    // text.setLayoutData(data);

    // Create a control decoration to indicate an error.
    ControlDecoration dec = new ControlDecoration(nameText, SWT.TOP | SWT.LEFT);
    FieldDecoration errorFieldIndicator = FieldDecorationRegistry.getDefault()
        .getFieldDecoration(FieldDecorationRegistry.DEC_ERROR);
    dec.setImage(errorFieldIndicator.getImage());
    dec.setDescriptionText(errorFieldIndicator.getDescription());
    // Set the layout data
    GridData data = new GridData(IDialogConstants.ENTRY_FIELD_WIDTH,
        SWT.DEFAULT);
    data.horizontalIndent = FieldDecorationRegistry.getDefault()
        .getMaximumDecorationWidth();
    nameText.setLayoutData(data);

    return super.createContents(parent);
  }
}
