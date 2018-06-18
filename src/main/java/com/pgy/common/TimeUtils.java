package com.pgy.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Preconditions;

/**
 * The time utils.
 *
 * @author Felix
 */
public class TimeUtils {
    public static final long MILLISECONDS_PER_SECOND = 1000;
    public static final long MICROSECONDS_PER_SECOND = 1000 * MILLISECONDS_PER_SECOND;
    public static final long MILLISECONDS_PER_MINUTE = 60 * MILLISECONDS_PER_SECOND;
    public static final long MILLISECONDS_PER_HOUR = 60 * MILLISECONDS_PER_MINUTE;
    public static final long MILLISECONDS_PER_DAY = 24 * MILLISECONDS_PER_HOUR;
    public static final long SECONDS_PER_DAY = 3600 * 24;

    // Date format
    public static final FastDateFormat MM_DD_FORMAT = FastDateFormat.getInstance("MMdd");
    public static final FastDateFormat YYYY_MM_DD_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd");
    public static final FastDateFormat FULL_FORMAT = FastDateFormat.getInstance("yyyyMMddHHmmss");
    public static final FastDateFormat DEFAULT_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
    public static final FastDateFormat YYYY_MM_DD_HH_MM_FORMAT = FastDateFormat.getInstance("yyyyMMddHHmm");

    private static final Log log = LogFactory.getLog(TimeUtils.class);

    public static Date formatDate(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(dateFormat.format(date));
        } catch (ParseException e) {
            log.error(e);
            return date;
        }
    }

    public static String formatDate(long timeMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeMillis));
    }

    public static Date parseDate(String date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            log.error(e);
            return null;
        }
    }

    public static int getDayOfYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    public static Date getCurrentTime() {
        return new Date();
    }

    public static long getCurrentTimeInSeconds() {
        return getTimeInSeconds(getCurrentTime());
    }

    public static String formatTime(Date time) {
        return FULL_FORMAT.format(time);
    }

    public static long getTimeInSeconds(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return calendar.getTimeInMillis() / MILLISECONDS_PER_SECOND;
    }

    /**
     * Returns the time interval (in seconds) between two given time.
     */
    public static long timeIntervalInSecond(Date date1, Date date2) {
        return (date1.getTime() - date2.getTime()) / MILLISECONDS_PER_SECOND;
    }

    /**
     * Returns the time interval (in minutes) between two given time.
     */
    public static long timeIntervalInMinute(Date date1, Date date2) {
        return (date1.getTime() - date2.getTime()) / MILLISECONDS_PER_MINUTE;
    }

    /**
     * Returns the time interval (in days) between two given time.
     */
    public static long timeIntervalInDay(Date date1, Date date2) {
        return (date1.getTime() - date2.getTime()) / MILLISECONDS_PER_DAY;
    }

    public static Date getDateFromMilliseconds(String input) throws NumberFormatException {
        long timeMilliseconds = Long.parseLong(input);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMilliseconds);
        calendar = DateUtils.truncate(calendar, Calendar.DATE);
        return calendar.getTime();
    }

    public static boolean isTimeInRange(Date current, Date start, Date end) {
        Preconditions.checkNotNull(start);
        Preconditions.checkNotNull(current);
        Preconditions.checkNotNull(end);
        Preconditions.checkArgument(start.before(end) || start.equals(end));

        return start.before(current) && current.before(end);
    }

    public static Date getTimeBeforeDays(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -days);
        return calendar.getTime();
    }

    public static Date getTimeBeforeHours(int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, -hours);
        return calendar.getTime();
    }

}

