package com.gcsf.pcm.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class AddGroupWizardPageOne extends WizardPage {

  private Text text1;

  private Text text2;

  private Composite container;

  public AddGroupWizardPageOne() {
    super("First Page");
    setTitle("Group details");
    setDescription("Please complete the group details.");
  }

  @Override
  public void createControl(Composite parent) {
    container = new Composite(parent, SWT.NULL);
    GridLayout layout = new GridLayout();
    container.setLayout(layout);
    layout.numColumns = 2;

    Label label1 = new Label(container, SWT.NULL);
    label1.setText("Group Name");
    text1 = new Text(container, SWT.BORDER | SWT.SINGLE);
    text1.setText("");
    text1.addKeyListener(new KeyListener() {
      @Override
      public void keyPressed(KeyEvent e) {
      }

      @Override
      public void keyReleased(KeyEvent e) {
        if (!text1.getText().isEmpty() && !text2.getText().isEmpty()) {
          setPageComplete(true);
        }
      }
    });
    GridData gd = new GridData(GridData.FILL_HORIZONTAL);
    text1.setLayoutData(gd);

    Label label2 = new Label(container, SWT.NULL);
    label2.setText("Group Description");
    text2 = new Text(container, SWT.BORDER | SWT.SINGLE);
    text2.setText("");
    text2.addKeyListener(new KeyListener() {
      @Override
      public void keyPressed(KeyEvent e) {
      }

      @Override
      public void keyReleased(KeyEvent e) {
        if (!text2.getText().isEmpty() && !text1.getText().isEmpty()) {
          setPageComplete(true);

        }
      }
    });
    GridData gd1 = new GridData(GridData.FILL_HORIZONTAL);
    text2.setLayoutData(gd1);
    // Required to avoid an error in the system
    setControl(container);
    setPageComplete(false);
  }

  public String getText1() {
    return text1.getText();
  }

  public String getText2() {
    return text2.getText();
  }

}
