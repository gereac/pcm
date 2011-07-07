package com.gcsf.pcm.persist.pref;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.gcsf.pcm.Activator;

public class PreferencesInitializer extends AbstractPreferenceInitializer {

  @Override
  public void initializeDefaultPreferences() {
    IPreferenceStore store = Activator.getDefault().getPreferenceStore();
    /* Default Contacts View */
    initContactsViewDefaults(store);
  }

  private void initContactsViewDefaults(IPreferenceStore store) {
    store.setDefault(Preference.BE_BEGIN_SEARCH_ON_TYPING.id(), true);
    store.setDefault(Preference.BE_ALWAYS_SHOW_SEARCH.id(), false);
  }

}
