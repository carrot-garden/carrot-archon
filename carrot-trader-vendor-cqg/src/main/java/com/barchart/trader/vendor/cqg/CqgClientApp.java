package com.barchart.trader.vendor.cqg;

import static com.carrotgarden.trader.util.quickfix.FixSessionSettings.*;
import quickfix.Message;
import quickfix.Message.Header;
import quickfix.SessionID;
import quickfix.SessionSettings;
import quickfix.field.MsgType;
import quickfix.field.RawData;
import quickfix.field.RawDataLength;
import quickfix.field.ResetSeqNumFlag;

import com.carrotgarden.trader.util.quickfix.FixApp;

//
class CqgClientApp extends FixApp {

	protected CqgClientApp(final SessionSettings settings) {
		super(settings);
	}

	@Override
	public void toAdmin(final Message message, final SessionID sessionId) {

		super.toAdmin(message, sessionId);

		try {

			final Header header = message.getHeader();

			final String type = header.getString(MsgType.FIELD);

			switch (type) {

			case MsgType.LOGON:

				/** provide credentials */

				final String senderPassword = getSetting(sessionId,
						SETTING_SENDER_PASSWORD);

				if (isValid(senderPassword)) {
					message.setField(new RawData(senderPassword));
					message.setField(new RawDataLength(senderPassword.length()));
				}

				/** reset sequence numbers */

				message.setField(new ResetSeqNumFlag(true));

				break;

			default:
				break;
			}

		} catch (final Exception e) {
			log.error("", e);
		}

	}

}
