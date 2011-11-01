package com.gcsf.pcm.handlers.group;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.gcsf.pcm.gui.views.ContactsView;
import com.gcsf.pcm.model.UserGroup;
import com.gcsf.pcm.model.treeviewer.GroupsProviderMock;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class DeleteGroupHandler extends AbstractHandler {

  /**
   * the command has been executed, so extract extract the needed information
   * from the application context.
   */
  public Object execute(ExecutionEvent event) throws ExecutionException {
    IWorkbenchWindow window = HandlerUtil
        .getActiveWorkbenchWindowChecked(event);
    IWorkbenchPage page = window.getActivePage();
    ContactsView view = (ContactsView) page.findView(ContactsView.VIEW_ID);
    ISelection selection = view.getSite().getSelectionProvider().getSelection();
    IStructuredSelection sel = (IStructuredSelection) selection;

    boolean deleteConfirmation = MessageDialog.openConfirm(window.getShell(),
        "Delete Group Confirmation",
        "Are you sure that you want to delete the selected group(s)?");

    if (deleteConfirmation) {
      if (selection != null && selection instanceof IStructuredSelection) {
        List<UserGroup> usergroups = GroupsProviderMock.getInstance()
            .getUserGroups();
        if (usergroups.size() == 1 || sel.size() == usergroups.size()) {
          MessageDialog.openInformation(window.getShell(), "Delete",
              "Last group(s) cannot be deleted");
        } else {
          for (Iterator<UserGroup> iterator = sel.iterator(); iterator
              .hasNext();) {
            UserGroup usergroup = iterator.next();
            usergroups.remove(usergroup);
          }
          view.getViewer().refresh();
        }
      }
    }

    return null;

  }
}
