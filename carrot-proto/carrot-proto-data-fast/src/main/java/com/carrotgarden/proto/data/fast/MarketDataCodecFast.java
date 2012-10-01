package com.carrotgarden.proto.data.fast;

import org.openfast.GroupValue;
import org.openfast.Message;
import org.openfast.SequenceValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.carrotgarden.proto.data.MarketData;
import com.carrotgarden.proto.data.MarketDataEntry;
import com.carrotgarden.proto.data.MarketDataEntry.Builder;
import com.carrotgarden.proto.data.MarketDataEntry.Type;
import com.carrotgarden.util.enums.fix.FixMessageType;
import com.carrotgarden.util.enums.fix.FixSequence;
import com.carrotgarden.util.openfast.OpenFastUtil;
import com.google.protobuf.ByteString;

public class MarketDataCodecFast {

	private static final Logger log = LoggerFactory
			.getLogger(MarketDataCodecFast.class);

	public static MarketData.Builder decode(final Message message,
			final MarketMapper mapper) {

		final FixMessageType type = UtilType.getMessageType(message);

		switch (type) {

		case Update:
			return decodeUpdate(message, mapper);

		case Snapshot:
			return decodeSnapshot(message, mapper);

		default:
			log.warn("unsupported type",
					new IllegalArgumentException(type.name()));
			return null;

		}

	}

	private static MarketData.Builder decodeSnapshot(final Message message,
			final MarketMapper mapper) {

		final String vendorId = "vendor";

		final Long targetId = UtilIdentity.getTargetId(vendorId, message,
				mapper);

		if (targetId == null) {
			log.warn("missing mapping", new NullPointerException("targetId"));
			return null;
		}

		final SequenceValue sequenceValue = OpenFastUtil.getRepeatGroup(
				message, FixSequence.MDEntries);

		if (sequenceValue == null) {
			log.warn("missing sequenceValue", new NullPointerException(
					FixSequence.MDEntries.name()));
			return null;
		}

		final MarketData.Builder data = MarketData.newBuilder();

		data.setMarketId(targetId);
		data.setType(MarketData.Type.SNAPSHOT);

		for (final GroupValue group : sequenceValue.getValues()) {

			final MarketDataEntry.Builder entry = MarketDataEntry.newBuilder();

			UtilType.apply(group, entry);
			UtilValue.apply(group, entry);

			data.addEntry(entry);

		}

		return data;

	}

	private static MarketData.Builder decodeUpdate(final Message message,
			final MarketMapper mapper) {

		final String vendorId = "vendor";

		final SequenceValue sequenceValue = OpenFastUtil.getRepeatGroup(
				message, FixSequence.MDEntries);

		if (sequenceValue == null) {
			log.warn("missing sequenceValue", new NullPointerException(
					FixSequence.MDEntries.name()));
			return null;
		}

		final MarketData.Builder data = MarketData.newBuilder();

		data.setType(MarketData.Type.UPDATE);

		for (final GroupValue group : sequenceValue.getValues()) {

			final Long targetId = UtilIdentity.getTargetId(vendorId, group,
					mapper);

			if (targetId == null) {
				log.warn("missing mapping",
						new NullPointerException("targetId"));
				continue;
			}

			final MarketDataEntry.Builder entry = MarketDataEntry.newBuilder();

			entry.setMarketId(targetId);

			UtilAction.apply(group, entry);
			UtilType.apply(group, entry);
			UtilValue.apply(group, entry);

			UtilDescriptor.applyDescriptor(group, entry);

			data.addEntry(entry);

			if (entry.getType() == Type.TRADE) {

				final Builder entryChange = UtilValue.cloneEntryChange(group,
						entry);

				if (entryChange != null) {
					data.addEntry(entryChange);
				}

				final Builder entryVolume = UtilValue.cloneEntryVolume(group,
						entry);

				if (entryVolume != null) {
					data.addEntry(entryVolume);
				}

			}

		}

		if (data.getEntryBuilderList().isEmpty()) {
			return null;
		}

		return data;

	}

	public static ByteString encode(final MarketData message) {

		if (message == null) {
			return ByteString.EMPTY;
		}

		return message.toByteString();

	}

	private MarketDataCodecFast() {
	}

}
