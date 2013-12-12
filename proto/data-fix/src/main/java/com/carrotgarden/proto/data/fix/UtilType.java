package com.carrotgarden.proto.data.fix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quickfix.FieldMap;
import quickfix.Message;
import quickfix.field.MDEntryType;
import quickfix.field.MsgType;

import com.carrotgarden.proto.data.MarketDataEntry;
import com.carrotgarden.proto.data.MarketDataEntry.Descriptor;
import com.carrotgarden.proto.data.MarketDataEntry.Type;

public final class UtilType {

	private static final Logger log = LoggerFactory
			.getLogger(UtilType.class);

	public static void apply(final FieldMap group,
			final MarketDataEntry.Builder entry) {

		final String type = Util.getString(group, MDEntryType.FIELD);

		apply(type, entry);

	}

	/** http://fixprotocol.org/FIXimate3.0/en/FIX.5.0SP2/tag269.html */
	public static void apply(final String type,
			final MarketDataEntry.Builder entry) {

		if (isInvalid(type)) {
			entry.setType(Type.UNKNOWN_TYPE);
			return;
		}

		final MarketDataEntry.Type entryType;

		switch (type.charAt(0)) {

		case MDEntryType.BID:
			entryType = Type.BID;
			break;

		case MDEntryType.OFFER:
			entryType = Type.ASK;
			break;

		case MDEntryType.TRADE:
			entryType = Type.TRADE;
			break;

		case MDEntryType.INDEX_VALUE:
			entryType = Type.TRADE;
			entry.addDescriptor(Descriptor.INDEX_VALUE);
			break;

		case MDEntryType.OPENING_PRICE:
			entryType = Type.OPEN;
			break;

		case MDEntryType.CLOSING_PRICE:
			entryType = Type.CLOSE;
			break;

		case MDEntryType.SETTLEMENT_PRICE:
			entryType = Type.SETTLE;
			break;

		case MDEntryType.TRADING_SESSION_HIGH_PRICE:
			entryType = Type.HIGH;
			entry.addDescriptor(Descriptor.PERIOD_SESSION);
			break;

		case MDEntryType.TRADING_SESSION_LOW_PRICE:
			entryType = Type.LOW;
			entry.addDescriptor(Descriptor.PERIOD_SESSION);
			break;

		case MDEntryType.TRADING_SESSION_VWAP_PRICE:
			entryType = Type.TRADE;
			entry.addDescriptor(Descriptor.PRICE_VWAP);
			break;

		case MDEntryType.IMBALANCE:
			entryType = Type.TRADE;
			entry.addDescriptor(Descriptor.PRICE_IMBALANCE);
			break;

		case MDEntryType.TRADE_VOLUME:
			entryType = Type.VOLUME;
			break;

		case MDEntryType.OPEN_INTEREST:
			entryType = Type.INTEREST;
			break;

		case MDEntryType.COMPOSITE_UNDERLYING_PRICE:
			entryType = Type.UNDERLYING;
			break;

		case MDEntryType.SIMULATED_SELL_PRICE:
			entryType = Type.ASK;
			entry.addDescriptor(Descriptor.PRICE_SIMULATED);
			break;

		case MDEntryType.SIMULATED_BUY_PRICE:
			entryType = Type.BID;
			entry.addDescriptor(Descriptor.PRICE_SIMULATED);
			break;

		case MDEntryType.MARGIN_RATE:
			entryType = Type.PERCENT;
			entry.addDescriptor(Descriptor.PERCENT_MARGIN_RATE);
			break;

		case MDEntryType.MID_PRICE:
			entryType = Type.GAP;
			break;

		case MDEntryType.EMPTY_BOOK:
			entryType = Type.BOOK_RESET;
			break;

		case MDEntryType.SETTLE_HIGH_PRICE:
			entryType = Type.SETTLE;
			entry.addDescriptor(Descriptor.SETTLE_HIGH);
			break;

		case MDEntryType.SETTLE_LOW_PRICE:
			entryType = Type.SETTLE;
			entry.addDescriptor(Descriptor.SETTLE_LOW);
			break;

		case MDEntryType.PRIOR_SETTLE_PRICE:
			entryType = Type.SETTLE;
			entry.addDescriptor(Descriptor.SETTLE_PREVIOUS);
			break;

		case MDEntryType.SESSION_HIGH_BID:
			entryType = Type.BID;
			entry.addDescriptor(Descriptor.PERIOD_SESSION);
			break;

		case MDEntryType.SESSION_LOW_OFFER:
			entryType = Type.ASK;
			entry.addDescriptor(Descriptor.PERIOD_SESSION);
			break;

		case MDEntryType.EARLY_PRICES:
			entryType = Type.OPEN;
			entry.addDescriptor(Descriptor.STATUS_MARKET_PRE_OPEN);
			break;

		case MDEntryType.AUCTION_CLEARING_PRICE:
			entryType = Type.SETTLE;
			entry.addDescriptor(Descriptor.SETTLE_AUCTION);
			break;

		default:
			entryType = Type.UNKNOWN_TYPE;
			break;
		}

		entry.setType(entryType);

	}

	public static String getMessageType(final Message message) {

		return Util.getString(message.getHeader(), MsgType.FIELD);

	}

	private static boolean isInvalid(final String text) {
		return text == null || text.length() == 0;
	}

	private UtilType() {
	}

}
