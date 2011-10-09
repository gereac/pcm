package com.gcsf.pcm.wizards;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class AddGroupWizardPageOne extends WizardPage {

  private static final int DESC_LENGTH = 15;

  private Text textName;

  private Text textDescription;

  private Composite container;
  
  private   ISummaryListener                mySummaryListener  = null;

  public AddGroupWizardPageOne() {
    super("First Page");
    setTitle("Group details");
    setDescription("Please complete the group details.");
  }

  @Override
  public void createControl(Composite parent) {
    container = new Composite(parent, SWT.NULL);
    GridLayout layoutContainer = new GridLayout(1, false);
    layoutContainer.marginWidth = 0;
    layoutContainer.marginHeight = 0;
    layoutContainer.verticalSpacing = 0;
    layoutContainer.horizontalSpacing = 0;
    container.setLayout(layoutContainer);
    GridData gridDataContainer = new GridData(SWT.FILL, SWT.FILL, true, true);
    gridDataContainer.widthHint = SWT.DEFAULT;
    gridDataContainer.heightHint = SWT.DEFAULT;
    container.setLayoutData(gridDataContainer);

    Composite upperComposite = new Composite(container, SWT.NULL);
    GridLayout layout = new GridLayout(2, false);
    layout.marginWidth = 0;
    layout.marginHeight = 0;
    layout.verticalSpacing = 0;
    layout.horizontalSpacing = 0;
    upperComposite.setLayout(layout);
    GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
    gridData.widthHint = SWT.DEFAULT;
    gridData.heightHint = SWT.DEFAULT;
    upperComposite.setLayoutData(gridData);

    Label labelName = new Label(upperComposite, SWT.NULL);
    labelName.setText("Group Name");
    GridData gridData2 = new GridData(SWT.LEFT, SWT.TOP, false, false);
    gridData2.widthHint = SWT.DEFAULT;
    gridData2.heightHint = SWT.DEFAULT;
    labelName.setLayoutData(gridData2);

    textName = new Text(upperComposite, SWT.BORDER | SWT.SINGLE);
    textName.setText("");
    textName.addKeyListener(new KeyListener() {
      @Override
      public void keyPressed(KeyEvent e) {
      }

      @Override
      public void keyReleased(KeyEvent e) {
        if (e.doit == false) {
          // event already consumed
          return;
        }
        if (!textName.getText().isEmpty()
            && !textDescription.getText().isEmpty()) {
          setPageComplete(true);
        } else {
          setPageComplete(false);
        }
      }
    });
    GridData gd = new GridData(SWT.FILL, SWT.FILL, true, false);
    gd.widthHint = SWT.DEFAULT;
    gd.heightHint = SWT.DEFAULT;
    gd.horizontalIndent = FieldDecorationRegistry.getDefault()
        .getMaximumDecorationWidth();
    textName.setLayoutData(gd);

    Composite bottomComposite = new Composite(container, SWT.NULL);
    GridLayout gridLayout = new GridLayout(2, false);
    gridLayout.marginWidth = 0;
    gridLayout.marginHeight = 0;
    gridLayout.verticalSpacing = 0;
    gridLayout.horizontalSpacing = 0;
    bottomComposite.setLayout(gridLayout);
    GridData gridData3 = new GridData(SWT.FILL, SWT.FILL, true, true);
    gridData3.widthHint = SWT.DEFAULT;
    gridData3.heightHint = SWT.DEFAULT;
    bottomComposite.setLayoutData(gridData3);

    Label labelDescription = new Label(bottomComposite, SWT.NULL);
    labelDescription.setText("Group Description");
    GridData gridData4 = new GridData(SWT.LEFT, SWT.TOP, false, false);
    gridData4.widthHint = SWT.DEFAULT;
    gridData4.grabExcessHorizontalSpace = false;
    gridData4.heightHint = SWT.DEFAULT;
    labelDescription.setLayoutData(gridData4);

    textDescription = new Text(bottomComposite, SWT.BORDER | SWT.MULTI);
    textDescription.setText("");
    textDescription.addKeyListener(new KeyListener() {
      @Override
      public void keyPressed(KeyEvent e) {
      }

      @Override
      public void keyReleased(KeyEvent e) {
        if (!textDescription.getText().isEmpty()
            && !textName.getText().isEmpty()) {
          setPageComplete(true);
        } else {
          setPageComplete(false);
        }
      }
    });
    final ControlDecoration dec = new ControlDecoration(textDescription,
        SWT.TOP | SWT.LEFT);
    FieldDecoration errorFieldIndicator = FieldDecorationRegistry.getDefault()
        .getFieldDecoration(FieldDecorationRegistry.DEC_WARNING);
    dec.setImage(errorFieldIndicator.getImage());
    dec.setDescriptionText("the description cannot be longer than "
        + DESC_LENGTH + " characters");
    dec.setMarginWidth(1);
    dec.hide();
    textDescription.addVerifyListener(new VerifyListener() {
      @Override
      public void verifyText(VerifyEvent event) {
        if (event.doit == false) {
          // event already consumed
          return;
        }
        if (textDescription.getText().length() > DESC_LENGTH) {
          // show the field decorator
          dec.show();
          textDescription.redraw();
          event.doit = false;
          if (event.character == '\u0008' || event.character == '\u007F') {
            // allow delete and/or backspace to be processed
            event.doit = true;
          }
        } else {
          // hide the field decorator
          dec.hide();
          textDescription.redraw();
        }
      }
    });
    textDescription.addPaintListener(new PaintListener() {
      public void paintControl(PaintEvent evt) {
        if (textDescription.getText().length() > DESC_LENGTH) {
          Point s = textDescription.getSize();
          Color c = evt.gc.getForeground();
          evt.gc.setForeground(textDescription.getDisplay().getSystemColor(
              SWT.COLOR_RED));
          evt.gc.drawRectangle(0, 0, s.x - 5, s.y - 5);
          evt.gc.setForeground(c);
        }
      }
    });

    GridData gd1 = new GridData(SWT.FILL, SWT.FILL, true, true);
    gd1.widthHint = SWT.DEFAULT;
    gd1.heightHint = SWT.DEFAULT;
    gd1.horizontalIndent = FieldDecorationRegistry.getDefault()
        .getMaximumDecorationWidth();
    textDescription.setLayoutData(gd1);
    // Required to avoid an error in the system
    setControl(container);
    setPageComplete(false);
  }

  public String getTextName() {
    if (mySummaryListener != null) {
      mySummaryListener.setTitle(textName.getText());
    }
    return textName.getText();
  }

  @Override
  public boolean canFlipToNextPage() {
  	return super.canFlipToNextPage();
  }

  @Override
  public boolean isPageComplete() {
  	return super.isPageComplete() && (!getTextName().isEmpty());
  }

  public String getTextDescription() {
    if (mySummaryListener != null) {
      mySummaryListener.setDescription(textDescription.getText());
    }
    return textDescription.getText();
  }
  
  public void setSummaryListener(ISummaryListener aListener) {
    mySummaryListener = aListener;
  }


}
