package com.carrotgarden.proto.data.fix;

import quickfix.FieldMap;

import com.carrotgarden.util.math.MathExtra;
import com.carrotgarden.util.math.MathExtra.DoubleParts;

public final class Util {

	public static DoubleParts getDoubleParts(final FieldMap group, final int tag) {

		if (isInvalid(group, tag)) {
			return null;
		}

		try {
			final double value = group.getDouble(tag);
			return MathExtra.extractDecimal(value);
		} catch (final Throwable e) {
			return null;
		}

	}

	public static Integer getInt(final FieldMap group, final int tag) {

		if (isInvalid(group, tag)) {
			return null;
		}

		try {
			return group.getInt(tag);
		} catch (final Throwable e) {
			return null;
		}

	}

	public static String getString(final FieldMap group, final int tag) {

		if (isInvalid(group, tag)) {
			return null;
		}

		try {
			return group.getString(tag);
		} catch (final Throwable e) {
			return null;
		}

	}

	private static boolean isInvalid(final FieldMap group, final int tag) {
		return group == null || !group.isSetField(tag);
	}

	private Util() {
	}

}
