package org.zenja.havideo.user.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
public class UsernameValidator{
 
	  private static final String USERNAME_PATTERN = "^[a-z0-9_]{4,15}$";
	  private static final Pattern PATTERN = Pattern.compile(USERNAME_PATTERN);
 
	  public static boolean validate(final String username) {
		  Matcher matcher = PATTERN.matcher(username);
		  return matcher.matches();
	  }
}