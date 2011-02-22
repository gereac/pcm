package com.gcsf.pcm.model.tableviewer;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

import com.gcsf.pcm.model.User;

public class UserNameEditingSupport extends EditingSupport {

  private final TableViewer viewer;

  public UserNameEditingSupport(TableViewer viewer) {
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
    return ((User) element).getUserName();
  }

  @Override
  protected void setValue(Object element, Object value) {
    ((User) element).setUserName(String.valueOf(value));
    viewer.refresh();
  }

}
