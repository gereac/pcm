package com.gcsf.pcm.intro;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

  public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
    super(configurer);
  }

  protected void makeActions(IWorkbenchWindow window) {
    // nothing to do
  }

  protected void fillMenuBar(IMenuManager menuBar) {
    // nothing to do
  }
}
