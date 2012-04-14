package org.zenja.havideo.metadata.daos;

import java.util.List;

import org.bson.types.ObjectId;
import org.zenja.havideo.metadata.beans.Video;
import org.zenja.havideo.metadata.mongodb.MongoDBConfiguration;

import com.google.code.morphia.Morphia;
import com.google.code.morphia.dao.BasicDAO;
import com.mongodb.Mongo;

public class VideoDAO extends BasicDAO<Video,ObjectId> {

	public VideoDAO(Mongo mongo, Morphia morphia) {
		super(mongo, morphia, MongoDBConfiguration.DB_NAME);
	}
	
	public List<Video> findAll() {
		return ds.find(Video.class).asList();
	}

	public Video findById(String id) {
		return get(new ObjectId(id));
	}
}
