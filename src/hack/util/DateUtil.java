package hack.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DateUtil 
{
	private DateUtil()
	{
	}
	
	public static int fetchDateUtilbetTwoDates(Date date1,Date date2){
		long diff = date2.getTime() - date1.getTime();
	    return (int) (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)); 
	}
	
	public static java.sql.Date convertStringToSQLDate(String date, String format)
	{
		Date utilDate = parseDateInCustomFormat(date, format);
		return convertUtilDateToSQLDate(utilDate);
	}
	
	
	public static java.sql.Date convertUtilDateToSQLDate(Date date)
	{
		return new java.sql.Date(date.getTime());
	}
	
	
	public static Date getBeginDate(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}
	
	public static Date getEndDate(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}
	
	public static Date addOneMonthToDate(Date date)
	{
		Calendar newDate = Calendar.getInstance();
		newDate.setTime(date);
		
		newDate.add(Calendar.MONTH, 1);
		
		return newDate.getTime();
		
	}
	
	public static Integer numberOfMonthsBetween(Date fromDate, Date toDate)
	{
		 Calendar cal1 = Calendar.getInstance();
		    cal1.setTime(fromDate);
		    Calendar cal2 = Calendar.getInstance();
		    cal2.setTime(toDate);
		    int diff = 0;
		    if (cal2.after(cal1)) {
		        while (cal2.after(cal1)) {
		            cal1.add(Calendar.MONTH, 1);
		            if (cal2.after(cal1)) {
		                diff++;
		            }
		        }
		    } else if (cal2.before(cal1)) {
		        while (cal2.before(cal1)) {
		            cal1.add(Calendar.MONTH, -1);
		            if (cal1.before(cal2)) {
		                diff--;
		            }
		        }
		    }
		    return diff;
	}
	
	public static Date parseDateInCustomFormat(String date, String format)
	{
		DateFormat sourceFormat = new SimpleDateFormat(format);
		try 
		{
			return sourceFormat.parse(date);
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Integer numOfDaysBetween(Date beginDate, Date endDate)
	{
		return null;
		
	}
	
	public static Date generateDateInBetween(Date dMin, Date dMax) 
	{
        long MILLIS_PER_DAY = 1000*60*60*24;
        GregorianCalendar s = new GregorianCalendar();
        s.setTimeInMillis(dMin.getTime());
        GregorianCalendar e = new GregorianCalendar();
        e.setTimeInMillis(dMax.getTime());
        
        // Get difference in milliseconds
        long endL   =  e.getTimeInMillis() +  e.getTimeZone().getOffset(e.getTimeInMillis());
        long startL = s.getTimeInMillis() + s.getTimeZone().getOffset(s.getTimeInMillis());
        long dayDiff = (endL - startL) / MILLIS_PER_DAY;
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(dMin);
        cal.add(Calendar.DATE, new Random().nextInt((int)dayDiff));          
        return cal.getTime();
    }


	public static String formatDate(Date date, String dateFormat) 
	{
		DateFormat sourceFormat = new SimpleDateFormat(dateFormat);
		return sourceFormat.format(date);
	}
}
