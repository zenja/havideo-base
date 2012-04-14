package org.zenja.havideo.metadata.morphia;

import org.zenja.havideo.metadata.beans.Comment;
import org.zenja.havideo.metadata.beans.Video;

import com.google.code.morphia.Morphia;

/**
 * Singleton Factory for Morphia object
 * @author wangxing
 *
 */
public class MophiaFactory {
	private static Morphia morphia = null;
	
	private MophiaFactory() {}
	
	public static Morphia getMorphia() {
		if(morphia == null) {
			morphia = new Morphia();
			morphia.map(Video.class).map(Comment.class);
		}
		
		return morphia;
	}
}
