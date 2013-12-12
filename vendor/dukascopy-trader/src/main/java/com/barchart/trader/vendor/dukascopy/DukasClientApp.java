package com.barchart.trader.vendor.dukascopy;

import static com.carrotgarden.trader.util.quickfix.FixSessionSettings.*;
import quickfix.Message;
import quickfix.Message.Header;
import quickfix.SessionID;
import quickfix.SessionSettings;
import quickfix.field.MsgType;
import quickfix.field.Password;
import quickfix.field.ResetSeqNumFlag;
import quickfix.field.Username;

import com.carrotgarden.trader.util.quickfix.FixApp;

//
class DukasClientApp extends FixApp {

	protected DukasClientApp(final SessionSettings settings) {
		super(settings);
	}

	@Override
	public void toAdmin(final Message message, final SessionID sessionId) {

		log.debug("\n\t ADM OUT : {} : {}", sessionId, message);

		try {

			final Header header = message.getHeader();

			final String type = header.getString(MsgType.FIELD);

			switch (type) {

			case MsgType.LOGON:

				/** provide credentials */

				final String connectUsername = getSetting(sessionId,
						SETTING_CONNECT_USERNAME);
				final String connectPassword = getSetting(sessionId,
						SETTING_CONNECT_PASSWORD);

				if (isValid(connectUsername) && isValid(connectPassword)) {
					message.setField(new Username(connectUsername));
					message.setField(new Password(connectPassword));
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
