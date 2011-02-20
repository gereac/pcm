package com.gcsf.pcm.handlers.user;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import com.gcsf.pcm.gui.GeneralPerspective;

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
    // IWorkbenchWindow window =
    // HandlerUtil.getActiveWorkbenchWindowChecked(event);
    // MessageDialog.openInformation(
    // window.getShell(),
    // "Personal Contact Manager",
    // "Hello, Eclipse world");
    // IWorkbenchPage page = getActivePage();
    try {
      IWorkbenchPage page = HandlerUtil.getActiveWorkbenchWindowChecked(event)
          .getActivePage();
      for (IPerspectiveDescriptor descriptor : PlatformUI.getWorkbench()
          .getPerspectiveRegistry().getPerspectives())
        if (descriptor.getId().equalsIgnoreCase(
            GeneralPerspective.PERSPECTIVE_ID)) {
          page.setPerspective(descriptor);
        }
    } catch (Exception e) {
      System.out.println(e);
    }
    return null;
  }

  private static IWorkbenchPage getActivePage() {
    IWorkbenchPage result = null;
    IWorkbenchWindow window = PlatformUI.getWorkbench()
        .getActiveWorkbenchWindow();
    if (window != null) {
      result = window.getActivePage();
    }

    return result;
  }
}
