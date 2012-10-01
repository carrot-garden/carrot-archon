package com.carrotgarden.proto.data.siac;

import java.nio.ByteBuffer;

/**
 * fixed size field codec helper
 * 
 * decode method naming convention
 * 
 * [native-codec-name] is source SIAC data encoding
 * 
 * [java-type-name] is target java primitive type name
 * 
 * decoder:
 * 
 * value = [native-codec-name]_[java-type-name](buffer, size)
 * 
 * encoder:
 * 
 * [native-codec-name]_[java-type-name](value, buffer, size)
 * 
 */
public interface Codec {

	/** encode native encoded number from byte; ignore size parameter */
	void number_byte(byte value, ByteBuffer buffer, int size) throws Exception;

	/** decode native encoded number into byte; ignore size parameter */
	byte number_byte(ByteBuffer buffer, int size) throws Exception;

	/** decode native encoded number into integer */
	int number_int(ByteBuffer buffer, int size) throws Exception;

	/** encode native encoded number from integer */
	void number_int(int value, ByteBuffer buffer, int size) throws Exception;

	/** decode native encoded number into long */
	long number_long(ByteBuffer buffer, int size) throws Exception;

	/** encode native encoded number from long */
	void number_long(long value, ByteBuffer buffer, int size) throws Exception;

	/** decode native number into String */
	String number_String(ByteBuffer buffer, int size) throws Exception;

	/** encode native number from String */
	void number_String(String value, ByteBuffer buffer, int size)
			throws Exception;

	/** encode fake byte value for ignored fields */
	void skip_byte(byte value, ByteBuffer buffer, int size) throws Exception;

	/** decode fake byte value for ignored fields */
	byte skip_byte(ByteBuffer buffer, int size) throws Exception;

	/** decode fake integer value for ignored fields */
	int skip_int(ByteBuffer buffer, int size) throws Exception;

	/** encode fake integer value for ignored fields */
	void skip_int(int value, ByteBuffer buffer, int size) throws Exception;

	/** decode fake long value for ignored fields */
	long skip_long(ByteBuffer buffer, int size) throws Exception;

	/** encode fake long value for ignored fields */
	void skip_long(long value, ByteBuffer buffer, int size) throws Exception;

	/** encode native string from byte */
	void string_byte(byte value, ByteBuffer buffer, int size) throws Exception;

	/** decode native string into byte */
	byte string_byte(ByteBuffer buffer, int size) throws Exception;

	/** decode native string into String */
	String string_String(ByteBuffer buffer, int size) throws Exception;

	/** encode native string from String */
	void string_String(String value, ByteBuffer buffer, int size)
			throws Exception;

}
