package com.gcsf.pcm.wizards;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.gcsf.pcm.Activator;
import com.gcsf.pcm.dialogs.UserDetailsDialog;
import com.gcsf.pcm.model.User;
import com.gcsf.pcm.model.treeviewer.GroupsProviderMock;

public class GroupDetailsWizardPageTwo extends WizardPage {

  private Composite container;

  private Composite allUsersComposite = null;

  private Composite buttonsComposite = null;

  private Composite newUserComposite = null;

  private Composite selectedUsersComposite = null;

  private TableViewer allUsersViewer;

  private TableViewer selectedUsersViewer;

  private WritableList allUsersInput;

  private Button addOne = null;

  private Button removeOne = null;

  private Button newUser = null;

  private ISummaryListener mySummaryListener = null;

  public GroupDetailsWizardPageTwo() {
    super("Second Page");
    setTitle("Users details");
    setDescription("Please complete the users details.");
  }

  @Override
  public void createControl(Composite parent) {
    container = new Composite(parent, SWT.NULL);
    GridLayout layout = new GridLayout();
    container.setLayout(layout);
    layout.numColumns = 3;

    allUsersComposite = new Composite(container, SWT.NULL);
    GridLayout gridLayout = new GridLayout(1, false);
    gridLayout.marginWidth = 5;
    gridLayout.marginHeight = 5;
    gridLayout.verticalSpacing = 0;
    gridLayout.horizontalSpacing = 0;
    allUsersComposite.setLayout(gridLayout);
    GridData gridData = new GridData(SWT.BEGINNING, SWT.FILL, false, true);
    gridData.widthHint = SWT.DEFAULT;
    gridData.heightHint = SWT.DEFAULT;
    allUsersComposite.setLayoutData(gridData);

    allUsersViewer = new TableViewer(allUsersComposite, SWT.MULTI
        | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
    createColumns(allUsersComposite, allUsersViewer);
    final Table allUsersTable = allUsersViewer.getTable();
    allUsersTable.setHeaderVisible(false);
    allUsersTable.setLinesVisible(false);

    // allUsersViewer.setContentProvider(new ArrayContentProvider());
    // allUsersViewer.setInput(GroupsProviderMock.getInstance().getUsers());

    allUsersInput = new WritableList(GroupsProviderMock.getInstance()
        .getUsers(), User.class);
    ViewerSupport.bind(
        allUsersViewer,
        allUsersInput,
        BeanProperties.values(new String[] { "userName", "userPhone",
            "userEmail" }));

    allUsersViewer.addDoubleClickListener(new IDoubleClickListener() {

      @Override
      public void doubleClick(DoubleClickEvent aEvent) {
        if (aEvent.getSource() instanceof TableViewer) {
          TableViewer sourceTableViewer = (TableViewer) aEvent.getSource();
          TableViewer destinationTableViewer = (sourceTableViewer == allUsersViewer) ? selectedUsersViewer
              : allUsersViewer;
          moveSelectedEntries(sourceTableViewer, destinationTableViewer);
          if (mySummaryListener != null) {
            mySummaryListener
                .setUsers((java.util.List<User>) selectedUsersViewer.getInput());
          }
        }
      }
    });
    allUsersViewer.addSelectionChangedListener(new ISelectionChangedListener() {

      @Override
      public void selectionChanged(SelectionChangedEvent event) {
        updateButtons();
      }
    });

    // Layout the viewer
    GridData gridData12 = new GridData();
    gridData12.verticalAlignment = GridData.FILL;
    gridData12.horizontalSpan = 2;
    gridData12.grabExcessHorizontalSpace = true;
    gridData12.grabExcessVerticalSpace = true;
    gridData12.horizontalAlignment = GridData.FILL;
    allUsersViewer.getControl().setLayoutData(gridData);

    buttonsComposite = new Composite(container, SWT.NULL);
    RowLayout rowLayout = new RowLayout();
    rowLayout.type = org.eclipse.swt.SWT.VERTICAL;
    rowLayout.fill = true;
    buttonsComposite.setLayout(rowLayout);
    addOne = new Button(buttonsComposite, SWT.PUSH);
    addOne.setText(">>>");
    addOne.setEnabled(false);
    addOne.addSelectionListener(new SelectionListener() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        moveSelectedEntries(allUsersViewer, selectedUsersViewer);
        if (mySummaryListener != null) {
          mySummaryListener.setUsers((java.util.List<User>) selectedUsersViewer
              .getInput());
        }
      }

      @Override
      public void widgetDefaultSelected(SelectionEvent e) {
        // TODO Auto-generated method stub

      }
    });
    removeOne = new Button(buttonsComposite, SWT.PUSH);
    removeOne.setText("<<<");
    removeOne.setEnabled(false);
    removeOne.addSelectionListener(new SelectionListener() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        moveSelectedEntries(allUsersViewer, selectedUsersViewer);
        if (mySummaryListener != null) {
          mySummaryListener.setUsers((java.util.List<User>) selectedUsersViewer
              .getInput());
        }
      }

