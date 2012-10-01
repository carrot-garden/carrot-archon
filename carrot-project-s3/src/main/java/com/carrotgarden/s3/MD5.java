package com.carrotgarden.s3;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;

public class MD5 {

	final static int SIZE_1 = 1 * 1024;
	final static int SIZE_2 = 4 * 1024;
	final static int SIZE_3 = 16 * 1024;
	final static int SIZE_X = 64 * 1024;

	public static byte[] arrayMD5(final String filename) throws Exception {

		final File file = new File(filename);

		final long length = file.length();

		final int size;

		if (length < SIZE_1) {
			size = SIZE_1;
		} else if (length < SIZE_2) {
			size = SIZE_2;
		} else if (length < SIZE_3) {
			size = SIZE_3;
		} else {
			size = SIZE_X;
		}

		final InputStream stream = new FileInputStream(filename);

		final byte[] array = new byte[size];

		final MessageDigest builder = MessageDigest.getInstance("MD5");

		while (true) {

			final int count = stream.read(array);

			if (count < 0) {
				break;
			}

			builder.update(array, 0, count);

		}

		stream.close();

		return builder.digest();

	}

	public static String textMD5(final String filename) throws Exception {

		return toHexString(arrayMD5(filename));

	}

	private static final char[] HEX = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	private static final Charset UTF_8 = Charset.forName("UTF-8");

	public static String toHexString(final byte[] array) {

		final int size = array.length;

		final char[] result = new char[size * 2];

		for (int in = 0; in < size; in++) {

			final int value = array[in] & 0xFF;

			final int out = in * 2;

			result[out] = HEX[value / 16];
			result[out + 1] = HEX[value % 16];

		}

		return new String(result);

	}

	public static void main(final String args[]) {
		try {
			System.out.println(textMD5("pom.xml"));
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

}