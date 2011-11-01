package com.gcsf.pcm.actions;

import java.util.Properties;

import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.intro.IIntroPart;
import org.eclipse.ui.intro.IIntroSite;
import org.eclipse.ui.intro.config.IIntroAction;

import com.gcsf.pcm.gui.views.ContactsView;

public class OpenContactsViewAction implements IIntroAction {

  @Override
  public void run(IIntroSite site, Properties params) {
    IIntroPart introPart = PlatformUI.getWorkbench().getIntroManager()
        .getIntro();
    PlatformUI.getWorkbench().getIntroManager().closeIntro(introPart);
    run();
  }

  private void run() {
    try {
      PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
          .showView(ContactsView.VIEW_ID);
    } catch (PartInitException aPie) {
      System.out.println(aPie);
    }
  }
}
