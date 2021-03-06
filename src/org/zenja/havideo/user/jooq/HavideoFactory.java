/**
 * This class is generated by jOOQ
 */
package org.zenja.havideo.user.jooq;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = {"http://www.jooq.org", "2.1.0"},
                            comments = "This class is generated by jOOQ")
public class HavideoFactory extends org.jooq.util.mysql.MySQLFactory {

	private static final long serialVersionUID = 1061069875;

	/**
	 * Create a factory with a connection
	 *
	 * @param connection The connection to use with objects created from this factory
	 */
	public HavideoFactory(java.sql.Connection connection) {
		super(connection);
	}

	/**
	 * Create a factory with a connection and a schema mapping
	 * 
	 * @deprecated - 2.0.5 - Use {@link #HavideoFactory(java.sql.Connection, org.jooq.conf.Settings)} instead
	 */
	@Deprecated
	public HavideoFactory(java.sql.Connection connection, org.jooq.SchemaMapping mapping) {
		super(connection, mapping);
	}

	/**
	 * Create a factory with a connection and some settings
	 *
	 * @param connection The connection to use with objects created from this factory
	 * @param settings The settings to apply to objects created from this factory
	 */
	public HavideoFactory(java.sql.Connection connection, org.jooq.conf.Settings settings) {
		super(connection, settings);
	}
}
