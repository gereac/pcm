package com.gcsf.pcm.model.treeviewer.dnd;

import java.util.List;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.TransferData;

import com.gcsf.pcm.model.User;
import com.gcsf.pcm.model.UserGroup;

public class UserTreeDropAdapter extends ViewerDropAdapter {

  public UserTreeDropAdapter(TreeViewer viewer) {
    super(viewer);
  }

  @Override
  public boolean performDrop(Object data) {
    System.out.println("drop object is " + data.getClass());
    if (data instanceof UserGroup) {
      System.out.println("cannot paste a user group ... it is not allowed");
      return false;
    }
    if (getCurrentTarget() instanceof User) {
      System.out
          .println("cannot drop a user onto another user ... operation not allowed");
      return false;
    } else if (getCurrentTarget() instanceof UserGroup) {
      UserGroup targetGroup = (UserGroup) getCurrentTarget();
      List<User> usersList = targetGroup.getGroupMembers();
      System.out.println("valid target for drop ... performing drop");
      User[] toDrop = (User[]) data;
      for (int i = 0; i < toDrop.length; i++) {
        usersList.add(toDrop[i]);
        targetGroup.setGroupMembers(usersList);
        ((TreeViewer) getViewer()).add(targetGroup, toDrop[i]);
        ((TreeViewer) getViewer()).reveal(toDrop[i]);
      }
      return true;
    } else {
      // this point is useless ... cannot drop to anything else
      return false;
    }

  }

  @Override
  public boolean validateDrop(Object target, int operation,
      TransferData transferType) {
    return (UserTransfer.getInstance().isSupportedType(transferType) && (target instanceof UserGroup));
  }
}
