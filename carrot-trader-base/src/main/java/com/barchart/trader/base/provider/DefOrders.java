package com.barchart.trader.base.provider;

import com.barchart.trader.base.api.values.TradeOrder;
import com.barchart.trader.base.api.values.TradeOrderList;
import com.carrotgarden.util.values.provider.ValueFreezer;

class DefOrders extends ValueFreezer<TradeOrderList> implements TradeOrderList {

	protected final TradeOrder[] entries;

	DefOrders(final TradeOrder[] entries) {
		assert entries != null;
		this.entries = entries;
	}

	@Override
	public TradeOrder[] entries() {
		return entries.clone();
	}

}
