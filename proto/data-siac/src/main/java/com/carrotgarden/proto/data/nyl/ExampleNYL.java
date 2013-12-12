package com.carrotgarden.proto.data.nyl;

import java.nio.ByteBuffer;

import com.carrotgarden.proto.data.siac.NYL;
import com.carrotgarden.proto.data.siac.NYL.Packet;

public class ExampleNYL {

	public static void decode(final ByteBuffer buffer) throws Exception {

		final Packet packet = NYL.Packet.from(buffer, null);

	}

}
