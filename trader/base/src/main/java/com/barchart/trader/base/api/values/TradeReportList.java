package com.barchart.trader.base.api.values;

import com.carrotgarden.util.values.api.Value;

public interface TradeReportList extends Value<TradeReportList> {

	TradeReport[] entries();

}
