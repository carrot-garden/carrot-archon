package com.barchart.trader.base.provider;

import com.barchart.trader.base.api.enums.TradeField;
import com.barchart.trader.base.api.values.TradeReport;
import com.carrotgarden.util.values.api.Value;

interface TradeDoReport extends TradeReport {

	<V extends Value<V>> void set(TradeField.Report<V> field, V value);

}
