package org.zenja.havideo.metadata.mongodb;

import java.net.UnknownHostException;

import com.mongodb.Mongo;
import com.mongodb.MongoException;

/**
 * Singleton Factory for Mongo Object
 * @author wangxing
 *
 */
public class MongoFactory {
	private static Mongo mongo = null;
	
	// Singleton
	private MongoFactory(){}
	
	public static Mongo getMongo() throws UnknownHostException, MongoException {
		if(mongo == null) {
			mongo = new Mongo(MongoDBConfiguration.getHost());
		}
		return mongo;
	}
}
