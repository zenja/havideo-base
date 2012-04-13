package cn.edu.nju.software.liushuai.storage.mongodb;

import java.net.UnknownHostException;

import cn.edu.nju.software.liushuai.storage.exception.SeeGodException;

import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class MongoFactory {

	public static Mongo getMongoConnetion() throws MongoException, SeeGodException{
		Mongo mongo = null;
		try {
			mongo = new Mongo(MongoConst.HOST);
		} catch (UnknownHostException e) {
			throw new SeeGodException(e.getMessage());
		}
		return mongo;
	}
	
	public static void recoverMongoConnection(Mongo mongo) {
		if(mongo != null) {
			mongo.close();
		}
	}
}
