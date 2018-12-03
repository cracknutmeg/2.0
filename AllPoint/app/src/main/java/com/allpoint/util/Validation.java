package com.allpoint.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

	public static final String SPECIAL_CHARACTERS = "!@#$%^&*()~`-=_+[]{}|:\";',./<>?";
	public static final int MIN_PASSWORD_LENGTH = 6;
	public static final int MAX_PASSWORD_LENGTH = 15;

	/*--------------------------------------------------------------------------------------------
	Funtion Name : isValid
	Process : Name Validation check Here
	Comment Description :
	----------------------------------------------------------------------------------------------*/
	public static boolean isValid(String str) {
		boolean isValid = false;
		String expression = "^[a-z_A-Z ]*$";
		CharSequence inputStr = str;
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}

	public static boolean isValidEmail(String str) {
		boolean isValidEmail = false;
		// check valid email id
		String validMailNew = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
				+ "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
				+ "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
				+ "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
				+ "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
				+ "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

		// Pattern pattern = Pattern.compile(".+@.+\\.[a-z]+");
		Pattern pattern = Pattern.compile(validMailNew);
		CharSequence inputStr = str;
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValidEmail = true;
		}
		return isValidEmail;
	}

	public static boolean validatePhoneNumber(String phoneNo) {
		// validate phone numbers of format "1234567890"
		if (phoneNo.matches("\\d{10}"))
			return true;
		// validating phone number with -, . or spaces
		// else if(phoneNo.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}"))
		// return true;
		// validating phone number with extension length from 3 to 5
		// else if(phoneNo.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}"))
		// return true;
		// validating phone number where area code is in braces ()
		// else if(phoneNo.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) return true;
		// return false if nothing matches the input
		else
			return false;

	}

	public static boolean validateZipCode(String phoneNo) {
		// validate phone numbers of format "1234567890"
		if (phoneNo.matches("\\d{5}"))
			return true;
		else
			return false;
	}

	public static boolean validateOTPCode(String phoneNo) {
		// validate phone numbers of format "1234567890"
		
		if (phoneNo.matches("\\d{8}"))
			return true;
		else
			return false;
	}

	/*
	 * Password validation - (?=.*[0-9]) a digit must occur at least once
	 * (?=.*[a-z]) a lower case letter must occur at least once (?=.*[A-Z]) an
	 * upper case letter must occur at least once (?=.*[@#$%^&+=]) a special
	 * character must occur at least once (?=\\S+$) no whitespace allowed in the
	 * entire string .{8,} at least 8 characters
	 */

	public static boolean passwordvalidation(String pass) {

		boolean isPassword = false;
		// check valid email id
		String validMailNew = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,15}";

		Pattern pattern = Pattern.compile(validMailNew);
		CharSequence inputStr = pass;
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isPassword = true;
		}
		return isPassword;
	}

	public static boolean passwordLenght(String pass) {
		boolean isPasswordLength = false;
		// check valid email id
		String validMailLength = "(?=\\S+$).{8,15}";

		Pattern pattern = Pattern.compile(validMailLength);
		CharSequence inputStr = pass;
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isPasswordLength = true;
		}
		return isPasswordLength;
	}

	public static boolean passwordCheck(String pass) {
		boolean isPasswordLength = false;
		// check valid email id
		String validMailLength = "(?=\\S+$).{8,15}";

		Pattern pattern = Pattern.compile(validMailLength);
		CharSequence inputStr = pass;
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isPasswordLength = true;
		}
		return isPasswordLength;
	}

	public static boolean passwordNumber(String pass) {
		boolean isPasswordNumber = false;
		// check valid email id
		String validMailLength = "(?=.*[a-zA-Z])(?=.*[0-9])";

		Pattern pattern = Pattern.compile(validMailLength);
		CharSequence inputStr = pass;
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isPasswordNumber = true;
		}
		return isPasswordNumber;
	}

	public static boolean passwordUperCase(String pass) {
		boolean isPasswordupperCase = false;
		// check valid email id
		String validMailLength = "(?=.*[a-zA-Z])";

		Pattern pattern = Pattern.compile(validMailLength);
		CharSequence inputStr = pass;
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isPasswordupperCase = true;
		}
		return isPasswordupperCase;
	}

	public static boolean passwordLowerCase(String pass) {
		boolean isPasswordlowerCase = false;
		// check valid email id
		String validMailLength = "(?=.*[A-Z])";

		Pattern pattern = Pattern.compile(validMailLength);
		CharSequence inputStr = pass;
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isPasswordlowerCase = true;
		}
		return isPasswordlowerCase;
	}

	public static boolean checkPasswordValidation(String password) {

		password = password.trim();
		int len = password.length();
		if (len < MIN_PASSWORD_LENGTH || len > MAX_PASSWORD_LENGTH) {
			return false;
		}
		char[] aC = password.toCharArray();
		for (char c : aC) {
			if (Character.isUpperCase(c)) {
			} else if (Character.isLowerCase(c)) {
			} else if (Character.isDigit(c)) {
			} else if (SPECIAL_CHARACTERS.indexOf(String.valueOf(c)) >= 0) {
			} else {
				return false;
			}
		}
		return true;
	}

	public String checkPasswordValidation1(String password) {
		String value = null;
		password = password.trim();
		int len = password.length();
		if (len < MIN_PASSWORD_LENGTH || len > MAX_PASSWORD_LENGTH) {
			value = "wrong size, it must have at least 8 characters and less than 20.";
			return value;
		}
		char[] aC = password.toCharArray();
		for (char c : aC) {
			if (!Character.isUpperCase(c)) {
				value = "Not Contain Upper Case";
				return value;
			} else if (!Character.isLowerCase(c)) {
				value = "Not Contain Lower Case";
				return value;
			} else if (Character.isDigit(c)) {
				value = "Not Contain Digit";
				return value;
			} /*
			 * else if (SPECIAL_CHARACTERS.indexOf(String.valueOf(c)) >= 0) {
			 */else {
				value = "is an invalid character in the password.";
				return value;
			}
		}
		return value;
	}

	/**
	 * Validates the credit card number using the Luhn algorithm
	 * 
	 * @param cardNumber
	 *            the credit card number
	 * 
	 */
	public static boolean validateCardNumber(String cardNumber)
			throws NumberFormatException {
		int sum = 0, digit, addend = 0;
		boolean doubled = false;
		for (int i = cardNumber.length() - 1; i >= 0; i--) {
			digit = Integer.parseInt(cardNumber.substring(i, i + 1));
			if (doubled) {
				addend = digit * 2;
				if (addend > 9) {
					addend -= 9;
				}
			} else {
				addend = digit;
			}
			sum += addend;
			doubled = !doubled;
		}
		return (sum % 10) == 0;
	}

	public static String keepNumbersOnly(CharSequence s) {
		return s.toString().replaceAll("[^0-9]", ""); // Should of course be
		// more robust
	}
}
