package com.gcsf.pcm.wizards;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.gcsf.pcm.gui.ContactsView;
import com.gcsf.pcm.model.UserGroup;
import com.gcsf.pcm.model.treeviewer.GroupsProviderMock;

public class AddGroupWizard extends Wizard {

  private AddGroupWizardPageOne one;

  private AddGroupWizardPageTwo two;

  public AddGroupWizard() {
    super();
    setNeedsProgressMonitor(true);
  }

  @Override
  public void addPages() {
    one = new AddGroupWizardPageOne();
    two = new AddGroupWizardPageTwo();
    addPage(one);
    addPage(two);
  }

  @Override
  public boolean performFinish() {
    System.out.println(one.getTextName());
    System.out.println(one.getTextDescription());
    GroupsProviderMock groups = GroupsProviderMock.getInstance();
    UserGroup aGroup = new UserGroup();
    aGroup.setGroupName(one.getTextName());
    aGroup.setGroupDescription(one.getTextDescription());
    groups.getUserGroups().add(aGroup);
    // Updating the display in the view
    IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
        .getActivePage();
    ContactsView view = (ContactsView) page.findView(ContactsView.VIEW_ID);
    view.getViewer().refresh();
    // just put the result to the console, imagine here much more
    // intelligent stuff.

    return true;
  }
}
