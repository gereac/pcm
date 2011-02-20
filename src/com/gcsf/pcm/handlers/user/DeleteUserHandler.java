package com.gcsf.pcm.handlers.user;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

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
    MessageDialog.openInformation(window.getShell(),
        "Personal Contact Manager", "Hello, Eclipse world");
    return null;
  }
}