      @Override
      public void widgetDefaultSelected(SelectionEvent e) {
        // TODO Auto-generated method stub

      }
    });

    selectedUsersComposite = new Composite(container, SWT.NULL);
    GridLayout gridLayout3 = new GridLayout(1, false);
    gridLayout3.marginWidth = 5;
    gridLayout3.marginHeight = 5;
    gridLayout3.verticalSpacing = 0;
    gridLayout3.horizontalSpacing = 0;
    selectedUsersComposite.setLayout(gridLayout3);
    GridData gridData3 = new GridData(SWT.FILL, SWT.FILL, true, false);
    gridData3.widthHint = SWT.DEFAULT;
    gridData3.heightHint = SWT.DEFAULT;
    selectedUsersComposite.setLayoutData(gridData3);

    selectedUsersViewer = new TableViewer(selectedUsersComposite, SWT.MULTI
        | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
    createColumns(selectedUsersComposite, selectedUsersViewer);
    final Table selectedUsersTable = selectedUsersViewer.getTable();
    selectedUsersTable.setHeaderVisible(false);
    selectedUsersTable.setLinesVisible(false);

    selectedUsersViewer.setContentProvider(new ArrayContentProvider());

    newUserComposite = new Composite(container, SWT.NULL);
    GridData gridData2 = new GridData(SWT.FILL, SWT.FILL, false, false);
    gridData2.widthHint = SWT.DEFAULT;
    gridData2.heightHint = SWT.DEFAULT;
    newUserComposite.setLayoutData(gridData2);
    GridLayout gridLayout2 = new GridLayout(1, false);
    gridLayout2.marginWidth = 5;
    gridLayout2.marginHeight = 5;
    gridLayout2.verticalSpacing = 0;
    gridLayout2.horizontalSpacing = 0;
    newUserComposite.setLayout(gridLayout2);

    newUser = new Button(newUserComposite, SWT.PUSH);
    newUser.setImage(Activator.getImageDescriptor("/icons/user/user_add.png")
        .createImage());
    newUser.setText("Add new user");
    newUser.addSelectionListener(new SelectionListener() {

      @Override
      public void widgetSelected(SelectionEvent event) {
        UserDetailsDialog dialog = new UserDetailsDialog(container.getShell(),
            allUsersInput);
        if (dialog != null) {
          dialog.open();
        }
      }

      @Override
      public void widgetDefaultSelected(SelectionEvent e) {
        // TODO Auto-generated method stub

      }
    });

    // Required to avoid an error in the system
    setControl(container);
    setPageComplete(true);
  }

  // This will create the columns for the table
  private void createColumns(final Composite parent, final TableViewer viewer) {
    String[] titles = { "User Name", "User Email" };
    int[] bounds = { 100, 100 };
    int[] weights = { 50, 50 };

    TableColumnLayout tableColumnLayout = new TableColumnLayout();
    parent.setLayout(tableColumnLayout);

    // First column is for the name
    TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0,
        viewer);
    col.setLabelProvider(new ColumnLabelProvider() {
      @Override
      public String getText(Object element) {
        User p = (User) element;
        return p.getUserName();
      }
    });
    tableColumnLayout.setColumnData(col.getColumn(), new ColumnWeightData(
        weights[0]));

    // Now the email
    col = createTableViewerColumn(titles[1], bounds[1], 1, viewer);
    col.setLabelProvider(new ColumnLabelProvider() {
      @Override
      public String getText(Object element) {
        User p = (User) element;
        return p.getUserEmail();
      }
    });
    tableColumnLayout.setColumnData(col.getColumn(), new ColumnWeightData(
        weights[1]));
  }

  private TableViewerColumn createTableViewerColumn(String title, int bound,
      final int colNumber, TableViewer viewer) {
    final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
        SWT.NONE);
    final TableColumn column = viewerColumn.getColumn();
    column.setText(title);
    column.setWidth(bound);
    column.setResizable(true);
    column.setMoveable(true);
    return viewerColumn;
  }

  private void updateButtons() {
    addOne.setEnabled(allUsersViewer.getTable().getSelectionCount() > 0);
    removeOne
        .setEnabled(selectedUsersViewer.getTable().getSelectionCount() > 0);
  }

  @SuppressWarnings({ "unchecked" })
  private void moveSelectedEntries(TableViewer aSource, TableViewer aDestination) {
    IStructuredSelection selection = (IStructuredSelection) aSource
        .getSelection();
    java.util.List<User> destinationInput = (java.util.List<User>) aDestination
        .getInput();
    if (destinationInput == null) {
      destinationInput = new ArrayList<User>();
    }
    java.util.List<User> sourceInput = (java.util.List<User>) aSource
        .getInput();
    for (User data : (java.util.List<User>) selection.toList()) {
      destinationInput.add(data);
      sourceInput.remove(data);
    }
    aSource.setInput(sourceInput);
    aDestination.setInput(destinationInput);

    aSource.refresh();
    aDestination.refresh();
  }

  @SuppressWarnings("unchecked")
  public List<User> getSelectedUsers() {
    return (java.util.List<User>) selectedUsersViewer.getInput();
  }

  public void setSummaryListener(ISummaryListener aListener) {
    mySummaryListener = aListener;
  }

}
