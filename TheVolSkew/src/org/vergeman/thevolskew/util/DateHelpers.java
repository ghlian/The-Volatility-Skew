package org.vergeman.thevolskew.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;



public class DateHelpers {
		
	//DateDiff
	public static int getDaysfromToday(String input_date)  {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date myDate = new Date();
		int days = 0;
		
		try {
			myDate = df.parse(input_date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		GregorianCalendar today = new GregorianCalendar();
		GregorianCalendar test = new GregorianCalendar();
		test.setTime(myDate);
		
		/*
		 * yahoo option expiration data is a 'day' behind
		 * we count up since its reliable way of dealing
		 * with date diffs over time
		 */
		test.add(Calendar.DAY_OF_MONTH, 1); 
		
		while (today.before(test)) {
			today.add(Calendar.DAY_OF_MONTH, 1);
			days++;
		}
		return days;
	}
}
