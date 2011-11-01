package com.gcsf.pcm.propertiesview;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.gcsf.pcm.adapter.UserGroupPropertySource;
import com.gcsf.pcm.model.UserGroup;

public class NamePropertySection extends AbstractPropertySection implements
    PropertyChangeListener {

  private UserGroup userGroup;

  private Text labelText;

  public void setInput(IWorkbenchPart part, ISelection selection) {
    super.setInput(part, selection);
    Assert.isTrue(selection instanceof IStructuredSelection);
    Object input = ((IStructuredSelection) selection).getFirstElement();
    Assert.isTrue(input instanceof UserGroup);
    this.userGroup = (UserGroup) input;
  }

  public void createControls(Composite parent,
      TabbedPropertySheetPage aTabbedPropertySheetPage) {
    super.createControls(parent, aTabbedPropertySheetPage);
    Composite composite = getWidgetFactory().createFlatFormComposite(parent);

    Group group = getWidgetFactory().createGroup(composite, "Name");
    FormLayout layout = new FormLayout();
    layout.marginWidth = ITabbedPropertyConstants.HSPACE + 2;
    layout.marginHeight = ITabbedPropertyConstants.VSPACE;
    layout.spacing = ITabbedPropertyConstants.VMARGIN + 1;
    group.setLayout(layout);
    FormData data = new FormData();
    data.left = new FormAttachment(0, 0);
    data.right = new FormAttachment(100, 0);
    data.top = new FormAttachment(0, 0);
    data.bottom = new FormAttachment(100, 0);
    group.setLayoutData(data);

    labelText = getWidgetFactory().createText(group, ""); //$NON-NLS-1$
    data = new FormData();
    data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH);
    data.right = new FormAttachment(100, 0);
    data.top = new FormAttachment(0, ITabbedPropertyConstants.VSPACE);
    labelText.setLayoutData(data);

    CLabel labelLabel = getWidgetFactory().createCLabel(group, "Name:"); //$NON-NLS-1$
    data = new FormData();
    data.left = new FormAttachment(0, 0);
    data.right = new FormAttachment(labelText, -ITabbedPropertyConstants.HSPACE);
    data.top = new FormAttachment(labelText, 0, SWT.CENTER);
    labelLabel.setLayoutData(data);

    labelLabel.setEnabled(false);
  }

  /**
   * Get the usergroup.
   * 
   * @return the usergroup.
   */
  public UserGroup getElement() {
    return userGroup;
  }

  /**
   * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#aboutToBeShown()
   */
  @Override
  public void aboutToBeShown() {
    getElement().addPropertyChangeListener(this);
  }

  /**
   * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#aboutToBeHidden()
   */
  @Override
  public void aboutToBeHidden() {
    getElement().removePropertyChangeListener(this);
  }

  public void refresh() {
    UserGroupPropertySource properties = (UserGroupPropertySource) userGroup
        .getAdapter(IPropertySource.class);
    labelText.setText((String) properties.getPropertyValue("groupName"));
  }

  /**
   * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
   */
  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    refresh();
  }
}
