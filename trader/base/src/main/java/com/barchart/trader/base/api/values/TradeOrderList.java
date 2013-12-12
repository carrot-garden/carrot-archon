package com.barchart.trader.base.api.values;

import com.carrotgarden.util.values.api.Value;

public interface TradeOrderList extends Value<TradeOrderList> {

	TradeOrder[] entries();

}
