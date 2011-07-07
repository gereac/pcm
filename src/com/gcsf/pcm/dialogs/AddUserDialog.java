package com.gcsf.pcm.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class AddUserDialog extends Dialog {

  private String SHELL_TITLE = " Add user - dialog";

  public AddUserDialog(Shell parentShell) {
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

    Group dialogGroup = new Group(parent, SWT.NONE);
    dialogGroup.setText("Add User Details");
    GridLayout groupLayout = new GridLayout(2, false);
    dialogGroup.setLayout(groupLayout);
    GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
    gridData.widthHint = SWT.DEFAULT;
    gridData.heightHint = SWT.DEFAULT;
    dialogGroup.setLayoutData(gridData);
    dialogGroup.setLayoutData(gridData);

    Label nameLabel = new Label(dialogGroup, SWT.LEFT);
    nameLabel.setText("User Name: ");
    final Text nameText = new Text(dialogGroup, SWT.BORDER);
    nameText.setText("some text");
    Label phoneLabel = new Label(dialogGroup, SWT.LEFT);
    phoneLabel.setText("User Phone: ");
    Text phoneText = new Text(dialogGroup, SWT.BORDER);
    phoneText.setText("some text");
    Label emailLabel = new Label(dialogGroup, SWT.LEFT);
    emailLabel.setText("User Email: ");
    Text emailText = new Text(dialogGroup, SWT.BORDER);
    emailText.setText("some text");

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

    nameText.addPaintListener(new PaintListener() {
      public void paintControl(PaintEvent evt) {
        Point s = nameText.getSize();
        Color c = evt.gc.getForeground();

        evt.gc.setForeground(nameText.getDisplay()
            .getSystemColor(SWT.COLOR_RED));
        evt.gc.drawRectangle(0, 0, s.x - 5, s.y - 5);
        evt.gc.setForeground(c);
      }
    });

    return super.createContents(parent);
  }
}
