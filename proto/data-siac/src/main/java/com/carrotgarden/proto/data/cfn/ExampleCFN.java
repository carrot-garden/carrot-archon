package com.carrotgarden.proto.data.cfn;

import java.nio.ByteBuffer;
import java.util.List;

import com.carrotgarden.proto.data.siac.CFN;
import com.carrotgarden.proto.data.siac.CFN.Body;
import com.carrotgarden.proto.data.siac.CFN.Body.Type;
import com.carrotgarden.proto.data.siac.CFN.Head;
import com.carrotgarden.proto.data.siac.CFN.Message;
import com.carrotgarden.proto.data.siac.CFN.Node;
import com.carrotgarden.proto.data.siac.CFN.Packet;

public class ExampleCFN {

	public static void decode(final ByteBuffer buffer,
			final CFN.Body.Visitor<String> visitor) throws Exception {

		final String context = "";

		final Packet packet = CFN.Packet.from(buffer, null);

		final List<Message> messageList = packet.messageList();

		for (final Message message : messageList) {

			final Head head = message.head();

			final Body body = message.body();

			final Type type = body.type();

			final Node option = body.value();

			type.apply(visitor, option, context);

		}

	}

}
