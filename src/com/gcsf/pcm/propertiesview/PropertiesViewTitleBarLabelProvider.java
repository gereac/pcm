package com.gcsf.pcm.propertiesview;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.graphics.Image;

import com.gcsf.pcm.Activator;
import com.gcsf.pcm.model.UserGroup;

public class PropertiesViewTitleBarLabelProvider implements ILabelProvider {

  @Override
  public void addListener(ILabelProviderListener listener) {
    // TODO Auto-generated method stub

  }

  @Override
  public void dispose() {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean isLabelProperty(Object element, String property) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void removeListener(ILabelProviderListener listener) {
    // TODO Auto-generated method stub

  }

  @Override
  public Image getImage(Object element) {
    return Activator.getImageDescriptor("/icons/form_banner.gif").createImage();
  }

  @Override
  public String getText(Object element) {
    Assert.isTrue(element instanceof IStructuredSelection);
    IStructuredSelection structuredSelection = (IStructuredSelection) element;
    if (structuredSelection.equals(StructuredSelection.EMPTY)) {
      return null;
    }
    return ((UserGroup) structuredSelection.getFirstElement()).getGroupName();
  }

}
