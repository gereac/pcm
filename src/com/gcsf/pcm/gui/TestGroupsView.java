package com.gcsf.pcm.gui;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.TreeViewerEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.gcsf.pcm.gui.properties.tabbed.GroupElement;
import com.gcsf.pcm.model.User;
import com.gcsf.pcm.model.UserGroup;
import com.gcsf.pcm.model.treeviewer.SecondClickColumnViewerEditorActivationStrategy;

public class TestGroupsView extends ViewPart implements
    ITabbedPropertySheetPageContributor {

  public static final String ID = "com.gcsf.pcm.view.testgroups";

  private TreeViewer viewer;

  public void createPartControl(Composite parent) {
    viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
    viewer.getTree().setLinesVisible(true);
    viewer.getTree().setHeaderVisible(true);
    ColumnViewerToolTipSupport.enableFor(viewer);
    TreeViewerEditor.create(viewer, null,
        new SecondClickColumnViewerEditorActivationStrategy(viewer),
        ColumnViewerEditor.DEFAULT);

    TreeViewerColumn treeColumn = new TreeViewerColumn(viewer, SWT.NONE);
    TreeColumnLayout treeColumnLayout = new TreeColumnLayout();
    parent.setLayout(treeColumnLayout);
    treeColumnLayout.setColumnData(treeColumn.getColumn(),
        new ColumnWeightData(100, false));

    // Expand the tree
    viewer.setAutoExpandLevel(1);
    // Provide the input to the ContentProvider
    // fill in the element
    UserGroup usergroup = new UserGroup();
    usergroup.setGroupName("Group one");
    usergroup.setGroupDescription("Group one description");
    User user = new User("Rainer", "12345", "a@a");
    usergroup.getGroupMembers().add(user);
    user = new User("Reiner", "23456", "b@b");
    usergroup.getGroupMembers().add(user);
    user = new User("Marie", "34567", "c@c");
    usergroup.getGroupMembers().add(user);
    ArrayList ctlList = new ArrayList();
    GroupElement grpEl = new GroupElement(usergroup);//$NON-NLS-1$
    ctlList.add(grpEl);
    viewer.setContentProvider(new SampleTreeContentProvider());
    viewer.setLabelProvider(new WorkbenchLabelProvider());
    viewer.setInput(ctlList);

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

  @Override
  public String getContributorId() {
    return getSite().getId();
  }

  /**
   * @see org.eclipse.core.runtime.IAdaptable#getAdapter(Class)
   */
  public Object getAdapter(Class adapter) {
    if (adapter == IPropertySheetPage.class)
      return new TabbedPropertySheetPage(this);
    return super.getAdapter(adapter);
  }

  private class SampleTreeContentProvider implements ITreeContentProvider {

    private GroupElement grpEl = null;

    @Override
    public void dispose() {
    }

    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
      viewer.refresh();
    }

    @Override
    public Object[] getElements(Object inputElement) {
      if (inputElement instanceof Object[]) {
        return (Object[]) inputElement;
      }
      if (inputElement instanceof Collection) {
        return ((Collection) inputElement).toArray();
      }
      return new Object[0];
    }

    @Override
    public Object[] getChildren(Object parentElement) {
      grpEl = (GroupElement) parentElement;
      return grpEl.getChildren(parentElement);
    }

    @Override
    public Object getParent(Object element) {
      return null;
    }

    @Override
    public boolean hasChildren(Object element) {
      if (grpEl instanceof GroupElement) {
        grpEl = (GroupElement) element;
        if (grpEl.getChildren(element).length > 0) {
          return true;
        }
      }
      return false;
    }
  }

}
