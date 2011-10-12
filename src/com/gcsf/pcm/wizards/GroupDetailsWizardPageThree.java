package com.gcsf.pcm.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

import com.gcsf.pcm.model.User;
import com.gcsf.pcm.utils.SuppressKeyboardListener;

public class GroupDetailsWizardPageThree extends WizardPage implements ISummaryListener {
  
  private Composite container;
  private Composite groupInfoComposite;
  private Composite groupUsersComposite;
  
  private List      listGroupUsers            = null;
  
  private Text groupTitleText = null;
  
  private Text groupDescriptionText = null;

  protected GroupDetailsWizardPageThree() {
    super("Page Three");
    setTitle("Summary for group addition/editing");
    setDescription("These are the details inserted by you");
    setControl(container);
  }

  @Override
  public void createControl(Composite aParent) {
    container = new Composite(aParent, SWT.NONE);
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
    
    groupInfoComposite = new Composite(container, SWT.NONE);
    GridData gridData = new GridData();
    gridData.horizontalAlignment       = GridData.FILL;
    gridData.grabExcessHorizontalSpace = true;
    gridData.grabExcessVerticalSpace   = true;
    gridData.verticalAlignment         = GridData.FILL;
    GridLayout layout1 = new GridLayout();
    layout1.horizontalSpacing = 0;
    layout1.marginWidth       = 0;
    layout1.marginHeight      = 0;
    layout1.horizontalSpacing = 5;
    layout1.verticalSpacing   = 5;
    layout1.marginWidth       = 5;
    layout1.marginHeight      = 5;
    layout1.verticalSpacing   = 0;
    groupInfoComposite.setLayout(layout1);
    groupInfoComposite.setLayoutData(gridData);
    groupTitleText = new Text(groupInfoComposite, SWT.BORDER);
    groupTitleText.setEditable(false);
    groupTitleText.setLayoutData(gridData);
    groupTitleText.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
    groupDescriptionText = new Text(groupInfoComposite, SWT.BORDER);
    groupDescriptionText.setEditable(false);
    groupDescriptionText.setLayoutData(gridData);
    groupDescriptionText.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
    
    groupUsersComposite = new Composite(container, SWT.NONE);
    GridData gridData1 = new GridData();
    gridData1.horizontalAlignment       = GridData.FILL;
    gridData1.grabExcessHorizontalSpace = true;
    gridData1.grabExcessVerticalSpace   = true;
    gridData1.verticalAlignment         = GridData.FILL;
    GridLayout layout11 = new GridLayout();
    layout11.horizontalSpacing = 0;
    layout11.marginWidth       = 0;
    layout11.marginHeight      = 0;
    layout11.horizontalSpacing = 5;
    layout11.verticalSpacing   = 5;
    layout11.marginWidth       = 5;
    layout11.marginHeight      = 5;
    layout11.verticalSpacing   = 0;
    groupUsersComposite.setLayout(layout11);
    groupUsersComposite.setLayoutData(gridData1);
    gridData = new GridData(GridData.FILL_BOTH);
    gridData.grabExcessHorizontalSpace = true;
    gridData.grabExcessVerticalSpace   = true;
    
    listGroupUsers = new List(groupUsersComposite, SWT.BORDER | SWT.V_SCROLL);
    listGroupUsers.setEnabled(true);
    listGroupUsers.setLayoutData(gridData1);
    new SuppressKeyboardListener(listGroupUsers);
    // Required to avoid an error in the system
    setControl(container);
  }

  public void setGroupTitle(String title) {
    if(title != null && !title.equals("")){
      groupTitleText.setText(title);
    } else {
      groupTitleText.setText("");
    }
  }
  
  public void setGroupDescription(String description) {
    if(description != null && !description.equals("")){
      groupDescriptionText.setText(description);
    } else {
      groupDescriptionText.setText("");
    }
  }

  public void setUsers(java.util.List<User> users) {
    listGroupUsers.removeAll();
    if (users != null) {
      for (User user : users) {
        listGroupUsers.add(user.getUserName() + ":" + user.getUserEmail());
      }
    }
  }


}
