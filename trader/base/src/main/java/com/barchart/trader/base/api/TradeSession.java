package com.barchart.trader.base.api;

import com.barchart.trader.base.api.enums.TradeField;
import com.barchart.trader.base.api.values.TradeOrder;
import com.barchart.trader.base.api.values.TradeOrderBuilder;
import com.barchart.util.values.api.Value;

public interface TradeSession {

	void login();

	void logout();

	void shutdown();

	//

	TradeOrderBuilder newOrderBuilder();

	TradeOrder place(TradeOrderBuilder orderBuilder)
			throws IllegalArgumentException;

	void cancel(TradeOrder order) throws IllegalStateException;

	//

	<V extends Value<V>> V get(TradeField.Session<V> field);

	//

	<V extends Value<V>> void register(TradeListener<V> listener);

	<V extends Value<V>> void unregister(TradeListener<V> listener);

}
