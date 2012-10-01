package com.barchart.trader.vendor.cqg;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestCqgClientHost {

	static final Logger log = LoggerFactory.getLogger(TestCqgClientHost.class);

	private CqgUserConfBuilder builder;

	@Before
	public void setUp() throws Exception {

		builder = new CqgUserConfBuilder();

		builder.senderCompID = "mharaburdatestfix";
		builder.senderSubID = "Test FIX";
		builder.senderPassword = "pass";

		builder.connectHost = "demo.cqgtrader.com";
		builder.connectPort = "6912";

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test1() {

		try {

			final CqgClientHost client = CqgBuilder.newClient(builder);

			log.info("ABOUT TO LOGIN");
			client.activate();

			log.info("ABOUT TO SLEEP");
			Thread.sleep(1000 * 1000);

			log.info("ABOUT TO LOGOUT");
			client.deactivate();

		} catch (final Exception e) {
			e.printStackTrace();
		}

		assertTrue(true);

	}

}
