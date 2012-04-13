package cn.edu.nju.software.liushuai.storage.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.bson.types.BasicBSONList;
import org.bson.types.ObjectId;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cn.edu.nju.software.liushuai.storage.exception.ErrorBranchException;
import cn.edu.nju.software.liushuai.storage.exception.SeeGodException;
import cn.edu.nju.software.liushuai.storage.mongodb.MongoFactory;
import cn.edu.nju.software.liushuai.storage.mongodb.MongoConst;
import cn.edu.nju.software.liushuai.storage.util.ErrorMessage;
import cn.edu.nju.software.liushuai.storage.util.XmlConst;
import cn.edu.nju.software.liushuai.storage.util.XmlMaker;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

@Path("/user")
public class UserOption {
	/*
	 * options acted by user
	 * contains upload,view,catalogs,catalog,score/give,comment,comments,search/tags
	 * all encode must be utf-8
	 * method name : httpMethodName + pathName(if sub path , ?/* means ?by*)
	 */
	
	@POST
	@Path("/upload")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_XML)
	public Document postUpload(
			@FormParam("uploader") String uploader,//@TODO check with wang
			@FormParam("caption") String caption,
			@FormParam("catalog") String catalog,
			@FormParam("description") String description,
			@FormParam("orig_file_address") String origFileAddress,//@TODO check with wang
			@FormParam("new_file_address") String newFileAddress,//@TODO check with wang
			@FormParam("tags") String tags,//@TODO check with liang
			@FormParam("upload_time") String uploadTime//@TODO check with liang
			) throws SeeGodException {
		Document doc = XmlMaker.createDocumentWithHeader();
		Mongo mongo = null;
		try {
			mongo = MongoFactory.getMongoConnetion();
			DB db = mongo.getDB(MongoConst.DATABASE_NAME);
			DBCollection vedioCollection = db.getCollection(
							MongoConst.COLLECTION_VEDIO);
			ObjectId objectId = new ObjectId();
			DBObject vedioBean = new BasicDBObject();
			vedioBean.put(MongoConst.DATABASE_KEYWORD_ID, objectId);
			vedioBean.put(MongoConst.KEY_VEDIO_UPLOADER, uploader);
			vedioBean.put(MongoConst.KEY_VEDIO_CAPTION, caption);			
			vedioBean.put(MongoConst.KEY_VEDIO_DESCRIPTION, description);
			vedioBean.put(MongoConst.KEY_VEDIO_ORIG_FILE_ADDRESS, origFileAddress);
			vedioBean.put(MongoConst.KEY_VEDIO_NEW_FILE_ADDRESS, newFileAddress);
			vedioBean.put(MongoConst.KEY_VEDIO_UPLOAD_TIME, uploadTime);
			vedioBean.put(MongoConst.KEY_VEDIO_IS_ACTIVE, Boolean.FALSE);
			vedioBean.put(MongoConst.KEY_VEDIO_CLICK_COUNT, Integer.valueOf(0));
			//catalog
			String[] catalogArray = catalog.split("\\s");
			vedioBean.put(MongoConst.KEY_VEDIO_CATALOG, catalogArray);
			//tags
			String[] tagArray = tags.split("\\s");
			vedioBean.put(MongoConst.KEY_VEDIO_TAGS, tagArray);
			//score
			DBObject score = new BasicDBObject();
			score.put(MongoConst.KEY_VEDIO_SCORE_COUNT, Integer.valueOf(0));
			score.put(MongoConst.KEY_VEDIO_SCORE_TOTAL, Double.valueOf(0));
			vedioBean.put(MongoConst.KEY_VEDIO_SCORE, score);
			
			vedioCollection.insert(vedioBean);
			DBObject error = db.getLastError();
			Object err = error.get(MongoConst.FUNCTION_GETLASTERROR_RETURN_KEY_ERR);
			if(err != null) {
				//log mail
				XmlMaker.setDocumentHeader(doc, err.toString(), false);
			} else {
				Element root = doc.getDocumentElement();
				Element body = XmlMaker.createElementChild(doc, root, XmlConst.DOCUMENT_BODY);
				Element id = XmlMaker.createElementChild(doc, body, XmlConst.VEDIO_ID);
				id.setTextContent(objectId.toString());
			}
		} catch (MongoException e) {
			//log mail
			XmlMaker.setDocumentHeader(doc, e.getMessage(), false);
		} finally {
			MongoFactory.recoverMongoConnection(mongo);
		}
		return doc;
	}
	
	@GET
	@Path("/view/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public Document getView(@PathParam("id") String id) throws SeeGodException {
		Document doc = XmlMaker.createDocumentWithHeader();
		Mongo mongo = null;
		try {
			mongo = MongoFactory.getMongoConnetion();
			DB db = mongo.getDB(MongoConst.DATABASE_NAME);
			DBCollection vedioCollection = db.getCollection(MongoConst.COLLECTION_VEDIO);
			DBObject query = new BasicDBObject();
			query.put(MongoConst.DATABASE_KEYWORD_ID, new ObjectId(id));
			query.put(MongoConst.KEY_VEDIO_IS_ACTIVE, Boolean.TRUE);
			DBObject vedioBean = vedioCollection.findOne(query);
			if(vedioBean == null) {
				throw new ErrorBranchException(ErrorMessage.NO_THIS_ID);
			}
			//increase click count
			DBObject update = new BasicDBObject();
			DBObject increase = new BasicDBObject();
			increase.put(MongoConst.KEY_VEDIO_CLICK_COUNT, Integer.valueOf(1));
			update.put(MongoConst.MONGO_MODIFYER_INCREASE, increase);
			vedioCollection.update(query, update);
			DBObject error = db.getLastError();
			Object err = error.get(MongoConst.FUNCTION_GETLASTERROR_RETURN_KEY_ERR);
			if(err != null) {
				//may log
			}
			
			Element root = doc.getDocumentElement();
			Element body = XmlMaker.createElementChild(doc, root, XmlConst.DOCUMENT_BODY);
			Element vedio = XmlMaker.createElementChild(doc, body, XmlConst.BODY_VEDIO);
			Element uploader = XmlMaker.createElementChild(doc, vedio, XmlConst.VEDIO_UPLOADER);
			Element caption = XmlMaker.createElementChild(doc, vedio, XmlConst.VEDIO_CAPTION);
			Element catalog = XmlMaker.createElementChild(doc, vedio, XmlConst.VEDIO_CATALOG);
			Element description = XmlMaker.createElementChild(doc, vedio, XmlConst.VEDIO_DESCRIPTION);
			Element origFileAddress = XmlMaker.createElementChild(doc, vedio, XmlConst.VEDIO_ORIG_FILE_ADDRESS);
			Element newFileAddress = XmlMaker.createElementChild(doc, vedio, XmlConst.VEDIO_NEW_FILE_ADDRESS);
			Element tags = XmlMaker.createElementChild(doc, vedio, XmlConst.VEDIO_TAGS);
			Element uploadTime = XmlMaker.createElementChild(doc, vedio, XmlConst.VEDIO_UPLOAD_TIME);
			Element clickCount = XmlMaker.createElementChild(doc, vedio, XmlConst.VEDIO_CLICK_COUNT);
			Element score = XmlMaker.createElementChild(doc, vedio, XmlConst.VEDIO_SCORE);
			Element scoreCount = XmlMaker.createElementChild(doc, vedio, XmlConst.VEDIO_SCORE_COUNT);
			
			uploader.setTextContent(toString(vedioBean.get(MongoConst.KEY_VEDIO_UPLOADER)));
			caption.setTextContent(toString(vedioBean.get(MongoConst.KEY_VEDIO_CAPTION)));
			
			//catalog array to catalog string
			BasicBSONList catalogList = (BasicBSONList)vedioBean.get(MongoConst.KEY_VEDIO_CATALOG);
			String catalogString = "";
			if(catalogList != null) {
				for(Object t : catalogList) {
					catalogString += (toString(t) + " ");
				}
			}
			catalog.setTextContent(catalogString.trim());
			
			description.setTextContent(toString(vedioBean.get(MongoConst.KEY_VEDIO_DESCRIPTION)));
			origFileAddress.setTextContent(toString(vedioBean.get(MongoConst.KEY_VEDIO_ORIG_FILE_ADDRESS)));
			newFileAddress.setTextContent(toString(vedioBean.get(MongoConst.KEY_VEDIO_NEW_FILE_ADDRESS)));
			//tags to tag
			BasicBSONList tagList = (BasicBSONList)vedioBean.get(MongoConst.KEY_VEDIO_TAGS);
			if(tagList != null) {
				for(Object t : tagList) {
					Element tag = XmlMaker.createElementChild(doc, tags, XmlConst.VEDIO_TAG);
					tag.setTextContent(toString(t));
				}
			}
			
			uploadTime.setTextContent(toString(vedioBean.get(MongoConst.KEY_VEDIO_UPLOAD_TIME)));
			clickCount.setTextContent(toString(vedioBean.get(MongoConst.KEY_VEDIO_CLICK_COUNT)));
			//score
			DBObject scoreBean = (DBObject)vedioBean.get(MongoConst.KEY_VEDIO_SCORE);
			Object scoreTotal = scoreBean.get(MongoConst.KEY_VEDIO_SCORE_TOTAL);
			Object scoreCountBean = scoreBean.get(MongoConst.KEY_VEDIO_SCORE_COUNT);
			
			score.setTextContent(divide(scoreTotal, scoreCountBean));
			scoreCount.setTextContent(toString(scoreCountBean));
		} catch (MongoException e) {
			//log mail
			XmlMaker.setDocumentHeader(doc, e.getMessage(), false);
		} catch (IllegalArgumentException e) {
			//log mail
			XmlMaker.setDocumentHeader(doc, e.getMessage(), false);
		} catch (ErrorBranchException e) {
			XmlMaker.setDocumentHeader(doc, e.getMessage(), false);
		} finally {
			MongoFactory.recoverMongoConnection(mongo);
		}
		return doc;
	}
	
	@POST
	@Path("/score/give")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_XML)
	public Document postVedioByScore(
			@FormParam("id") String id,
			@FormParam("user") String user,
			@FormParam("score") String scoreString) throws SeeGodException {
		Document doc = XmlMaker.createDocumentWithHeader();
		Mongo mongo = null;
		try {
			mongo = MongoFactory.getMongoConnetion();
			DB db = mongo.getDB(MongoConst.DATABASE_NAME);
			DBCollection vedioCollection = db.getCollection(
							MongoConst.COLLECTION_VEDIO);
			
			DBObject queryBean = new BasicDBObject();
			queryBean.put(MongoConst.DATABASE_KEYWORD_ID, new ObjectId(id));
			DBObject updateBean = new BasicDBObject();
			DBObject increaseBean = new BasicDBObject();
			String scoreTotalKey = MongoConst.KEY_VEDIO_SCORE + "." + MongoConst.KEY_VEDIO_SCORE_TOTAL;
			String scoreCountKey = MongoConst.KEY_VEDIO_SCORE + "." + MongoConst.KEY_VEDIO_SCORE_COUNT;
			increaseBean.put(scoreTotalKey, Double.valueOf(scoreString));
			increaseBean.put(scoreCountKey, Integer.valueOf(1));
			updateBean.put(MongoConst.MONGO_MODIFYER_INCREASE, increaseBean);
			
			vedioCollection.update(queryBean, updateBean);
			DBObject error = db.getLastError();
			Object err = error.get(MongoConst.FUNCTION_GETLASTERROR_RETURN_KEY_ERR);
			if(err != null) {
				//log mail
				XmlMaker.setDocumentHeader(doc, err.toString(), false);
			}
			
			//get score
			DBObject returnBean = new BasicDBObject();
			returnBean.put(MongoConst.KEY_VEDIO_SCORE, 1);
			DBObject bean = vedioCollection.findOne(queryBean, returnBean);
			if(bean != null) {
				Element root = doc.getDocumentElement();
				Element body = XmlMaker.createElementChild(doc, root, XmlConst.DOCUMENT_BODY);
				Element score = XmlMaker.createElementChild(doc, body, XmlConst.VEDIO_SCORE);
				Element scoreCount = XmlMaker.createElementChild(doc, body, XmlConst.VEDIO_SCORE_COUNT);
				
				DBObject scoreBean = (DBObject)bean.get(MongoConst.KEY_VEDIO_SCORE);
				Object scoreTotal = scoreBean.get(MongoConst.KEY_VEDIO_SCORE_TOTAL);
				Object scoreCountBean = scoreBean.get(MongoConst.KEY_VEDIO_SCORE_COUNT);
				
				score.setTextContent(divide(scoreTotal, scoreCountBean));
				scoreCount.setTextContent(toString(scoreCountBean));
			}
		} catch (MongoException e) {
			//log mail
			XmlMaker.setDocumentHeader(doc, e.getMessage(), false);
		} catch (IllegalArgumentException e) {
			//log mail
			XmlMaker.setDocumentHeader(doc, e.getMessage(), false);
		} finally {
			MongoFactory.recoverMongoConnection(mongo);
		}
		return doc;
	}

	@GET
	@Path("/catalogs")
	@Produces(MediaType.APPLICATION_XML)
	public Document getCatalogs() throws SeeGodException {
		Document doc = XmlMaker.createDocumentWithHeader();
		Mongo mongo = null;
		try {
			mongo = MongoFactory.getMongoConnetion();
			DB db = mongo.getDB(MongoConst.DATABASE_NAME);
			DBCollection catalogCollection = db.getCollection(MongoConst.COLLECTION_CATALOG);
			
			DBObject order = new BasicDBObject();
			order.put(MongoConst.KEY_CATALOG_POSITION, 1);
			DBCursor catalogCursor = catalogCollection.find();
			catalogCursor = catalogCursor.sort(order);
			
			DBObject error = db.getLastError();
			Object err = error.get(MongoConst.FUNCTION_GETLASTERROR_RETURN_KEY_ERR);
			if(err != null) {
				throw new ErrorBranchException(err.toString());
			}
			
			int count = catalogCursor.count();
			
			Element root = doc.getDocumentElement();
			Element body = XmlMaker.createElementChild(doc, root, XmlConst.DOCUMENT_BODY);
			Element catalogList = XmlMaker.createElementChild(doc, body, XmlConst.BODY_LIST);
			catalogList.setAttribute(XmlConst.LIST_SIZE, String.valueOf(count));
			catalogList.setAttribute(XmlConst.LIST_TYPE, XmlConst.BODY_CATALOG);
			
			while(catalogCursor.hasNext()) {
				DBObject curCatalog = catalogCursor.next();
				Element catalog = XmlMaker.createElementChild(doc, catalogList, XmlConst.BODY_CATALOG);
				Element id = XmlMaker.createElementChild(doc, catalog, XmlConst.CATALOG_ID);
				Element name = XmlMaker.createElementChild(doc, catalog, XmlConst.CATALOG_NAME);
				Element children = XmlMaker.createElementChild(doc, catalog, XmlConst.CATALOG_CHILDREN);
				id.setTextContent(toString(curCatalog.get(MongoConst.DATABASE_KEYWORD_ID)));
				name.setTextContent(toString(curCatalog.get(MongoConst.KEY_CATALOG_NAME)));
				//children to child
				BasicBSONList childList = (BasicBSONList)curCatalog.get(MongoConst.KEY_CATALOG_CHILDREN);
				if(childList != null) {
					for(Object c : childList) {
						Element child = XmlMaker.createElementChild(doc, children, XmlConst.CATALOG_CHILD);
						child.setTextContent(toString(c));
					}
				}
			}
		} catch (MongoException e) {
			//log mail
			XmlMaker.setDocumentHeader(doc, e.getMessage(), false);
		} catch (IllegalArgumentException e) {
			//log mail
			XmlMaker.setDocumentHeader(doc, e.getMessage(), false);
		} catch (ErrorBranchException e) {
			XmlMaker.setDocumentHeader(doc, e.getMessage(), false);
		} finally {
			MongoFactory.recoverMongoConnection(mongo);
		}
		return doc;
	}
	
	@GET
	@Path("/catalog/{catalog}")
	@Produces(MediaType.APPLICATION_XML)
	public Document getCatalog(
			@PathParam("catalog") String catalogString,
			@QueryParam("start") String start,
			@QueryParam("limit") String limit) throws SeeGodException {
		Document doc = XmlMaker.createDocumentWithHeader();
		Mongo mongo = null;
		try {
			mongo = MongoFactory.getMongoConnetion();
			DB db = mongo.getDB(MongoConst.DATABASE_NAME);
			DBCollection vedioCollection = db.getCollection(MongoConst.COLLECTION_VEDIO);
			
			DBObject query = new BasicDBObject();
			String[] catalogName = catalogString.split("\\s");
			query.put(MongoConst.KEY_VEDIO_CATALOG, catalogName[catalogName.length - 1]);
			query.put(MongoConst.KEY_VEDIO_IS_ACTIVE, true);
			
			if(start != null) {
				DBObject from = new BasicDBObject();
				from.put(MongoConst.MONGO_QUERY_LESS_THAN, new ObjectId(start));
				query.put(MongoConst.DATABASE_KEYWORD_ID, from);
			}
			
			
			DBCursor vedioCursor = vedioCollection.find(query);			
			DBObject order = new BasicDBObject();
			order.put(MongoConst.DATABASE_KEYWORD_ID, -1);
			vedioCursor = vedioCursor.sort(order);
			int vedioCount = ServiceConst.LISTING_LIMIT;
			if(limit != null) {
				vedioCount = Integer.parseInt(limit);
			}
			vedioCursor.limit(vedioCount);

			int count = vedioCursor.count();
			Element root = doc.getDocumentElement();
			Element body = XmlMaker.createElementChild(doc, root, XmlConst.DOCUMENT_BODY);
			Element vedioList = XmlMaker.createElementChild(doc, body, XmlConst.BODY_LIST);
			vedioList.setAttribute(XmlConst.LIST_SIZE, String.valueOf(count));
			vedioList.setAttribute(XmlConst.LIST_TYPE, XmlConst.BODY_VEDIO);
			
			while (vedioCursor.hasNext()) {
				DBObject vedioBean = vedioCursor.next();
				Element vedio = XmlMaker.createElementChild(doc, vedioList, XmlConst.BODY_VEDIO);
				Element id = XmlMaker.createElementChild(doc, vedio, XmlConst.VEDIO_ID);
				Element uploader = XmlMaker.createElementChild(doc, vedio, XmlConst.VEDIO_UPLOADER);
				Element caption = XmlMaker.createElementChild(doc, vedio, XmlConst.VEDIO_CAPTION);
				Element catalog = XmlMaker.createElementChild(doc, vedio, XmlConst.VEDIO_CATALOG);
				Element description = XmlMaker.createElementChild(doc, vedio, XmlConst.VEDIO_DESCRIPTION);
				Element tags = XmlMaker.createElementChild(doc, vedio, XmlConst.VEDIO_TAGS);
				Element uploadTime = XmlMaker.createElementChild(doc, vedio, XmlConst.VEDIO_UPLOAD_TIME);

				id.setTextContent(toString(vedioBean.get(MongoConst.DATABASE_KEYWORD_ID)));
				uploader.setTextContent(toString(vedioBean.get(MongoConst.KEY_VEDIO_UPLOADER)));
				caption.setTextContent(toString(vedioBean.get(MongoConst.KEY_VEDIO_CAPTION)));
				description.setTextContent(toString(vedioBean.get(MongoConst.KEY_VEDIO_DESCRIPTION)));
				
				//catalog array to catalog string
				BasicBSONList catalogList = (BasicBSONList)vedioBean.get(MongoConst.KEY_VEDIO_CATALOG);
				catalogString = "";
				if(catalogList != null) {
					for(Object t : catalogList) {
						catalogString += (toString(t) + " ");
					}
				}
				catalog.setTextContent(catalogString.trim());
				
				// tags to tag
				BasicBSONList tagList = (BasicBSONList) vedioBean.get(MongoConst.KEY_VEDIO_TAGS);
				if (tagList != null) {
					for (Object t : tagList) {
						Element tag = XmlMaker.createElementChild(doc, tags, XmlConst.VEDIO_TAG);
						tag.setTextContent(toString(t));
					}
				}
				
				uploadTime.setTextContent(toString(vedioBean.get(MongoConst.KEY_VEDIO_UPLOAD_TIME)));
			}
		} catch (MongoException e) {
			//log mail
			XmlMaker.setDocumentHeader(doc, e.getMessage(), false);
		} catch (IllegalArgumentException e) {
			//log mail
			XmlMaker.setDocumentHeader(doc, e.getMessage(), false);
		} finally {
			MongoFactory.recoverMongoConnection(mongo);
		}
		return doc;
	}
	
	@POST
	@Path("/comment")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_XML)
	public Document postComment(
			@FormParam("id") String id,
			@FormParam("user") String user,
			@FormParam("user_name") String userName,
			@FormParam("content") String content,
			@FormParam("time") String time) throws SeeGodException {
		Document doc = XmlMaker.createDocumentWithHeader();
		Mongo mongo = null;
		try {
			mongo = MongoFactory.getMongoConnetion();
			DB db = mongo.getDB(MongoConst.DATABASE_NAME);
			DBCollection commentCollection = db.getCollection(MongoConst.COLLECTION_COMMENT);
			DBObject commentBean = new BasicDBObject();
			commentBean.put(MongoConst.KEY_COMMENT_VEDIO_ID, id);
			commentBean.put(MongoConst.KEY_COMMENT_USER, user);			
			commentBean.put(MongoConst.KEY_COMMENT_USER_NAME, userName);
			commentBean.put(MongoConst.KEY_COMMENT_CONTENT, content);
			commentBean.put(MongoConst.KEY_COMMENT_TIME, time);
			
			commentCollection.insert(commentBean);
			DBObject error = db.getLastError();
			Object err = error.get(MongoConst.FUNCTION_GETLASTERROR_RETURN_KEY_ERR);
			if(err != null) {
				//log mail
				XmlMaker.setDocumentHeader(doc, err.toString(), false);
			}
		} catch (MongoException e) {
			//log mail
			XmlMaker.setDocumentHeader(doc, e.getMessage(), false);
		} finally {
			MongoFactory.recoverMongoConnection(mongo);
		}
		return doc;
	}
	
	@GET
	@Path("/comments/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public Document getComments(
			@PathParam("id") String id,
			@QueryParam("limit") String limit,
			@QueryParam("start") String start) throws SeeGodException {
		Document doc = XmlMaker.createDocumentWithHeader();
		Mongo mongo = null;
		try {
			mongo = MongoFactory.getMongoConnetion();
			DB db = mongo.getDB(MongoConst.DATABASE_NAME);
			DBCollection commentCollection = db.getCollection(MongoConst.COLLECTION_COMMENT);
			
			DBObject query = new BasicDBObject();
			query.put(MongoConst.KEY_COMMENT_VEDIO_ID, new ObjectId(id));
			
			if(start != null) {
				DBObject from = new BasicDBObject();
				from.put(MongoConst.MONGO_QUERY_LESS_THAN, new ObjectId(start));
				query.put(MongoConst.DATABASE_KEYWORD_ID, from);
			}
			
			
			DBCursor commentCursor = commentCollection.find(query);			
			DBObject order = new BasicDBObject();
			order.put(MongoConst.DATABASE_KEYWORD_ID, -1);
			commentCursor = commentCursor.sort(order);
			int commentCount = ServiceConst.LISTING_LIMIT;
			if(limit != null) {
				commentCount = Integer.parseInt(limit);
			}
			commentCursor.limit(commentCount);
			
			int count = commentCursor.count();
			Element root = doc.getDocumentElement();
			Element body = XmlMaker.createElementChild(doc, root, XmlConst.DOCUMENT_BODY);
			Element commentList = XmlMaker.createElementChild(doc, body, XmlConst.BODY_LIST);
			commentList.setAttribute(XmlConst.LIST_SIZE, String.valueOf(count));
			commentList.setAttribute(XmlConst.LIST_TYPE, XmlConst.BODY_COMMENT);
			
			while (commentCursor.hasNext()) {
				DBObject commentBean = commentCursor.next();
				Element comment = XmlMaker.createElementChild(doc, commentList, XmlConst.BODY_COMMENT);
				Element commentId = XmlMaker.createElementChild(doc, comment, XmlConst.COMMENT_ID);
				Element vedioId = XmlMaker.createElementChild(doc, comment, XmlConst.COMMENT_VEDIO_ID);
				Element user = XmlMaker.createElementChild(doc, comment, XmlConst.COMMENT_USER);
				Element userName = XmlMaker.createElementChild(doc, comment, XmlConst.COMMENT_UESR_NAME);
				Element content = XmlMaker.createElementChild(doc, comment, XmlConst.COMMENT_CONTENT);
				Element time = XmlMaker.createElementChild(doc, comment, XmlConst.COMMENT_TIME);
				
				commentId.setTextContent(toString(commentBean.get(MongoConst.DATABASE_KEYWORD_ID)));
				vedioId.setTextContent(toString(commentBean.get(MongoConst.KEY_COMMENT_VEDIO_ID)));
				user.setTextContent(toString(commentBean.get(MongoConst.KEY_COMMENT_USER)));
				userName.setTextContent(toString(commentBean.get(MongoConst.KEY_COMMENT_USER_NAME)));
				content.setTextContent(toString(commentBean.get(MongoConst.KEY_COMMENT_CONTENT)));
				time.setTextContent(toString(commentBean.get(MongoConst.KEY_COMMENT_TIME)));
			}
		} catch (MongoException e) {
			//log mail
			XmlMaker.setDocumentHeader(doc, e.getMessage(), false);
		} catch (IllegalArgumentException e) {
			//log mail
			XmlMaker.setDocumentHeader(doc, e.getMessage(), false);
		} finally {
			MongoFactory.recoverMongoConnection(mongo);
		}
		return doc;
	}
	
	@GET
	@Path("/search/tags")
	@Produces(MediaType.APPLICATION_XML)
	public Document getSearchByTags(
			@QueryParam("find") String find,
			@QueryParam("limit") String limit,
			@QueryParam("start") String start) throws SeeGodException {
		Document doc = XmlMaker.createDocumentWithHeader();
		Mongo mongo = null;
		try {
			mongo = MongoFactory.getMongoConnetion();
			DB db = mongo.getDB(MongoConst.DATABASE_NAME);
			DBCollection vedioCollection = db.getCollection(MongoConst.COLLECTION_VEDIO);
			
			DBObject queryBean = new BasicDBObject();
			DBObject inBean = new BasicDBObject();
			String[] tagArray = find.trim().split("\\s");
			inBean.put(MongoConst.MONGO_QUERY_IN, tagArray);
			queryBean.put(MongoConst.KEY_VEDIO_TAGS, inBean);
			queryBean.put(MongoConst.KEY_VEDIO_IS_ACTIVE, Boolean.TRUE);
			
			if(start != null) {
				DBObject from = new BasicDBObject();
				from.put(MongoConst.MONGO_QUERY_LESS_THAN, new ObjectId(start));
				queryBean.put(MongoConst.DATABASE_KEYWORD_ID, from);
			}
			
			
			DBCursor videoCursor = vedioCollection.find(queryBean);			
			DBObject orderBean = new BasicDBObject();
			orderBean.put(MongoConst.DATABASE_KEYWORD_ID, -1);
			videoCursor = videoCursor.sort(orderBean);
			int vedioCount = ServiceConst.LISTING_LIMIT;
			if(limit != null) {
				vedioCount = Integer.parseInt(limit);
			}
			videoCursor.limit(vedioCount);
			
			int count = videoCursor.count();
			Element root = doc.getDocumentElement();
			Element body = XmlMaker.createElementChild(doc, root, XmlConst.DOCUMENT_BODY);
			Element vedioList = XmlMaker.createElementChild(doc, body, XmlConst.BODY_LIST);
			vedioList.setAttribute(XmlConst.LIST_SIZE, String.valueOf(count));
			vedioList.setAttribute(XmlConst.LIST_TYPE, XmlConst.BODY_VEDIO);
			
			while (videoCursor.hasNext()) {
				DBObject vedioBean = videoCursor.next();
				Element vedio = XmlMaker.createElementChild(doc, vedioList, XmlConst.BODY_VEDIO);
				Element id = XmlMaker.createElementChild(doc, vedio, XmlConst.VEDIO_ID);
				Element uploader = XmlMaker.createElementChild(doc, vedio, XmlConst.VEDIO_UPLOADER);
				Element caption = XmlMaker.createElementChild(doc, vedio, XmlConst.VEDIO_CAPTION);
				Element catalog = XmlMaker.createElementChild(doc, vedio, XmlConst.VEDIO_CATALOG);
				Element description = XmlMaker.createElementChild(doc, vedio, XmlConst.VEDIO_DESCRIPTION);
				Element tags = XmlMaker.createElementChild(doc, vedio, XmlConst.VEDIO_TAGS);
				Element clickCount = XmlMaker.createElementChild(doc, vedio, XmlConst.VEDIO_CLICK_COUNT);
				Element uploadTime = XmlMaker.createElementChild(doc, vedio, XmlConst.VEDIO_UPLOAD_TIME);

				id.setTextContent(toString(vedioBean.get(MongoConst.DATABASE_KEYWORD_ID)));
				uploader.setTextContent(toString(vedioBean.get(MongoConst.KEY_VEDIO_UPLOADER)));
				caption.setTextContent(toString(vedioBean.get(MongoConst.KEY_VEDIO_CAPTION)));
				description.setTextContent(toString(vedioBean.get(MongoConst.KEY_VEDIO_DESCRIPTION)));
				clickCount.setTextContent(toString(vedioBean.get(MongoConst.KEY_VEDIO_CLICK_COUNT)));
				
				//catalog array to catalog string
				BasicBSONList catalogList = (BasicBSONList)vedioBean.get(MongoConst.KEY_VEDIO_CATALOG);
				String catalogString = "";
				if(catalogList != null) {
					for(Object t : catalogList) {
						catalogString += (toString(t) + " ");
					}
				}
				catalog.setTextContent(catalogString.trim());
				
				// tags to tag
				BasicBSONList tagList = (BasicBSONList) vedioBean.get(MongoConst.KEY_VEDIO_TAGS);
				if (tagList != null) {
					for (Object t : tagList) {
						Element tag = XmlMaker.createElementChild(doc, tags, XmlConst.VEDIO_TAG);
						tag.setTextContent(toString(t));
					}
				}
				uploadTime.setTextContent(toString(vedioBean.get(MongoConst.KEY_VEDIO_UPLOAD_TIME)));
			}
		} catch (MongoException e) {
			//log mail
			XmlMaker.setDocumentHeader(doc, e.getMessage(), false);
		} catch (IllegalArgumentException e) {
			//log mail
			XmlMaker.setDocumentHeader(doc, e.getMessage(), false);
		} finally {
			MongoFactory.recoverMongoConnection(mongo);
		}
		return doc;
	}
	
	private String divide(Object total, Object count) {
		double score = 0.0;
		double scoreTotal = (total != null) ? Double.parseDouble(total.toString()) : 0;
		double scoreCount = (count != null) ? Double.parseDouble(count.toString()) : 0;
		if(scoreCount != 0) {
			score = scoreTotal / scoreCount;
		}
		return String.format("%.1f", score);
	}
	
	private String toString(Object o) {
		if(o == null) {
			return null;
		} else {
			return o.toString();
		}
	}
}