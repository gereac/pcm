package com.gcsf.pcm.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class AddGroupWizardPageTwo extends WizardPage {

  private Composite container;

  public AddGroupWizardPageTwo() {
    super("Second Page");
    setTitle("Users details");
    setDescription("Please complete the users details. [for the moment no user can be operated here]");
    setControl(container);
  }

  @Override
  public void createControl(Composite parent) {
    container = new Composite(parent, SWT.NULL);
    GridLayout layout = new GridLayout();
    container.setLayout(layout);
    layout.numColumns = 2;

    // Required to avoid an error in the system
    setControl(container);
    setPageComplete(true);
  }

}
