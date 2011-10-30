package com.gcsf.pcm.adapter;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.gcsf.pcm.model.User;

public class UserPropertySource implements IPropertySource {

  protected static final String PROPERTY_NAME = "userName"; //$NON-NLS-1$

  protected static final String PROPERTY_PHONE = "userPhone"; //$NON-NLS-1$

  protected static final String PROPERTY_EMAIL = "userEmail"; //$NON-NLS-1$

  private final User user;

  private String defTextName;

  public UserPropertySource(User user) {
    this.user = user;
  }

  public UserPropertySource(User user, String defTextName) {
    this.user = user;
    this.defTextName = defTextName;
  }

  @Override
  public boolean isPropertySet(Object id) {
    if (id.equals(PROPERTY_NAME)) {
      String crtName = (String) getPropertyValue(id);
      return !crtName.equals(defTextName);
    }
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
        new TextPropertyDescriptor(PROPERTY_PHONE, "Phone"),
        new TextPropertyDescriptor(PROPERTY_EMAIL, "E-mail") };
  }

  @Override
  public Object getPropertyValue(Object id) {
    if (id.equals(PROPERTY_NAME)) {
      return user.getUserName();
    }
    if (id.equals(PROPERTY_PHONE)) {
      return user.getUserPhone();
    }
    if (id.equals(PROPERTY_EMAIL)) {
      return user.getUserEmail();
    }
    return null;
  }

  @Override
  public void resetPropertyValue(Object id) {
    if (id.equals(PROPERTY_NAME)) {
      setPropertyValue(id, defTextName);
    }
  }

  @Override
  public void setPropertyValue(Object id, Object value) {
    String s = (String) value;
    if (id.equals(PROPERTY_NAME)) {
      user.setUserName(s);
    }
    if (id.equals(PROPERTY_PHONE)) {
      user.setUserPhone(s);
    }
    if (id.equals(PROPERTY_EMAIL)) {
      user.setUserEmail(s);
    }
  }

}
