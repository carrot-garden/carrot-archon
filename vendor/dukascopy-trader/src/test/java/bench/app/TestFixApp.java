package bench.app;

import quickfix.DoNotSend;
import quickfix.FieldNotFound;
import quickfix.IncorrectDataFormat;
import quickfix.IncorrectTagValue;
import quickfix.Message;
import quickfix.RejectLogon;
import quickfix.SessionID;
import quickfix.SessionSettings;
import quickfix.UnsupportedMessageType;

import com.carrotgarden.trader.util.quickfix.FixApp;

class TestFixApp extends FixApp {

	protected TestFixApp(final SessionSettings settings) {
		super(settings);
	}

	@Override
	public void fromAdmin(final Message message, final SessionID sessionId)
			throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue,
			RejectLogon {

		log.info("\n\t message : {}", message);

	}

	@Override
	public void fromApp(final Message message, final SessionID sessionId)
			throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue,
			UnsupportedMessageType {

		log.info("\n\t message : {}", message);

	}

	@Override
	public void onCreate(final SessionID sessionId) {

		log.info("sessionId : {}", sessionId);

	}

	@Override
	public void onLogon(final SessionID sessionId) {

		log.info("sessionId : {}", sessionId);

	}

	@Override
	public void onLogout(final SessionID sessionId) {

		log.info("sessionId : {}", sessionId);

	}

	@Override
	public void toAdmin(final Message message, final SessionID sessionId) {

		log.info("\n\t message : {}", message);

		super.toAdmin(message, sessionId);

		log.info("\n\t message : {}", message);

	}

	@Override
	public void toApp(final Message message, final SessionID sessionId)
			throws DoNotSend {

		log.info("message : {}", message);

	}

}
