package com.gcsf.pcm.model.tableviewer;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

import com.gcsf.pcm.model.User;

public class UserEmailEditingSupport extends EditingSupport {

  private final TableViewer viewer;

  public UserEmailEditingSupport(TableViewer viewer) {
    super(viewer);
    this.viewer = viewer;
  }

  @Override
  protected CellEditor getCellEditor(Object element) {
    return new TextCellEditor(viewer.getTable());
  }

  @Override
  protected boolean canEdit(Object element) {
    return true;
  }

  @Override
  protected Object getValue(Object element) {
    return ((User) element).getUserEmail();
  }

  @Override
  protected void setValue(Object element, Object value) {
    ((User) element).setUserEmail(String.valueOf(value));
    viewer.refresh();
  }

}
