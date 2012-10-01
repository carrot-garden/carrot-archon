package com.carrotgarden.proto.inst;

public interface InstrumentVisitor<T> {

	void apply(Forex inst, T target);

}
