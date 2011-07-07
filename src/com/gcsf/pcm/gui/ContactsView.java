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
import org.eclipse.jface.viewers.IInputProvider;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.TreeViewerEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.progress.IWorkbenchSiteProgressService;
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
import com.gcsf.pcm.persist.pref.Preference;

public class ContactsView extends ViewPart implements
    ITabbedPropertySheetPageContributor {

  public static final String ID = "com.gcsf.pcm.view.groups";

  private TreeViewer treeViewer;

  private Clipboard clipboard;

  private Action cutAction, copyAction, pasteAction;

  private boolean fBeginSearchOnTyping;

  private boolean fAlwaysShowSearch;

  private Label fSeparator;

  private Composite fSearchBarContainer;

  private ContactsSearchBar fSearchBar;

  private IViewSite fViewSite;

  public void createPartControl(Composite parent) {
    GridLayout gridLayout = new GridLayout(1, false);
    gridLayout.marginWidth = 5;
    gridLayout.marginHeight = 5;
    gridLayout.verticalSpacing = 0;
    gridLayout.horizontalSpacing = 0;
    parent.setLayout(gridLayout);

    Composite treeComposite = new Composite(parent, SWT.NONE);
    GridLayout gridLayout2 = new GridLayout(1, false);
    gridLayout2.marginWidth = 5;
    gridLayout2.marginHeight = 5;
    gridLayout2.verticalSpacing = 0;
    gridLayout2.horizontalSpacing = 0;
    treeComposite.setLayout(gridLayout2);
    GridData gridData2 = new GridData(SWT.FILL, SWT.FILL, true, true);
    gridData2.widthHint = SWT.DEFAULT;
    gridData2.heightHint = SWT.DEFAULT;
    treeComposite.setLayoutData(gridData2);

    treeViewer = new TreeViewer(treeComposite, SWT.MULTI | SWT.H_SCROLL
        | SWT.V_SCROLL);
    // viewer.getTree().setLinesVisible(true);
    // viewer.getTree().setHeaderVisible(true);
    ColumnViewerToolTipSupport.enableFor(treeViewer);
    TreeViewerEditor.create(treeViewer, null,
        new SecondClickColumnViewerEditorActivationStrategy(treeViewer),
        ColumnViewerEditor.DEFAULT);

    int ops = DND.DROP_MOVE;
    Transfer[] transfers = new Transfer[] { UserTransfer.getInstance() };
    treeViewer.addDragSupport(ops, transfers, new UserDragListener(treeViewer));
    treeViewer.addDropSupport(ops, transfers, new UserTreeDropAdapter(
        treeViewer));

    TreeViewerColumn treeColumn = new TreeViewerColumn(treeViewer, SWT.NONE);
    TreeColumnLayout treeColumnLayout = new TreeColumnLayout();
    treeComposite.setLayout(treeColumnLayout);
    treeColumnLayout.setColumnData(treeColumn.getColumn(),
        new ColumnWeightData(100, false));

    treeColumn.setEditingSupport(new UserGroupNameEditingSupport(treeViewer));
    treeColumn.setLabelProvider(new UserGroupLabelProvider());

    treeViewer.setContentProvider(new UserGroupContentProvider());
    // Expand the tree
    treeViewer.setAutoExpandLevel(1);
    // Provide the input to the ContentProvider
    treeViewer.setInput(GroupsProviderMock.getInstance());

    getViewSite().setSelectionProvider(treeViewer);

    clipboard = new Clipboard(fViewSite.getShell().getDisplay());
    createActions();
    createTableMenu();
    IActionBars bars = getViewSite().getActionBars();
    bars.setGlobalActionHandler(ActionFactory.CUT.getId(), cutAction);
    bars.setGlobalActionHandler(ActionFactory.COPY.getId(), copyAction);
    bars.setGlobalActionHandler(ActionFactory.PASTE.getId(), pasteAction);
    bars.updateActionBars();

    treeViewer.getControl().addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {

        /* Feature not Used, return */
        if (!fBeginSearchOnTyping)
          return;

        /* Transfer typed key into SearchBar */
        if (e.character > 0x20 && e.character != SWT.DEL) {
          setSearchBarVisible(true);

          fSearchBar.getControl().append(String.valueOf(e.character));
          fSearchBar.getControl().setFocus();

          /* Consume the Event */
          e.doit = false;
        }

        /* Reset any Filter if set */
        else if (e.keyCode == SWT.ESC
            && fSearchBar.getControl().getText().length() != 0) {
          fSearchBar.setFilterText(""); //$NON-NLS-1$
          setSearchBarVisible(fAlwaysShowSearch);
          setFocus();
        }
      }
    });

    /* Propagate Selection Events */
    fViewSite.setSelectionProvider(treeViewer);

    /* Create the Search Bar */
    createSearchBar(parent);

    /* Show Busy when reload occurs */
    IWorkbenchSiteProgressService service = (IWorkbenchSiteProgressService) fViewSite
        .getAdapter(IWorkbenchSiteProgressService.class);
    // TODO implement this correctly
    service.showBusyForFamily(null);
  }

  public TreeViewer getViewer() {
    return treeViewer;
  }

  void setSearchBarVisible(boolean visible) {

    /* Return if no State Change */
    if (visible != ((GridData) fSeparator.getLayoutData()).exclude)
      return;
    /* Update LayoutData and layout Parent */
    ((GridData) fSeparator.getLayoutData()).exclude = !visible;
    ((GridData) fSearchBarContainer.getLayoutData()).exclude = !visible;
    fSearchBarContainer.getParent().layout();
  }

  /**
   * Passing the focus request to the viewer's control.
   */
  public void setFocus() {
    treeViewer.getControl().setFocus();
  }

  @Override
  public String getContributorId() {
    return fViewSite.getId();
  }

  /**
   * @see org.eclipse.core.runtime.IAdaptable#getAdapter(Class)
   */
  public Object getAdapter(Class adapter) {
    if (adapter == IPropertySheetPage.class) {
      return new TabbedPropertySheetPage(this);
    }
    if (ISelectionProvider.class.equals(adapter)) {
      return treeViewer;
    }

    if (IInputProvider.class.equals(adapter)) {
      return treeViewer.getInput();
    }
    return super.getAdapter(adapter);
  }

  private void createActions() {
    cutAction = new CutUserAction(treeViewer, clipboard);
    cutAction.setImageDescriptor(Activator
        .getImageDescriptor("icons/edit/cut_edit.gif"));
    copyAction = new CopyUserAction(treeViewer, clipboard);
    copyAction.setImageDescriptor(Activator
        .getImageDescriptor("icons/edit/copy_edit.gif"));
    pasteAction = new PasteUserAction(treeViewer, clipboard);
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
        IStructuredSelection selection = (IStructuredSelection) treeViewer
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
    treeViewer.getTree()
        .setMenu(mgr.createContextMenu(treeViewer.getControl()));
  }

  private void createSearchBar(final Composite parent) {

    /* Add Separator between Tree and Search Bar */
    fSeparator = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
    fSeparator
        .setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

    /* Container for the SearchBar */
    fSearchBarContainer = new Composite(parent, SWT.NONE);
    fSearchBarContainer.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING,
        true, false));

    /* Hide Searchbar in case settings tell so */
    ((GridData) fSeparator.getLayoutData()).exclude = !fAlwaysShowSearch;
    ((GridData) fSearchBarContainer.getLayoutData()).exclude = !fAlwaysShowSearch;

    /* Apply Layout */
    GridLayout searchBarLayout = new GridLayout(1, false);
    searchBarLayout.marginHeight = 2;
    searchBarLayout.marginWidth = 2;
    fSearchBarContainer.setLayout(searchBarLayout);

    /* Create the SearchBar */
    fSearchBar = new ContactsSearchBar(fViewSite, fSearchBarContainer,
        treeViewer);

    /* Show the SearchBar on Printable Key pressed */
    treeViewer.getControl().addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {

        /* Feature not Used, return */
        if (!fBeginSearchOnTyping)
          return;

        /* Transfer typed key into SearchBar */
        if (e.character > 0x20 && e.character != SWT.DEL) {
          setSearchBarVisible(true);

          fSearchBar.getControl().append(String.valueOf(e.character));
          fSearchBar.getControl().setFocus();

          /* Consume the Event */
          e.doit = false;
        }

        /* Reset any Filter if set */
        else if (e.keyCode == SWT.ESC
            && fSearchBar.getControl().getText().length() != 0) {
          fSearchBar.setFilterText(""); //$NON-NLS-1$
          setSearchBarVisible(fAlwaysShowSearch);
          setFocus();
        }
      }
    });

    /* Hide SearchBar if search is done */
    fSearchBar.getControl().addModifyListener(new ModifyListener() {
      public void modifyText(ModifyEvent e) {

        /* Feature not Used, return */
        if (fAlwaysShowSearch)
          return;

        /* Search is Done */
        if (fSearchBar.getControl().getText().length() == 0) {
          setSearchBarVisible(false);
          setFocus();
        }
      }
    });
  }

  @Override
  public void init(IViewSite site) throws PartInitException {
    super.init(site);
    fViewSite = site;
    loadState();
  }

  @Override
  public void dispose() {
    super.dispose();
    saveState();
  }

  /**
   * Save all Settings of the Contacts View
   */
  public void saveState() {
    Activator
        .getDefault()
        .getPreferenceStore()
        .setValue(Preference.BE_BEGIN_SEARCH_ON_TYPING.id(),
            fBeginSearchOnTyping);
    Activator.getDefault().getPreferenceStore()
        .setValue(Preference.BE_ALWAYS_SHOW_SEARCH.id(), fAlwaysShowSearch);
  }

  private void loadState() {
    /* Misc. Settings */
    fBeginSearchOnTyping = Activator.getDefault().getPreferenceStore()
        .getBoolean(Preference.BE_BEGIN_SEARCH_ON_TYPING.id());
    fAlwaysShowSearch = Activator.getDefault().getPreferenceStore()
        .getBoolean(Preference.BE_ALWAYS_SHOW_SEARCH.id());
  }

  /**
   * The user performed the "Find" action.
   */
  public void find() {
    setSearchBarVisible(true);
    fSearchBar.getControl().setFocus();
  }

}
