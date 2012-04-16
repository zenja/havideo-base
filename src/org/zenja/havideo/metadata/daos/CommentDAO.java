package org.zenja.havideo.metadata.daos;

import java.util.List;

import org.bson.types.ObjectId;
import org.zenja.havideo.metadata.beans.Comment;
import org.zenja.havideo.metadata.mongodb.MongoDBConfiguration;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.query.Query;
import com.mongodb.Mongo;

public class CommentDAO extends BasicDAO<Comment, ObjectId> {

	public static final String VIDEOID_FILED_NAME = "videoId";

	public CommentDAO(Mongo mongo, Morphia morphia) {
		super(mongo, morphia, MongoDBConfiguration.DB_NAME);
	}

	public List<Comment> findAll() {
		return ds.find(Comment.class).asList();
	}
	
	public Comment findById(String id) {
		return get(new ObjectId(id));
	}
	
	public Comment findById(ObjectId id) {
		return get(id);
	}
	
	public List<Comment> findByVideoId(String videoId) {
		Datastore ds = getDatastore();
		Query<Comment> query = ds.createQuery(Comment.class)
				.field(VIDEOID_FILED_NAME).equal(new ObjectId(videoId));
		return find(query).asList();
	}
}
