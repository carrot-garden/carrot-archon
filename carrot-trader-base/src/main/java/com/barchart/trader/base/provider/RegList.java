package com.barchart.trader.base.provider;

import java.util.concurrent.CopyOnWriteArrayList;

import com.barchart.trader.base.api.TradeListener;

class RegList extends CopyOnWriteArrayList<TradeListener<?>> {

	private static final long serialVersionUID = 1L;

}
