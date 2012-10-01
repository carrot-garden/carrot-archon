package com.barchart.trader.base.provider;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.barchart.trader.base.api.TradeListener;
import com.barchart.trader.base.api.enums.TradeField.Order;
import com.barchart.trader.base.api.enums.TradeField.Session;
import com.barchart.trader.base.api.values.TradeOrder;
import com.barchart.trader.base.api.values.TradeOrderBuilder;
import com.carrotgarden.util.values.api.TextValue;

public class VarSession extends VarValueArray<VarSession> implements
		TradeDoSession {

	final ConcurrentMap<TextValue, TradeDoAccount> accounts = //
	new ConcurrentHashMap<TextValue, TradeDoAccount>();

	final ConcurrentMap<TextValue, TradeDoOrder> tradeOrders = //
	new ConcurrentHashMap<TextValue, TradeDoOrder>();

	final RegArchive reg = new RegArchive();

	public VarSession() {
		super(Session.size());
	}

	@Override
	public void cancel(final TradeOrder order) throws IllegalStateException {
		// TODO Auto-generated method stub
	}

	@Override
	public synchronized <V extends Value<V>> V get(final Session<V> field) {
		return unsafeGet(field);
	}

	@Override
	public synchronized <V extends Value<V>> void set(final Session<V> field,
			final V value) {
		unsafeSet(field, value);
	}

	@Override
	public void login() {
		// TODO Auto-generated method stub
	}

	@Override
	public void logout() {
		// TODO Auto-generated method stub
	}

	@Override
	public TradeOrderBuilder newOrderBuilder() {

		final VarOrder order = new VarOrder();

		return order;

	}

	@Override
	public TradeOrder place(final TradeOrderBuilder builder)
			throws IllegalArgumentException {

		final VarOrder order = new VarOrder();

		final TextValue value = null;

		order.set(Order.ID, value);

		return order;

	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
	}

	@Override
	public <V extends Value<V>> void register(final TradeListener<V> listener) {
		reg.register(listener);
	}

	@Override
	public <V extends Value<V>> void unregister(final TradeListener<V> listener) {
		reg.unregister(listener);
	}

}
