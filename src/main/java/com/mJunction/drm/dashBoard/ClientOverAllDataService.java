package com.mJunction.drm.dashBoard;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mJunction.drm.utility.DBConnection;
import com.mJunction.drm.utility.QueryStatic;

public class ClientOverAllDataService {

	public static List<Client> getClientListOverAll() throws SQLException {
		QueryStatic qs = new QueryStatic();

		List<Client> listOfclientsOverAll = new ArrayList<Client>();

		Client client = new Client();
		DBConnection dbConnection = new DBConnection();
		Statement st = null;
		ResultSet rs = null;
		Connection connection = null;
		try {
			 connection = (Connection) dbConnection.dbConnection();
		

			String client_name = "";
			String type = "";
			String activity = "";
			String timestamp = "";
			String Final_status = "";
			String imgUrlSuccess="resources/img/correct.png";
			String imgUrlFail="resources/img/wrong2.png";
			String imgUrlUnder="resources/img/loading.png";
			String originalImgPop="";
			
			st = connection.createStatement();
		
			String query= qs.CLIENT_OVERALL_SERVICE;
			rs = st.executeQuery(query);

			while (rs.next()) {
				client = new Client();

				client_name = rs.getString(1);
				type = rs.getString(2);
				activity = rs.getString(3);
				timestamp = rs.getString(4);
				Final_status = rs.getString(5);
				if(Final_status.equalsIgnoreCase("Processed")){
					originalImgPop=imgUrlSuccess;
				}
				else if(Final_status.equalsIgnoreCase("Failure")){
					originalImgPop=imgUrlFail;
				}
				else {
					originalImgPop=imgUrlUnder;
				}

				client.setActivity(activity+":"+type);

				client.setClient(client_name);

				client.setRecordDate(timestamp);
				
				

				client.setStatus(originalImgPop);

				listOfclientsOverAll.add(client);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		
		finally{
			connection.close();
		}	
		return listOfclientsOverAll;
	}
}
