package com.gcsf.pcm.handlers.user;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.gcsf.pcm.model.User;
import com.gcsf.pcm.model.treeviewer.GroupsProviderMock;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class DeleteUserHandler extends AbstractHandler {
  /**
   * The constructor.
   */
  public DeleteUserHandler() {
  }

  /**
   * the command has been executed, so extract extract the needed information
   * from the application context.
   */
  public Object execute(ExecutionEvent event) throws ExecutionException {
    IWorkbenchWindow window = HandlerUtil
        .getActiveWorkbenchWindowChecked(event);
    ISelection selection = HandlerUtil.getCurrentSelectionChecked(event);
    IStructuredSelection sel = (IStructuredSelection) selection;
    
    boolean deleteConfirmation = MessageDialog.openConfirm(window.getShell(),
        "Delete Group Confirmation",
        "Are you sure that you want to delete the selected users?");

    if (deleteConfirmation) {
      if (selection != null && selection instanceof IStructuredSelection) {
        List<User> users = GroupsProviderMock.getInstance()
            .getUsers();
        if (users.size() == 1 || sel.size() == users.size()) {
          MessageDialog.openInformation(window.getShell(), "Delete",
              "Last user(s) cannot be deleted");
        } else {
          for (Iterator<User> iterator = sel.iterator(); iterator
              .hasNext();) {
            User user = iterator.next();
            users.remove(user);
          }
        }
      }
    }
    
    return null;
  }
  
  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean isHandled() {
    return true;
  }
}
