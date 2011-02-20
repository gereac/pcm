package com.gcsf.pcm.gui;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.gcsf.pcm.model.User;
import com.gcsf.pcm.model.UserGroup;

public class UsersView extends ViewPart implements ISelectionListener {

  public static final String ID = "com.gcsf.pcm.view.users";

  private TableViewer viewer;

  @Override
  public void createPartControl(Composite parent) {
    viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL
        | SWT.FULL_SELECTION | SWT.BORDER);
    createColumns(parent, viewer);
    final Table table = viewer.getTable();
    table.setHeaderVisible(true);
    table.setLinesVisible(true);

    viewer.setContentProvider(new ArrayContentProvider());
    // Get the content for the viewer, setInput will call getElements in the
    // contentProvider
    // viewer.setInput(UsersProviderMock.INSTANCE.getUsers());
    // Make the selection available to other views
    getSite().setSelectionProvider(viewer);
    // Set the sorter for the table

    // Layout the viewer
    GridData gridData = new GridData();
    gridData.verticalAlignment = GridData.FILL;
    gridData.horizontalSpan = 2;
    gridData.grabExcessHorizontalSpace = true;
    gridData.grabExcessVerticalSpace = true;
    gridData.horizontalAlignment = GridData.FILL;
    viewer.getControl().setLayoutData(gridData);

    PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService()
        .addSelectionListener(this);
  }

  public TableViewer getViewer() {
    return viewer;
  }

  // This will create the columns for the table
  private void createColumns(final Composite parent, final TableViewer viewer) {
    String[] titles = { "User Name", "User Phone", "User Email" };
    int[] bounds = { 100, 100, 100 };
    int[] weights = { 30, 30, 30 };

    TableColumnLayout tableColumnLayout = new TableColumnLayout();
    parent.setLayout(tableColumnLayout);

    // First column is for the first name
    TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
    col.setLabelProvider(new ColumnLabelProvider() {
      @Override
      public String getText(Object element) {
        User p = (User) element;
        return p.getUserName();
      }
    });
    tableColumnLayout.setColumnData(col.getColumn(), new ColumnWeightData(
        weights[0]));

    // Second column is for the last name
    col = createTableViewerColumn(titles[1], bounds[1], 1);
    col.setLabelProvider(new ColumnLabelProvider() {
      @Override
      public String getText(Object element) {
        User p = (User) element;
        return p.getUserPhone();
      }
    });
    tableColumnLayout.setColumnData(col.getColumn(), new ColumnWeightData(
        weights[1]));

    // Now the gender
    col = createTableViewerColumn(titles[2], bounds[2], 2);
    col.setLabelProvider(new ColumnLabelProvider() {
      @Override
      public String getText(Object element) {
        User p = (User) element;
        return p.getUserEmail();
      }
    });
    tableColumnLayout.setColumnData(col.getColumn(), new ColumnWeightData(
        weights[2]));

  }

  private TableViewerColumn createTableViewerColumn(String title, int bound,
      final int colNumber) {
    final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
        SWT.NONE);
    final TableColumn column = viewerColumn.getColumn();
    column.setText(title);
    column.setWidth(bound);
    column.setResizable(true);
    column.setMoveable(true);
    return viewerColumn;

  }

  @Override
  public void setFocus() {
    viewer.getControl().setFocus();
  }

  @Override
  public void selectionChanged(IWorkbenchPart part, ISelection selection) {
    System.out.println("selection is : " + selection);
    if (part != this) {
      if (!selection.isEmpty()) {
        if ((((IStructuredSelection) selection).getFirstElement()) instanceof UserGroup) {
          viewer.setInput(((UserGroup) ((IStructuredSelection) selection)
              .getFirstElement()).getGroupMembers());
        } else {
          // Item aItem = (Item) (((IStructuredSelection) selection)
          // .getFirstElement());
          // ArrayList<Item> aList = new ArrayList<Item>();
          // aList.add(aItem);
          // tableViewer.setInput(aList);
          viewer.setInput(null);
        }
      } else {
        viewer.setInput(null);
      }
    } else {
      if (!selection.isEmpty()) {
        // if (doubleClickOccured) {
        // doubleClickOccured = false;
        if ((((IStructuredSelection) selection).getFirstElement()) instanceof UserGroup) {
          viewer.setInput(((UserGroup) ((IStructuredSelection) selection)
              .getFirstElement()).getGroupMembers());
          // }
        }
      }
    }
  }

}
