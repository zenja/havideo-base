package org.zenja.havideo.metadata.daos.factories;

import java.net.UnknownHostException;

import org.zenja.havideo.metadata.daos.CommentDAO;
import org.zenja.havideo.metadata.mongodb.MongoFactory;
import org.zenja.havideo.metadata.morphia.MophiaFactory;

import com.mongodb.MongoException;

public class CommentDAOFactory {

	public static CommentDAO getDAO() {
		try {
			return new CommentDAO(MongoFactory.getMongo(), MophiaFactory.getMorphia());
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
