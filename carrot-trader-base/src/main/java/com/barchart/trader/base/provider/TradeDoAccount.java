package com.barchart.trader.base.provider;

import com.barchart.trader.base.api.enums.TradeField;
import com.barchart.trader.base.api.values.TradeAccount;
import com.carrotgarden.util.values.api.Value;

interface TradeDoAccount extends TradeAccount {

	<V extends Value<V>> void set(TradeField.Account<V> field, V value);

}
