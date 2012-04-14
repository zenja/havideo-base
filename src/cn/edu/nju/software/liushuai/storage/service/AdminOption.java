package cn.edu.nju.software.liushuai.storage.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.bson.types.BasicBSONList;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

import cn.edu.nju.software.liushuai.storage.bean.CatalogBean;
import cn.edu.nju.software.liushuai.storage.exception.ErrorBranchException;
import cn.edu.nju.software.liushuai.storage.exception.SeeGodException;
import cn.edu.nju.software.liushuai.storage.mongodb.MongoConst;
import cn.edu.nju.software.liushuai.storage.mongodb.MongoFactory;
import cn.edu.nju.software.liushuai.storage.util.ErrorMessage;
import cn.edu.nju.software.liushuai.storage.util.HtmlTemplater;

@Path("/admin")
public class AdminOption {
	/*
	 * options acted by site admin contains
	 * catalog/modify,catalog/create,catalog/delete
	 * all encode must be utf-8 method
	 * name : httpMethodName + pathName
	 */

	@POST
	@Path("/catalog/modify/{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public String postCatalogByModify(
			@PathParam("id") String id,
			@FormParam("name") String name,
			@FormParam("children") String children,
			@FormParam("position") String position) throws SeeGodException {

		String ret = HtmlTemplater.redirectHtml(
				ServiceConst.SERVICE_ROOT + "/admin/catalogs.jsp");
		Mongo mongo = null;
		try {
			mongo = MongoFactory.getMongoConnetion();
			DB db = mongo.getDB(MongoConst.DATABASE_NAME);
			DBCollection catalogCollection = db
					.getCollection(MongoConst.COLLECTION_CATALOG);
			
			DBObject find = new BasicDBObject();
			find.put(MongoConst.DATABASE_KEYWORD_ID, new ObjectId(id));
			
			DBObject catalogBean = new BasicDBObject();
			catalogBean.put(MongoConst.KEY_CATALOG_NAME, name);
			String[] childrenArray = children.split("\\s");
			catalogBean.put(MongoConst.KEY_CATALOG_CHILDREN, childrenArray);
			catalogBean.put(MongoConst.KEY_CATALOG_POSITION, Integer.parseInt(position));
			
			catalogCollection.update(find, catalogBean);
			DBObject error = db.getLastError();
			if (!(Boolean) error
					.get(MongoConst.FUNCTION_GETLASTERROR_RETURN_KEY_UPDATED_EXISTING)) {
				ret = HtmlTemplater.errorHtml(ErrorMessage.NO_THIS_ID);
				//may log error
			}
		} catch (MongoException e) {
			// log mail
			ret = HtmlTemplater.errorHtml(e.getMessage());
		} catch (NumberFormatException e) {
			//human input error, no need to log
			ret = HtmlTemplater.errorHtml(e.getMessage());
		} catch (IllegalArgumentException e) {
			// log mail
			ret = HtmlTemplater.errorHtml(e.getMessage());
		} finally {
			MongoFactory.recoverMongoConnection(mongo);
		}
		return ret;
	}

	@POST
	@Path("/catalog/create")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public String postCatalogByCreate(
			@FormParam("name") String name,
			@FormParam("children") String children,
			@FormParam("position") String position) throws SeeGodException {

		String ret = HtmlTemplater.redirectHtml(
				ServiceConst.SERVICE_ROOT + "/admin/catalogs.jsp");
		Mongo mongo = null;
		try {
			mongo = MongoFactory.getMongoConnetion();
			DB db = mongo.getDB(MongoConst.DATABASE_NAME);
			DBCollection catalogCollection = db
					.getCollection(MongoConst.COLLECTION_CATALOG);
			
			DBObject catalogBean = new BasicDBObject();
			catalogBean.put(MongoConst.KEY_CATALOG_NAME, name);
			String[] childrenArray = children.split("\\s");
			catalogBean.put(MongoConst.KEY_CATALOG_CHILDREN, childrenArray);
			catalogBean.put(MongoConst.KEY_CATALOG_POSITION, Integer.parseInt(position));
			
			catalogCollection.insert(catalogBean);
			DBObject error = db.getLastError();
			Object err = error.get(MongoConst.FUNCTION_GETLASTERROR_RETURN_KEY_ERR);
			if (err != null) {
				ret = HtmlTemplater.errorHtml(err.toString());
				//may log error
			}
		} catch (MongoException e) {
			// log mail
			ret = HtmlTemplater.errorHtml(e.getMessage());
		} catch (NumberFormatException e) {
			//human input error, no need to log
			ret = HtmlTemplater.errorHtml(e.getMessage());
		} catch (IllegalArgumentException e) {
			// log mail
			ret = HtmlTemplater.errorHtml(e.getMessage());
		} finally {
			MongoFactory.recoverMongoConnection(mongo);
		}
		return ret;
	}
	
