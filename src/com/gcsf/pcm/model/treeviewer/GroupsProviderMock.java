package com.gcsf.pcm.model.treeviewer;

import java.util.ArrayList;
import java.util.List;

import com.gcsf.pcm.model.User;
import com.gcsf.pcm.model.UserGroup;

public class GroupsProviderMock {

  private static GroupsProviderMock ourInstance;

  private List<UserGroup> usergroups = new ArrayList<UserGroup>();

  private GroupsProviderMock() {
    UserGroup usergroup = new UserGroup();
    usergroup.setGroupName("Group one");
    usergroups.add(usergroup);
    User user = new User("Rainer", "12345", "a@a");
    usergroup.getGroupMembers().add(user);
    user = new User("Reiner", "23456", "b@b");
    usergroup.getGroupMembers().add(user);
    user = new User("Marie", "34567", "c@c");
    usergroup.getGroupMembers().add(user);

    usergroup = new UserGroup();
    usergroup.setGroupName("Group two");
    usergroups.add(usergroup);
    user = new User("Holger", "456789", "d@d");
    usergroup.getGroupMembers().add(user);
    user = new User("Juliane", "567890", "e@e");
    usergroup.getGroupMembers().add(user);

    usergroup = new UserGroup();
    usergroup.setGroupName("Group three");
    usergroups.add(usergroup);
  }

  public static GroupsProviderMock getInstance() {
    if (ourInstance == null) {
      ourInstance = new GroupsProviderMock();
    }
    return ourInstance;
  }

  public List<UserGroup> getUserGroups() {

    return usergroups;
  }

}
