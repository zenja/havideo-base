package org.zenja.havideo.user.services;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.jooq.Record;
import org.jooq.Result;
import org.zenja.havideo.db.ConnectionFactory;
import org.zenja.havideo.user.jooq.HavideoFactory;
import org.zenja.havideo.user.utils.UsernameValidator;

import static org.zenja.havideo.user.jooq.tables.User.*;

public class UserService {
	private static Logger logger = Logger.getLogger(UserService.class);

	public static boolean addUser(String username, String password) {
		Connection conn = null;

		try {
			conn = ConnectionFactory.makeConnection();
			
			// Check if username is a valid name
			logger.debug("addUser: username not valid");
			if(UsernameValidator.validate(username) == false) {
				return false;
			}

			HavideoFactory create = new HavideoFactory(conn);
			create.insertInto(USER, USER.USERNAME, USER.PASSWORD)
					.values(username, password)
					.execute();

			logger.debug("Add User: success!");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.error("Exception when conn.close()");
				e.printStackTrace();
			}
		}

		logger.debug("Add User: fail!");
		return false;
	}

	public static boolean deleteUser(String username) {
		Connection conn = null;

		try {
			conn = ConnectionFactory.makeConnection();

			HavideoFactory create = new HavideoFactory(conn);
			create.delete(USER)
					.where(USER.USERNAME.equal(username))
					.execute();

			logger.debug("Delete User: success!");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.error("Exception when conn.close()");
				e.printStackTrace();
			}
		}

		logger.debug("Delete User: fail!");
		return false;
	}

	public static boolean checkUser(String username, String password) {
		Connection conn = null;

		try {
			conn = ConnectionFactory.makeConnection();

			HavideoFactory create = new HavideoFactory(conn);
			Result<Record> result = create.select().from(USER)
					.where(USER.USERNAME.equal(username))
					.and(USER.PASSWORD.equal(password)).fetch();

			logger.debug("Check User: true!");
			return result.isEmpty() ? false : true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.error("Exception when conn.close()");
				e.printStackTrace();
			}
		}

		logger.debug("Check User: false!");
		return false;
	}

	public static boolean isUserExists(String username) {
		Connection conn = null;

		try {
			conn = ConnectionFactory.makeConnection();

			HavideoFactory create = new HavideoFactory(conn);
			Result<Record> result = create.select().from(USER)
					.where(USER.USERNAME.equal(username)).fetch();

			logger.debug("isUserExists: true!");
			return result.isEmpty() ? false : true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.error("Exception when conn.close()");
				e.printStackTrace();
			}
		}

		logger.debug("isUserExists: false!");
		return false;
	}
}
