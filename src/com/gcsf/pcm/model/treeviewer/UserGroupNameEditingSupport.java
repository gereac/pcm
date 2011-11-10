package com.gcsf.pcm.model.treeviewer;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Text;

import com.gcsf.pcm.model.UserGroup;

public class UserGroupNameEditingSupport extends EditingSupport {

  private final TreeViewer viewer;

  public UserGroupNameEditingSupport(TreeViewer viewer) {
    super(viewer);
    this.viewer = viewer;
  }

  @Override
  protected CellEditor getCellEditor(Object element) {
    TextCellEditor textCellEditor = new TextCellEditor(viewer.getTree());
    textCellEditor.getControl().addPaintListener(new PaintListener() {
      public void paintControl(PaintEvent e) {
        GC gc = e.gc;
        gc.setForeground(e.gc.getDevice().getSystemColor(SWT.COLOR_BLACK));
        Point r = ((Text) e.widget).getSize();
        gc.drawRectangle(0, 0, r.x - 1, r.y - 1);
      }
    });
    return textCellEditor;
  }

  @Override
  protected boolean canEdit(Object element) {
    if (element instanceof UserGroup) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  protected Object getValue(Object element) {
    return ((UserGroup) element).getGroupName();
  }

  @Override
  protected void setValue(Object element, Object value) {
    if (element instanceof UserGroup) {
      ((UserGroup) element).setGroupName(String.valueOf(value));
    }
    String[] props = { "groupName" };
    viewer.update(element, props);
  }
}
