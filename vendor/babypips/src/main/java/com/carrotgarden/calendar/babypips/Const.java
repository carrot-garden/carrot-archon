package com.carrotgarden.calendar.babypips;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public interface Const {

	/**
	 * "http://www.babypips.com/tools/forex-calendar/events.json?range_start=2011-11-01+00%3A00%3A00&range_end=2011-11-01+23%3A59%3A59&timezone_offset=GMT"
	 */

	String EVENTS = "http://www.babypips.com/tools/forex-calendar/events.json?range_start=%s&range_end=%s&timezone_offset=GMT";

	String STAMP = "yyyy-MM-dd HH:mm:ss";

	DateTimeFormatter FORMAT = DateTimeFormat.forPattern(STAMP);

	String FOLDER_DAYS = "/work/download/calendar/babypips/days";

	String FOLDER_WEEKS = "/work/download/calendar/babypips/weeks";

	String DATE = "%04d-%02d-%02d";

	String FILE_DAYS = FOLDER_DAYS + "/" + DATE + ".json";

	String FILE_WEEKS = FOLDER_WEEKS + "/" + "%s" + ".json";

}
