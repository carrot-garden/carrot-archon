package bench.config;

import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValue;

public class MainConfig {

	protected final static Logger log = LoggerFactory
			.getLogger(MainConfig.class);

	public static void main(final String[] args) {

		log.debug("### init");

		final Config conf = ConfigFactory.load("test.conf");

		log.debug("conf = {}", conf);

		log.debug("### 1");

		{

			final Set<Entry<String, ConfigValue>> entrySet = conf.entrySet();

			for (final Entry<String, ConfigValue> entry : entrySet) {
				log.debug("entry : {} {}", entry.getKey(), entry.getValue());
			}

		}

		log.debug("### 2");

		final Config test = conf.getConfig("carrot.config.test");

		{

			final Set<Entry<String, ConfigValue>> entrySet = test.entrySet();

			for (final Entry<String, ConfigValue> entry : entrySet) {
				log.debug("entry : {} '{}'", entry.getKey(), entry.getValue()
						.unwrapped());
			}

		}

		log.debug("done");

	}

}
