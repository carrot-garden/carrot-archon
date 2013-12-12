package com.barchart.trader.vendor.cqg;

import quickfix.Application;
import quickfix.SessionSettings;

import com.carrotgarden.trader.util.quickfix.FixBuilder;
import com.carrotgarden.trader.util.quickfix.FixSessionSettingsBuilder;

public class CqgBuilder {

	static CqgClientHost newClient(final CqgUserConfBuilder confBuilder)
			throws Exception {

		final FixSessionSettingsBuilder builder = new FixSessionSettingsBuilder();

		builder.senderCompID = confBuilder.senderCompID;
		builder.senderSubID = confBuilder.senderSubID;

		builder.connectHost = confBuilder.connectHost;
		builder.connectPort = confBuilder.connectPort;

		builder.senderPassword = confBuilder.senderPassword;

		builder.beginString = "FIX.4.2";
		builder.targetCompID = "CQG_Gateway";
		builder.dataDictionary = "CQG_FIX_4.2.xml";
		builder.connectType = "initiator";

		final SessionSettings settings = FixBuilder.newSettings(builder);

		final Application application = new CqgClientApp(settings);

		final CqgClientHost client = new CqgClientHost(settings, application);

		return client;

	}

}
