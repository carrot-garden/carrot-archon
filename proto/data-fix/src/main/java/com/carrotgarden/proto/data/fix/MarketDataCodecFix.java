package com.carrotgarden.proto.data.fix;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quickfix.Group;
import quickfix.Message;
import quickfix.Message.Header;
import quickfix.field.MsgType;
import quickfix.field.NoMDEntries;

import com.carrotgarden.proto.data.MarketData;
import com.carrotgarden.proto.data.MarketDataEntry;

public class MarketDataCodecFix {

	private static final Logger log = LoggerFactory
			.getLogger(MarketDataCodecFix.class);

	public static MarketData.Builder decode(final Message message,
			final MarketMapper mapper) {

		final String type = UtilType.getMessageType(message);

		if (type == null) {
			log.warn("missing type", new NullPointerException("type"));
			return null;
		}

		switch (type) {

		case MsgType.MARKET_DATA_INCREMENTAL_REFRESH:
			return decodeUpdate(message, mapper);

		case MsgType.MARKET_DATA_SNAPSHOT_FULL_REFRESH:
			return decodeSnapshot(message, mapper);

		default:
			log.warn("unsupported type", new IllegalArgumentException(type));
			return null;

		}

	}

	private static MarketData.Builder decodeSnapshot(final Message message,
			final MarketMapper mapper) {

		final Header header = message.getHeader();

		final Long targetId = UtilIdentity.getTargetId(header, message, mapper);

		if (targetId == null) {
			log.warn("missing mapping", new NullPointerException("targetId"));
			return null;
		}

		final MarketData.Builder data = MarketData.newBuilder();

		data.setMarketId(targetId);
		data.setType(MarketData.Type.SNAPSHOT);

		final List<Group> groupList = message.getGroups(NoMDEntries.FIELD);

		for (final Group group : groupList) {

			final MarketDataEntry.Builder entry = MarketDataEntry.newBuilder();

			UtilType.apply(group, entry);
			UtilValue.apply(group, entry);

			data.addEntry(entry);

		}

		return data;

	}

	private static MarketData.Builder decodeUpdate(final Message message,
			final MarketMapper mapper) {

		final Header header = message.getHeader();

		final MarketData.Builder data = MarketData.newBuilder();

		data.setType(MarketData.Type.UPDATE);

		final List<Group> groupList = message.getGroups(NoMDEntries.FIELD);

		for (final Group group : groupList) {

			final Long targetId = UtilIdentity.getTargetId(header, group,
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

			data.addEntry(entry);

		}

		if (data.getEntryBuilderList().isEmpty()) {
			return null;
		}

		return data;

	}

	private MarketDataCodecFix() {
	}

}
