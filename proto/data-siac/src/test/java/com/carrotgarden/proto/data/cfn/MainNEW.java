package com.carrotgarden.proto.data.cfn;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.carrotgarden.proto.data.siac.CFN.Body;
import com.carrotgarden.proto.data.siac.CFN.Body.Type;
import com.carrotgarden.proto.data.siac.CFN.BodyFutureBookTop;
import com.carrotgarden.proto.data.siac.CFN.ExpirationMonth;
import com.carrotgarden.proto.data.siac.CFN.ExpirationYear;
import com.carrotgarden.proto.data.siac.CFN.Head;
import com.carrotgarden.proto.data.siac.CFN.InstFuture;
import com.carrotgarden.proto.data.siac.CFN.Message;
import com.carrotgarden.proto.data.siac.CFN.MessageCategory;
import com.carrotgarden.proto.data.siac.CFN.MessageType;
import com.carrotgarden.proto.data.siac.CFN.Packet;
import com.carrotgarden.proto.data.siac.CFN.PriceExponent;
import com.carrotgarden.proto.data.siac.CFN.SessionIndicator;

public class MainNEW {

	final static Logger log = LoggerFactory.getLogger(MainNEW.class);

	public static void main(final String[] args) throws Exception {

		final Packet packet = new Packet();

		final List<Message> messageList = new ArrayList<Message>();

		{

			final Head head = new Head();
			head.categoryEnum(MessageCategory.TRADE_F);
			head.typeEnum(MessageType.REGULAR);

			final BodyFutureBookTop value = new BodyFutureBookTop();
			final InstFuture inst = new InstFuture();
			inst.symbol("MSFT");
			inst.monthEnum(ExpirationMonth.AUG);
			inst.yearEnum(ExpirationYear.YEAR_2);
			value.instrument(inst);
			value.priceExponentEnum(PriceExponent.NEG_02);
			value.sessionIdicatorEnum(SessionIndicator.NORMAL);

			final Body body = new Body();
			body.type(Type.F_BT_3);
			body.value(value);

			final Message message = new Message();
			message.body(body);
			message.head(head);
			messageList.add(message);
		}

		{
			final Head head = new Head();
			head.categoryEnum(MessageCategory.TRADE_O);
			head.typeEnum(MessageType.REGULAR);

			final Body body = new Body();
			body.type(Type.F_BT_4);
			final BodyFutureBookTop value = (BodyFutureBookTop) Type.F_BT_4
					.newInstance();
			body.value(value);
			value.priceExponentEnum(PriceExponent.NEG_03);
			value.sessionIdicatorEnum(SessionIndicator.MORINING);

			final Message message = new Message();
			message.body(body);
			message.head(head);
			messageList.add(message);
		}

		packet.messageList(messageList);

		log.debug("packet : {}", packet);

	}

}
