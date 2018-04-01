package com.mJunction.drm.dashBoard;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.mJunction.drm.utility.DBConnection;
import com.mJunction.drm.utility.ReadDateFormatConfig;

public class ClientUnderDataService {

	public static List<Client> getClientListUnder() throws SQLException {

		List<Client> listOfclientsUnderProcessing = new ArrayList<Client>();

		Client client = new Client();
		DBConnection dbConnection = new DBConnection();
		Statement st = null;
		ResultSet rs = null;
		Connection connection = null;
		try {
			connection = (Connection) dbConnection.dbConnection();
		
			DateFormat dateFormat = new SimpleDateFormat(
					ReadDateFormatConfig.getDateFormat());
			Date myDate = new Date(System.currentTimeMillis());

			String startDateSystem = dateFormat.format(myDate);
			

			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(myDate);
			cal1.add(Calendar.DATE, -4);
			String endDateSystem = dateFormat.format(cal1.getTime());
			

			String client_name = "";
			String type = "";
			String activity = "";
			String timestamp = "";
			String Final_status = "";
			String forModal = "";
			String imgUrlSuccess = "resources/img/correct.png";
			String imgUrlFail = "resources/img/wrong2.png";
			String imgUrlUnder = "resources/img/loading.png";
			String originalImgPop = "";
			String underProcessing = "Processing";

			st = connection.createStatement();
			String query = "SELECT p.client_name,p.type,p.activity,p.timestamp,p.Final_status FROM process_state_table p"
					+ " where p.Final_status='' and cast(p.timestamp as date) between '"
					+ endDateSystem
					+ "' and '"
					+ startDateSystem
					+ "' UNION SELECT m.client_name,m.type,m.Activity,m.DateTimeStamp,m.Final_status FROM mj_report m "
					+ "where m.Final_status='' and cast(m.datetimestamp as date) between '"
					+ endDateSystem
					+ "' and '"
					+ startDateSystem
					+ "' order by 4 desc,2";
//					+ "UNION SELECT e.client_name,e.type,e.Error_description,e.Date,'Report' FROM error_table e where cast(e.Date as date) between "
//					+ "'" + endDateSystem + "' and '" + startDateSystem + "'";
			rs = st.executeQuery(query);

			while (rs.next()) {
				client = new Client();

				client_name = rs.getString(1);
				type = rs.getString(2);
				activity = rs.getString(3);
				timestamp = rs.getString(4);
				timestamp = timestamp.substring(0, timestamp.indexOf('.'));
				Final_status = rs.getString(5);
				forModal = Final_status;
				if (Final_status.equalsIgnoreCase("")
						|| Final_status.equalsIgnoreCase("null")
						|| Final_status.equals(null)) {
					Final_status = underProcessing;
				}
				if (Final_status.equalsIgnoreCase("Processed")) {
					originalImgPop = imgUrlSuccess;
				} else if (Final_status.equalsIgnoreCase("Failure")) {
					originalImgPop = imgUrlFail;
				} else {
					originalImgPop = imgUrlUnder;
				}
				client = new Client();

				client.setActivity(type + "~" + activity);

				client.setClient(client_name);

				client.setRecordDate(timestamp + "_" + Final_status);

				client.setStatus(originalImgPop);

				listOfclientsUnderProcessing.add(client);

			}

		} catch (Exception e) {
			
		}

		finally {
			connection.close();
		}

	

		return listOfclientsUnderProcessing;

	}
}
