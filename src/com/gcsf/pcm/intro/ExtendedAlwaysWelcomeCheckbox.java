package com.gcsf.pcm.intro;

import java.io.PrintWriter;
import java.util.Properties;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.internal.intro.impl.util.ReopenUtil;
import org.eclipse.ui.intro.IIntroSite;
import org.eclipse.ui.intro.config.IIntroAction;
import org.eclipse.ui.intro.config.IIntroContentProviderSite;
import org.eclipse.ui.intro.config.IIntroXHTMLContentProvider;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ExtendedAlwaysWelcomeCheckbox implements
    IIntroXHTMLContentProvider, IIntroAction {

  public final static String ALWAYS_SHOW_INTRO = "alwaysShowIntro"; //$NON-NLS-1$

  private boolean disposed = false;

  @Override
  public void init(IIntroContentProviderSite site) {
  }

  protected String getText() {
    return "Do you want to see this page next time?";
  }

  @Override
  public void dispose() {
    disposed = true;
  }

  @Override
  public void createContent(String id, Element parent) {
    if (disposed)
      return;

    boolean alwaysShowIntro = getAlwaysShowIntroPref();

    Document dom = parent.getOwnerDocument();
    Element div = dom.createElement("div");
    div.setAttribute("id", id);
    Element input = dom.createElement("input");
    input.setAttribute("type", "checkbox");
    input.setAttribute("value", "do you want to see this screen next time?");
    if (alwaysShowIntro) {
      input.setAttribute("checked", "checked");
      PlatformUI.getPreferenceStore().setValue(
          IWorkbenchPreferenceConstants.SHOW_INTRO, alwaysShowIntro);
    }
    input.setAttribute("onClick", "window.location=" + //$NON-NLS-1$
        "\"http://org.eclipse.ui.intro/runAction?" + //$NON-NLS-1$
        "pluginId=com.gcsf.pcm&#38;" + //$NON-NLS-1$
        "class=" + this.getClass().getName() + "\" ");
    input.setTextContent(getText());
    div.appendChild(input);
    parent.appendChild(div);
  }

  /**
   * Method called when box is clicked in html (swt is handled with a
   * SelectionAdapter - both methods call reverseShowIntroState())
   */
  public void run(IIntroSite site, Properties params) {
    reverseShowIntroState();
  }

  /**
   * Method reverses preference ALWAYS_SHOW_INTRO due to checkbox selection
   * change
   * 
   */
  private void reverseShowIntroState() {
    // Retrieve current state of IUniversalIntroConst.ALWAYS_SHOW_INTRO, change
    // it, and save it back
    // to both ALWAYS_SHOW_INTRO and SHOW_INTRO
    boolean alwaysShowIntro = !getAlwaysShowIntroPref();

    // local preference store
    setAlwaysShowIntroPref(alwaysShowIntro);

    // workbench preference store
    PlatformUI.getPreferenceStore().setValue(
        IWorkbenchPreferenceConstants.SHOW_INTRO, alwaysShowIntro);

  }

  public boolean getAlwaysShowIntroPref() {
    // If uninitialized, we will default to true (box will be checked)
    if (!ReopenUtil.isReopenPreferenceInitialized()) {
      setAlwaysShowIntroPref(true);
    }
    return ReopenUtil.isReopenPreference();
  }

  public void setAlwaysShowIntroPref(boolean val) {
    ReopenUtil.setReopenPreference(val);
  }

  @Override
  public void createContent(String id, PrintWriter out) {
  }

  @Override
  public void createContent(String id, Composite parent, FormToolkit toolkit) {
  }

}
