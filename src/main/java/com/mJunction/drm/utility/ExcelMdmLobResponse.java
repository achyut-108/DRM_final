package com.mJunction.drm.utility;

import com.google.gson.Gson;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

@WebServlet("/excelMdmLobRes")
public class ExcelMdmLobResponse extends HttpServlet {

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

		File file = new File(ReadDateFormatConfig.getFilePath() + "MdmLobXml\\");
		if (!file.exists()) {
			if (file.mkdirs()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		}

		try {
			st = connection.createStatement();
			String query = "select MdmLobResponse from process_state_table where final_status='failure' and Type='"
					+ catCode
					+ "' and timestamp='"
					+ failedDate
					+ "' and  Activity like '%" + activity + "%'";

			rs = st.executeQuery(query);

			int i = 0;

			while (rs.next()) {

				InputStream in = rs.getBinaryStream(1);

				

				if (in != null) {
					OutputStream f = new FileOutputStream(new File(
							ReadDateFormatConfig.getFilePath() + "MdmLobXml\\"
									+ "mdmLob.xml"));

					i++;
					int c = 0;
					while ((c = in.read()) > -1) {
						f.write(c);

					}
					f.close();
					in.close();
					File xmlDocument = new File(
							ReadDateFormatConfig.getFilePath() + "MdmLobXml\\"
									+ "mdmLob.xml");

					String filePath = generateExcel(xmlDocument);

					session = request.getSession();
					String fileSaveUrl1 = filePath;
					session.setAttribute("fileDownloadMdmExcel", fileSaveUrl1);
				}

				else {

					Map<String, String> options = new LinkedHashMap<>();
					options.put("nodata", "No Data Found For MDM LOB");
					String json = new Gson().toJson(options);

					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write(json);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String generateExcel(File xmlDocument) {
		String filePath = "";
		try {

			DateFormat folderDate = new SimpleDateFormat(
					ReadDateFormatConfig.getDateFormat());
			DateFormat fileTime = new SimpleDateFormat("HH-mm-ss");
			Calendar cal = Calendar.getInstance();

			int catCodeLoc = 0;
			int sl_noLoc = 0;
			int p_sl_noLoc = 0;
			int groupNoLoc = 0;
			int currencyLoc = 0;
			int lotNoLoc = 0;
			int material_noLoc = 0;
			int material_descriptionLoc = 0;
			int locationLoc = 0;
			int quantityLoc = 0;
			int uomLoc = 0;
			int liftingDaysLoc = 0;
			int mdmResponseLoc = 0;
			int lobResponseLoc = 0;
			int lobMaterialCodeLoc = 0;

			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet spreadSheet = wb.createSheet("spreadSheet");

			spreadSheet.setColumnWidth((short) 0, (short) (256 * 25));
			spreadSheet.setColumnWidth((short) 1, (short) (256 * 25));

			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(xmlDocument);
			NodeList nodeListCat = document.getElementsByTagName("CATALOGUE");
			NodeList nodeListHeader = document.getElementsByTagName("HEADER");
			NodeList nodeListHeaderResponse = document
					.getElementsByTagName("HEADERRESPONSE");
			NodeList nodeListItems = document.getElementsByTagName("ITEMS");
			NodeList nodeListItem = document.getElementsByTagName("ITEM");
			NodeList nodeListItemResponse = document
					.getElementsByTagName("ITEMRESPONSE");

			HSSFRow row = spreadSheet.createRow(0);

			HSSFCell cell = row.createCell((short) 1);

			HSSFRow row1 = spreadSheet.createRow(1);
			HSSFRow row2 = spreadSheet.createRow(2);
			HSSFRow row3 = spreadSheet.createRow(3);
			HSSFRow row4 = spreadSheet.createRow(4);
			HSSFRow row5 = spreadSheet.createRow(5);
			HSSFRow row6 = spreadSheet.createRow(6);
			HSSFRow row7 = spreadSheet.createRow(7);
			HSSFRow row8 = spreadSheet.createRow(8);
			HSSFRow row9 = spreadSheet.createRow(9);

			for (int i = 0; i < nodeListCat.getLength(); i++) {
				HSSFCellStyle cellStyle = wb.createCellStyle();
				cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
				cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
				cellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
				cellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);

				switch (i) {

				case 0:

					cell = row1.createCell((short) 0);
					cell.setCellValue("Catalogue Code");

					cell = row1.createCell((short) 1);
					cell.setCellValue(((Element) (nodeListHeader.item(0)))
							.getElementsByTagName("CATALOGUECODE").item(0)
							.getFirstChild().getNodeValue());

					cell = row2.createCell((short) 0);
					cell.setCellValue("Auction Date");

					cell = row2.createCell((short) 1);
					cell.setCellValue(((Element) (nodeListHeader.item(0)))
							.getElementsByTagName("AUCTIONDATE").item(0)
							.getFirstChild().getNodeValue());

					cell = row3.createCell((short) 0);
					cell.setCellValue("Revision No");

					cell = row3.createCell((short) 1);
					cell.setCellValue(((Element) (nodeListHeader.item(0)))
							.getElementsByTagName("REVISION_NO").item(0)
							.getFirstChild().getNodeValue());

					cell = row4.createCell((short) 0);
					cell.setCellValue("Client Code");

					cell = row4.createCell((short) 1);
					cell.setCellValue(((Element) (nodeListHeader.item(0)))
							.getElementsByTagName("CLIENT_CD").item(0)
							.getFirstChild().getNodeValue());

					cell = row5.createCell((short) 0);
					cell.setCellValue("Stage State");

					cell = row5.createCell((short) 1);
					cell.setCellValue(((Element) (nodeListHeader.item(0)))
							.getElementsByTagName("STAGESTATE").item(0)
							.getFirstChild().getNodeValue());

					cell.setCellStyle(cellStyle);
					break;

				default:
					break;
				}

			}

			for (int i = 0; i < nodeListHeaderResponse.getLength(); i++) {
				HSSFCellStyle cellStyle = wb.createCellStyle();
				cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);

				switch (i) {

				case 0:

					cell = row6.createCell((short) 0);
					cell.setCellValue("Catalogue Validity");

					cell = row6.createCell((short) 1);
					cell.setCellValue(((Element) (nodeListHeaderResponse
							.item(0)))
							.getElementsByTagName("CATALOGUE_VALIDITY").item(0)
							.getFirstChild().getNodeValue());
					cell.setCellStyle(cellStyle);
					break;

				default:
					break;
				}
			}

			for (int j = 0; j < nodeListItem.getLength(); j++) {
				Node nNode = nodeListItem.item(j);
				Element eElement = (Element) nNode;
				cell = row7.createCell((short) 0);
				cell.setCellValue("");
				cell = row9.createCell((short) 0);
				cell.setCellValue("SL No.");
				cell = row9.createCell((short) 1);
				cell.setCellValue("P Sl No.");
				cell = row9.createCell((short) 2);
				cell.setCellValue("Group No.");
				cell = row9.createCell((short) 3);
				cell.setCellValue("Category Code");
				cell = row9.createCell((short) 4);
				cell.setCellValue("Currency");
				cell = row9.createCell((short) 5);
				cell.setCellValue("Lot No.");
				cell = row9.createCell((short) 6);
				cell.setCellValue("Material No.");
				cell = row9.createCell((short) 7);
				cell.setCellValue("Material Description");

				cell = row9.createCell((short) 8);
				cell.setCellValue("Location");
				cell = row9.createCell((short) 9);
				cell.setCellValue("Quantity");
				cell = row9.createCell((short) 10);
				cell.setCellValue("UOM");
				cell = row9.createCell((short) 11);
				cell.setCellValue("Lifting Days");
				cell = row9.createCell((short) 12);
				cell.setCellValue("Mapped with MJ UOM");
				cell = row9.createCell((short) 13);

				cell.setCellValue("Mapped with MDM Item");
				cell = row9.createCell((short) 14);
				cell.setCellValue("Mapped with Sub Category");
				cell = row9.createCell((short) 15);
				cell.setCellValue("LOB Material Code");

				int nextRow = j + 10;
				HSSFRow r = spreadSheet.getRow(nextRow);
				HSSFCellStyle cellStyle = wb.createCellStyle();
				if (r == null) {
					r = spreadSheet.createRow(nextRow);
				}
				cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);

				Cell c = r.getCell(0, HSSFRow.CREATE_NULL_AS_BLANK);
				Cell c1 = r.getCell(1, HSSFRow.CREATE_NULL_AS_BLANK);
				Cell c2 = r.getCell(2, HSSFRow.CREATE_NULL_AS_BLANK);
				Cell c3 = r.getCell(3, HSSFRow.CREATE_NULL_AS_BLANK);
				Cell c4 = r.getCell(4, HSSFRow.CREATE_NULL_AS_BLANK);
				Cell c5 = r.getCell(5, HSSFRow.CREATE_NULL_AS_BLANK);
				Cell c6 = r.getCell(6, HSSFRow.CREATE_NULL_AS_BLANK);
				Cell c7 = r.getCell(7, HSSFRow.CREATE_NULL_AS_BLANK);

				Cell c8 = r.getCell(8, HSSFRow.CREATE_NULL_AS_BLANK);
				Cell c9 = r.getCell(9, HSSFRow.CREATE_NULL_AS_BLANK);
				Cell c10 = r.getCell(10, HSSFRow.CREATE_NULL_AS_BLANK);
				Cell c11 = r.getCell(11, HSSFRow.CREATE_NULL_AS_BLANK);
				Cell c12 = r.getCell(12, HSSFRow.CREATE_NULL_AS_BLANK);

				c.setCellValue(eElement.getAttributes().getNamedItem("SL_NO")
						.getNodeValue());

				p_sl_noLoc = (eElement.getElementsByTagName("P_SL_NO").item(0)
						.getTextContent().length());

				if (p_sl_noLoc != 0) {
					c1.setCellValue(eElement.getElementsByTagName("P_SL_NO")
							.item(0).getTextContent());
				} else {
					c1.setCellValue("");
				}

				groupNoLoc = (eElement.getElementsByTagName("GROUP_NO").item(0)
						.getTextContent().length());
				if (groupNoLoc != 0) {
					c2.setCellValue(eElement.getElementsByTagName("GROUP_NO")
							.item(0).getFirstChild().getNodeValue());
				} else {
					c2.setCellValue("");
				}

				catCodeLoc = (eElement.getElementsByTagName("CAT_CODE").item(0)
						.getTextContent().length());

				if (catCodeLoc != 0) {
					c3.setCellValue(eElement.getElementsByTagName("CAT_CODE")
							.item(0).getFirstChild().getNodeValue());
				}

				else {
					c3.setCellValue("");
				}

				currencyLoc = (eElement.getElementsByTagName("CURRENCY")
						.item(0).getTextContent().length());

				if (currencyLoc != 0) {
					c4.setCellValue(eElement.getElementsByTagName("CURRENCY")
							.item(0).getFirstChild().getNodeValue());
				} else {
					c4.setCellValue("");
				}

				lotNoLoc = (eElement.getElementsByTagName("LOT_NO").item(0)
						.getTextContent().length());

				if (lotNoLoc != 0) {
					c5.setCellValue(eElement.getElementsByTagName("LOT_NO")
							.item(0).getFirstChild().getNodeValue());

				} else {
					c5.setCellValue("");
				}

				material_noLoc = (eElement.getElementsByTagName("MATERIAL_NO")
						.item(0).getTextContent().length());

				if (material_noLoc != 0) {
					c6.setCellValue(eElement
							.getElementsByTagName("MATERIAL_NO").item(0)
							.getFirstChild().getNodeValue());
				} else {
					c6.setCellValue("");
				}

				material_descriptionLoc = (eElement
						.getElementsByTagName("MATERIAL_DESCRIPTION").item(0)
						.getTextContent().length());

				if (material_descriptionLoc != 0) {
					c7.setCellValue(eElement
							.getElementsByTagName("MATERIAL_DESCRIPTION")
							.item(0).getFirstChild().getNodeValue());
				} else {

					c7.setCellValue("");
				}

				locationLoc = (eElement.getElementsByTagName("LOCATION")
						.item(0).getTextContent().length());
				if (locationLoc != 0) {
					c8.setCellValue(eElement.getElementsByTagName("LOCATION")
							.item(0).getFirstChild().getNodeValue());
				} else {
					c8.setCellValue("");
				}

				quantityLoc = (eElement.getElementsByTagName("QUANTITY")
						.item(0).getTextContent().length());
				if (quantityLoc != 0) {
					c9.setCellValue(eElement.getElementsByTagName("QUANTITY")
							.item(0).getFirstChild().getNodeValue());
				} else {
					c9.setCellValue("");
				}

				uomLoc = (eElement.getElementsByTagName("UOM").item(0)
						.getTextContent().length());
				if (uomLoc != 0) {
					c10.setCellValue(eElement.getElementsByTagName("UOM")
							.item(0).getFirstChild().getNodeValue());

				} else {
					c10.setCellValue("");

				}

				liftingDaysLoc = (eElement.getElementsByTagName("LIFTINGDAYS")
						.item(0).getTextContent().length());

				if (liftingDaysLoc != 0) {
					c11.setCellValue(eElement
							.getElementsByTagName("LIFTINGDAYS").item(0)
							.getFirstChild().getNodeValue());
				} else {
					c11.setCellValue("");
				}

				if (uomLoc != 0) {

					c12.setCellValue(eElement.getElementsByTagName("UOM")
							.item(0).getFirstChild().getNodeValue());
				} else {

					c12.setCellValue("");
				}

				nextRow++;

			}

			for (int j = 0; j < nodeListItemResponse.getLength(); j++) {
				Node nNode = nodeListItem.item(j);
				Element eElement = (Element) nNode;
				cell = row7.createCell((short) 0);
				cell.setCellValue("");

				int nextRow = j + 10;
				HSSFRow r = spreadSheet.getRow(nextRow);
				HSSFCellStyle cellStyle = wb.createCellStyle();
				if (r == null) {
					r = spreadSheet.createRow(nextRow);
				}
				cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);

				Cell c13 = r.getCell(13, HSSFRow.CREATE_NULL_AS_BLANK);
				Cell c14 = r.getCell(14, HSSFRow.CREATE_NULL_AS_BLANK);
				Cell c15 = r.getCell(15, HSSFRow.CREATE_NULL_AS_BLANK);

				mdmResponseLoc = (eElement.getElementsByTagName("MDMRESPONSE")
						.item(0).getTextContent().length());

				if (mdmResponseLoc != 0) {
					c13.setCellValue(eElement
							.getElementsByTagName("MDMRESPONSE").item(0)
							.getFirstChild().getNodeValue());
				} else {
					c13.setCellValue("");
				}

				lobResponseLoc = (eElement.getElementsByTagName("LOBRESPONSE")
						.item(0).getTextContent().length());

				if (lobResponseLoc != 0) {
					c14.setCellValue(eElement
							.getElementsByTagName("LOBRESPONSE").item(0)
							.getFirstChild().getNodeValue());

				} else {
					c14.setCellValue("");
				}

				lobMaterialCodeLoc = (eElement
						.getElementsByTagName("LOBMATERIALCODE").item(0)
						.getTextContent().length());
				if (lobMaterialCodeLoc != 0) {
					c15.setCellValue(eElement
							.getElementsByTagName("LOBMATERIALCODE").item(0)
							.getFirstChild().getNodeValue());

				} else {
					c15.setCellValue("");
				}

			}

			try {
				File file = new File(ReadDateFormatConfig.getFilePath()
						+ "MdmLobExcel\\" + folderDate.format(cal.getTime()));
				if (!file.exists()) {
					if (file.mkdirs()) {
						System.out.println("Directory is created!");
					} else {
						System.out.println("Failed to create directory!");
					}
				}

				FileOutputStream output = new FileOutputStream(new File(
						ReadDateFormatConfig.getFilePath() + "MdmLobExcel\\"
								+ folderDate.format(cal.getTime())
								+ "\\MdmLobExcel.xls"));

				wb.write(output);
				output.flush();
				output.close();

				filePath = ReadDateFormatConfig.getFilePath() + "MdmLobExcel\\"
						+ folderDate.format(cal.getTime())
						+ "\\MdmLobExcel.xls";

			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return filePath;
	}
}
