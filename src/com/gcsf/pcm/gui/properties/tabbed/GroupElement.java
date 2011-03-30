package com.gcsf.pcm.gui.properties.tabbed;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.views.properties.IPropertySource;

import com.gcsf.pcm.model.UserGroup;

public class GroupElement implements IWorkbenchAdapter, IAdaptable {

  private UserGroup group;

  public GroupElement(UserGroup group) {
    this.group = group;
  }

  @Override
  public Object getAdapter(Class adapter) {
    if (adapter == IWorkbenchAdapter.class)
      return this;
    if (adapter == IPropertySource.class)
      return new GroupElementProperties(this);
    return null;
  }

  @Override
  public Object[] getChildren(Object o) {
    // return this.group.getGroupMembers().toArray();
    return null;
  }

  @Override
  public ImageDescriptor getImageDescriptor(Object object) {
    return null;
  }

  @Override
  public String getLabel(Object o) {
    return this.group.getGroupName();
  }

  @Override
  public Object getParent(Object o) {
    return null;
  }

  public UserGroup getGroup() {
    return this.group;
  }

}
