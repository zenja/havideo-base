package cn.edu.nju.software.liushuai.storage.mongodb;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MongoConst implements ServletContextListener {

	// connection const, default value: localhost
	public static String HOST = "localhost";

	// database and collection const
	public static final String DATABASE_NAME = "hadoop_video_meta";
	public static final String CREATE_COLLECTION_CAPPED = "capped";
	public static final String COLLECTION_VIDEO = "video";
	public static final String COLLECTION_COMMENT = "comment";
	public static final String COLLECTION_CATALOG = "catalog";

	// collection video key
	public static final String KEY_VIDEO_UPLOADER = "uploader";
	public static final String KEY_VIDEO_CAPTION = "caption";
	public static final String KEY_VIDEO_CATALOG = "catalog";
	public static final String KEY_VIDEO_DESCRIPTION = "description";
	public static final String KEY_VIDEO_ORIG_FILE_ADDRESS = "orig_file_address";
	public static final String KEY_VIDEO_NEW_FILE_ADDRESS = "new_file_address";
	public static final String KEY_VIDEO_TAGS = "tags";
	public static final String KEY_VIDEO_UPLOAD_TIME = "upload_time";
	public static final String KEY_VIDEO_IS_ACTIVE = "is_active";
	public static final String KEY_VIDEO_CLICK_COUNT = "click_count";
	public static final String KEY_VIDEO_SCORE = "score";
	public static final String KEY_VIDEO_SCORE_TOTAL = "score_total";
	public static final String KEY_VIDEO_SCORE_COUNT = "score_count";
	
	// collection catalog key
	public static final String KEY_CATALOG_NAME = "name";
	public static final String KEY_CATALOG_CHILDREN = "children";
	public static final String KEY_CATALOG_POSITION = "position";
	
	// collection comment key
	public static final String KEY_COMMENT_VIDEO_ID = "video_id";
	public static final String KEY_COMMENT_USER = "user";
	public static final String KEY_COMMENT_USER_NAME = "name";
	public static final String KEY_COMMENT_CONTENT = "content";
	public static final String KEY_COMMENT_TIME = "time";
	
	// mongo query
	public static final String FUNCTION_GETLASTERROR_RETURN_KEY_ERR = "err";
	public static final String FUNCTION_GETLASTERROR_RETURN_KEY_UPDATED_EXISTING = "updatedExisting";
	public static final String DATABASE_KEYWORD_ID = "_id";
	public static final String MONGO_MODIFYER_SET = "$set";
	public static final String MONGO_MODIFYER_INCREASE = "$inc";
	public static final String MONGO_QUERY_IN = "$in";
	public static final String MONGO_QUERY_GREATER_THAN = "$gt";
	public static final String MONGO_QUERY_LESS_THAN = "$lt";

	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		// load configuration properties
		HOST = event.getServletContext().getInitParameter("mongodb_connection_host");
		
		assert HOST != null;
	}
}
