package bench.dns;

import java.io.IOException;

import org.xbill.DNS.DClass;
import org.xbill.DNS.Message;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.Resolver;
import org.xbill.DNS.ReverseMap;
import org.xbill.DNS.Section;
import org.xbill.DNS.SimpleResolver;
import org.xbill.DNS.Type;

public class ReverseDnsTest {

	public static String reverseDns(final String hostAddress)
			throws IOException {

		// final Resolver resolver = new SimpleResolver("ns-25.awsdns-03.com");
		final Resolver resolver = new SimpleResolver();

		final Name name = ReverseMap.fromAddress(hostAddress);

		final int type = Type.PTR;
		final int dclass = DClass.IN;

		final Record record = Record.newRecord(name, type, dclass);

		final Message request = Message.newQuery(record);
		final Message response = resolver.send(request);

		final Record[] answers = response.getSectionArray(Section.ANSWER);

		if (answers.length == 0)
			return hostAddress;
		else
			return answers[0].rdataToString();
	}

	public static void main(final String args[]) throws IOException {

		final long start = System.currentTimeMillis();

		System.out.println(reverseDns("192.222.1.13"));

		System.out.println(reverseDns("208.201.239.36"));

		System.out.println(reverseDns("67.184.59.208"));

		final long finish = System.currentTimeMillis();

		System.out.println((finish - start) + " ms");

	}

}
