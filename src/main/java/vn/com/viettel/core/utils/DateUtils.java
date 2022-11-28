package vn.com.viettel.core.utils;

import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class DateUtils {

    private static final Logger LOGGER = Logger.getLogger(DateUtils.class);

    public static final String DATE_FORMAT_PROBLEM = "HH:mm dd/MM/yyyy";
    public static final String DATE_FORMAT_PROBLEM2 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String DATE_FORMAT_FULL = "HH:mm:ss dd/MM/yyyy";
    public static final String DATE_FORMAT_STR = "dd/MM/yyyy";
    public static final String TIME_FORMAT = "HH:mm";
    public static final String TIME_FORMAT2 = "HH:mm-HH:mm";
    public static final String TIME_FORMAT3 = "HH:mm-HH:mm|dd/MM/yyyy";
    public static final String DATE_FORMAT_YYY_MM_DD = "yyyy-MM-dd";

    public static final String DATE_FORMAT_NOW = "dd/MM/yyyy HH:mm:ss";
    public static final String DATE_FORMAT_NOW2 = "yyyy-MM-dd HH:mm:ss";

    public static final String DATETIME_FORMAT_STR = "dd/MM/yyyy HH:mm";
    public static final String DATETIME_FORMAT_Z = "dd/MM/yyyy HH:mm z";

    /**
     * The Constant SECOND.
     */
    public static final long SECOND = 1000;

    /**
     * The Constant MINUTE.
     */
    public static final long MINUTE = SECOND * 60;

    /**
     * The Constant HOUR.
     */
    public static final long HOUR = MINUTE * 60;

    /**
     * The Constant DAY.
     */
    public static final long DAY = HOUR * 24;

    public static final String FROM_DATE = "fromDate";

    public static final String TO_DATE = "toDate";

    /**
     * Return 0 if equals, 1 if date1 > date2, -1 if date1 < date2
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int compareDateWithoutTime(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MILLISECOND, 0);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        cal2.set(Calendar.MINUTE, 0);
        cal2.set(Calendar.HOUR_OF_DAY, 0);
        cal2.set(Calendar.SECOND, 0);
        cal2.set(Calendar.MILLISECOND, 0);
        if (cal1.equals(cal2)) {
            return 0;
        } else if (cal1.after(cal2)) {// cal1 > cal2
            return 1;
        } else {
            return -1;
        }
    }

    public static String toDateString(Date date, String format) {
        String dateString = "";
        if (date == null)
            return dateString;
        Object[] params = new Object[]{date};
        try {
            dateString = MessageFormat.format("{0,date," + format + "}", params);
        } catch (Exception e) {
            LOGGER.error(e);
            return "";
        }
        return dateString;
    }

    public static int getNumDaysBetweenTwoDates(Date startDate, Date endDate) {
        Calendar startCal;
        Calendar endCal;
        startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        int workDays = 0;
        // Return 0 if start and end are the same
        if (startCal.getTimeInMillis() == endCal.getTimeInMillis()) {
            return 0;
        }
        if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
            startCal.setTime(endDate);
            endCal.setTime(startDate);
        }
        do {
            startCal.add(Calendar.DAY_OF_MONTH, 1);
            ++workDays;
        } while (startCal.getTimeInMillis() < endCal.getTimeInMillis());
        return workDays;
    }

    public static Date addHour(Date input, int hours) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(input);
        cal.add(Calendar.HOUR, hours);

        return cal.getTime();
    }

    public static Date addDate(Date input, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(input);
        cal.add(Calendar.DATE, days);

        return cal.getTime();
    }

    public static Date addMonth(Date input, int months) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(input);
        cal.add(Calendar.MONTH, months);

        return cal.getTime();
    }


    public static Date getDate(Date input) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(input);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    public static int getWorkingDateInMonth(int month, int year) {
        int numDate = 0;

        Calendar cal = new GregorianCalendar(year, month - 1, 1);

        do {
            int day = cal.get(Calendar.DAY_OF_WEEK);

            if (day != Calendar.SUNDAY) {
                ++numDate;
            }

            cal.add(Calendar.DAY_OF_YEAR, 1);
        } while (cal.get(Calendar.MONTH) == month - 1);

        return numDate;
    }


    public static int getAge(Date birthday) {
        Calendar cBirthDay = Calendar.getInstance();
        Calendar currentDate = Calendar.getInstance();
        cBirthDay.setTime(birthday);
        int age = currentDate.get(Calendar.YEAR) - cBirthDay.get(Calendar.YEAR);
        if (age < 0) {
            return 0;
        }
        if ((currentDate.get(Calendar.MONTH) > cBirthDay.get(Calendar.MONTH))
                || ((currentDate.get(Calendar.MONTH) == cBirthDay.get(Calendar.MONTH))
                && (currentDate.get(Calendar.DATE) > cBirthDay.get(Calendar.DATE)))) {
            age += 1;
        }
        return age;
    }

    /**
     * Format.
     *
     * @param d      the d
     * @param format the format
     * @return the string
     */
    public static String format(Date d, String format) {
        if (d == null) {
            return " ";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(d);
    }


    /**
     * Format.
     *
     * @param d the d
     * @return the string
     */
    public static String format(Date d) {
        return format(d, DATE_FORMAT_NOW);
    }


    public static Date format(String str, String format) {
        DateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            LOGGER.error(e);
            return null;
        }
    }


    /**
     * Gets the hour.
     *
     * @param date the date
     * @return the hour
     */
    public static int getHour(Date date) {
        String hour = null;
        DateFormat f = new SimpleDateFormat("HH");
        try {
            hour = f.format(date);
            return Integer.parseInt(hour);
        } catch (Exception e) {
            LOGGER.error(e);
            return -1;
        }
    }

    /**
     * Gets the minute.
     *
     * @param date the date
     * @return the minute
     */
    public static int getMinute(Date date) {
        String minute = null;
        DateFormat f = new SimpleDateFormat("mm");
        try {
            minute = f.format(date);
            return Integer.parseInt(minute);
        } catch (Exception e) {
            LOGGER.error(e);
            return -1;
        }
    }

    /**
     * Gets the aMPM.
     *
     * @param date the date
     * @return the aMPM
     */
    public static String getAMPM(Date date) {
        DateFormat f = new SimpleDateFormat("a");
        try {
            return f.format(date).toUpperCase();
        } catch (Exception e) {
            LOGGER.error(e);
            return "";
        }
    }

    /**
     * Gets the month.
     *
     * @param date the date
     * @return the month
     */
    public static int getMonth(Date date) {
        String month = null;
        DateFormat f = new SimpleDateFormat("MM");
        try {
            month = f.format(date);
            return Integer.parseInt(month);
        } catch (Exception e) {
            LOGGER.error(e);
            return -1;
        }
    }

    /**
     * Gets the year.
     *
     * @param date the date
     * @return the year
     */
    public static int getYear(Date date) {
        String year = null;
        DateFormat f = new SimpleDateFormat("yyyy");
        try {
            year = f.format(date);
            return Integer.parseInt(year);
        } catch (Exception e) {
            LOGGER.error(e);
            return -1;
        }
    }

    /**
     * Gets the day.
     *
     * @param date the date
     * @return the day
     */
    /*
     * public static int getDay(Date date) { String day = null; DateFormat f = new
     * SimpleDateFormat("dd"); try { day = f.format(date); return
     * Integer.parseInt(day); } catch (Exception e) { return -1; } }
     */
    public static int getDay(Date date) {
        /*
         * Sunday Day of Week 1 Monday Day of Week 2 Tuesday Day of Week 3 Wednesday Day
         * of Week 4 Thrusday Day of Week 5 Friday Day of Week 6 Saturday Day of Week 7
         */

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * Compare to.
     *
     * @param date1 the date1
     * @param date2 the date2
     * @return the int
     */
    public static int compareTo(Date date1, Date date2) {
        return compareTo(date1, date2, false);
    }

    /**
     * Compare to.
     *
     * @param date1              the date1
     * @param date2              the date2
     * @param ignoreMilliseconds the ignore milliseconds
     * @return the int
     */
    public static int compareTo(Date date1, Date date2, boolean ignoreMilliseconds) {

        if ((date1 != null) && (date2 == null)) {
            return -1;
        } else if ((date1 == null) && (date2 != null)) {
            return 1;
        } else if (date1 == null) {
            return 0;
        }

        long time1 = date1.getTime();
        long time2 = date2.getTime();

        if (ignoreMilliseconds) {
            time1 = time1 / SECOND;
            time2 = time2 / SECOND;
        }

        if (time1 == time2) {
            return 0;
        } else if (time1 < time2) {
            return -1;
        } else {
            return 1;
        }
    }

    /**
     * Compare two date.
     *
     * @param date1 the date1
     * @param date2 the date2
     * @return the int
     */
    public static int compareTwoDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MILLISECOND, 0);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        cal2.set(Calendar.MINUTE, 0);
        cal2.set(Calendar.HOUR_OF_DAY, 0);
        cal2.set(Calendar.SECOND, 0);
        cal2.set(Calendar.MILLISECOND, 0);
        if (cal1.equals(cal2)) {
            return 0;
        } else if (cal1.after(cal2)) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * Equals.
     *
     * @param date1 the date1
     * @param date2 the date2
     * @return true, if successful
     */
    public static boolean equals(Date date1, Date date2) {
        if (compareTo(date1, date2) == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Equals.
     *
     * @param date1              the date1
     * @param date2              the date2
     * @param ignoreMilliseconds the ignore milliseconds
     * @return true, if successful
     */
    public static boolean equals(Date date1, Date date2, boolean ignoreMilliseconds) {

        if (!ignoreMilliseconds) {
            return equals(date1, date2);
        }

        long time1 = 0;

        if (date1 != null) {
            time1 = date1.getTime() / SECOND;
        }

        long time2 = 0;

        if (date2 != null) {
            time2 = date2.getTime() / SECOND;
        }

        if (time1 == time2) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets the minutes between.
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the minutes between
     */
    public static long getMinutesBetween(Date startDate, Date endDate) {

        TimeZone timeZone = TimeZone.getTimeZone("GMT");
        int offset = timeZone.getRawOffset();

        Calendar startCal = new GregorianCalendar(timeZone);
        startCal.setTime(startDate);
        startCal.add(Calendar.MILLISECOND, offset);

        Calendar endCal = new GregorianCalendar(timeZone);
        endCal.setTime(endDate);
        endCal.add(Calendar.MILLISECOND, offset);

        long milis1 = startCal.getTimeInMillis();
        long milis2 = endCal.getTimeInMillis();

        long diff = milis2 - milis1;
        long minutesBetween = diff / (60 * 1000);
        return minutesBetween;
    }

    public static String getToSearchDate(String strToDate) {

        // String dt = "2008-01-01"; // Start date
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_STR);
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(strToDate));
        } catch (ParseException e) {
            LOGGER.error(e);
        }
        c.add(Calendar.DATE, 1); // number of days to add
        String output = sdf.format(c.getTime()); // dt is now the new date
        return output;

    }

    public static String getToSearchMinutes(String strToDate, String format) {
        //String dt = "2008-01-01";  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(strToDate));
        } catch (ParseException e) {
            LOGGER.error(e);
        }
        c.add(Calendar.MINUTE, 1);  // number of days to add
        String output = sdf.format(c.getTime());  // dt is now the new date
        return output;
    }

    /**
     * Gets the days between.
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the days between
     */
    public static int getDaysBetween(Date startDate, Date endDate) {

        TimeZone timeZone = TimeZone.getTimeZone("GMT");

        int offset = timeZone.getRawOffset();

        Calendar startCal = new GregorianCalendar(timeZone);

        startCal.setTime(startDate);
        startCal.add(Calendar.MILLISECOND, offset);

        Calendar endCal = new GregorianCalendar(timeZone);

        endCal.setTime(endDate);
        endCal.add(Calendar.MILLISECOND, offset);

        int daysBetween = 0;

        while (beforeByDay(startCal.getTime(), endCal.getTime())) {
            startCal.add(Calendar.DAY_OF_MONTH, 1);

            daysBetween++;
        }

        return daysBetween;
    }

    /**
     * Before by day.
     *
     * @param date1 the date1
     * @param date2 the date2
     * @return true, if successful
     */
    public static boolean beforeByDay(Date date1, Date date2) {
        long millis1 = getTimeInMillis(date1);
        long millis2 = getTimeInMillis(date2);

        if (millis1 < millis2) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * _get time in millis.
     *
     * @param date the date
     * @return the long
     */
    public static long getTimeInMillis(Date date) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE);
        int hour = 0;
        int minute = 0;
        int second = 0;

        cal.set(year, month, day, hour, minute, second);

        long millis = cal.getTimeInMillis() / DAY;

        return millis;
    }

    /**
     * @param currentDate
     * @param lastCheckInTime
     * @return
     * @author LuanDV
     * @since 04/04/2011 - Created.
     */
    public static boolean isNewWeek(Date currentDate, Date lastCheckInTime) {
        Date firstDayOfWeek = getFirstDayOfWeek(currentDate);
        if (compareTwoDate(lastCheckInTime, firstDayOfWeek) == -1) {
            return true;
        }
        return false;
    }

    public static Date getFirstDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dow = cal.get(Calendar.DAY_OF_WEEK);
        // System.out.println(dow);
        cal.add(Calendar.DAY_OF_YEAR, (dow * -1) + 2);
        return cal.getTime();
    }

    public static Date getFirstDayNextWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dow = cal.get(Calendar.DAY_OF_WEEK);
        // System.out.println(dow);
        cal.add(Calendar.DAY_OF_YEAR, (dow * -1) + 9);
        return cal.getTime();
    }

    public static Date getNextDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_WEEK, 1);
        Date nextDay = cal.getTime();
        return nextDay;
    }

    public static Date getPreviousDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_WEEK, -1);
        Date nextDay = cal.getTime();
        return nextDay;
    }

    public static Date getNextWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, 7);
        Date nextWeek = cal.getTime();
        return nextWeek;
    }

    public static Date getNextMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 1);
        Date nextMonth = cal.getTime();
        return nextMonth;
    }

    public static Date getFirstDayOfNextMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DATE, 1);
        Date nextMonth = cal.getTime();
        return nextMonth;
    }

    public static Date getNextYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, 1);
        Date nextYear = cal.getTime();
        return nextYear;
    }

    public static Date addDay(Date date, Integer numDay) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, numDay);
        Date nextTime = cal.getTime();
        return nextTime;
    }

    public static List<Date> getListFirstDayOfWeek(Date startDate, Date endDate) {
        Date dayOfWeek = getFirstDayOfWeek(startDate);
        List<Date> ListFirstDayOfWeekWeek = new ArrayList<Date>();
        Date nextWeek = dayOfWeek;
        do {
            ListFirstDayOfWeekWeek.add(nextWeek);
            nextWeek = getNextWeek(nextWeek);
        } while (compareTwoDate(nextWeek, endDate) < 0);

        return ListFirstDayOfWeekWeek;
    }

    public static String toMonthYearString(Date date) {
        String dateStr = "";
        try {
            dateStr = new SimpleDateFormat("MM/yyyy").format(date);
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return dateStr;
    }

    /**
     * Lay tuan trong nam cua ngay
     *
     * @param date
     * @return
     * @author thuattq
     */
    public static Integer getWeekOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    public static Date parseDateWithoutTime(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Long getHouseDiff(Date d1, Date d2) {
        // in milliseconds
        Long diff = d2.getTime() - d1.getTime();

        // Long diffSeconds = diff / 1000 % 60;
        // Long diffMinutes = diff / (60 * 1000) % 60;
        Long diffHours = diff / (60 * 60 * 1000) % 24;
        // Long diffDays = diff / (24 * 60 * 60 * 1000);

        /*
         * System.out.print(diffDays + " days, "); System.out.print(diffHours +
         * " hours, "); System.out.print(diffMinutes + " minutes, ");
         * System.out.print(diffSeconds + " seconds.");
         */
        return diffHours;
    }

    /*
     * @author:anhhpt
     *
     * @description: neu d1 -d2 >= 24 hourse return true
     */
    public static Boolean isMore24House(Date d1, Date d2) {

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(d1);
        cal1.add(Calendar.HOUR_OF_DAY, 24);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(d2);

        if (cal1.before(cal2)) {
            return true;
        }

        return false;
    }

    public static String getDateTimeNowString() {
        return format(new Date(), DATE_FORMAT_NOW);
    }

    public static Date converDateFromUnixTime(long unixTime) {
        try {
            Date date = new Date(unixTime * 1000L);
            return date;
        } catch (Exception e) {
            LOGGER.error(e);
            return null;
        }
    }

    public static Date addMinute(Date input, int minutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(input);
        cal.add(Calendar.MINUTE, minutes);
        return cal.getTime();
    }

    public static Date toMidNight(Date input) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(input);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date parseStringToDateWithNullResult(String str, String format) {
        DateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            LOGGER.error(e);
            return null;
        }
    }


    public static String convertDateToString(Date date, String formatPattern) {
        if (date == null) {
            return null;
        } else {
            formatPattern = formatPattern == null ? "dd/MM/yyyy" : formatPattern;
            SimpleDateFormat dateFormat = new SimpleDateFormat(formatPattern);
            return dateFormat.format(date);
        }
    }

    /**
     * @param date
     * @return
     * @throws Exception
     */
    public static Date convertStringToDate(String date) throws Exception {
        if (date == null || date.trim().isEmpty()) {
            return null;
        } else {
            String pattern = "dd/MM/yyyy";
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            dateFormat.setLenient(false);
            try {
                return dateFormat.parse(date);
            } catch (ParseException ex) {
                LOGGER.error(ex);
                return null;
            }
        }
    }

}
