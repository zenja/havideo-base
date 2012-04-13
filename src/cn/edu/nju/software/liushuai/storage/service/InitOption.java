package cn.edu.nju.software.liushuai.storage.service;

import java.util.List;
import java.util.Set;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import cn.edu.nju.software.liushuai.storage.exception.SeeGodException;
import cn.edu.nju.software.liushuai.storage.mongodb.MongoConst;
import cn.edu.nju.software.liushuai.storage.mongodb.MongoFactory;
import cn.edu.nju.software.liushuai.storage.util.HtmlTemplater;
import cn.edu.nju.software.liushuai.storage.util.HtmlTemplater.Tag;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

@Path("/init")
public class InitOption {
	/*
	 * options acted when first use the system by admin
	 * contains checkAll, initAll
	 * all encode must be utf-8
	 */
	
	public String getStatus() throws SeeGodException {
		StringBuilder sb = new StringBuilder();
		Mongo mongo = null;
		try {
			mongo = MongoFactory.getMongoConnetion();
			DB db = mongo.getDB(MongoConst.DATABASE_NAME);
			HtmlTemplater.appendLine(sb, db.getName(), Tag.h1);
			
			Set<String> collections = db.getCollectionNames();
			HtmlTemplater.appendLine(sb, "Collections:", Tag.h3);
			for(String s :collections) {
				HtmlTemplater.appendLine(sb, s, Tag.p);
			}

			HtmlTemplater.appendLine(sb, "Indexes:", Tag.h3);
			for(String name :collections) {
				DBCollection collection = db.getCollection(name);
				List<DBObject> list = collection.getIndexInfo();
				for(DBObject o : list) {
					HtmlTemplater.appendLine(sb, o, Tag.p);
				}
			}
		} catch (MongoException e) {
			// log mail
			HtmlTemplater.appendLine(sb, e.getMessage(), Tag.p);
		} catch (NumberFormatException e) {
			//human input error, no need to log
			HtmlTemplater.appendLine(sb, e.getMessage(), Tag.p);
		} catch (IllegalArgumentException e) {
			// log mail
			HtmlTemplater.appendLine(sb, e.getMessage(), Tag.p);
		} finally {
			MongoFactory.recoverMongoConnection(mongo);
		}
		return sb.toString();
	}
	
	@POST
	@Path("do")
	@Produces(MediaType.TEXT_HTML)
	public String postDo() throws SeeGodException {
		String ret = HtmlTemplater.redirectHtml(
				ServiceConst.SERVICE_ROOT + "/admin/init.jsp");
		Mongo mongo = null;
		try {
			mongo = MongoFactory.getMongoConnetion();
			DB db = mongo.getDB(MongoConst.DATABASE_NAME);
			db.dropDatabase();
			
			DBObject error = db.getLastError();
			Object err = error.get(MongoConst.FUNCTION_GETLASTERROR_RETURN_KEY_ERR);
			if (err != null) {
				ret = HtmlTemplater.errorHtml(err.toString());
				//may log error
			}
			
			DBObject createBean = new BasicDBObject();
			createBean.put(MongoConst.CREATE_COLLECTION_CAPPED, false);
			db.createCollection(MongoConst.COLLECTION_CATALOG, createBean);
			db.createCollection(MongoConst.COLLECTION_COMMENT, createBean);
			db.createCollection(MongoConst.COLLECTION_VIDEO, createBean);
			
			DBCollection commentCollection = db.getCollection(MongoConst.COLLECTION_COMMENT);
			commentCollection.ensureIndex(MongoConst.KEY_COMMENT_VIDEO_ID);
			
			DBCollection videoCollection = db.getCollection(MongoConst.COLLECTION_VIDEO);
			videoCollection.ensureIndex(MongoConst.KEY_VIDEO_CATALOG);
			videoCollection.ensureIndex(MongoConst.KEY_VIDEO_TAGS);
		} catch (MongoException e) {
			// log mail
			ret = HtmlTemplater.errorHtml(e.getMessage());
		} catch (NumberFormatException e) {
			//human input error, no need to log
			ret = HtmlTemplater.errorHtml(e.getMessage());
		} catch (IllegalArgumentException e) {
			// log mail
			ret = HtmlTemplater.errorHtml(e.getMessage());
		} finally {
			MongoFactory.recoverMongoConnection(mongo);
		}
		return ret;
	}
}
