package com.barchart.trader.base.api.values;

import com.barchart.trader.base.api.enums.TradeField;
import com.carrotgarden.util.values.api.Value;

public interface TradeAccount extends Value<TradeAccount> {

	<V extends Value<V>> V get(TradeField.Account<V> field);

}
