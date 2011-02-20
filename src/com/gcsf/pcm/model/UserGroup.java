package com.gcsf.pcm.model;

import java.util.ArrayList;
import java.util.List;

public class UserGroup {

  private String groupName;

  private String groupDescription;

  private List<User> groupMembers = new ArrayList<User>();

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public String getGroupDescription() {
    return groupDescription;
  }

  public void setGroupDescription(String groupDescription) {
    this.groupDescription = groupDescription;
  }

  public List<User> getGroupMembers() {
    return groupMembers;
  }

  public void setGroupMembers(List<User> groupMembers) {
    this.groupMembers = groupMembers;
  }

}
