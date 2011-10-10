package com.gcsf.pcm.wizards;

import java.util.List;

import com.gcsf.pcm.model.User;

public interface ISummaryListener {

  public void setGroupTitle(String title);
  
  public void setGroupDescription(String description);

  /**
   * Set users.
   * 
   * @param users
   *          List of users
   */
  public void setUsers(List<User> users);


}
