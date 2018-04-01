/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mJunction.drm.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mJunction.drm.common.exception.InvalidDateFormatException;


/**
 *
 * @author siddhartha.k
 */
public class FormatDate {

	private static final Logger LOGGER = LoggerFactory.getLogger(FormatDate.class);

	public static String convertTimeTo24hourFomat(String timeInPM) {

		String[] a_timeInPM;
		int i_timeInPM;
		if (timeInPM != null) {
			a_timeInPM = timeInPM.split(":");
			try {
				i_timeInPM = Integer.parseInt(a_timeInPM[0]);
				i_timeInPM = i_timeInPM + 12;
			} catch (NumberFormatException exp) {
				exp.printStackTrace();
				return "ParsingError";
			}

			return "" + i_timeInPM + a_timeInPM[1];
		} else {
			return null;
		}
	}

	public static String formatStringToString(String date, String fromFormat, String toFormat) throws ParseException {
		SimpleDateFormat sdfFrom = new SimpleDateFormat(fromFormat);
		SimpleDateFormat sdfTo = new SimpleDateFormat(toFormat);
		Date enddate = null;
		// try {
		enddate = sdfFrom.parse(date);
		// } catch (ParseException e) {
		// e.printStackTrace();
		// }
		String endDateString = sdfTo.format(enddate);
		return endDateString;
	}

	public static Date StringToDate(String strDate, String format) {
		DateFormat df = null;
		df = new SimpleDateFormat(format, new Locale(Locale.ENGLISH.toString(), Locale.US.toString()));
		Date today = null;
		try {
			today = df.parse(strDate);
		} catch (ParseException e) {
			LOGGER.info("error " + e.getMessage());
		}
		return today;
	}

	public static String dateToCustomformatString(Date date, String format) {
		DateFormat df = null;
		df = new SimpleDateFormat(format, new Locale(Locale.ENGLISH.toString(), Locale.US.toString()));

		String today = null;

		try {
			today = df.format(date);
		} catch (Exception e) {
			LOGGER.info("error " + e.getMessage());
		}
		return today;
	}

	public static Date dateToCustomFormatDate(Date date, String format) {

		return StringToDate(dateToCustomformatString(date, format), format);
	}

	public static boolean isValidDateFormat(String DateSTR) {

		SimpleDateFormat dateFormat = null;
		if (DateSTR == null) {
			return false;
		}
		if (DateSTR.contains("/")) {
			dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		} else if (DateSTR.contains("-")) {
			dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		} else {
			return false;
		}

		DateSTR = DateSTR.trim();

		if (DateSTR.length() > 10) {
			if (DateSTR.contains("/")) {
				dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			}
			if (DateSTR.contains("-")) {
				dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			}
		}

		if (DateSTR.length() != dateFormat.toPattern().length()) {
			return false;
		}
		dateFormat.setLenient(false);
		try {
			dateFormat.parse(DateSTR.trim());
		} catch (ParseException pe) {
			return false;
		}
		return true;
	}

	public static boolean isValidDateFormat1(String DateSTR) {
		SimpleDateFormat dateFormat = null;
		if (DateSTR == null) {
			return false;
		}
		if (DateSTR.contains("/")) {
			dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		} else if (DateSTR.contains("-")) {
			dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		} else {
			return false;
		}
		DateSTR = DateSTR.trim();
		if (DateSTR.length() > 10) {
			if (DateSTR.contains("/")) {
				dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			}
			if (DateSTR.contains("-")) {
				dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			}
		}
		if (DateSTR.length() != dateFormat.toPattern().length()) {
			return false;
		}
		dateFormat.setLenient(false);
		try {
			dateFormat.parse(DateSTR.trim());
		} catch (ParseException pe) {
			return false;
		}
		return true;
	}

	/* **************** VALID DATE CHECK **************** */
	public static boolean isValidDate(String DateSTR) {
		int Jan = 31;
		int Mar = 31;
		int Apr = 30;
		int May = 31;
		int Jun = 30;
		int Jul = 31;
		int Aug = 31;
		int Sep = 30;
		int Oct = 31;
		int Nov = 30;
		int Dec = 31;
		boolean flag = true;
		String seperator = "";
		if (isValidDateFormat(DateSTR) == true) {
			flag = true;
		} else {
			return flag = false;
		}
		if (DateSTR.contains("/")) {
			seperator = "/";
		}
		if (DateSTR.contains("-")) {
			seperator = "-";
		}
		StringTokenizer st = new StringTokenizer(DateSTR, seperator);
		int day = Integer.parseInt(st.nextToken());
		int month = Integer.parseInt(st.nextToken());
		int year = Integer.parseInt(st.nextToken());
		if ((month > 12) && (month < 100)) {
			return flag = false;
		}
		if (String.valueOf(year).length() != 4) {
			return flag = false;
		} else {
			flag = true;
		}
		switch (month) {
		case 1:
			if (day > Jan) {
				flag = false;
			}
			break;
		case 2:
			if ((year % 4) == 0) {
				if (day > 29) {
					flag = false;
				}
			} else {
				if (day > 28) {
					flag = false;
				}
			}
		case 3:
			if (day > Mar) {
				flag = false;
			}
			break;
		case 4:
			if (day > Apr) {
				flag = false;
			}
			break;
		case 5:
			if (day > May) {
				flag = false;
			}
			break;
		case 6:
			if (day > Jun) {
				flag = false;
			}
			break;
		case 7:
			if (day > Jul) {
				flag = false;
			}
			break;
		case 8:
			if (day > Aug) {
				flag = false;
			}
			break;
		case 9:
			if (day > Sep) {
				flag = false;
			}
			break;
		case 10:
			if (day > Oct) {
				flag = false;
			}
			break;
		case 11:
			if (day > Nov) {
				flag = false;
			}
			break;
		case 12:
			if (day > Dec) {
				flag = false;
			}
			break;
		}
		return flag;
	}

