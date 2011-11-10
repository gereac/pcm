package com.gcsf.pcm.dialogs;

import org.apache.commons.validator.EmailValidator;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class EmailRequiredValidator implements IValidator {

  private final String errorText;

  public EmailRequiredValidator(String errorText) {
    super();
    this.errorText = errorText;
  }

  @Override
  public IStatus validate(Object value) {
    if (value instanceof String) {
      String text = (String) value;
      if (text.trim().length() != 0) {
        System.out.println("we have some text ... will look at it");
        if (!EmailValidator.getInstance().isValid(text)) {
          System.out.println("not a valid email");
          return ValidationStatus.error(errorText);
        } else {
          System.out.println("is valid email");
        }
      } else {
        System.out.println("no text ... this is not good");
        return ValidationStatus.error(errorText);
      }
    }
    return Status.OK_STATUS;
  }

}
