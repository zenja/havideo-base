package org.zenja.havideo.metadata.services;

import java.util.List;

import org.zenja.havideo.metadata.beans.Video;
import org.zenja.havideo.metadata.daos.VideoDAO;
import org.zenja.havideo.metadata.daos.factories.VideoDAOFactory;

public class CatalogService {
	private VideoDAO videoDAO = VideoDAOFactory.getDAO();
	
	public List<Video> getAllVideosByCatalog(String catalog) {
		return videoDAO.findByCatalog(catalog);
	}
}
