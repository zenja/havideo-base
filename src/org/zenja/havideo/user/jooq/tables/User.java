/**
 * This class is generated by jOOQ
 */
package org.zenja.havideo.user.jooq.tables;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = {"http://www.jooq.org", "2.1.0"},
                            comments = "This class is generated by jOOQ")
public class User extends org.jooq.impl.UpdatableTableImpl<org.zenja.havideo.user.jooq.tables.records.UserRecord> {

	private static final long serialVersionUID = -910945422;

	/**
	 * The singleton instance of havideo.user
	 */
	public static final org.zenja.havideo.user.jooq.tables.User USER = new org.zenja.havideo.user.jooq.tables.User();

	/**
	 * The class holding records for this type
	 */
	private static final java.lang.Class<org.zenja.havideo.user.jooq.tables.records.UserRecord> __RECORD_TYPE = org.zenja.havideo.user.jooq.tables.records.UserRecord.class;

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<org.zenja.havideo.user.jooq.tables.records.UserRecord> getRecordType() {
		return __RECORD_TYPE;
	}

	/**
	 * An uncommented item
	 * 
	 * PRIMARY KEY
	 */
	public final org.jooq.TableField<org.zenja.havideo.user.jooq.tables.records.UserRecord, java.lang.Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<org.zenja.havideo.user.jooq.tables.records.UserRecord, java.lang.String> USERNAME = createField("username", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<org.zenja.havideo.user.jooq.tables.records.UserRecord, java.lang.String> PASSWORD = createField("password", org.jooq.impl.SQLDataType.VARCHAR, this);

	/**
	 * No further instances allowed
	 */
	private User() {
		super("user", org.zenja.havideo.user.jooq.Havideo.HAVIDEO);
	}

	/**
	 * No further instances allowed
	 */
	private User(java.lang.String alias) {
		super(alias, org.zenja.havideo.user.jooq.Havideo.HAVIDEO, org.zenja.havideo.user.jooq.tables.User.USER);
	}

	@Override
	public org.jooq.UniqueKey<org.zenja.havideo.user.jooq.tables.records.UserRecord> getMainKey() {
		return org.zenja.havideo.user.jooq.Keys.KEY_USER_PRIMARY;
	}

	@Override
	@SuppressWarnings("unchecked")
	public java.util.List<org.jooq.UniqueKey<org.zenja.havideo.user.jooq.tables.records.UserRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<org.zenja.havideo.user.jooq.tables.records.UserRecord>>asList(org.zenja.havideo.user.jooq.Keys.KEY_USER_PRIMARY);
	}

	@Override
	public org.zenja.havideo.user.jooq.tables.User as(java.lang.String alias) {
		return new org.zenja.havideo.user.jooq.tables.User(alias);
	}
}