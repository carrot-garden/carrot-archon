package com.carrotgarden.proto.data.fix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quickfix.FieldMap;
import quickfix.field.MDEntryPositionNo;
import quickfix.field.MDEntryPx;
import quickfix.field.MDEntrySize;

import com.carrotgarden.proto.data.MarketDataEntry;
import com.carrotgarden.util.math.MathExtra.DoubleParts;

public final class UtilValue {

	private static final Logger log = LoggerFactory
			.getLogger(UtilValue.class);

	public static void apply(final FieldMap group,
			final MarketDataEntry.Builder entry) {

		applyIndex(group, MDEntryPositionNo.FIELD, entry);

		applyPrice(group, MDEntryPx.FIELD, entry);

		applySize(group, MDEntrySize.FIELD, entry);

	}

	public static void applyIndex(final FieldMap group, final int tag,
			final MarketDataEntry.Builder entry) {

		final Integer index = Util.getInt(group, tag);

		if (index == null) {
			return;
		}

		entry.setIndex(index);

	}

	public static void applyPrice(final FieldMap group, final int tag,
			final MarketDataEntry.Builder entry) {

		final DoubleParts parts = Util.getDoubleParts(group, tag);

		if (parts == null) {
			return;
		}

		entry.setPriceMantissa(parts.getMantissa());
		entry.setPriceExponent(parts.getExponent());

	}

	public static void applySize(final FieldMap group, final int tag,
			final MarketDataEntry.Builder entry) {

		final DoubleParts parts = Util.getDoubleParts(group, tag);

		if (parts == null) {
			return;
		}

		entry.setSizeMantissa(parts.getMantissa());
		entry.setSizeExponent(parts.getExponent());

	}

	private UtilValue() {
	}

}
