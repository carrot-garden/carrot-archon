package com.barchart.trader.base.api;

import com.barchart.trader.base.api.enums.TradeEvent;
import com.barchart.trader.base.api.enums.TradeField.Session;
import com.carrotgarden.util.values.api.Value;

public interface TradeListener<V extends Value<V>> {

	Session<V> bindField();

	TradeEvent[] bindEvents();

	void onTradeEvent(TradeEvent event, V value);

}
