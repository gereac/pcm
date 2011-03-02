package com.gcsf.pcm.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class UserGroup implements PropertyChangeListener {

  private String groupName;

  private String groupDescription;

  private List<User> groupMembers = new ArrayList<User>();

  private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
      this);

  public void addPropertyChangeListener(String propertyName,
      PropertyChangeListener listener) {
    propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
  }

  public void removePropertyChangeListener(PropertyChangeListener listener) {
    propertyChangeSupport.removePropertyChangeListener(listener);
  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    // this.groupName = groupName;
    propertyChangeSupport.firePropertyChange("groupName", this.groupName,
        this.groupName = groupName);

  }

  public String getGroupDescription() {
    return groupDescription;
  }

  public void setGroupDescription(String groupDescription) {
    // this.groupDescription = groupDescription;
    propertyChangeSupport.firePropertyChange("groupDescription",
        this.groupDescription, this.groupDescription = groupDescription);
  }

  public List<User> getGroupMembers() {
    return groupMembers;
  }

  public void setGroupMembers(List<User> groupMembers) {
    // this.groupMembers = groupMembers;
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

}
