package com.gcsf.pcm.dialogs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class PhoneRequiredValidator implements IValidator {

  private final String errorText;

  public PhoneRequiredValidator(String errorText) {
    super();
    this.errorText = errorText;
  }

  @Override
  public IStatus validate(Object value) {
    if (value instanceof String) {
      String text = (String) value;
      if (text.trim().length() != 0) {
        if (!isNumeric(text)) {
          return ValidationStatus.error(errorText);
        }
      } else {
        return ValidationStatus.error(errorText);
      }
    }
    return Status.OK_STATUS;
  }

  /**
   * isPhoneNumberValid: Validate phone number using Java reg ex. This method
   * checks if the input string is a valid phone number.
   * 
   * @param email
   *          String. Phone number to validate
   * @return boolean: true if phone number is valid, false otherwise.
   */
  @SuppressWarnings("unused")
  private boolean isPhoneNumberValid(String phoneNumber) {
    boolean isValid = false;
    /*
     * Phone Number formats: (nnn)nnn-nnnn; nnnnnnnnnn; nnn-nnn-nnnn ^\\(? : May
     * start with an option "(" . (\\d{3}): Followed by 3 digits. \\)? : May
     * have an optional ")" [- ]? : May have an optional "-" after the first 3
     * digits or after optional ) character. (\\d{3}) : Followed by 3 digits. [-
     * ]? : May have another optional "-" after numeric digits. (\\d{4})$ : ends
     * with four digits.
     * 
     * Examples: Matches following phone numbers: (123)456-7890, 123-456-7890,
     * 1234567890, (123)-456-7890
     */
    // Initialize reg ex for phone number.
    String expression = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$";
    CharSequence inputStr = phoneNumber;
    Pattern pattern = Pattern.compile(expression);
    Matcher matcher = pattern.matcher(inputStr);
    if (matcher.matches()) {
      isValid = true;
    }
    return isValid;
  }

  /**
   * isNumeric: Validate a number using Java regex. This method checks if the
   * input string contains all numeric characters.
   * 
   * @param email
   *          String. Number to validate
   * @return boolean: true if the input is all numeric, false otherwise.
   */

  private boolean isNumeric(String number) {
    boolean isValid = false;

    /*
     * Number: A numeric value will have following format: ^[-+]?: Starts with
     * an optional "+" or "-" sign. [0-9]*: May have one or more digits. \\.? :
     * May contain an optional "." (decimal point) character. [0-9]+$ : ends
     * with numeric digit.
     */

    // Initialize reg ex for numeric data.
    String expression = "^[-+]?[0-9]*\\.?[0-9]+$";
    CharSequence inputStr = number;
    Pattern pattern = Pattern.compile(expression);
    Matcher matcher = pattern.matcher(inputStr);
    if (matcher.matches()) {
      isValid = true;
    }
    return isValid;
  }

}
