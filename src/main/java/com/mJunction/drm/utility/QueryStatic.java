package com.mJunction.drm.utility;

public class QueryStatic {

	public static final String CHECKAUTHTOKEN = "SELECT authtoken,active FROM userauth";
	public final static String LOGIN_DETAILS = "SELECT * FROM user";
	public final static String UPDATE_LOGIN_COUNT = "SELECT count(*) FROM userauth";
	public final static String INSERT_USERAUTH = "INSERT INTO m_junction.userauth (userid,authtoken,active,logintime,username) VALUES ";
	public final static String CLIENT_OVERALL_SERVICE = "SELECT p.client_name,p.type,p.activity,p.timestamp,p.Final_status FROM process_state_table p UNION SELECT m.client_name,m.type,m.Activity,m.DateTimeStamp,m.Final_status FROM mj_report m";
	public final static String UPDATE_CURRENT_LOGIN = "UPDATE m_junction.userauth set authtoken=? , active=? , logintime=? where userid=?";
	public final static String SEARCH_POPULATE_CLIENT_LIST = "SELECT distinct (client_name) FROM m_junction.process_state_table";
	public final static String UPDATE_LOGOUT = "UPDATE m_junction.userauth set authtoken=? , active=? , logintime=? where userid=?";


}
