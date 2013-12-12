package com.barchart.trader.base.api.values;

import com.carrotgarden.util.values.api.Value;

public interface TradeAccountList extends Value<TradeAccountList> {

	TradeAccount[] entries();

}
