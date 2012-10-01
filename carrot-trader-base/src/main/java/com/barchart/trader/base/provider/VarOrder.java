package com.barchart.trader.base.provider;

import static com.barchart.trader.base.api.enums.TradeField.Order.*;

import com.barchart.trader.base.api.enums.TradeField.Order;
import com.barchart.trader.base.api.values.TradeOrder;
import com.barchart.trader.base.api.values.TradeOrderBuilder;

class VarOrder extends VarValueArray<TradeOrder> implements TradeDoOrder {

	protected VarOrder() {
		super(Order.size());
	}

	// TODO add more
	protected static final Order<?>[] USER_INIT = new Order<?>[] { INSTRUMENT,
			PRICE_LIMIT, SIZE };

	@Override
	public <V extends Value<V>> TradeOrderBuilder init(final Order<V> field,
			final V value) throws IllegalArgumentException {

		if (field.isIn(USER_INIT)) {
			set(field, value);
			return this;
		}

		throw new IllegalArgumentException("not a user-init field : " + field);

	}

	@Override
	public synchronized <V extends Value<V>> V get(final Order<V> field) {
		return unsafeGet(field);
	}

	@Override
	public synchronized <V extends Value<V>> void set(final Order<V> field,
			final V value) {
		unsafeSet(field, value);
	}

}
