package cn.edu.nju.software.liushuai.storage.util;

public class HtmlTemplater {
	
	public enum Tag {
		p, h1, h2, h3, h4, h5
	}

	private static final String redirectHtmlTemplate = 
			"<!DOCTYPE html>" +
			"<html><head>" +
			"<meta http-equiv=\"content-type\" content=\"text/html;charset=UTF-8\">" +
			"<title>upload</title>" +
			"<script type=\"text/javascript\">" +
			"location.href = \"##REDIRECTION##\";" +
			"</script></head><body></body></html>";
	
	private static final String errorHtmlTemplate = 
			"<!DOCTYPE html>" +
			"<html><head>" +
			"<meta http-equiv=\"content-type\" content=\"text/html;charset=UTF-8\">" +
			"<title>Error!</title>" +
			"</head><body>##MESSAGE##</body></html>";
	
	public static String redirectHtml(String url) {
		return redirectHtmlTemplate.replace("##REDIRECTION##", url);
	}
	
	public static String errorHtml(String message) {
		return errorHtmlTemplate.replace("##MESSAGE##", message);
	}
	
	public static void appendLine(StringBuilder sb, Object content, Tag tag) {
		sb.append("<" + tag.toString() + ">" + String.valueOf(content) + "</" + tag.toString() + ">\n" );
	}
}
