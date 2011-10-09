package com.gcsf.pcm.model;

public class User implements ITreeElement, Comparable<User> {

  private String userName;

  private String userPhone;

  private String userEmail;

  public User(String userName, String userPhone, String userEmail) {
    super();
    this.userName = userName;
    this.userPhone = userPhone;
    this.userEmail = userEmail;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserPhone() {
    return userPhone;
  }

  public void setUserPhone(String userPhone) {
    this.userPhone = userPhone;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
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
