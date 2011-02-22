package com.gcsf.pcm.gui;

import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.gcsf.pcm.model.treeviewer.GroupsProviderMock;
import com.gcsf.pcm.model.treeviewer.UserGroupContentProvider;
import com.gcsf.pcm.model.treeviewer.UserGroupLabelProvider;
import com.gcsf.pcm.model.treeviewer.UserGroupNameEditingSupport;

public class GroupsView extends ViewPart {

  public static final String ID = "com.gcsf.pcm.view.groups";

  private TreeViewer viewer;

  public void createPartControl(Composite parent) {
    viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
    viewer.getTree().setLinesVisible(true);
    viewer.getTree().setHeaderVisible(true);
    ColumnViewerToolTipSupport.enableFor(viewer);

    TreeViewerColumn treeColumn = new TreeViewerColumn(viewer, SWT.NONE);
    TreeColumnLayout treeColumnLayout = new TreeColumnLayout();
    parent.setLayout(treeColumnLayout);
    treeColumnLayout.setColumnData(treeColumn.getColumn(),
        new ColumnWeightData(100, false));

    treeColumn.setEditingSupport(new UserGroupNameEditingSupport(viewer));
    treeColumn.setLabelProvider(new UserGroupLabelProvider());

    viewer.setContentProvider(new UserGroupContentProvider());
    // viewer.setLabelProvider(new UserGroupLabelProvider());
    // Expand the tree
    viewer.setAutoExpandLevel(1);
    // Provide the input to the ContentProvider
    viewer.setInput(GroupsProviderMock.getInstance());

    getViewSite().setSelectionProvider(viewer);
  }

  public TreeViewer getViewer() {
    return viewer;
  }

  /**
   * Passing the focus request to the viewer's control.
   */
  public void setFocus() {
    viewer.getControl().setFocus();
  }

}
