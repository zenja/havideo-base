package cn.edu.nju.software.liushuai.storage.util;

public class XmlConst {
	//document structure
	public static final String VERSION = "1.0";
	public static final String DOCUMENT_ROOT = "root";
	public static final String DOCUMENT_HEADER = "header";
	public static final String HEADER_STATUS = "status";
	public static final String HEADER_MESSAGE = "message";
	public static final String DOCUMENT_BODY = "body";
	//body tags
	public static final String BODY_LIST = "list";
	public static final String BODY_VEDIO = "vedio";
	public static final String BODY_CATALOG = "catalog";
	public static final String BODY_COMMENT = "comment";
	//tag list
	public static final String LIST_SIZE = "size";
	public static final String LIST_TYPE = "type";
	//tag vedio
	public static final String VEDIO_ID = "id";
	public static final String VEDIO_UPLOADER = "uploader";
	public static final String VEDIO_CAPTION = "caption";
	public static final String VEDIO_CATALOG = "catalog";
	public static final String VEDIO_DESCRIPTION = "description";
	public static final String VEDIO_ORIG_FILE_ADDRESS = "orig_file_address";
	public static final String VEDIO_NEW_FILE_ADDRESS = "new_file_address";
	public static final String VEDIO_TAGS = "tags";
	public static final String VEDIO_TAG = "tag";
	public static final String VEDIO_UPLOAD_TIME = "upload_time";
	public static final String VEDIO_CLICK_COUNT = "click_count";
	public static final String VEDIO_SCORE = "score";
	public static final String VEDIO_SCORE_COUNT = "score_count";
	//tag catalog
	public static final String CATALOG_ID = "id";
	public static final String CATALOG_NAME = "name";
	public static final String CATALOG_CHILDREN = "children";
	public static final String CATALOG_CHILD = "child";
	public static final String CATALOG_POSITION = "position";
	//tag comment
	public static final String COMMENT_ID = "id";
	public static final String COMMENT_VEDIO_ID = "vedio_id";
	public static final String COMMENT_USER = "user";
	public static final String COMMENT_UESR_NAME = "user_name";
	public static final String COMMENT_CONTENT = "content";
	public static final String COMMENT_TIME = "time";
}