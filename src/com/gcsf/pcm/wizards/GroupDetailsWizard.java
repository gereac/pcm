package com.gcsf.pcm.wizards;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.gcsf.pcm.gui.ContactsView;
import com.gcsf.pcm.model.UserGroup;
import com.gcsf.pcm.model.treeviewer.GroupsProviderMock;

public class GroupDetailsWizard extends Wizard {

  private GroupDetailsWizardPageOne groupPage;

  private GroupDetailsWizardPageTwo usersPage;
  
  private GroupDetailsWizardPageThree summaryPage;

  public GroupDetailsWizard() {
    super();
    setNeedsProgressMonitor(true);
  }

  @Override
  public void addPages() {
    groupPage = new GroupDetailsWizardPageOne();
    addPage(groupPage);
    usersPage = new GroupDetailsWizardPageTwo();
    addPage(usersPage);
    summaryPage = new GroupDetailsWizardPageThree();
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
    if(usersPage.getSelectedUsers() != null){
      aGroup.getGroupMembers().addAll(usersPage.getSelectedUsers());
    }
    groups.getUserGroups().add(aGroup);
    // Updating the display in the view
    IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
        .getActivePage();
    ContactsView view = (ContactsView) page.findView(ContactsView.VIEW_ID);
    view.getViewer().refresh();
    ((ContactsView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().
        getActivePage().findView(ContactsView.VIEW_ID)).updateStatusBar();

    return true;
  }
}
