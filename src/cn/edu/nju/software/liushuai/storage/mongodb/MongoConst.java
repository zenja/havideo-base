package cn.edu.nju.software.liushuai.storage.mongodb;

public class MongoConst {

	//connection const
	public static final String HOST = "localhost";
	//database and collection const
	public static final String DATABASE_NAME = "hadoop_vedio_meta";
	public static final String CREATE_COLLECTION_CAPPED = "capped";
	public static final String COLLECTION_VEDIO = "vedio";
	public static final String COLLECTION_COMMENT = "comment";
	public static final String COLLECTION_CATALOG = "catalog";
	//collection vedio key
	public static final String KEY_VEDIO_UPLOADER = "uploader";
	public static final String KEY_VEDIO_CAPTION = "caption";
	public static final String KEY_VEDIO_CATALOG = "catalog";
	public static final String KEY_VEDIO_DESCRIPTION = "description";
	public static final String KEY_VEDIO_ORIG_FILE_ADDRESS = "orig_file_address";
	public static final String KEY_VEDIO_NEW_FILE_ADDRESS = "new_file_address";
	public static final String KEY_VEDIO_TAGS = "tags";
	public static final String KEY_VEDIO_UPLOAD_TIME = "upload_time";
	public static final String KEY_VEDIO_IS_ACTIVE = "is_active";
	public static final String KEY_VEDIO_CLICK_COUNT = "click_count";
	public static final String KEY_VEDIO_SCORE = "score";
	public static final String KEY_VEDIO_SCORE_TOTAL = "score_total";
	public static final String KEY_VEDIO_SCORE_COUNT = "score_count";
	//collection catalog key
	public static final String KEY_CATALOG_NAME = "name";
	public static final String KEY_CATALOG_CHILDREN = "children";
	public static final String KEY_CATALOG_POSITION = "position";
	//collection comment key
	public static final String KEY_COMMENT_VEDIO_ID = "vedio_id";
	public static final String KEY_COMMENT_USER = "user";
	public static final String KEY_COMMENT_USER_NAME = "name";
	public static final String KEY_COMMENT_CONTENT = "content";
	public static final String KEY_COMMENT_TIME = "time";
	//mongo query
	public static final String FUNCTION_GETLASTERROR_RETURN_KEY_ERR = "err";
	public static final String FUNCTION_GETLASTERROR_RETURN_KEY_UPDATED_EXISTING = "updatedExisting";
	public static final String DATABASE_KEYWORD_ID = "_id";
	public static final String MONGO_MODIFYER_SET = "$set";
	public static final String MONGO_MODIFYER_INCREASE = "$inc";
	public static final String MONGO_QUERY_IN = "$in";
	public static final String MONGO_QUERY_GREATER_THAN = "$gt";
	public static final String MONGO_QUERY_LESS_THAN = "$lt";
}
