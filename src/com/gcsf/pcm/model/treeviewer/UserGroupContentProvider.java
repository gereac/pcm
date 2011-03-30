package com.gcsf.pcm.model.treeviewer;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.gcsf.pcm.model.User;
import com.gcsf.pcm.model.UserGroup;

public class UserGroupContentProvider implements ITreeContentProvider {

  private GroupsProviderMock model;

  @Override
  public void dispose() {
  }

  @Override
  public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    this.model = (GroupsProviderMock) newInput;
  }

  @Override
  public Object[] getElements(Object inputElement) {
    return model.getUserGroups().toArray();
  }

  @Override
  public Object[] getChildren(Object parentElement) {
    if (parentElement instanceof UserGroup) {
      UserGroup usergroup = (UserGroup) parentElement;
      return usergroup.getGroupMembers().toArray();
    }
    return null;
  }

  @Override
  public Object getParent(Object element) {
    Object parent = null;
    if (element instanceof User) {
      for (UserGroup aGrp : model.getUserGroups()) {
        for (User aUser : aGrp.getGroupMembers()) {
          if (aUser.equals(element)) {
            parent = aGrp;
            break;
          }
        }
      }
    }
    return parent;
  }

  @Override
  public boolean hasChildren(Object element) {
    if (element instanceof UserGroup) {
      return true;
    }
    return false;
  }

}
