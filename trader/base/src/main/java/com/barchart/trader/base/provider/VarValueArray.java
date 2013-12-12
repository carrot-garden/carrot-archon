package com.barchart.trader.base.provider;

import com.carrotgarden.util.values.api.Value;
import com.carrotgarden.util.values.provider.ValueFreezer;

class VarValueArray<T extends Value<T>> extends ValueFreezer<T> {

	protected final Value<?>[] valueArray;

	protected VarValueArray(final int size) {

		assert size > 0;

		valueArray = new Value<?>[size];

	}

	@Override
	public T freeze() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isFrozen() {
		return false;
	}

	@Override
	public boolean isNull() {
		return false;
	}

	@SuppressWarnings("unchecked")
	protected <V extends Value<V>, F extends ParamEnum<V, F>> V unsafeGet(
			final F field) {

		assert field != null;

		return (V) valueArray[field.ordinal()];
	}

	protected <V extends Value<V>, F extends ParamEnum<V, F>> void unsafeSet(
			final F field, final V value) {

		assert field != null;
		assert value != null;

		valueArray[field.ordinal()] = value;

	}

}
