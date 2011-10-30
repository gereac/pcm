package com.gcsf.pcm.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.gcsf.pcm.Activator;

/**
 * This class represents a preference page that is contributed to the
 * Preferences dialog. By subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 */

public class GeneralPreferencePage extends FieldEditorPreferencePage implements
    IWorkbenchPreferencePage {

  public GeneralPreferencePage() {
    super(GRID);
    setPreferenceStore(Activator.getDefault().getPreferenceStore());
  }

  /**
   * Creates the field editors. Field editors are abstractions of the common GUI
   * blocks needed to manipulate various types of preferences. Each field editor
   * knows how to save and restore itself.
   */
  public void createFieldEditors() {
    addField(new BooleanFieldEditor(Preference.P_AUTO_LOGIN.id(),
        "&Login automatically at startup", getFieldEditorParent()));
    addField(new BooleanFieldEditor(Preference.P_BEGIN_SEARCH_ON_TYPING.id(),
        "&Start search on typing", getFieldEditorParent()));
    addField(new BooleanFieldEditor(Preference.P_ALWAYS_SHOW_SEARCH.id(),
        "&Always show search", getFieldEditorParent()));
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
   */
  public void init(IWorkbench workbench) {
    noDefaultAndApplyButton();
  }

}