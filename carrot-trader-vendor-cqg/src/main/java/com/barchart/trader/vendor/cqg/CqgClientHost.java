package com.barchart.trader.vendor.cqg;

import quickfix.Application;
import quickfix.SessionSettings;

import com.carrotgarden.trader.util.quickfix.FixHost;

class CqgClientHost extends FixHost {

	protected CqgClientHost(final SessionSettings settings,
			final Application application) {
		super(settings, application, true);
	}

}
