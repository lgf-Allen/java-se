/**
 * 
 */
package com.allen.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author first
 *
 */
public class CommonUtil {

	/** define constant:SEPARATOR*/
	private static final String SEPARATOR = "/";
	/**
	 * Get now date in GMT+8
	 */
	public static String getDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		String date = year+CommonUtil.SEPARATOR+month+CommonUtil.SEPARATOR+day;
		return date;
	}

	/**
	 * Format money
	 */
	public static String moneyFormat(Double money) {
		//$:"###,###.##$"; ¥:"###,###.##￥" ;
		DecimalFormat format = new DecimalFormat("###,###.##￥");
		String moneyStr = format.format(money);
		return moneyStr;
	}

	public static String dateFormat(Date date) {
		DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		String dateStr = format.format(date);
		return dateStr;
	}

}
