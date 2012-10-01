package com.barchart.trader.vendor.dukascopy;

import quickfix.Application;
import quickfix.SessionSettings;

import com.carrotgarden.trader.util.quickfix.FixHost;

class DukasClientHost extends FixHost {

	protected DukasClientHost(final SessionSettings settings,
			final Application application) {
		super(settings, application, true);
	}

}
