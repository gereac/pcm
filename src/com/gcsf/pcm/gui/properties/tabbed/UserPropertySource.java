package com.gcsf.pcm.gui.properties.tabbed;

import java.util.List;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class UserPropertySource implements IPropertySource {
  protected GroupElement element;

  /**
   * The name.
   */
  public static String ID_NAME = "Name"; //$NON-NLS-1$

  /**
   * The phone.
   */
  public static String ID_PHONE = "Phone"; //$NON-NLS-1$

  /**
   * The e-mail.
   */
  public static String ID_EMAIL = "Email"; //$NON-NLS-1$

  protected static IPropertyDescriptor[] descriptors;

  static {
    descriptors = new IPropertyDescriptor[] {
        new TextPropertyDescriptor(ID_NAME, "name"),//$NON-NLS-1$
        new TextPropertyDescriptor(ID_PHONE, "phone"), //$NON-NLS-1$
        new TextPropertyDescriptor(ID_EMAIL, "email") //$NON-NLS-1$
    };
  }

  protected List users;

  /**
   * The constructor for SizePropertySource.
   * 
   * @param m_element
   *          the button element.
   * @param point
   *          the size of the button element.
   */
  public UserPropertySource(GroupElement m_element, List m_users) {
    users = m_users;
    element = m_element;
  }

  /**
   * Fire a property change event.
   * 
   * @param propName
   *          the name of the property change.
   */
  protected void firePropertyChanged() {
    element.getGroup().setGroupMembers(users);
  }

  public Object getEditableValue() {
    return this;
  }

  public IPropertyDescriptor[] getPropertyDescriptors() {
    return descriptors;
  }

  public Object getPropertyValue(Object propName) {
    if (ID_NAME.equals(propName)) {
      return new String(users.get(0).toString());
    }
    if (ID_PHONE.equals(propName)) {
      return new String(users.get(0).toString());
    }
    if (ID_EMAIL.equals(propName)) {
      return new String(users.get(0).toString());
    }
    return null;
  }

  /**
   * Retrieve the value of the size property.
   * 
   * @return the value of the size property.
   */
  public List getValue() {
    return users;
  }

  /**
   * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(Object)
   */
  public boolean isPropertySet(Object propName) {
    if (ID_NAME.equals(propName) || ID_PHONE.equals(propName))
      return true;
    return false;
  }

  public void resetPropertyValue(Object propName) {
    //
  }

  public void setPropertyValue(Object propName, Object value) {
    int newInt;
    try {
      newInt = Integer.parseInt((String) value);
    } catch (NumberFormatException e) {
      newInt = -1;
    }

    // if (newInt > 0) {
    // if (ID_NAME.equals(propName)) {
    // point.x = newInt;
    // } else if (ID_PHONE.equals(propName)) {
    // point.y = newInt;
    // }
    // }
    firePropertyChanged();
  }

  public String toString() {
    return users.toString();
  }

}