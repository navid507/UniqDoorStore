package com.sai.udstore.sai;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

	// private Pattern pattern;
	// private Matcher matcher;
	//
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String PHONE_PATTERN = "^[0][9][1-9][0-9]{8}$";

	public Validator() {

	}

	/**
	 * Validate hex with regular expression
	 * 
	 * @param hex
	 *            hex for validation
	 * @return true valid hex, false invalid hex
	 */
	public static boolean email_validate(final String hex) {
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(hex);
		return matcher.matches();

	}

	public static boolean phone_validate(final String hex) {
		Pattern pattern = Pattern.compile(PHONE_PATTERN);
		Matcher matcher = pattern.matcher(hex);
		return matcher.matches();

	}

	public static boolean day_validate(int month, int day) {
		if (month < 7) {
			if (day < 32 && day > 0)
				return true;
		}
		if (month > 6) {
			if (day < 31 && day > 0)
				return true;
		}
		return false;

	}

	public static boolean month_validate(int month) {
		if (month < 13) {

			return true;
		} else
			return false;
	}

	public static boolean year_validate(int year) {
		if (year < 1400 && year > 1300) {

			return true;
		} else
			return false;
	}
}