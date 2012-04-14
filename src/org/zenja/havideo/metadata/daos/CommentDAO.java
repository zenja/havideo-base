package org.zenja.havideo.metadata.daos;

import java.util.List;

import org.bson.types.ObjectId;
import org.zenja.havideo.metadata.beans.Comment;
import org.zenja.havideo.metadata.mongodb.MongoDBConfiguration;

import com.google.code.morphia.Morphia;
import com.google.code.morphia.dao.BasicDAO;
import com.mongodb.Mongo;

public class CommentDAO extends BasicDAO<Comment,ObjectId> {

	public CommentDAO(Mongo mongo, Morphia morphia) {
		super(mongo, morphia, MongoDBConfiguration.DB_NAME);
	}
	
	public List<Comment> findAll() {
		return ds.find(Comment.class).asList();
	}
}
