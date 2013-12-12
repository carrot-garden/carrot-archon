package bench;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainNEW {

	final static Logger log = LoggerFactory.getLogger(MainNEW.class);

	final static int COUNT = 100 * 1000 * 1000;

	static void testEmpty() {

		final long timeStart = System.nanoTime();

		for (int k = 0; k < COUNT; k++) {

		}

		final long timeFinish = System.nanoTime();

		final long timeDiff = timeFinish - timeStart;

		log.debug("time empty {}", 1.0D * timeDiff / COUNT);

	}

	static void testNew0() {

		final long timeStart = System.nanoTime();

		for (int k = 0; k < COUNT; k++) {
			new Object();
		}

		final long timeFinish = System.nanoTime();

		final long timeDiff = timeFinish - timeStart;

		log.debug("time constructor {}", 1.0D * timeDiff / COUNT);

	}

	static void testNew1() throws Exception {

		final long timeStart = System.nanoTime();

		for (int k = 0; k < COUNT; k++) {
			Object.class.newInstance();
		}

		final long timeFinish = System.nanoTime();

		final long timeDiff = timeFinish - timeStart;

		log.debug("time new instance {}", 1.0D * timeDiff / COUNT);

	}

	static String one = "ab";
	static String two = "ac";

	static void testEquals() throws Exception {

		final long timeStart = System.nanoTime();

		for (int k = 0; k < COUNT; k++) {
			one.equals(two);
		}

		final long timeFinish = System.nanoTime();

		final long timeDiff = timeFinish - timeStart;

		log.debug("time equals {}", 1.0D * timeDiff / COUNT);

	}

	public static void main(final String[] args) throws Exception {

		if (log.isDebugEnabled()) {

		}

		testEmpty();

		testNew0();

		testNew1();

		testEquals();

	}

}
