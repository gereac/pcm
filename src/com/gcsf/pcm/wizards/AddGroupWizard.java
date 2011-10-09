package com.gcsf.pcm.wizards;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.gcsf.pcm.gui.ContactsView;
import com.gcsf.pcm.model.UserGroup;
import com.gcsf.pcm.model.treeviewer.GroupsProviderMock;

public class AddGroupWizard extends Wizard {

  private AddGroupWizardPageOne groupPage;

  private AddGroupWizardPageTwo usersPage;
  
  private AddGroupWizardPageThree summaryPage;

  public AddGroupWizard() {
    super();
    setNeedsProgressMonitor(true);
  }

  @Override
  public void addPages() {
    groupPage = new AddGroupWizardPageOne();
    addPage(groupPage);
    usersPage = new AddGroupWizardPageTwo();
    addPage(usersPage);
    summaryPage = new AddGroupWizardPageThree();
    addPage(summaryPage);
    
    groupPage.setSummaryListener(summaryPage);
    usersPage.setSummaryListener(summaryPage);
  }

  @Override
  public boolean performFinish() {
    GroupsProviderMock groups = GroupsProviderMock.getInstance();
    UserGroup aGroup = new UserGroup();
    aGroup.setGroupName(groupPage.getTextName());
    aGroup.setGroupDescription(groupPage.getTextDescription());
    aGroup.getGroupMembers().addAll(usersPage.getSelectedUsers());
    groups.getUserGroups().add(aGroup);
    // Updating the display in the view
    IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
        .getActivePage();
    ContactsView view = (ContactsView) page.findView(ContactsView.VIEW_ID);
    view.getViewer().refresh();

    return true;
  }
}
