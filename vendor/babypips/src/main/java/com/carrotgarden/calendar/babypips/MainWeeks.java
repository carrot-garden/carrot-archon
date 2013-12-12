package com.carrotgarden.calendar.babypips;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainWeeks {

	static final Logger logger = LoggerFactory.getLogger(MainWeeks.class);

	static void test(final DateTime date) {

		final DateTime dateStart = date.withZone(DateTimeZone.UTC);

		final DateTime dateFinish = dateStart.plusDays(20);

		final String STAMP = "xxxx'W'ww";

		final DateTimeFormatter FORMAT = DateTimeFormat.forPattern(STAMP);

		final int size = Days.daysBetween(dateStart, dateFinish).getDays();

		for (int days = 0; days < size; days++) {

			final DateTime test = dateStart.plusDays(days);

			logger.debug("" + FORMAT.print(test) + " " + Util.getYearWeek(test)
					+ " " + test + " DOW=" + test.getDayOfWeek());

		}

	}

	public static void main(final String[] args) throws Exception {

		logger.debug("init");

		test(new DateTime("2010-12-22T00:00:00.000Z"));

		logger.debug("####");

		test(new DateTime("2011-12-22T00:00:00.000Z"));

		logger.debug("####");

		test(new DateTime("2012-12-22T00:00:00.000Z"));

		logger.debug("done");

	}

}
