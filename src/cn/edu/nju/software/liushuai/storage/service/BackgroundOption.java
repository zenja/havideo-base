package cn.edu.nju.software.liushuai.storage.service;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import org.bson.types.ObjectId;

import cn.edu.nju.software.liushuai.storage.exception.SeeGodException;
import cn.edu.nju.software.liushuai.storage.mongodb.MongoFactory;
import cn.edu.nju.software.liushuai.storage.mongodb.MongoConst;
import cn.edu.nju.software.liushuai.storage.util.ErrorMessage;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

@Path("/background")
public class BackgroundOption {
	/*
	 * options acted by site's web arch
	 * contains activate(activate a video),inactivate
	 * all encode must be utf-8
	 * method name : httpMethodName + pathName
	 */
	@GET
	@Path("/activate/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getActivate(@PathParam("id") String id) throws SeeGodException {
		return changeActiving(id, true);
	}
	
	@GET
	@Path("/inactivate/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getInactivate(@PathParam("id") String id) throws SeeGodException {
		return changeActiving(id, false);
	}
	
	private String changeActiving(String id, boolean makeActive) throws SeeGodException {
		String ret = ServiceConst.RETURN_TEXT_PLAIN_FALSE;
		Mongo mongo = null;
		try {
			mongo = MongoFactory.getMongoConnetion();
			DB db = mongo.getDB(MongoConst.DATABASE_NAME);
			DBCollection videoCollection = db.getCollection(
							MongoConst.COLLECTION_VIDEO);
			DBObject orig = new BasicDBObject();
			orig.put(MongoConst.DATABASE_KEYWORD_ID, new ObjectId(id));
			DBObject change = new BasicDBObject();
			change.put(MongoConst.KEY_VIDEO_IS_ACTIVE, new Boolean(makeActive));
			DBObject update = new BasicDBObject();
			update.put(MongoConst.MONGO_MODIFYER_SET, change);
			videoCollection.update(orig, update);
			DBObject error = db.getLastError();
			if((Boolean)error.get(MongoConst.FUNCTION_GETLASTERROR_RETURN_KEY_UPDATED_EXISTING)) {
				ret = ServiceConst.RETURN_TEXT_PLAIN_TRUE;
			} else {
				ret += ("\n" + ErrorMessage.NO_THIS_ID);
			}
		} catch (MongoException e) {
			//log mail
			ret += ("\n" + e.getMessage());
		} catch (IllegalArgumentException e) {
			//log mail
			ret += ("\n" + e.getMessage());
		}finally{
			MongoFactory.recoverMongoConnection(mongo);
		}
		return ret;
	}
}