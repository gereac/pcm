package com.gcsf.pcm.persist.pref;

public enum Preference {

  /** Contacts View */
  BE_BEGIN_SEARCH_ON_TYPING(
      "com.gcsf.pcm.ui.internal.views.contacts.BeginSearchOnTyping"), //$NON-NLS-1$

  /** Contacts View */
  BE_ALWAYS_SHOW_SEARCH(
      "com.gcsf.pcm.ui.internal.views.contacts.AlwaysShowSearch"); //$NON-NLS-1$

  private String fId;

  Preference(String id) {
    fId = id;
  }

  /**
   * @return the identifier of the preference.
   */
  public String id() {
    return fId;
  }

}