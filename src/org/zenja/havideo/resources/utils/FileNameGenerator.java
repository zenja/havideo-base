package org.zenja.havideo.resources.utils;

import java.util.Date;
import java.util.Random;

public class FileNameGenerator {
	private static final String alphabet =
			"0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final Random r = new Random();
	
	public static String generateVideoFileName(int user_id, String originFileName) {
		assert originFileName != null;
		
		/*
		 * Format:
		 * <user_id>_<timestamp>_<ten_random_chars>.<file_type>
		 * 
		 * Note:
		 * ".<file_type>" is omitted if there is no file type in originFileName
		 */
		StringBuilder builder = new StringBuilder();
		builder.append(user_id);
		builder.append('_');
		builder.append(getTimeStamp());
		builder.append('_');
		builder.append(getRandomChar(10));
		
		//append file type if it exists
		String[] splitParts = originFileName.split("\\.");
		if(splitParts.length > 0) {
			builder.append('.');
			builder.append(splitParts[splitParts.length - 1]);
		}
		
		return builder.toString();
	}

	private static String getRandomChar(int num) {
		StringBuilder builder = new StringBuilder();
	    for (int i = 0; i < num; i++) {
	        builder.append(alphabet.charAt(r.nextInt(alphabet.length())));
	    }
	    return builder.toString();
	}

	private static String getTimeStamp() {
		Date date = new Date();
		return new Long(date.getTime()).toString();
	}
	
	/*
	 * Test Code
	 */
	public static void main(String[] args) {
		for(int i = 0; i < 100; i++)
			System.out.println(generateVideoFileName(1, "my_video.old.wmv"));
	}
}
