package com.carrotgarden.proto.assm;

import com.carrotgarden.proto.data.MarketData;
import com.carrotgarden.proto.inst.Instrument;

public interface PacketVisitor<T> {

	void apply(MarketData message, T target);

	void apply(Instrument message, T target);

}
