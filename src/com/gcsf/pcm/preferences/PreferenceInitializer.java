package com.gcsf.pcm.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.gcsf.pcm.Activator;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
   * initializeDefaultPreferences()
   */
  public void initializeDefaultPreferences() {
    IPreferenceStore store = Activator.getDefault().getPreferenceStore();
    store.setDefault(Preference.P_AUTO_LOGIN.id(), false);
    /* Default Contacts View */
    initContactsViewDefaults(store);
  }

  private void initContactsViewDefaults(IPreferenceStore store) {
    store.setDefault(Preference.P_BEGIN_SEARCH_ON_TYPING.id(), true);
    store.setDefault(Preference.P_ALWAYS_SHOW_SEARCH.id(), false);
  }

}
