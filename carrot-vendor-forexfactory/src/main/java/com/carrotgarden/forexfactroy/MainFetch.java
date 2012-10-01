package com.carrotgarden.forexfactroy;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.util.Cookie;

public class MainFetch {

	static final Logger logger = LoggerFactory.getLogger(MainFetch.class);

	static final ObjectMapper mapper = new ObjectMapper();
	static {
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
	}

	public static void main(final String[] args) throws Exception {

		logger.debug("init");

		final WebClient client = new WebClient(BrowserVersion.FIREFOX_3_6);

		final CookieManager manager = new CookieManager();
		manager.addCookie(new Cookie("forexfactory.com", "ffverifytimes", "1"));
		manager.addCookie(new Cookie("forexfactory.com", "fftimeformat", "1"));
		manager.addCookie(new Cookie("forexfactory.com", "ffstartofweek", "1"));
		manager.addCookie(new Cookie("forexfactory.com", "ffdstonoff", "0"));
		manager.addCookie(new Cookie("forexfactory.com", "fftimezoneoffset",
				"0"));

		client.setCookieManager(manager);

		client.setJavaScriptEnabled(false);

		//

		final DateTime dateInit = new DateTime("2007-01-01T00:00:00.000Z")
				.withZone(DateTimeZone.UTC);
		final DateTime dateDone = new DateTime("2012-03-28T00:00:00.000Z")
				.withZone(DateTimeZone.UTC);

		final int size = Days.daysBetween(dateInit, dateDone).getDays();

		for (int days = 0; days < size; days++) {

			final DateTime date = dateInit.plusDays(days).withZone(
					DateTimeZone.UTC);

			final File file = new File(targetURL(date));

			if (Util.isPresent(file)) {
				continue;
			}

			final String result = parse(client, date);

			final FileWriter writer = new FileWriter(file);

			writer.write(result);

			writer.close();

		}

		client.closeAllWindows();

		logger.debug("done");

	}

	static String sourceURL(final DateTime date) {

		final DateTime dateUTC = date.withZone(DateTimeZone.UTC);

		final int year = dateUTC.getYear();
		final int month = dateUTC.getMonthOfYear();
		final int day = dateUTC.getDayOfMonth();

		return String.format(Const.CALENDAR, year, month, day);

	}

	static String targetURL(final DateTime date) {

		final DateTime dateUTC = date.withZone(DateTimeZone.UTC);

		final int year = dateUTC.getYear();
		final int month = dateUTC.getMonthOfYear();
		final int day = dateUTC.getDayOfMonth();

		return String.format(Const.FILE, year, month, day);

	}

	static String parse(final WebClient client, final DateTime date)
			throws Exception {

		//

		final String location = sourceURL(date);

		logger.debug("location = " + location);

		final HtmlPage page = client.getPage(location);

		final HtmlTable table = page.getHtmlElementById("weekdays");

		final List<HtmlTableRow> rowList = table.getRows();

		final Map<String, Object> eventStore = new HashMap<String, Object>();

		for (int rowNum = 1; rowNum < rowList.size(); rowNum++) {

			final HtmlTableRow row = rowList.get(rowNum);

			final List<HtmlTableCell> colList = row.getCells();

			if (colList.size() < Name.INDEX_CHART) {
				logger.warn("empty");
				continue;
			}

			final String rowId = row.getAttribute("id")
					.replaceAll("[^\\d]", "").trim();

			final String firstId = colList.get(0).getAttribute("id").trim();
			final boolean hasDateColumn = !"".equals(firstId);

			final int base = hasDateColumn ? 1 : 0;

			final Map<String, String> eventEntry = new HashMap<String, String>();

			{

				eventEntry.put(Name.NAME_ID, rowId);
				// logger.info("   rowId=" + rowId);

				eventEntry.put(Name.NAME_TYPE, "calendar");

			}

			{
				final HtmlTableCell cell = colList.get(base + Name.INDEX_TIME);

				final String value = cell.asText().trim().toLowerCase();
				// logger.info("   value=" + value);

				LocalTime local = null;

				if (value.contains("day")) {
					local = new LocalTime("00:00");
				} else {
					try {
						local = new LocalTime(value);
					} catch (final Throwable e) {
						logger.error("   local=" + value, e);
						local = new LocalTime("00:00");
					}
				}
				// logger.info("   local=" + local);

				final DateTime time = date.withZone(DateTimeZone.UTC)
						.withHourOfDay(local.getHourOfDay())
						.withMinuteOfHour(local.getMinuteOfHour());
				// logger.info("   time=" + time);

				eventEntry.put(Name.NAME_TIME, time.toString());

			}

			{
				final HtmlTableCell cell = colList.get(base
						+ Name.INDEX_CURRENCY);
				eventEntry.put(Name.NAME_CURRENCY, cell.asText().trim());
			}

			{
				final HtmlTableCell cell = colList
						.get(base + Name.INDEX_IMPACT);

				final HtmlElement div = cell.getElementsByTagName("div").get(0);

				final String title = div.getAttribute("title").toLowerCase();

				if (title.contains("low")) {

					eventEntry.put(Name.NAME_IMPACT, "low");

				} else if (title.contains("high")) {

					eventEntry.put(Name.NAME_IMPACT, "high");

				} else if (title.contains("medium")) {

					eventEntry.put(Name.NAME_IMPACT, "medium");

				} else if (title.contains("non-economic")) {

					final String evenTitle = colList
							.get(base + Name.INDEX_TITLE).asText().trim()
							.toLowerCase();

					if (evenTitle.contains("holiday")) {
						eventEntry.put(Name.NAME_IMPACT, "holiday");
					} else {
						eventEntry.put(Name.NAME_IMPACT, "error");
					}

				} else {

					eventEntry.put(Name.NAME_IMPACT, "unknown");

				}

			}

			{
				final HtmlTableCell cell = colList.get(base + Name.INDEX_TITLE);
				eventEntry.put(Name.NAME_TITLE, cell.asText().trim());
			}

			{
				final HtmlTableCell cell = colList.get(base
						+ Name.INDEX_VALUE_ACTUAL);
				eventEntry.put(Name.NAME_VALUE_ACTUAL, cell.asText().trim());
			}
			{
				final HtmlTableCell cell = colList.get(base
						+ Name.INDEX_VALUE_FORECAST);
				eventEntry.put(Name.NAME_VALUE_FORECAST, cell.asText().trim());
			}
			{
				final HtmlTableCell cell = colList.get(base
						+ Name.INDEX_VALUE_PREVIOUS);
				eventEntry.put(Name.NAME_VALUE_PREVIOUS, cell.asText().trim());
			}

			eventStore.put(Const.VENDOR + "/" + rowId, eventEntry);

		}

		return mapper.writeValueAsString(eventStore);

	}

}
