package bench.dns;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.route53.AmazonRoute53;
import com.amazonaws.services.route53.AmazonRoute53Client;
import com.amazonaws.services.route53.model.HostedZone;
import com.amazonaws.services.route53.model.ListResourceRecordSetsRequest;
import com.amazonaws.services.route53.model.ListResourceRecordSetsResult;
import com.amazonaws.services.route53.model.ResourceRecordSet;

public class MainDNS_2 {

	static final Logger log = LoggerFactory.getLogger(MainDNS_2.class);

	public static void main(final String[] args) throws Exception {

		log.debug("init");

		// read only
		final AWSCredentials credentials = new AWSCredentials() {

			@Override
			public String getAWSAccessKeyId() {
				return "AKIAITLCVFWD5MOMGRFA";
			}

			@Override
			public String getAWSSecretKey() {
				return "fIYtPtnLMs4nIb3v8xtsFMuQr7EPxbcAvmpXtIfe";
			}

		};

		final AmazonRoute53 client = new AmazonRoute53Client(credentials);

		final List<HostedZone> zoneList = client.listHostedZones()
				.getHostedZones();

		HostedZone zone = null;

		for (final HostedZone temp : zoneList) {

			final String name = temp.getName();

			if ("carrotgarden.com.".equals(name)) {
				zone = temp;
				break;
			}

		}

		log.debug("zone : " + zone);

		final ListResourceRecordSetsRequest listRequest = new ListResourceRecordSetsRequest(
				zone.getId());

		final ListResourceRecordSetsResult listResult = client
				.listResourceRecordSets(listRequest);

		final List<ResourceRecordSet> listRecords = listResult
				.getResourceRecordSets();

		for (final ResourceRecordSet record : listRecords) {

			log.debug("record : " + record.getName());

		}

		log.debug("done");

	}
}
