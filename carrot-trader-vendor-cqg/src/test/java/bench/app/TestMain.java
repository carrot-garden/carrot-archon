package bench.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quickfix.Application;
import quickfix.DefaultMessageFactory;
import quickfix.FileLogFactory;
import quickfix.FileStoreFactory;
import quickfix.Initiator;
import quickfix.LogFactory;
import quickfix.MessageFactory;
import quickfix.MessageStoreFactory;
import quickfix.Session;
import quickfix.SessionID;
import quickfix.SessionSettings;
import quickfix.SocketInitiator;

import com.carrotgarden.trader.util.quickfix.FixBuilder;
import com.carrotgarden.trader.util.quickfix.FixSessionSettingsBuilder;

public class TestMain {

	static final Logger log = LoggerFactory.getLogger(TestMain.class);

	static void logSession(final SessionID sessionId) {

		final Session session = Session.lookupSession(sessionId);

		if (session == null) {
			log.info("### session not present");
		} else {
			log.info("### session.isLoggedOn : {}", session.isLoggedOn());
		}

	}

	public static void main(final String args[]) throws Exception {

		final FixSessionSettingsBuilder builder = new FixSessionSettingsBuilder();

		builder.beginString = "FIX.4.2";
		builder.dataDictionary = "CQG_FIX_4.2.xml";

		builder.targetCompID = "CQG_Gateway";

		builder.senderCompID = "mharaburdatestfix";
		builder.senderSubID = "Test FIX";

		builder.senderPassword = "pass";

		builder.connectHost = "demo.cqgtrader.com";
		builder.connectPort = "6912";

		//

		final SessionID sessionId = FixBuilder.newSessionId(builder);
		log.info("sessionId = \n\t{}", sessionId);

		final SessionSettings settings = FixBuilder.newSettings(builder);
		log.info("settings = \n\t{}", settings);

		//

		final MessageStoreFactory storeFactory = new FileStoreFactory(settings);

		final LogFactory logFactory = new FileLogFactory(settings);

		final MessageFactory messageFactory = new DefaultMessageFactory();

		final Application app = new TestFixApp(settings);

		final Initiator connector = new SocketInitiator(app, storeFactory,
				settings, logFactory, messageFactory);

		//

		logSession(sessionId);

		log.info("######################################################## 1");
		connector.start();
		Thread.sleep(2 * 1000);

		logSession(sessionId);

		log.info("######################################################## 2");
		connector.stop();
		Thread.sleep(2 * 1000);

		logSession(sessionId);

		//

		logSession(sessionId);

		log.info("######################################################## 3");
		connector.start();
		Thread.sleep(2 * 1000);

		logSession(sessionId);

		log.info("######################################################## 4");
		connector.stop();
		Thread.sleep(2 * 1000);

		logSession(sessionId);

	}

}
