package com.carrotgarden.proto.data.siac;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CodecAdaptor implements Codec {

	protected final static Logger log = LoggerFactory
			.getLogger(CodecAdaptor.class);

	private void logTODO() {
		log.debug("TODO", new UnsupportedOperationException());
	}

	@Override
	public void number_byte(final byte value, final ByteBuffer buffer,
			final int size) {
		logTODO();
	}

	@Override
	public byte number_byte(final ByteBuffer buffer, final int size) {
		logTODO();
		return 0;
	}

	@Override
	public int number_int(final ByteBuffer buffer, final int size) {
		logTODO();
		return 0;
	}

	@Override
	public void number_int(final int value, final ByteBuffer buffer,
			final int size) {
		logTODO();
	}

	@Override
	public long number_long(final ByteBuffer buffer, final int size) {
		logTODO();
		return 0;
	}

	@Override
	public void number_long(final long value, final ByteBuffer buffer,
			final int size) {
		logTODO();
	}

	@Override
	public String number_String(final ByteBuffer buffer, final int size) {
		logTODO();
		return null;
	}

	@Override
	public void number_String(final String value, final ByteBuffer buffer,
			final int size) {
		logTODO();
	}

	@Override
	public void skip_byte(final byte value, final ByteBuffer buffer,
			final int size) {
		logTODO();
	}

	@Override
	public byte skip_byte(final ByteBuffer buffer, final int size) {
		logTODO();
		return 0;
	}

	@Override
	public int skip_int(final ByteBuffer buffer, final int size) {
		logTODO();
		return 0;
	}

	@Override
	public void skip_int(final int value, final ByteBuffer buffer,
			final int size) {
		logTODO();
	}

	@Override
	public long skip_long(final ByteBuffer buffer, final int size) {
		logTODO();
		return 0;
	}

	@Override
	public void skip_long(final long value, final ByteBuffer buffer,
			final int size) {
		logTODO();
	}

	@Override
	public void string_byte(final byte value, final ByteBuffer buffer,
			final int size) throws Exception {
		logTODO();
	}

	@Override
	public byte string_byte(final ByteBuffer buffer, final int size)
			throws Exception {
		logTODO();
		return 0;
	}

	@Override
	public String string_String(final ByteBuffer buffer, final int size) {
		logTODO();
		return null;
	}

	@Override
	public void string_String(final String value, final ByteBuffer buffer,
			final int size) {
		logTODO();
	}

}
