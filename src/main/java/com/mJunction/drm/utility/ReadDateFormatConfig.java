package com.mJunction.drm.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Deprecated
public class ReadDateFormatConfig {

	@Deprecated
	public static String getDateFormat() throws IOException {

		Properties prop = new Properties();
		InputStream input = null;
		String dateFormatDb = "";

		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		
//		input = classLoader.getResourceAsStream("com/mJunction/bam/properties/config.properties");
		input = classLoader.getResourceAsStream("config.properties");

		prop.load(input);

		dateFormatDb = prop.getProperty("dbdateformat");

		return dateFormatDb;
	}

	@Deprecated
	public static String getGraphDateFormat() throws IOException {

		Properties prop = new Properties();
		InputStream input = null;
		String dateFormatgraph = "";

		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		
//		input = classLoader.getResourceAsStream("com/mJunction/bam/properties/config.properties");
		input = classLoader.getResourceAsStream("config.properties");

		prop.load(input);

		dateFormatgraph = prop.getProperty("graphdateformat");

		return dateFormatgraph;
	}

	public static String getsearchDateFormat() throws IOException {

		Properties prop = new Properties();
		InputStream input = null;
		String dateFormatsearch = "";

		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		
//		input = classLoader.getResourceAsStream("com/mJunction/bam/properties/config.properties");
		input = classLoader.getResourceAsStream("config.properties");

		prop.load(input);

		dateFormatsearch = prop.getProperty("searchdateformat");

		return dateFormatsearch;
	}


	public static String getsearchDateFormatNew() throws IOException {

		Properties prop = new Properties();
		InputStream input = null;
		String dateFormatsearchNew = "";

		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		
//		input = classLoader.getResourceAsStream("com/mJunction/bam/properties/config.properties");
		input = classLoader.getResourceAsStream("config.properties");

		prop.load(input);

		dateFormatsearchNew = prop.getProperty("searchnewdateformat");

		return dateFormatsearchNew;
	}

	public static String getFilePath() throws IOException {

		Properties prop = new Properties();
		InputStream input = null;
		String filepath = "";

		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		
//		input = classLoader.getResourceAsStream("com/mJunction/bam/properties/config.properties");
		input = classLoader.getResourceAsStream("config.properties");

		prop.load(input);

		filepath = prop.getProperty("pdfexcelfilepath");

		return filepath;
	}
	
	public static String getFilePathMdmWebService() throws IOException {

		Properties prop = new Properties();
		InputStream input = null;
		String filepath = "";

		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		
//		input = classLoader.getResourceAsStream("com/mJunction/bam/properties/config.properties");
		input = classLoader.getResourceAsStream("config.properties");

		prop.load(input);

		filepath = prop.getProperty("mdmlobwebservice");

		return filepath;
	}
	@Deprecated
	public static String getFilePathBidderSync() throws IOException {

		Properties prop = new Properties();
		InputStream input = null;
		String filepathBidderSync = "";

		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		
//		input = classLoader.getResourceAsStream("com/mJunction/bam/properties/config.properties");
		input = classLoader.getResourceAsStream("config.properties");

		prop.load(input);

		filepathBidderSync = prop.getProperty("bidderSyncPath");

		return filepathBidderSync;
	}

}
