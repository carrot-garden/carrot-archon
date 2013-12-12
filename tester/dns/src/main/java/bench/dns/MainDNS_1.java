package bench.dns;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xbill.DNS.DClass;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.Resolver;
import org.xbill.DNS.SimpleResolver;
import org.xbill.DNS.Type;
import org.xbill.DNS.ZoneTransferIn;

public class MainDNS_1 {

	static final Logger log = LoggerFactory.getLogger(MainDNS_1.class);

	public static void main(final String[] args) throws Exception {

		log.debug("init");

		final Resolver resolver = new SimpleResolver("8.8.8.8");

		// final Message request = null;
		// final Message response = resolver.send(request);

		//

		final Lookup lookup = new Lookup("carrotgarden.com", Type.ANY,
				DClass.IN);
		lookup.setResolver(resolver);
		final Record[] result = lookup.run();
		log.debug("result = " + lookup.getResult());
		final Record[] list1 = lookup.getAnswers();

		for (final Record record : list1) {
			log.debug("record = " + record);
		}

		//

		final Name name = new Name("carrotgarden.com");
		final String host = "205.251.192.25";// "ns-25.awsdns-03.com";
		final ZoneTransferIn xfr = ZoneTransferIn.newAXFR(name, host, null);
		final List<Record> list2 = xfr.run();

		for (final Record record : list2) {
			log.debug("record = " + record);
		}

		log.debug("done");

	}
}
