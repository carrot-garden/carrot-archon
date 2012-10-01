package com.barchart.trader.base.api.values;

import com.barchart.trader.base.api.enums.TradeField;
import com.carrotgarden.util.values.api.Value;

public interface TradeReport extends Value<TradeReport> {

	<V extends Value<V>> V get(TradeField.Report<V> field);

}
