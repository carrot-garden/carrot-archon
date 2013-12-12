package com.carrotgarden.proto.data.cfn;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import com.carrotgarden.proto.data.siac.CodecAdaptor;

/**
 * 
 */
public final class CodecCFN extends CodecAdaptor {

	/** fake byte value for ignored fields */
	@Override
	public byte skip_byte(final ByteBuffer buffer, final int size) {

		buffer.position(buffer.position() + size);

		return 0;

	}

	/** fake integer value for ignored fields */
	@Override
	public int skip_int(final ByteBuffer buffer, final int size) {

		buffer.position(buffer.position() + size);

		return 0;

	}

	/** fake long value for ignored fields */
	@Override
	public long skip_long(final ByteBuffer buffer, final int size) {

		buffer.position(buffer.position() + size);

		return 0;

	}

	/** read SIAC ASCII encoded number as byte; ignore size parameter */
	@Override
	public byte number_byte(final ByteBuffer buffer, final int size) {

		final int digit = buffer.get() - (byte) '0';

		return (byte) digit;

	}

	/** read SIAC ASCII encoded number as integer */
	@Override
	public int number_int(final ByteBuffer buffer, final int size) {

		int value = 0;

		for (int k = 0; k < size; k++) {
			final int digit = buffer.get() - (byte) '0';
			value = value * 10 + digit;
		}

		return value;

	}

	/** read SIAC ASCII encoded number as long */
	@Override
	public long number_long(final ByteBuffer buffer, final int size) {

		long value = 0;

		for (int k = 0; k < size; k++) {
			final int digit = buffer.get() - (byte) '0';
			value = value * 10 + digit;
		}

		return value;

	}

	private static final Charset ASCII = Charset.forName("US-ASCII");

	/** read SIAC ASCII encoded string as String */
	@Override
	public String string_String(final ByteBuffer buffer, final int size) {

		final byte[] array = new byte[size];

		buffer.get(array);

		final String value = new String(array, ASCII);

		return value.trim();

	}

}
