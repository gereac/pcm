package com.gcsf.pcm.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.views.properties.IPropertySource;

import com.gcsf.pcm.adapter.UserGroupPropertySource;

public class UserGroup implements PropertyChangeListener, ITreeElement {

  private String groupName;

  private String groupDescription;

  private List<User> groupMembers = new ArrayList<User>();

  private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
      this);

  public void addPropertyChangeListener(PropertyChangeListener listener) {
    propertyChangeSupport.addPropertyChangeListener(listener);
  }

  public void removePropertyChangeListener(PropertyChangeListener listener) {
    propertyChangeSupport.removePropertyChangeListener(listener);
  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    propertyChangeSupport.firePropertyChange("groupName", this.groupName,
        this.groupName = groupName);

  }

  public String getGroupDescription() {
    return groupDescription;
  }

  public void setGroupDescription(String groupDescription) {
    propertyChangeSupport.firePropertyChange("groupDescription",
        this.groupDescription, this.groupDescription = groupDescription);
  }

  public List<User> getGroupMembers() {
    return groupMembers;
  }

  public void setGroupMembers(List<User> groupMembers) {
    propertyChangeSupport.firePropertyChange("groupMembers", this.groupMembers,
        this.groupMembers = groupMembers);
  }

  @Override
  public String toString() {
    return "UserGroup [groupName=" + groupName + ", groupDescription="
        + groupDescription + ", groupMembers=" + groupMembers + "]";
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    propertyChangeSupport.firePropertyChange("groupName", this.groupName,
        this.groupName = groupName);
  }

  public UserGroupPropertySource getAdapter(Class adapter) {
    if (adapter == IPropertySource.class)
      return new UserGroupPropertySource(this);
    return null;
  }

}