	public static boolean DateDifference(String startDate, String EndDate) {
		boolean result = false;
		String seperator = "";
		if (startDate.contains("/")) {
			seperator = "/";
		}
		if (startDate.contains("-")) {
			seperator = "-";
		}
		StringTokenizer str1 = new StringTokenizer(startDate, seperator);
		StringTokenizer str2 = new StringTokenizer(EndDate, seperator);

		int day1 = Integer.parseInt(str1.nextToken());
		int month1 = Integer.parseInt(str1.nextToken());
		int year1 = Integer.parseInt(str1.nextToken());
		int day2 = Integer.parseInt(str2.nextToken());
		int month2 = Integer.parseInt(str2.nextToken());
		int year2 = Integer.parseInt(str2.nextToken());
		Calendar ca1 = Calendar.getInstance();
		Calendar ca2 = Calendar.getInstance();

		ca1.set(year1, month1, day1);
		ca2.set(year2, month2, day2);

		// Get date in milliseconds
		long milisecond1 = ca1.getTimeInMillis();
		long milisecond2 = ca2.getTimeInMillis();

		// Find date difference in milliseconds
		long diffInMSec = milisecond2 - milisecond1;
		// //LOGGER.info("mill :"+diffInMSec);

		/*
		 * Find date difference in days (24 hours 60 minutes 60 seconds 1000
		 * millisecond)
		 */
		long diffOfDays = diffInMSec / (24 * 60 * 60 * 1000);

		// LOGGER.info("Date Difference in : " + diffOfDays + " days.");
		if (diffOfDays > 0) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	public static String validateAndFormat(String date, String format)
			throws InvalidDateFormatException, ParseException {
		if (date != null && !date.isEmpty()) {
			if (date.trim().length() <= 10) {
				if (date.contains("/")) {
					return formatStringToString(date, "dd/MM/yyyy", format);
				} else if (date.contains("-")) {
					return formatStringToString(date, "dd-MM-yyyy", format);
				} else {
					throw new InvalidDateFormatException("The Format expected is : dd-MM-yyyy or dd/mm/yyyy");
				}
			}
			if (date.trim().length() > 10) {
				if (date.contains("/")) {
					return formatStringToString(date, "dd/MM/yyyy HH:mm:ss", format);
				} else if (date.contains("-")) {
					return formatStringToString(date, "dd-MM-yyyy HH:mm:ss", format);
				} else {
					throw new InvalidDateFormatException(
							"The Format expected is : dd-MM-yyyy HH:mm:ss or dd/mm/yyyy HH:mm:ss");
				}
			}
		}
		return null;
	}

	public static String validateAndFormatAndNoNullCheck(String date, String format)
			throws ParseException, InvalidDateFormatException {
		System.out.println("date:" + date);
		System.out.println("format:" + format);
		if (date != null && !date.isEmpty()) { // throw new
												// InvalidDateFormatException("The
												// date is either null or
												// blank!!");
			if (date.trim().length() <= 10) {
				if (date.contains("/")) {
					if (isValidDate(date))
						return formatStringToString(date, "dd/MM/yyyy", format);
					else
						throw new InvalidDateFormatException("[" + date + "] is not a valid date!!");
				} else if (date.contains("-")) {
					if (isValidDate(date))
						return formatStringToString(date, "dd-MM-yyyy", format);
					else
						throw new InvalidDateFormatException("[" + date + "] is not a valid date!!");
				} else
					throw new InvalidDateFormatException("The Format expected is : dd-MM-yyyy or dd/mm/yyyy");
			}
			if (date.trim().length() > 10) {
				if (date.contains("/")) {
					if (isValidDate(date))
						return formatStringToString(date, "dd/MM/yyyy HH:mm:ss", format);
					else
						throw new InvalidDateFormatException("[" + date + "] is not a valid date!!");
				} else if (date.contains("-")) {
					if (isValidDate(date))
						return formatStringToString(date, "dd-MM-yyyy HH:mm:ss", format);
					else
						throw new InvalidDateFormatException("[" + date + "] is not a valid date!!");
				} else
					throw new InvalidDateFormatException(
							"The Format expected is : dd-MM-yyyy HH:mm:ss or dd/mm/yyyy HH:mm:ss");
			}
		}
		return null;
	}

	/***
	 * @author siddhartha.k
	 * @param time
	 *            (e.g : valid time([16:00],[12:35:00]),invalid
	 *            time([26:98],[2323],[12]))
	 * @param format
	 *            (e.g: ',',':','-','\')
	 * @return
	 * @throws ParseException
	 */
	public static boolean isValidTime(String time, String format) {
		if (Objects.isNull(time) || Objects.isNull(format))
			return false;
        String tempTime;
		try {
			tempTime = formatStringToString(time, format, format);
		} catch (ParseException pe) {
			return false;
		}
		
		return time.equals(tempTime);
	}

	/**
	 *
	 */



}
