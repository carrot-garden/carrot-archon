package com.barchart.trader.base.provider;

import com.barchart.trader.base.api.TradeListener;
import com.barchart.trader.base.api.enums.TradeField.Session;
import com.carrotgarden.util.values.api.Value;

class RegListener<V extends Value<V>> {

	final Session<V> field;

	final TradeListener<V> listener;

	RegListener(final Session<V> field, final TradeListener<V> listener) {
		this.field = field;
		this.listener = listener;
	}

}
