package com.gcsf.pcm.gui;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class GeneralPerspective implements IPerspectiveFactory {

  public static final String PERSPECTIVE_ID = "com.gcsf.pcm.perspective";

  public void createInitialLayout(IPageLayout layout) {
    layout.setEditorAreaVisible(false);
  }
}
