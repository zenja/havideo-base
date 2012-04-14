package org.zenja.havideo.metadata.daos.factories;

import java.net.UnknownHostException;

import org.zenja.havideo.metadata.daos.VideoDAO;
import org.zenja.havideo.metadata.mongodb.MongoFactory;
import org.zenja.havideo.metadata.morphia.MophiaFactory;

import com.mongodb.MongoException;

public class VideoDAOFactory {

	public static VideoDAO getDAO() {
		try {
			return new VideoDAO(MongoFactory.getMongo(), MophiaFactory.getMorphia());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
