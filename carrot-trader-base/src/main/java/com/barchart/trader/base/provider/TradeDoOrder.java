package com.barchart.trader.base.provider;

import com.barchart.trader.base.api.enums.TradeField;
import com.barchart.trader.base.api.values.TradeOrderBuilder;
import com.carrotgarden.util.values.api.Value;

interface TradeDoOrder extends TradeOrderBuilder {

	<V extends Value<V>> void set(TradeField.Order<V> field, V value);

}
