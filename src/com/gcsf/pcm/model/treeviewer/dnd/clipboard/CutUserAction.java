package com.gcsf.pcm.model.treeviewer.dnd.clipboard;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.Transfer;

import com.gcsf.pcm.model.User;
import com.gcsf.pcm.model.treeviewer.dnd.UserTransfer;

/**
 * Class for cutting a selection of gadgets from a view, and placing them on the
 * clipboard.
 */
public class CutUserAction extends Action {
  protected Clipboard clipboard;

  protected StructuredViewer viewer;

  public CutUserAction(StructuredViewer viewer, Clipboard clipboard) {
    super("Cut");
    this.viewer = viewer;
    this.clipboard = clipboard;
  }

  public void run() {
    IStructuredSelection sel = (IStructuredSelection) viewer.getSelection();
    User[] gadgets = (User[]) sel.toList().toArray(new User[sel.size()]);
    clipboard.setContents(new Object[] { gadgets },
        new Transfer[] { UserTransfer.getInstance() });
    // for (int i = 0; i < gadgets.length; i++) {
    // gadgets[i].setParent(null);
    // }
    viewer.refresh();
  }
}