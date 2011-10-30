package com.gcsf.pcm.adapter;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.views.properties.IPropertySource;

import com.gcsf.pcm.model.User;
import com.gcsf.pcm.model.UserGroup;

public class InternalModelAdapterFactory implements IAdapterFactory {

  @Override
  public Object getAdapter(Object adaptableObject, Class adapterType) {
    if (adapterType == IPropertySource.class
        && adaptableObject instanceof UserGroup) {
      return new UserGroupPropertySource((UserGroup) adaptableObject);
    }
    if (adapterType == IPropertySource.class && adaptableObject instanceof User) {
      return new UserPropertySource((User) adaptableObject,
          ((User) adaptableObject).getUserName());
    }
    return null;
  }

  @Override
  public Class[] getAdapterList() {
    return new Class[] { IPropertySource.class };
  }

}