	@POST
	@Path("/catalog/delete")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public String postCatalogByDelete(@FormParam("id") String id) throws SeeGodException {
		String ret = HtmlTemplater.redirectHtml(
				ServiceConst.SERVICE_ROOT + "/admin/catalogs.jsp");
		Mongo mongo = null;
		try {
			mongo = MongoFactory.getMongoConnetion();
			DB db = mongo.getDB(MongoConst.DATABASE_NAME);
			DBCollection catalogCollection = db
					.getCollection(MongoConst.COLLECTION_CATALOG);
			
			DBObject catalogBean = new BasicDBObject();
			catalogBean.put(MongoConst.DATABASE_KEYWORD_ID, new ObjectId(id));
			
			catalogCollection.remove(catalogBean);
			DBObject error = db.getLastError();
			Object err = error.get(MongoConst.FUNCTION_GETLASTERROR_RETURN_KEY_ERR);
			if (err != null) {
				ret = HtmlTemplater.errorHtml(err.toString());
				//may log error
			}
		} catch (MongoException e) {
			// log mail
			ret = HtmlTemplater.errorHtml(e.getMessage());
		} catch (NumberFormatException e) {
			//human input error, no need to log
			ret = HtmlTemplater.errorHtml(e.getMessage());
		} catch (IllegalArgumentException e) {
			// log mail
			ret = HtmlTemplater.errorHtml(e.getMessage());
		} finally {
			MongoFactory.recoverMongoConnection(mongo);
		}
		return ret;
	}
	
	//for jsp page
	public List<CatalogBean> getCatalogs() throws SeeGodException, ErrorBranchException {
		List<CatalogBean> list = new ArrayList<CatalogBean>();
		Mongo mongo = null;
		try {
			mongo = MongoFactory.getMongoConnetion();
			DB db = mongo.getDB(MongoConst.DATABASE_NAME);
			DBCollection catalogCollection = db.getCollection(MongoConst.COLLECTION_CATALOG);
			
			DBObject order = new BasicDBObject();
			order.put(MongoConst.KEY_CATALOG_POSITION, 1);
			DBCursor catalogCursor = catalogCollection.find();
			catalogCursor = catalogCursor.sort(order);
			
			DBObject error = db.getLastError();
			Object err = error.get(MongoConst.FUNCTION_GETLASTERROR_RETURN_KEY_ERR);
			if(err != null) {
				throw new ErrorBranchException(error.toString());
			}

			while(catalogCursor.hasNext()) {
				DBObject curCatalog = catalogCursor.next();
				CatalogBean bean = new CatalogBean();
				bean.setId(toString(curCatalog.get(MongoConst.DATABASE_KEYWORD_ID)));
				bean.setName(toString(curCatalog.get(MongoConst.KEY_CATALOG_NAME)));
				bean.setPosition(toString(curCatalog.get(MongoConst.KEY_CATALOG_POSITION)));
				//children to child
				BasicBSONList childList = (BasicBSONList)curCatalog.get(MongoConst.KEY_CATALOG_CHILDREN);
				if(childList != null) {
					StringBuilder sb = new StringBuilder();
					for(Object c : childList) {
						sb.append(String.valueOf(c) + " ");
					}
					bean.setChildren(sb.toString().trim());
				}
				list.add(bean);
			}
		} finally {
			MongoFactory.recoverMongoConnection(mongo);
		}
		return list;
	}
	
	private String toString(Object o) {
		if(o == null) {
			return null;
		} else {
			return o.toString();
		}
	}
}