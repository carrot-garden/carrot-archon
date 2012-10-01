package com.carrotgarden.calendar.babypips;

import java.io.File;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.http.client.methods.HttpGet;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;

public class Util {

	public static boolean isPresent(final File file) {

		return file.exists();

	}

	static HttpGet makeGet(final String textURL) {

		final HttpGet getter = new HttpGet(textURL);

		getter.setHeader("Accept", "application/json, text/javascript, */*");

		getter.setHeader("Referer",
				"http://www.babypips.com/tools/forex-calendar/");

		getter.setHeader("X-Requested-With", "XMLHttpRequest");

		getter.setHeader(
				"User-Agent",
				"Mozilla/5.0 (X11; Linux i686) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.83 Safari/535.11");

		getter.setHeader("Content-Type", "application/x-www-form-urlencoded");

		return getter;

	}

	static String sourceDaysURL(final DateTime date) throws Exception {

		final DateTime rangeStart = date.withZone(DateTimeZone.UTC)
				.withMillisOfDay(0);

		final DateTime rangeFinish = rangeStart.plusDays(1).minusSeconds(1);

		final String textStart = URLEncoder.encode(
				Const.FORMAT.print(rangeStart), "UTF-8");

		final String textFinish = URLEncoder.encode(
				Const.FORMAT.print(rangeFinish), "UTF-8");

		final String textURL = String.format(Const.EVENTS, textStart,
				textFinish);

		return textURL;

	}

	static String sourceWeeksURL(final DateTime date) throws Exception {

		final DatePair pair = getWeekPair(date);

		final String textStart = URLEncoder.encode(
				Const.FORMAT.print(pair.getOne()), "UTF-8");

		final String textFinish = URLEncoder.encode(
				Const.FORMAT.print(pair.getTwo()), "UTF-8");

		final String textURL = String.format(Const.EVENTS, textStart,
				textFinish);

		return textURL;

	}

	static String targetDaysURL(final DateTime date) {

		final DateTime dateUTC = date.withZone(DateTimeZone.UTC)
				.withMillisOfDay(0);

		final int year = dateUTC.getYear();
		final int month = dateUTC.getMonthOfYear();
		final int day = dateUTC.getDayOfMonth();

		return String.format(Const.FILE_DAYS, year, month, day);

	}

	static String targetWeeksURL(final DateTime date) {

		final DateTime dateUTC = date.withZone(DateTimeZone.UTC)
				.withMillisOfDay(0);

		final String stamp = getYearWeek(dateUTC);

		return String.format(Const.FILE_WEEKS, stamp);

	}

	private static final ThreadLocal<Calendar> THREADS_CALENDAR = new ThreadLocal<Calendar>() {
		@Override
		protected Calendar initialValue() {
			final Calendar cal = Calendar.getInstance(Locale.US);
			cal.setTimeZone(TimeZone.getTimeZone("UTC"));
			return cal;
		}
	};

	static String getYearWeek(final DateTime date) {

		final Calendar cal = THREADS_CALENDAR.get();

		cal.setTimeInMillis(date.getMillis());

		final int year = cal.getWeekYear();
		final int week = cal.get(Calendar.WEEK_OF_YEAR);

		return String.format("%04dA%02d", year, week);

	}

	static DatePair getWeekPair(final DateTime date) {

		final DateTime one = date.withZone(DateTimeZone.UTC).withMillisOfDay(0)
				.withDayOfWeek(DateTimeConstants.SUNDAY);

		final DateTime two = one.plusDays(7).minusSeconds(1);

		final DatePair pair = new DatePair(one, two);

		return pair;

	}

	static void ensureFolder(final File file) {

		final File folder = file.getParentFile();

		if (folder.exists()) {
			return;
		}

		folder.mkdirs();

	}

}
