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
    users.add(new User("Rainer", "Zufall", "male"));
    users.add(new User("Reiner", "Babbel", "male"));
    users.add(new User("Marie", "Dortmund", "female"));
    users.add(new User("Holger", "Adams", "male"));
    users.add(new User("Juliane", "Adams", "female"));
  }

  public List<User> getUsers() {
    return users;
  }

}
