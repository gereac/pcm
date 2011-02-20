package com.gcsf.pcm.model.treeviewer;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

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
    return null;
  }

  @Override
  public boolean hasChildren(Object element) {
    if (element instanceof UserGroup) {
      return true;
    }
    return false;
  }

}
