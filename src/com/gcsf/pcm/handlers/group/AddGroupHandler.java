package com.gcsf.pcm.handlers.group;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.gcsf.pcm.dialogs.AddGroupDialog;
import com.gcsf.pcm.gui.GroupsView;
import com.gcsf.pcm.model.treeviewer.GroupsProviderMock;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class AddGroupHandler extends AbstractHandler {

  /**
   * the command has been executed, so extract extract the needed information
   * from the application context.
   */
  public Object execute(ExecutionEvent event) throws ExecutionException {
    IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
    GroupsProviderMock groups = GroupsProviderMock.getInstance();
    AddGroupDialog dialog = new AddGroupDialog(window.getShell());
    dialog.open();
    if (dialog.getUserGroup() != null) {
      groups.getUserGroups().add(dialog.getUserGroup());
      // Updating the display in the view
      IWorkbenchPage page = window.getActivePage();
      GroupsView view = (GroupsView) page.findView(GroupsView.ID);
      view.getViewer().refresh();
    }
    return null;
  }
}
