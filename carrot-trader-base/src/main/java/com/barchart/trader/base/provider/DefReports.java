package com.barchart.trader.base.provider;

import com.barchart.trader.base.api.values.TradeReport;
import com.barchart.trader.base.api.values.TradeReportList;
import com.carrotgarden.util.values.provider.ValueFreezer;

class DefReports extends ValueFreezer<TradeReportList> implements
		TradeReportList {

	protected final TradeReport[] entries;

	DefReports(final TradeReport[] entries) {
		assert entries != null;
		this.entries = entries;
	}

	@Override
	public TradeReport[] entries() {
		return entries.clone();
	}

}
