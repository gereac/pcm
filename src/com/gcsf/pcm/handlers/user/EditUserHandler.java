package com.gcsf.pcm.handlers.user;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

import com.gcsf.pcm.dialogs.UserDetailsDialog;
import com.gcsf.pcm.model.User;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class EditUserHandler extends AbstractHandler {
  /**
   * The constructor.
   */
  public EditUserHandler() {
  }

  /**
   * the command has been executed, so extract extract the needed information
   * from the application context.
   */
  public Object execute(ExecutionEvent event) throws ExecutionException {
    Shell aShell = HandlerUtil.getActiveShell(event);
    if (!HandlerUtil.getCurrentSelectionChecked(event).isEmpty()) {
      User selectedUser = (User) ((StructuredSelection) HandlerUtil
          .getCurrentSelectionChecked(event)).getFirstElement();
      UserDetailsDialog dialog = new UserDetailsDialog(aShell, selectedUser);
      if (dialog != null) {
        dialog.open();
      }
      return null;
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
