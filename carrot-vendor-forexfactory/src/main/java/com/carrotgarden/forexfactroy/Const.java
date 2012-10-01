package com.carrotgarden.forexfactroy;

public interface Const {

	String DATE = "%04d-%02d-%02d";

	String FOLDER = "/work/download/forexfactory";

	String FILE = FOLDER + "/" + DATE + ".json";

	String CALENDAR = "http://www.forexfactory.com/calendar.php?do=geteventinfo&day="
			+ DATE;

	String VENDOR = "forexfactory";

}
