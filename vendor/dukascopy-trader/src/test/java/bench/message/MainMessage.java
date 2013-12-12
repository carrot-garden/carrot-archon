package bench.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quickfix.DataDictionary;
import quickfix.DefaultDataDictionaryProvider;
import quickfix.DefaultMessageFactory;
import quickfix.Message;
import quickfix.MessageFactory;
import quickfix.field.ApplVerID;

public class MainMessage {

	private static final Logger log = LoggerFactory
			.getLogger(MainMessage.class);

	public static void main(final String[] args) throws Exception {

		log.info("started");

		final MessageFactory factory = new DefaultMessageFactory();

		final String beginString = "";
		final String msgType = "x";
		final Message message = factory.create(beginString, msgType);

		log.info("message :: {}", message);

		final DefaultDataDictionaryProvider provider = new DefaultDataDictionaryProvider();

		final DataDictionary dictionary = provider
				.getApplicationDataDictionary(new ApplVerID(ApplVerID.FIX50));

		final String messageString = "8=FIX.4.29=9935=D49=SenderCompId56=TargetCompId115=BROK128=INST11=ClOrdId1_101015=USD21=338=99940=154=155=IBM60=20050611-00:43:3410=999";

		final Message message2 = new Message(messageString, false);
		log.info("message2 :: {}", message2);

		log.info("finished");

	}

}
