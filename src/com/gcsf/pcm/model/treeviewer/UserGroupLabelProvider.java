package com.gcsf.pcm.model.treeviewer;

import org.eclipse.jface.viewers.LabelProvider;

import com.gcsf.pcm.model.User;
import com.gcsf.pcm.model.UserGroup;

public class UserGroupLabelProvider extends LabelProvider {
  @Override
  public String getText(Object element) {
    if (element instanceof UserGroup) {
      UserGroup usergroup = (UserGroup) element;
      return usergroup.getGroupName();
    }
    return ((User) element).getUserName();
  }

}
