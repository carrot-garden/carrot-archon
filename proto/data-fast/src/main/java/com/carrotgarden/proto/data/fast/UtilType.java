package com.carrotgarden.proto.data.fast;

import org.openfast.GroupValue;
import org.openfast.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.carrotgarden.proto.data.MarketDataEntry;
import com.carrotgarden.proto.data.MarketDataEntry.Descriptor;
import com.carrotgarden.proto.data.MarketDataEntry.Type;
import com.carrotgarden.util.enums.fix.FixEntryType;
import com.carrotgarden.util.enums.fix.FixMessageType;
import com.carrotgarden.util.openfast.OpenFastUtil;

public final class UtilType {

	private static final Logger log = LoggerFactory.getLogger(UtilType.class);

	public static MarketDataEntry.Type apply(final GroupValue group,
			final MarketDataEntry.Builder entry) {

		final FixEntryType type = OpenFastUtil.getEntryType(group);

		return apply(type, entry);

	}

	/** http://fixprotocol.org/FIXimate3.0/en/FIX.5.0SP2/tag269.html */
	public static MarketDataEntry.Type apply(final FixEntryType type,
			final MarketDataEntry.Builder entry) {

		final MarketDataEntry.Type entryType;

		switch (type) {

		case Bid:
			entryType = Type.BID;
			break;

		case Offer:
			entryType = Type.ASK;
			break;

		case Trade:
			entryType = Type.TRADE;
			break;

		case IndexValue:
			entryType = Type.TRADE;
			entry.addDescriptor(Descriptor.INDEX_VALUE);
			break;

		case OpeningPrice:
			entryType = Type.OPEN;
			break;

		case ClosingPrice:
			entryType = Type.CLOSE;
			break;

		case SettlementPrice:
			entryType = Type.SETTLE;
			break;

		case SessionHighPrice:
			entryType = Type.HIGH;
			entry.addDescriptor(Descriptor.PERIOD_SESSION);
			break;

		case SessionLowPrice:
			entryType = Type.LOW;
			entry.addDescriptor(Descriptor.PERIOD_SESSION);
			break;

		case SessionVwapPrice:
			entryType = Type.TRADE;
			entry.addDescriptor(Descriptor.PRICE_VWAP);
			break;

		case Imbalance:
			entryType = Type.TRADE;
			entry.addDescriptor(Descriptor.PRICE_IMBALANCE);
			break;

		case TradeVolume:
			entryType = Type.VOLUME;
			break;

		case OpenInterest:
			entryType = Type.INTEREST;
			break;

		case CompositeUnderlyingPrice:
			entryType = Type.UNDERLYING;
			break;

		case SimulatedSellPrice:
			entryType = Type.ASK;
			entry.addDescriptor(Descriptor.PRICE_SIMULATED);
			break;

		case SimulatedBuyPrice:
			entryType = Type.BID;
			entry.addDescriptor(Descriptor.PRICE_SIMULATED);
			break;

		case MarginRate:
			entryType = Type.PERCENT;
			entry.addDescriptor(Descriptor.PERCENT_MARGIN_RATE);
			break;

		case MidPrice:
			entryType = Type.GAP;
			break;

		case EmptyBook:
			entryType = Type.BOOK_RESET;
			break;

		case SettleHighPrice:
			entryType = Type.SETTLE;
			entry.addDescriptor(Descriptor.SETTLE_HIGH);
			break;

		case SettleLowPrice:
			entryType = Type.SETTLE;
			entry.addDescriptor(Descriptor.SETTLE_LOW);
			break;

		case PriorSettlePrice:
			entryType = Type.SETTLE;
			entry.addDescriptor(Descriptor.SETTLE_PREVIOUS);
			break;

		case SessionHighBid:
			entryType = Type.BID;
			entry.addDescriptor(Descriptor.PERIOD_SESSION);
			break;

		case SessionLowOffer:
			entryType = Type.ASK;
			entry.addDescriptor(Descriptor.PERIOD_SESSION);
			break;

		case EarlyPrices:
			entryType = Type.OPEN;
			entry.addDescriptor(Descriptor.STATUS_MARKET_PRE_OPEN);
			break;

		case AuctionClearingPrice:
			entryType = Type.SETTLE;
			entry.addDescriptor(Descriptor.SETTLE_AUCTION);
			break;

		default:
			entryType = Type.UNKNOWN_TYPE;
			break;
		}

		entry.setType(entryType);

		return entryType;

	}

	public static FixMessageType getMessageType(final Message message) {

		return OpenFastUtil.getMessageType(message);

	}

	private UtilType() {
	}

}
