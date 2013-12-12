package com.carrotgarden.trader.util.quickfix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quickfix.DoNotSend;
import quickfix.FieldNotFound;
import quickfix.IncorrectDataFormat;
import quickfix.IncorrectTagValue;
import quickfix.Message;
import quickfix.RejectLogon;
import quickfix.SessionID;
import quickfix.SessionSettings;
import quickfix.UnsupportedMessageType;

//
public class FixApp implements quickfix.Application {

	protected final Logger log = LoggerFactory.getLogger(getClass());

	protected final SessionSettings settings;

	protected FixApp(final SessionSettings settings) {
		this.settings = settings;
	}

	protected boolean isValid(final String text) {
		return text != null && text.length() > 0;
	}

	protected String getSetting(final SessionID sessionId, final String key) {
		try {
			return settings.getString(sessionId, key);
		} catch (final Exception e) {
			log.error("", e);
			return "";
		}
	}

	//

	@Override
	public void onCreate(final SessionID sessionId) {

		log.debug("\n\t CREATE : {}", sessionId);

	}

	@Override
	public void onLogon(final SessionID sessionId) {

		log.debug("\n\t LOG IN  : {}", sessionId);

	}

	@Override
	public void onLogout(final SessionID sessionId) {

		log.debug("\n\t LOG OUT : {}", sessionId);

	}

	@Override
	public void fromAdmin(final Message message, final SessionID sessionId)
			throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue,
			RejectLogon {

		log.debug("\n\t ADM IN : {} :  {}", sessionId, message);

	}

	@Override
	public void fromApp(final Message message, final SessionID sessionId)
			throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue,
			UnsupportedMessageType {

		log.debug("\n\t APP IN : {} : {}", sessionId, message);

	}

	@Override
	public void toAdmin(final Message message, final SessionID sessionId) {

		log.debug("\n\t ADM OUT : {} : {}", sessionId, message);

	}

	@Override
	public void toApp(final Message message, final SessionID sessionId)
			throws DoNotSend {

		log.debug("\n\t APP OUT : {} : {}", sessionId, message);

	}

}
