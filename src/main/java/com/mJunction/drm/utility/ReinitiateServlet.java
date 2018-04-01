package com.mJunction.drm.utility;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.apache.commons.codec.binary.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

@WebServlet("/reinitiateActivity")
public class ReinitiateServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String tokenValue = request.getParameter("tokenValue");
		String catCode = request.getParameter("catCode");
		String timeStamp = request.getParameter("timeStamp");
		String client = request.getParameter("client");
		String activity = request.getParameter("activity");
		String failedDate = request.getParameter("failedDate");

		String authStringEncNew = "";
		String userId = "user";
		String password = "password";

		String authString = "";
		byte[] authStringEnc = null;

		Properties prop = new Properties();
		InputStream input = null;

		String reinitiateUrl = "";

		DBConnection dbConnection = new DBConnection();
		Statement st = null;
		ResultSet rs = null;
		Connection connection = (Connection) dbConnection.dbConnection();
		DateFormat folderDate = new SimpleDateFormat(
				ReadDateFormatConfig.getDateFormat());
		DateFormat fileTime = new SimpleDateFormat("HH-mm-ss");
		Calendar cal = Calendar.getInstance();

		try {

			ClassLoader classLoader = Thread.currentThread()
					.getContextClassLoader();

//			input = classLoader
//					.getResourceAsStream("com/mJunction/bam/properties/config.properties");
			input = classLoader
					.getResourceAsStream("config.properties");

			prop.load(input);

			reinitiateUrl = prop.getProperty("reinitiateUrl");
		} catch (Exception e) {
			e.printStackTrace();
		}

		File file = new File(ReadDateFormatConfig.getFilePath()
				+ "XmlFileReinitiate\\");
		if (!file.exists()) {
			if (file.mkdirs()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		}

		try {
			st = connection.createStatement();
			String query = "SELECT xml FROM m_junction.process_state_table where timestamp = '"
					+ timeStamp + "' and type ='" + catCode + "'";
			rs = st.executeQuery(query);

			int i = 0;
			while (rs.next()) {

				InputStream in = rs.getBinaryStream(1);

				OutputStream f = new FileOutputStream(new File(
						ReadDateFormatConfig.getFilePath()
								+ "XmlFileReinitiate\\" + "Reinitiate.xml"));

				i++;
				int c = 0;
				while ((c = in.read()) > -1) {
					f.write(c);

				}
				f.close();
				in.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		BufferedReader br = new BufferedReader(new FileReader(new File(
				ReadDateFormatConfig.getFilePath() + "XmlFileReinitiate\\"
						+ "Reinitiate.xml")));
		String line;
		StringBuilder sb = new StringBuilder();

		while ((line = br.readLine()) != null) {
			sb.append(line.trim());
		}
		br.close();
		String newXml = sb.toString();
		newXml = newXml.replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();

		String output = "";
		try {
			authString = userId + ":" + password;
			authStringEnc = Base64.encodeBase64(authString.getBytes());
			authStringEncNew = new String(authStringEnc);
			Client client1 = Client.create();

			WebResource webResource = client1.resource(reinitiateUrl
					+ "material_list_mjunction");

			ClientResponse response1 = webResource.type("application/xml")
					.header("Authorization", "Basic " + authStringEncNew)
					.post(ClientResponse.class, newXml);

			if (response1.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response1.getStatus());
			}

			output = response1.getEntity(String.class);

		} catch (Exception e) {

			e.printStackTrace();

		}
		try {
			String initial_status = "";
			String nowStatus = "Re-initited";

			String getSql = "select Initial_status from m_junction.process_state_table where timestamp = '"
					+ timeStamp + "' and type ='" + catCode + "' ";

			st = connection.createStatement();
			rs = st.executeQuery(getSql);

			while (rs.next()) {

				initial_status = rs.getString(1);
			}

			String sql = "UPDATE m_junction.process_state_table SET Initial_status =  concat('"
					+ initial_status
					+ " ' ,'"
					+ nowStatus
					+ "')  WHERE  timestamp = '"
					+ timeStamp
					+ "' and type = '"
					+ catCode + "' ";

			st.executeUpdate(sql);

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}
}
