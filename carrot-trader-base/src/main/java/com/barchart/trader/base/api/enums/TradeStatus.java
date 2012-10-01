package com.barchart.trader.base.api.enums;

import com.carrotgarden.util.values.api.Value;

public enum TradeStatus implements Value<TradeStatus> {

	NULL_STATUS, //

	LOGON, //

	LOGOFF, //

	;

	@Override
	public TradeStatus freeze() {
		return this;
	}

	@Override
	public boolean isFrozen() {
		return true;
	}

	@Override
	public boolean isNull() {
		return this == NULL_STATUS;
	}

}
