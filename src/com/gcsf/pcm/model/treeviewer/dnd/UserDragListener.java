package com.gcsf.pcm.model.treeviewer.dnd;

import java.util.Iterator;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.ui.part.PluginTransfer;
import org.eclipse.ui.part.PluginTransferData;

import com.gcsf.pcm.model.User;
import com.gcsf.pcm.model.UserGroup;

public class UserDragListener extends DragSourceAdapter {

  private StructuredViewer viewer;

  private UserGroup sourceGroup;

  public UserDragListener(TreeViewer viewer) {
    this.viewer = viewer;
  }

  /**
   * Method declared on DragSourceListener
   */
  public void dragFinished(DragSourceEvent event) {
    if (!event.doit)
      return;
    // if the user was moved, remove it from the source viewer
    if (event.detail == DND.DROP_MOVE) {
      IStructuredSelection selection = (IStructuredSelection) viewer
          .getSelection();
      for (Iterator it = selection.iterator(); it.hasNext();) {
        User aUser = (User) it.next();
        sourceGroup.getGroupMembers().remove(aUser);
      }
      viewer.refresh();
    }
  }

  /**
   * Method declared on DragSourceListener
   */
  public void dragSetData(DragSourceEvent event) {
    IStructuredSelection selection = (IStructuredSelection) viewer
        .getSelection();
    if (selection.getFirstElement() instanceof User) {
      User[] users = (User[]) selection.toList().toArray(
          new User[selection.size()]);
      if (UserTransfer.getInstance().isSupportedType(event.dataType)) {
        event.data = users;
      } else if (PluginTransfer.getInstance().isSupportedType(event.dataType)) {
        byte[] data = UserTransfer.getInstance().toByteArray(users);
        event.data = new PluginTransferData("com.gcsf.pcm.dnd.gadgetDrop", data);
      }
    }
  }

  /**
   * Method declared on DragSourceListener
   */
  public void dragStart(DragSourceEvent event) {
    if (!viewer.getSelection().isEmpty()) {
      IStructuredSelection selection = (IStructuredSelection) viewer
          .getSelection();
      if (selection.getFirstElement() instanceof UserGroup) {
        System.out.println("UserGroup is not a valid drag start");
        event.doit = false;
      } else {
        event.doit = !viewer.getSelection().isEmpty();
        if (selection.getFirstElement() instanceof User) {
          System.out.println("starting to drag a user");
          sourceGroup = (UserGroup) ((ITreeContentProvider) viewer
              .getContentProvider()).getParent(selection.getFirstElement());
        }
      }
    }
  }
}
