package org.zenja.havideo.metadata.daos;

import java.util.List;

import org.bson.types.ObjectId;
import org.zenja.havideo.metadata.beans.Video;
import org.zenja.havideo.metadata.mongodb.MongoDBConfiguration;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.query.Query;
import com.mongodb.Mongo;

public class VideoDAO extends BasicDAO<Video,ObjectId> {

	private static final String TAGS_FILED_NAME = "tags";
	private static final String CATALOG_FILED_NAME = "catalog";

	public VideoDAO(Mongo mongo, Morphia morphia) {
		super(mongo, morphia, MongoDBConfiguration.DB_NAME);
	}
	
	public List<Video> findAll() {
		return ds.find(Video.class).asList();
	}

	public Video findById(String id) {
		return get(new ObjectId(id));
	}
	
	public List<Video> findByTag(String tag) {
		Datastore ds = getDatastore();
		Query<Video> query = ds.createQuery(Video.class)
				.field(TAGS_FILED_NAME).equal(tag);
		return find(query).asList();
	}

	public List<Video> findByCatalog(String catalog) {
		Datastore ds = getDatastore();
		Query<Video> query = ds.createQuery(Video.class)
				.field(CATALOG_FILED_NAME).equal(catalog);
		return find(query).asList();
	}
}
