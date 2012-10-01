package com.barchart.trader.base.provider;

import com.barchart.trader.base.api.TradeSession;
import com.barchart.trader.base.api.enums.TradeField;
import com.carrotgarden.util.values.api.Value;

interface TradeDoSession extends TradeSession {

	<V extends Value<V>> void set(TradeField.Session<V> field, V value);

}
