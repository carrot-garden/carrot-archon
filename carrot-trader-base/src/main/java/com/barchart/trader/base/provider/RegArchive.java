package com.barchart.trader.base.provider;

import java.util.EnumMap;

import com.barchart.trader.base.api.TradeListener;
import com.barchart.trader.base.api.TradeSession;
import com.barchart.trader.base.api.enums.TradeEvent;
import com.barchart.trader.base.api.enums.TradeField.Session;
import com.carrotgarden.util.values.api.Value;

class RegArchive {

	final EnumMap<TradeEvent, RegList> regMap = //
	new EnumMap<TradeEvent, RegList>(TradeEvent.class);

	synchronized <V extends Value<V>> void register(
			final TradeListener<V> listener) {

		// TODO enforce @UsedOnce
		final Session<V> field = listener.bindField();

		final TradeEvent[] events = listener.bindEvents();

		for (final TradeEvent event : events) {
			RegList list = regMap.get(event);
			if (list == null) {
				list = new RegList();
				regMap.put(event, list);
			}
			list.addIfAbsent(listener);
		}

	}

	synchronized <V extends Value<V>> void unregister(
			final TradeListener<V> listener) {

		for (final TradeEvent event : TradeEvent.values()) {
			final RegList list = regMap.get(event);
			if (list == null) {
				continue;
			}
			list.remove(listener);
		}

	}

	synchronized void fireEvent(final TradeSession session,
			final TradeEvent event) {

		final RegList list = regMap.get(event);

		if (list == null) {
			return;
		}

		for (final TradeListener<?> listener : list) {

			fireEvent(session, listener, event);

		}

	}

	private <V extends Value<V>> void fireEvent(final TradeSession session,
			final TradeListener<V> listener, final TradeEvent event) {

		final Session<V> field = listener.bindField();

		final V value = session.get(field);

		listener.onTradeEvent(event, value);

	}

}
