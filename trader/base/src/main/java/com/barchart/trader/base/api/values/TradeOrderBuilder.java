package com.barchart.trader.base.api.values;

import com.barchart.trader.base.api.enums.TradeField;
import com.carrotgarden.util.values.api.Value;

public interface TradeOrderBuilder extends TradeOrder {

	<V extends Value<V>> TradeOrderBuilder //

	init(TradeField.Order<V> field, V value) //
			throws IllegalArgumentException;

}
