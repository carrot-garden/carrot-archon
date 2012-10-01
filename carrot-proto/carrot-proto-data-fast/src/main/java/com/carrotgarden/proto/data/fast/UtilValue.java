package com.carrotgarden.proto.data.fast;

import org.openfast.GroupValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.carrotgarden.proto.data.MarketDataEntry;
import com.carrotgarden.proto.data.MarketDataEntry.Builder;
import com.carrotgarden.proto.data.MarketDataEntry.Descriptor;
import com.carrotgarden.proto.data.MarketDataEntry.Type;
import com.carrotgarden.util.enums.fix.FixTag;
import com.carrotgarden.util.math.MathExtra.DoubleParts;
import com.carrotgarden.util.openfast.OpenFastUtil;

public final class UtilValue {

	private static final Logger log = LoggerFactory.getLogger(UtilValue.class);

	public static void apply(final GroupValue group,
			final MarketDataEntry.Builder entry) {

		applySequence(group, FixTag.RptSeq, entry);

		applyIndex(group, FixTag.MDPriceLevel, entry);

		applyPrice(group, FixTag.MDEntryPx, entry);
		applySize(group, FixTag.MDEntrySize, entry);

	}

	public static void applySequence(final GroupValue group, final FixTag tag,
			final MarketDataEntry.Builder entry) {

		final Long sequence = OpenFastUtil.getLong(group, tag);

		if (sequence == null) {
			return;
		}

		entry.setSequence(sequence);

	}

	public static void applyIndex(final GroupValue group, final FixTag tag,
			final MarketDataEntry.Builder entry) {

		final Long index = OpenFastUtil.getLong(group, tag);

		if (index == null) {
			return;
		}

		entry.setIndex(index);

	}

	public static void applyPrice(final GroupValue group, final FixTag tag,
			final MarketDataEntry.Builder entry) {

		final DoubleParts parts = OpenFastUtil.getDoubleParts(group, tag);

		if (parts == null) {
			return;
		}

		entry.setPriceMantissa(parts.getMantissa());
		entry.setPriceExponent(parts.getExponent());

	}

	public static void applySize(final GroupValue group, final FixTag tag,
			final MarketDataEntry.Builder entry) {

		final DoubleParts parts = OpenFastUtil.getDoubleParts(group, tag);

		if (parts == null) {
			return;
		}

		entry.setSizeMantissa(parts.getMantissa());
		entry.setSizeExponent(parts.getExponent());

	}

	public static void clear(final MarketDataEntry.Builder entry) {
		clearIndex(entry);
		clearPrice(entry);
		clearSize(entry);
	}

	public static void clearIndex(final MarketDataEntry.Builder entry) {
		entry.clearIndex();
	}

	public static void clearPrice(final MarketDataEntry.Builder entry) {
		entry.clearPriceMantissa();
		entry.clearPriceExponent();
	}

	public static void clearSize(final MarketDataEntry.Builder entry) {
		entry.clearSizeMantissa();
		entry.clearSizeExponent();
	}

	public static MarketDataEntry.Builder cloneEntryChange(
			final GroupValue group, final MarketDataEntry.Builder entry) {

		if (!OpenFastUtil.hasFieldValue(group, FixTag.NetChgPrevDay)) {
			return null;
		}

		final Builder clone = entry.clone();

		clear(clone);

		clone.setType(Type.CHANGE);
		clone.addDescriptor(Descriptor.PERIOD_DAY);

		applyPrice(group, FixTag.NetChgPrevDay, clone);

		return clone;

	}

	public static MarketDataEntry.Builder cloneEntryVolume(
			final GroupValue group, final MarketDataEntry.Builder entry) {

		if (!OpenFastUtil.hasFieldValue(group, FixTag.TradeVolume)) {
			return null;
		}

		final Builder clone = entry.clone();

		clear(clone);

		clone.setType(Type.VOLUME);

		applySize(group, FixTag.TradeVolume, clone);

		return clone;

	}

	private UtilValue() {
	}

}
