package com.gcsf.pcm.intro;

import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

import com.gcsf.pcm.gui.GeneralPerspective;

public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {

  public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(
      IWorkbenchWindowConfigurer configurer) {
    return new ApplicationWorkbenchWindowAdvisor(configurer);
  }

  public void initialize(IWorkbenchConfigurer configurer) {
    // super.initialize(configurer);
    configurer.setSaveAndRestore(true);
  }

  public String getInitialWindowPerspectiveId() {
    return GeneralPerspective.PERSPECTIVE_ID;
  }
}
