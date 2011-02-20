package com.gcsf.pcm.model.tableviewer;

import java.util.ArrayList;
import java.util.List;

import com.gcsf.pcm.model.User;

public enum UsersProviderMock {

  INSTANCE;

  private List<User> users;

  private UsersProviderMock() {
    users = new ArrayList<User>();
    // Image here some fancy database access to read the persons and to
    // put them into the model
    users.add(new User("Rainer", "12345", "a@a"));
    users.add(new User("Reiner", "23456", "b@b"));
    users.add(new User("Marie", "34567", "c@c"));
    users.add(new User("Holger", "456789", "d@d"));
    users.add(new User("Juliane", "567890", "e@e"));
  }

  public List<User> getUsers() {
    return users;
  }

}
