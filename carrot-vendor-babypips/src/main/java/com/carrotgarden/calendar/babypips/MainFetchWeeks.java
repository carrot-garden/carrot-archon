package com.carrotgarden.calendar.babypips;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class MainFetchWeeks {

	static final Logger logger = LoggerFactory.getLogger(MainFetchWeeks.class);

	static final ObjectMapper mapper = new ObjectMapper();
	static {
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
	}

	public static void main(final String[] args) throws Exception {

		logger.debug("init");

		final ThreadSafeClientConnManager manager = new ThreadSafeClientConnManager();
		manager.setMaxTotal(50);
		manager.setDefaultMaxPerRoute(10);

		final HttpClient client = new DefaultHttpClient(manager);

		final DateTime dateStart = new DateTime("2007-01-01T00:00:00.000Z")
				.withZone(DateTimeZone.UTC);
		final DateTime dateFinish = new DateTime("2012-04-30T00:00:00.000Z")
				.withZone(DateTimeZone.UTC);

		final int size = Days.daysBetween(dateStart, dateFinish).getDays();

		for (int days = 0; days < size; days += 7) {

			final DateTime date = dateStart.plusDays(days);

			final File file = new File(Util.targetWeeksURL(date));

			if (Util.isPresent(file)) {
				continue;
			}

			Util.ensureFolder(file);

			final String textURL = Util.sourceWeeksURL(date);
			logger.debug("textURL=" + textURL);

			final HttpResponse response = client.execute(Util.makeGet(textURL));

			final ByteArrayOutputStream output = new ByteArrayOutputStream();

			response.getEntity().writeTo(output);

			output.close();

			//

			final String json = output.toString("UTF-8");

			final List<?> list = mapper.readValue(json, List.class);

			mapper.writeValue(file, list);

		}

		client.getConnectionManager().shutdown();

		logger.debug("done");

	}

}
