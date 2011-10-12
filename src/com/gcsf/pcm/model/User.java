package com.gcsf.pcm.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class User implements ITreeElement, Comparable<User> {

  private String userName;

  private String userPhone;

  private String userEmail;

  private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
      this);

  public User(String userName, String userPhone, String userEmail) {
    super();
    this.userName = userName;
    this.userPhone = userPhone;
    this.userEmail = userEmail;
  }
  
  public void addPropertyChangeListener(String propertyName,
      PropertyChangeListener listener) {
    propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
  }

  public void removePropertyChangeListener(PropertyChangeListener listener) {
    propertyChangeSupport.removePropertyChangeListener(listener);
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    propertyChangeSupport.firePropertyChange("userName", this.userName,
        this.userName = userName);
  }

  public String getUserPhone() {
    return userPhone;
  }

  public void setUserPhone(String userPhone) {
    propertyChangeSupport.firePropertyChange("userPhone", this.userPhone,
        this.userPhone = userPhone);
  }

  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    propertyChangeSupport.firePropertyChange("userEmail", this.userEmail,
        this.userEmail = userEmail);
  }

  @Override
  public String toString() {
    return "User [userName=" + userName + ", userPhone=" + userPhone
        + ", userEmail=" + userEmail + "]";
  }

  @Override
  public int compareTo(User aUser) {
    return this.userName.compareTo(aUser.userName);
  }

}
