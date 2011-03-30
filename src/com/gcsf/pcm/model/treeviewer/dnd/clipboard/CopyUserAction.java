package com.gcsf.pcm.model.treeviewer.dnd.clipboard;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.Transfer;

import com.gcsf.pcm.model.User;
import com.gcsf.pcm.model.treeviewer.dnd.UserTransfer;

/**
 * Action for copying a selection of gadgets to the clipboard
 */
public class CopyUserAction extends Action {
  protected Clipboard clipboard;

  protected StructuredViewer viewer;

  public CopyUserAction(StructuredViewer viewer, Clipboard clipboard) {
    super("Copy");
    this.viewer = viewer;
    this.clipboard = clipboard;
  }

  public void run() {
    IStructuredSelection sel = (IStructuredSelection) viewer.getSelection();
    User[] users = (User[]) sel.toList().toArray(new User[sel.size()]);
    clipboard.setContents(new Object[] { users },
        new Transfer[] { UserTransfer.getInstance() });
  }
}