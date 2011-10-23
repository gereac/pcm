package com.gcsf.pcm.adapter;

import java.util.List;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.gcsf.pcm.model.User;
import com.gcsf.pcm.model.UserGroup;

public class UserGroupPropertySource implements IPropertySource{
  
  protected static final String PROPERTY_NAME = "groupName"; //$NON-NLS-1$

  protected static final String PROPERTY_DESCRIPTION = "groupDescription"; //$NON-NLS-1$
  
  protected static final String PROPERTY_USERS = "groupMembers"; //$NON-NLS-1$
  
  private final UserGroup userGroup;

  public UserGroupPropertySource(UserGroup userGroup) {
    this.userGroup = userGroup;
  }


  @Override
  public boolean isPropertySet(Object id) {
    return false;
  }

  @Override
  public Object getEditableValue() {
    return this;
  }

  @Override
  public IPropertyDescriptor[] getPropertyDescriptors() {

    return new IPropertyDescriptor[] {
        new TextPropertyDescriptor(PROPERTY_NAME, "Name"),
        new TextPropertyDescriptor(PROPERTY_DESCRIPTION, "Description"),
        new TextPropertyDescriptor(PROPERTY_USERS, "Users")};
  }

  @Override
  public Object getPropertyValue(Object id) {
    if (id.equals(PROPERTY_NAME)) {
      return userGroup.getGroupName();
    }
    if (id.equals(PROPERTY_DESCRIPTION)) {
      return userGroup.getGroupDescription();
    }
    if (id.equals(PROPERTY_USERS)) {
      return userGroup.getGroupMembers();
    }
    return null;
  }

  @Override
  public void resetPropertyValue(Object id) {

  }

  @Override
  public void setPropertyValue(Object id, Object value) {
    String s = (String) value;
    if (id.equals(PROPERTY_NAME)) {
      userGroup.setGroupName(s);
    }
    if (id.equals(PROPERTY_DESCRIPTION)) {
      userGroup.setGroupDescription(s);
    }
    List<User> groupMembers = (List<User>) value;
    if (id.equals(PROPERTY_USERS)) {
      userGroup.setGroupMembers(groupMembers);
    }
  }

}
