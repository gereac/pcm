package com.gcsf.pcm.dialogs;

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

class StringRequiredValidator implements IValidator {

  private final String errorText;

  public StringRequiredValidator(String errorText) {
    super();
    this.errorText = errorText;
  }

  public IStatus validate(Object value) {
    if (value instanceof String) {
      String text = (String) value;
      if (text.trim().length() == 0) {
        return ValidationStatus.error(errorText);
      }
    }
    return Status.OK_STATUS;
  }
}