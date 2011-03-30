package com.gcsf.pcm.gui;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.TreeViewerEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.gcsf.pcm.Activator;
import com.gcsf.pcm.model.User;
import com.gcsf.pcm.model.UserGroup;
import com.gcsf.pcm.model.treeviewer.GroupsProviderMock;
import com.gcsf.pcm.model.treeviewer.SecondClickColumnViewerEditorActivationStrategy;
import com.gcsf.pcm.model.treeviewer.UserGroupContentProvider;
import com.gcsf.pcm.model.treeviewer.UserGroupLabelProvider;
import com.gcsf.pcm.model.treeviewer.UserGroupNameEditingSupport;
import com.gcsf.pcm.model.treeviewer.dnd.UserDragListener;
import com.gcsf.pcm.model.treeviewer.dnd.UserTransfer;
import com.gcsf.pcm.model.treeviewer.dnd.UserTreeDropAdapter;
import com.gcsf.pcm.model.treeviewer.dnd.clipboard.CopyUserAction;
import com.gcsf.pcm.model.treeviewer.dnd.clipboard.CutUserAction;
import com.gcsf.pcm.model.treeviewer.dnd.clipboard.PasteUserAction;

public class GroupsView extends ViewPart implements
    ITabbedPropertySheetPageContributor {

  public static final String ID = "com.gcsf.pcm.view.groups";

  private TreeViewer viewer;

  private Clipboard clipboard;

  private Action cutAction, copyAction, pasteAction;

  // private Menu tableMenu;

  public void createPartControl(Composite parent) {
    viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
    viewer.getTree().setLinesVisible(true);
    viewer.getTree().setHeaderVisible(true);
    ColumnViewerToolTipSupport.enableFor(viewer);
    TreeViewerEditor.create(viewer, null,
        new SecondClickColumnViewerEditorActivationStrategy(viewer),
        ColumnViewerEditor.DEFAULT);

    int ops = DND.DROP_MOVE;
    Transfer[] transfers = new Transfer[] { UserTransfer.getInstance() };
    viewer.addDragSupport(ops, transfers, new UserDragListener(viewer));
    viewer.addDropSupport(ops, transfers, new UserTreeDropAdapter(viewer));

    TreeViewerColumn treeColumn = new TreeViewerColumn(viewer, SWT.NONE);
    TreeColumnLayout treeColumnLayout = new TreeColumnLayout();
    parent.setLayout(treeColumnLayout);
    treeColumnLayout.setColumnData(treeColumn.getColumn(),
        new ColumnWeightData(100, false));

    treeColumn.setEditingSupport(new UserGroupNameEditingSupport(viewer));
    treeColumn.setLabelProvider(new UserGroupLabelProvider());

    viewer.setContentProvider(new UserGroupContentProvider());
    // Expand the tree
    viewer.setAutoExpandLevel(1);
    // Provide the input to the ContentProvider
    viewer.setInput(GroupsProviderMock.getInstance());

    getViewSite().setSelectionProvider(viewer);

    clipboard = new Clipboard(getSite().getShell().getDisplay());
    createActions();
    createTableMenu();
    IActionBars bars = getViewSite().getActionBars();
    bars.setGlobalActionHandler(ActionFactory.CUT.getId(), cutAction);
    bars.setGlobalActionHandler(ActionFactory.COPY.getId(), copyAction);
    bars.setGlobalActionHandler(ActionFactory.PASTE.getId(), pasteAction);
    bars.updateActionBars();

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

  private void createActions() {
    cutAction = new CutUserAction(viewer, clipboard);
    cutAction.setImageDescriptor(Activator
        .getImageDescriptor("icons/edit/cut_edit.gif"));
    copyAction = new CopyUserAction(viewer, clipboard);
    copyAction.setImageDescriptor(Activator
        .getImageDescriptor("icons/edit/copy_edit.gif"));
    pasteAction = new PasteUserAction(viewer, clipboard);
    pasteAction.setImageDescriptor(Activator
        .getImageDescriptor("icons/edit/paste_edit.gif"));
  }

  private void createTableMenu() {
    final MenuManager mgr = new MenuManager();
    mgr.setRemoveAllWhenShown(true);
    final Separator separator = new Separator();
    separator.setVisible(true);

    mgr.addMenuListener(new IMenuListener() {

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.eclipse.jface.action.IMenuListener#menuAboutToShow(org.eclipse.
       * jface.action.IMenuManager)
       */
      public void menuAboutToShow(IMenuManager manager) {
        IStructuredSelection selection = (IStructuredSelection) viewer
            .getSelection();

        if (!selection.isEmpty()) {
          if (selection.getFirstElement() instanceof User) {
            mgr.add(cutAction);
            mgr.add(copyAction);
          }
          if ((selection.getFirstElement() instanceof UserGroup)
              && clipboard.getContents(UserTransfer.getInstance()) != null) {
            mgr.add(pasteAction);
          }
        }
      }
    });
    viewer.getTree().setMenu(mgr.createContextMenu(viewer.getControl()));
  }
}
