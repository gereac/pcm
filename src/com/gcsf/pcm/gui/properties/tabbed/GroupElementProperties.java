package com.gcsf.pcm.gui.properties.tabbed;

import java.util.List;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class GroupElementProperties implements IPropertySource {

  private GroupElement groupElement;

  protected static final String PROPERTY_NAME = "groupName"; //$NON-NLS-1$

  protected static final String PROPERTY_DESCRIPTION = "groupDescription"; //$NON-NLS-1$

  protected static final String PROPERTY_USERS = "groupMembers"; //$NON-NLS-1$

  private final Object PropertiesTable[][] = {
      { PROPERTY_NAME, new TextPropertyDescriptor(PROPERTY_NAME, "Name") },//$NON-NLS-1$
      { PROPERTY_DESCRIPTION,
          new TextPropertyDescriptor(PROPERTY_DESCRIPTION, "Description") }, //$NON-NLS-1$
      { PROPERTY_USERS, new PropertyDescriptor(PROPERTY_USERS, "Users") } //$NON-NLS-1$
  };

  String strName = "";//$NON-NLS-1$

  String strDesc = "";//$NON-NLS-1$

  List strUsers;

  public GroupElementProperties(GroupElement groupElement) {
    super();
    this.groupElement = groupElement;
    initProperties();
  }

  private void initProperties() {
    strName = groupElement.getGroup().getGroupName();
    strDesc = groupElement.getGroup().getGroupDescription();
    strUsers = groupElement.getGroup().getGroupMembers();
  }

  protected void firePropertyChanged(String propName, Object value) {
    if (propName.equals(PROPERTY_NAME)) {
      groupElement.getGroup().setGroupName((String) value);
      return;
    }
    if (propName.equals(PROPERTY_DESCRIPTION)) {
      groupElement.getGroup().setGroupDescription((String) value);
      return;
    }
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
      propertyDescriptors[i] = descriptor;
    }

    // Return it.
    return propertyDescriptors;
  }

  @Override
  public Object getPropertyValue(Object name) {
    if (name.equals(PROPERTY_NAME))
      return strName;
    if (name.equals(PROPERTY_DESCRIPTION))
      return strDesc;
    if (name.equals(PROPERTY_USERS))
      return new UserPropertySource(groupElement, strUsers);

    return null;
  }

  @Override
  public boolean isPropertySet(Object id) {
    return false;
  }

  @Override
  public void resetPropertyValue(Object id) {
  }

  @Override
  public void setPropertyValue(Object name, Object value) {
    if (name.equals(PROPERTY_NAME)) {
      strName = (String) value;
      return;
    }
    if (name.equals(PROPERTY_DESCRIPTION)) {
      strDesc = (String) value;
      return;
    }
    // if (name.equals(PROPERTY_USERS)) {
    // strUsers = (String) value;
    // return;
    // }
  }

  public GroupElement getGroupElement() {
    return groupElement;
  }

}
