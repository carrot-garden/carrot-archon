package com.carrotgarden.calendar.babypips;

import org.joda.time.DateTime;

public class DatePair {

	private final DateTime one;
	private final DateTime two;

	public DatePair(final DateTime one, final DateTime two) {
		this.one = one;
		this.two = two;
	}

	public DateTime getOne() {
		return one;
	}

	public DateTime getTwo() {
		return two;
	}

}
