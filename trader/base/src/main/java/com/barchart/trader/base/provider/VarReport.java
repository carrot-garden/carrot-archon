package com.barchart.trader.base.provider;

import com.barchart.trader.base.api.enums.TradeField.Account;
import com.barchart.trader.base.api.enums.TradeField.Report;
import com.barchart.trader.base.api.values.TradeReport;
import com.carrotgarden.util.values.api.Value;

class VarReport extends VarValueArray<TradeReport> implements TradeDoReport {

	protected VarReport() {
		super(Account.size());
	}

	@Override
	public synchronized <V extends Value<V>> V get(final Report<V> field) {
		return unsafeGet(field);
	}

	@Override
	public synchronized <V extends Value<V>> void set(final Report<V> field,
			final V value) {
		unsafeSet(field, value);
	}

}
