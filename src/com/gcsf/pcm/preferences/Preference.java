package com.gcsf.pcm.preferences;

public enum Preference {

  /** Contacts View */
  P_BEGIN_SEARCH_ON_TYPING(
      "com.gcsf.pcm.ui.internal.views.contacts.BeginSearchOnTyping"), //$NON-NLS-1$

  /** Contacts View */
  P_ALWAYS_SHOW_SEARCH(
      "com.gcsf.pcm.ui.internal.views.contacts.AlwaysShowSearch"), //$NON-NLS-1$

  /** General **/
  P_AUTO_LOGIN("com.gcsf.pcm.ui.login"); //$NON-NLS-1$

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