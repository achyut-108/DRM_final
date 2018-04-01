package com.mJunction.drm.dashBoard;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mJunction.drm.utility.DBConnection;
import com.mJunction.drm.utility.ReasonLog;

public class FetchErrorStackDao {

	public static List<ReasonLog> errorStack(String catCode)
			throws SQLException {
		List<ReasonLog> listOfResponse = new ArrayList<ReasonLog>();
		
		DBConnection dbConnection = new DBConnection();
		Statement st = null;
		ResultSet rs = null;
		Connection connection = null;
		try {

			connection = (Connection) dbConnection.dbConnection();
			String reason = "";
			String exceptionLog = "";
			ReasonLog rl = new ReasonLog();
			st = connection.createStatement();
			String query = "SELECT m.Stage_state,m.Error_description  FROM m_junction.error_table m where m.Type='"
					+ catCode

					+ "' union SELECT b.Stage_state,b.Error_description  FROM m_junction.business_error_table b where b.Type='"
					+ catCode + "' ";

		
			rs = st.executeQuery(query);
		
			while (rs.next()) {
				rl = new ReasonLog();
				reason = rs.getString(1);
				exceptionLog = rs.getString(2);
				rl.setReason(reason + ",");
				rl.setExceptionLog(exceptionLog + ",");
				listOfResponse.add(rl);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			connection.close();
		}
		return listOfResponse;
	}

}
