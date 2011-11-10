package com.gcsf.pcm.propertiesview;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.gcsf.pcm.adapter.UserGroupPropertySource;
import com.gcsf.pcm.model.User;
import com.gcsf.pcm.model.UserGroup;

public class MembersPropertySection extends AbstractPropertySection {

  private UserGroup userGroup;

  /**
   * the table control for the section.
   */
  private Table table;

  /**
   * the title columns for the section.
   */
  protected List columns;

  @Override
  public void setInput(IWorkbenchPart part, ISelection selection) {
    super.setInput(part, selection);
    Assert.isTrue(selection instanceof IStructuredSelection);
    Object input = ((IStructuredSelection) selection).getFirstElement();
    Assert.isTrue(input instanceof UserGroup);
    this.userGroup = (UserGroup) input;
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public void createControls(Composite parent,
      TabbedPropertySheetPage aTabbedPropertySheetPage) {
    super.createControls(parent, aTabbedPropertySheetPage);
    Composite composite = getWidgetFactory().createFlatFormComposite(parent);
    TableColumnLayout tableColumnLayout = new TableColumnLayout();
    composite.setLayout(tableColumnLayout);
    FormData data;

    table = getWidgetFactory().createTable(composite,
        SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
    table.setHeaderVisible(true);
    table.setLinesVisible(true);

    columns = new ArrayList();

    String[] titles = UserGroupPropertySource.getPropertiesDisplayValuesTable();
    int[] weights = { 30, 30, 30 };

    for (int i = 0; i < titles.length; i++) {
      TableColumn column = new TableColumn(table, SWT.NONE);
      column.setText(titles[i]);
      tableColumnLayout.setColumnData(column, new ColumnWeightData(weights[i]));
      columns.add(column);
    }

    data = new FormData();
    data.left = new FormAttachment(0, 0);
    data.right = new FormAttachment(100, 0);
    data.top = new FormAttachment(0, 0);
    data.bottom = new FormAttachment(100, 0);
    table.setLayoutData(data);
  }

  @Override
  public void refresh() {
    table.removeAll();

    for (Iterator<?> i = getOwnedRows().iterator(); i.hasNext();) {
      Object next = i.next();
      User aUser = (User) next;
      TableItem item = new TableItem(table, SWT.NONE);
      item.setText(new String[] { aUser.getUserName(), aUser.getUserEmail(),
          aUser.getUserPhone() });
      item.setData(next);
    }
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  protected List getOwnedRows() {

    ArrayList ret = new ArrayList();
    for (Iterator i = userGroup.getGroupMembers().iterator(); i.hasNext();) {
      ret.add(i.next());
    }
    return ret;
  }

  @Override
  public boolean shouldUseExtraSpace() {
    return true;
  }
}
