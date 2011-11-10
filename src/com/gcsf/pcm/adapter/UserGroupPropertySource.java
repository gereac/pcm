package com.gcsf.pcm.adapter;

import java.util.List;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.gcsf.pcm.model.User;
import com.gcsf.pcm.model.UserGroup;

public class UserGroupPropertySource implements IPropertySource {

  private static final String PROPERTY_NAME = "groupName"; //$NON-NLS-1$

  private static final String PROPERTY_DESCRIPTION = "groupDescription"; //$NON-NLS-1$

  private static final String PROPERTY_USERS = "groupMembers"; //$NON-NLS-1$

  private static final String PROPERTY_NAME_DISPLAY_VALUE = "Name";

  private static final String PROPERTY_DESCRIPTION_DISPLAY_VALUE = "Description";

  private static final String PROPERTY_USERS_DISPLAY_VALUE = "Users";

  private static final String BASIC_CATEGORY = "Basic";

  private static final String ADVANCED_CATEGORY = "Advanced";

  private static String PropertiesDisplayValuesTable[] = {
      PROPERTY_NAME_DISPLAY_VALUE, PROPERTY_DESCRIPTION_DISPLAY_VALUE,
      PROPERTY_USERS_DISPLAY_VALUE };

  private final Object PropertiesTable[][] = {
      {
          PROPERTY_NAME,
          new TextPropertyDescriptor(PROPERTY_NAME, PROPERTY_NAME_DISPLAY_VALUE) },
      {
          PROPERTY_DESCRIPTION,
          new TextPropertyDescriptor(PROPERTY_DESCRIPTION,
              PROPERTY_DESCRIPTION_DISPLAY_VALUE) },
      {
          PROPERTY_USERS,
          new TextPropertyDescriptor(PROPERTY_USERS,
              PROPERTY_USERS_DISPLAY_VALUE) } };

  private final UserGroup myUserGroup;

  public UserGroupPropertySource(UserGroup userGroup) {
    myUserGroup = userGroup;
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
    // Create the property vector.
    IPropertyDescriptor[] propertyDescriptors = new IPropertyDescriptor[PropertiesTable.length];

    for (int i = 0; i < PropertiesTable.length; i++) {
      // Add each property supported.
      PropertyDescriptor descriptor;
      descriptor = (PropertyDescriptor) PropertiesTable[i][1];
      propertyDescriptors[i] = (IPropertyDescriptor) descriptor;
      if (!descriptor.getId().equals(PROPERTY_USERS)) {
        descriptor.setCategory(BASIC_CATEGORY);
      } else {
        descriptor.setCategory(ADVANCED_CATEGORY);
      }
    }
    // Return it.
    return propertyDescriptors;
  }

  @Override
  public Object getPropertyValue(Object id) {
    if (id.equals(PROPERTY_NAME)) {
      return myUserGroup.getGroupName();
    }
    if (id.equals(PROPERTY_DESCRIPTION)) {
      return myUserGroup.getGroupDescription();
    }
    if (id.equals(PROPERTY_USERS)) {
      return myUserGroup.getGroupMembers();
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
      myUserGroup.setGroupName(s);
    }
    if (id.equals(PROPERTY_DESCRIPTION)) {
      myUserGroup.setGroupDescription(s);
    }
    @SuppressWarnings("unchecked")
    List<User> groupMembers = (List<User>) value;
    if (id.equals(PROPERTY_USERS)) {
      myUserGroup.setGroupMembers(groupMembers);
    }
  }

  public static String[] getPropertiesDisplayValuesTable() {
    return PropertiesDisplayValuesTable;
  }

}
