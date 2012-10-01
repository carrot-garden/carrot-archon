package com.carrotgarden.proto.data.ice;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import com.carrotgarden.proto.data.siac.CodecAdaptor;

/**
 * note: NYL uses the same endianness agreement as java
 */
public final class CodecICE extends CodecAdaptor {

	// DECODE

	@Override
	public byte skip_byte(final ByteBuffer buffer, final int size) {

		buffer.position(buffer.position() + size);

		return 0;

	}

	@Override
	public int skip_int(final ByteBuffer buffer, final int size) {

		buffer.position(buffer.position() + size);

		return 0;

	}

	@Override
	public long skip_long(final ByteBuffer buffer, final int size) {

		buffer.position(buffer.position() + size);

		return 0;

	}

	/** ignore size parameter */
	@Override
	public byte number_byte(final ByteBuffer buffer, final int size) {

		return buffer.get();

	}

	@Override
	public int number_int(final ByteBuffer buffer, final int size) {

		switch (size) {
		case 1:
			return buffer.get();
		case 2:
			return buffer.getShort();
		case 4:
			return buffer.getInt();
		default:
			if (log.isDebugEnabled()) {
				log.debug("unexpected size", new IllegalArgumentException(
						"size=" + size));
			}
			buffer.position(buffer.position() + size);
			return 0;
		}

	}

	@Override
	public long number_long(final ByteBuffer buffer, final int size) {

		switch (size) {
		case 1:
			return buffer.get();
		case 2:
			return buffer.getShort();
		case 4:
			return buffer.getInt();
		case 8:
			return buffer.getLong();
		default:
			if (log.isDebugEnabled()) {
				log.debug("unexpected size", new IllegalArgumentException(
						"size=" + size));
			}
			buffer.position(buffer.position() + size);
			return 0;
		}

	}

	private static final Charset ASCII = Charset.forName("US-ASCII");

	/** read ascii bytes, store into string */
	@Override
	public String string_String(final ByteBuffer buffer, final int size) {

		final byte[] array = new byte[size];

		buffer.get(array);

		final String value = new String(array, ASCII);

		return value.trim();

	}

	/** read native number, store into string */
	@Override
	public String number_String(final ByteBuffer buffer, final int size) {

		final long value = number_long(buffer, size);

		return Long.toString(value);

	}

	// ENCODE

	/** ignore size parameter */
	@Override
	public void number_byte(final byte value, final ByteBuffer buffer,
			final int size) {

		buffer.put(value);

	}

	@Override
	public void number_int(final int value, final ByteBuffer buffer,
			final int size) {

		switch (size) {
		case 1:
			buffer.put((byte) value);
			break;
		case 2:
			buffer.putShort((short) value);
			break;
		case 4:
			buffer.putInt(value);
			break;
		default:
			if (log.isDebugEnabled()) {
				log.debug("unexpected size", new IllegalArgumentException(
						"size=" + size));
			}
			buffer.put(new byte[size]);
			break;
		}

	}

	@Override
	public void number_long(final long value, final ByteBuffer buffer,
			final int size) {

		switch (size) {
		case 1:
			buffer.put((byte) value);
			break;
		case 2:
			buffer.putShort((short) value);
			break;
		case 4:
			buffer.putInt((int) value);
			break;
		case 8:
			buffer.putLong(value);
			break;
		default:
			if (log.isDebugEnabled()) {
				log.debug("unexpected size", new IllegalArgumentException(
						"size=" + size));
			}
			buffer.put(new byte[size]);
			break;
		}

	}

	/** write native number from string value */
	@Override
	public void number_String(final String value, final ByteBuffer buffer,
			final int size) {

		final long number = Long.parseLong(value);

		number_long(number, buffer, size);

	}

}
