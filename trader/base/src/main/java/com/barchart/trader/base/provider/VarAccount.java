package com.barchart.trader.base.provider;

import com.barchart.trader.base.api.enums.TradeField.Account;
import com.barchart.trader.base.api.values.TradeAccount;

class VarAccount extends VarValueArray<TradeAccount> implements TradeDoAccount {

	protected VarAccount() {
		super(Account.size());
	}

	@Override
	public synchronized <V extends Value<V>> V get(final Account<V> field) {
		return unsafeGet(field);
	}

	@Override
	public synchronized <V extends Value<V>> void set(final Account<V> field,
			final V value) {
		unsafeSet(field, value);
	}

}
