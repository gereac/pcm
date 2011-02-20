package com.gcsf.pcm.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.internal.intro.IntroDescriptor;
import org.eclipse.ui.internal.intro.IntroMessages;

public class IntroHandler extends AbstractHandler {

  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {
    IWorkbenchWindow workbenchWindow = HandlerUtil
        .getActiveWorkbenchWindow(event);
    IntroDescriptor introDescriptor = ((Workbench) workbenchWindow
        .getWorkbench()).getIntroDescriptor();
    if (introDescriptor == null) {
      MessageDialog.openWarning(workbenchWindow.getShell(),
          IntroMessages.Intro_missing_product_title,
          IntroMessages.Intro_missing_product_message);
    } else {
      workbenchWindow.getWorkbench().getIntroManager()
          .showIntro(workbenchWindow, false);
    }
    return null;
  }

}
