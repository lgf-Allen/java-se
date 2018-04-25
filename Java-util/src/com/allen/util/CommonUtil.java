/**
 * 
 */
package com.allen.util;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * @author first
 *
 */
public class CommonUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	
	}
	
	/**
	 * Format java.util.Date to "GMT+8" time
	 */
	private static void dateFormat(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println(year+month+day);
    }
	
	/**
	 * Format money 
	 */
	private static void moneyFormat(){
        String s = "12345566";
        DecimalFormat format=new DecimalFormat("###,###.##￥");
        String str = format.format(Double.parseDouble(s));
        System.out.println(str);
    }
	
	


}
