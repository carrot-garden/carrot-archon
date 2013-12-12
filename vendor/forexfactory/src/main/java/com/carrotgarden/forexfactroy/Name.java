package com.carrotgarden.forexfactroy;

/**
 * http://www.forexfactory.com/calendar.php?do=geteventinfo&day=2012-3-27&c=2
 * */
public interface Name {

	int INDEX_TIME = 0;
	int INDEX_CURRENCY = 1;
	int INDEX_IMPACT = 2;
	int INDEX_TITLE = 3;
	int INDEX_DETAIL = 4;
	int INDEX_VALUE_ACTUAL = 5;
	int INDEX_VALUE_FORECAST = 6;
	int INDEX_VALUE_PREVIOUS = 7;
	int INDEX_CHART = 8;

	String NAME_TIME = "time";
	String NAME_CURRENCY = "currency";
	String NAME_IMPACT = "impact";
	String NAME_TITLE = "title";
	String NAME_DETAIL = "detail";
	String NAME_VALUE_ACTUAL = "value_actual";
	String NAME_VALUE_FORECAST = "value_forecast";
	String NAME_VALUE_PREVIOUS = "value_previous";
	String NAME_CHART = "chart";

	//

	String NAME_ID = "id";
	String NAME_TYPE = "type";

}
