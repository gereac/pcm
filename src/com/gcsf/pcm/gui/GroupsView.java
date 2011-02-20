package com.gcsf.pcm.gui;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.gcsf.pcm.model.treeviewer.GroupsProviderMock;
import com.gcsf.pcm.model.treeviewer.UserGroupContentProvider;
import com.gcsf.pcm.model.treeviewer.UserGroupLabelProvider;

public class GroupsView extends ViewPart {

  public static final String ID = "com.gcsf.pcm.view.groups";

  private TreeViewer viewer;

  public void createPartControl(Composite parent) {
    viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
    viewer.setContentProvider(new UserGroupContentProvider());
    viewer.setLabelProvider(new UserGroupLabelProvider());
    // Expand the tree
    viewer.setAutoExpandLevel(2);
    // Provide the input to the ContentProvider
    viewer.setInput(new GroupsProviderMock());
  }

  /**
   * Passing the focus request to the viewer's control.
   */
  public void setFocus() {
    viewer.getControl().setFocus();
  }

}
