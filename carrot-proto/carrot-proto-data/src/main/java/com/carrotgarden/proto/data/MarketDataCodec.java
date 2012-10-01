package com.carrotgarden.proto.data;

import com.google.protobuf.ByteString;

public class MarketDataCodec {

	private MarketDataCodec() {
	}

	public static MarketMessage decode(final ByteString data) throws Exception {

		return MarketMessage.newBuilder().mergeFrom(data).build();

	}

	public static ByteString encode(final MarketMessage message) {

		if (message == null) {
			return ByteString.EMPTY;
		}

		return message.toByteString();

	}

}
