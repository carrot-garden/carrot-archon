package com.carrotgarden.proto.data.cfn;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.carrotgarden.proto.data.siac.CFN;
import com.carrotgarden.proto.data.siac.CFN.Body;
import com.carrotgarden.proto.data.siac.CFN.Body.Type;
import com.carrotgarden.proto.data.siac.CFN.BodyFutureBookTop;
import com.carrotgarden.proto.data.siac.CFN.ExpirationMonth;
import com.carrotgarden.proto.data.siac.CFN.ExpirationYear;
import com.carrotgarden.proto.data.siac.CFN.Head;
import com.carrotgarden.proto.data.siac.CFN.InstFuture;
import com.carrotgarden.proto.data.siac.CFN.MessageCategory;
import com.carrotgarden.proto.data.siac.CFN.MessageType;

public class MainNEW {

	final static Logger log = LoggerFactory.getLogger(MainNEW.class);

	public static void main(final String[] args) throws Exception {

		final CFN.Packet packet = new CFN.Packet();

		final List<CFN.Message> messageList = new ArrayList<CFN.Message>();

		{

			final Head head = new Head();
			head.category(MessageCategory.TRADE_F);
			head.type(MessageType.REGULAR);

			final BodyFutureBookTop option = new CFN.BodyFutureBookTop();
			final InstFuture inst = new InstFuture();
			inst.symbol("MSFT");
			inst.month(ExpirationMonth.AUG);
			inst.year(ExpirationYear.YEAR_2);
			option.instrument(inst);

			final Body body = new Body();
			body.type(Type.F_BT_3);
			body.value(option);

			final CFN.Message message = new CFN.Message();
			message.body(body);
			message.head(head);
			messageList.add(message);
		}

		{
			final Head head = new Head();
			head.category(MessageCategory.TRADE_O);
			head.type(MessageType.REGULAR);

			final Body body = new Body();
			final BodyFutureBookTop option = new CFN.BodyFutureBookTop();
			body.type(Type.F_BT_4);
			body.value(option);

			final CFN.Message message = new CFN.Message();
			message.body(body);
			message.head(head);
			messageList.add(message);
		}

		packet.messageList(messageList);

		log.debug("packet : {}", packet);

	}
}
