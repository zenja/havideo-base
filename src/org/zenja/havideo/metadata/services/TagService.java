package org.zenja.havideo.metadata.services;

import java.util.List;

import org.zenja.havideo.metadata.beans.Video;
import org.zenja.havideo.metadata.daos.VideoDAO;
import org.zenja.havideo.metadata.daos.factories.VideoDAOFactory;

public class TagService {
	private VideoDAO videoDAO = VideoDAOFactory.getDAO();

	public List<Video> getAllVideosByTag(String tag) {
		return videoDAO.findByTag(tag);
	}

}
