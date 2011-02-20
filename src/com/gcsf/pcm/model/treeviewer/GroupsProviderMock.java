package com.gcsf.pcm.model.treeviewer;

import java.util.ArrayList;
import java.util.List;

import com.gcsf.pcm.model.User;
import com.gcsf.pcm.model.UserGroup;

public class GroupsProviderMock {

  public List<UserGroup> getUserGroups() {
    List<UserGroup> usergroups = new ArrayList<UserGroup>();
    UserGroup usergroup = new UserGroup();
    usergroup.setGroupName("Programming");
    usergroups.add(usergroup);
    User user = new User("Write more about e4", "Write more about e4",
        "Write more about e4");
    usergroup.getGroupMembers().add(user);
    user = new User("Android", "Write a widget.", "blah");
    usergroup.getGroupMembers().add(user);

    usergroup = new UserGroup();
    usergroup.setGroupName("Leasure");
    usergroups.add(usergroup);
    user = new User("Skiing", "Skiing", "Skiing");
    usergroup.getGroupMembers().add(user);

    return usergroups;
  }

}
