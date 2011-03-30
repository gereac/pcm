package com.gcsf.pcm.model.treeviewer.dnd.clipboard;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.dnd.Clipboard;

import com.gcsf.pcm.model.User;
import com.gcsf.pcm.model.UserGroup;
import com.gcsf.pcm.model.treeviewer.dnd.UserTransfer;

/**
 * Action for pasting a selection of gadgets from the clipboard into a tree
 * viewer.
 */
public class PasteUserAction extends Action {
  protected Clipboard clipboard;

  protected StructuredViewer viewer;

  public PasteUserAction(StructuredViewer viewer, Clipboard clipboard) {
    super("Paste");
    this.viewer = viewer;
    this.clipboard = clipboard;
  }

  public void run() {
    IStructuredSelection sel = (IStructuredSelection) viewer.getSelection();
    if (sel.getFirstElement() instanceof UserGroup) {
      User[] users = (User[]) clipboard.getContents(UserTransfer.getInstance());
      if (users == null) {
        return;
      }
      UserGroup aGroup = (UserGroup) sel.getFirstElement();
      for (User aUser : users) {
        aGroup.getGroupMembers().add(aUser);
      }
      viewer.setSelection(sel);
      viewer.refresh(aGroup);
    } else {
      System.out.println("Cannot paste a user onto a user");
      return;
    }

  }
}