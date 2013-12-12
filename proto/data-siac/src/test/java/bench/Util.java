package bench;

import java.nio.charset.Charset;

public class Util {

	private static final Charset ASCII = Charset.forName("US-ASCII");

	public static long convert(final String value) {

		final byte[] array = value.getBytes(ASCII);

		if (array.length > 8) {
			throw new IllegalArgumentException("string is too long");
		}

		return 0;

	}

}
