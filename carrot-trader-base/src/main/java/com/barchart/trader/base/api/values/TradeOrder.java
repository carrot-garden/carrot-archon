package com.barchart.trader.base.api.values;

import com.barchart.trader.base.api.enums.TradeField;
import com.carrotgarden.util.values.api.Value;

public interface TradeOrder extends Value<TradeOrder> {

	<V extends Value<V>> V get(TradeField.Order<V> field);

}
