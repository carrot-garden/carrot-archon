package com.carrotgarden.proto.bundle;

import com.carrotgarden.proto.feed.MarketDataEntryOrBuilder;
import com.carrotgarden.proto.feed.MarketDataOrBuilder;

/**
 * special contract on message/entry relationship
 * 
 * helps additional compression and program flow
 */
public final class MessageRules {

	/** entry overrides message; 0 is default */
	public static long getMarketId(final MarketDataOrBuilder message,
			final MarketDataEntryOrBuilder entry) {

		if (entry.hasMarketId()) {
			return entry.getMarketId();
		}

		if (message.hasMarketId()) {
			return message.getMarketId();
		}

		return 0;

	}

	/** entry overrides message; 0 is default */
	public static int getPriceExponent(final MarketDataOrBuilder message,
			final MarketDataEntryOrBuilder entry) {

		if (entry.hasPriceExponent()) {
			return entry.getPriceExponent();
		}

		if (message.hasPriceExponent()) {
			return message.getPriceExponent();
		}

		return 0;

	}

	/** entry is offset to message; 0 is default */
	public static long getSequence(final MarketDataOrBuilder message,
			final MarketDataEntryOrBuilder entry) {

		return message.getSequence() + entry.getSequence();

	}

	/** entry overrides message; 0 is default */
	public static int getSizeExponent(final MarketDataOrBuilder message,
			final MarketDataEntryOrBuilder entry) {

		if (entry.hasSizeExponent()) {
			return entry.getSizeExponent();
		}

		if (message.hasSizeExponent()) {
			return message.getSizeExponent();
		}

		return 0;

	}

	/** entry is offset to message; 0 is default */
	public static long getTimeStamp(final MarketDataOrBuilder message,
			final MarketDataEntryOrBuilder entry) {

		return message.getTimeStamp() + entry.getTimeStamp();

	}

	/** entry is offset to message; 0 is default */
	public static int getTradeDate(final MarketDataOrBuilder message,
			final MarketDataEntryOrBuilder entry) {

		return message.getTradeDate() + entry.getTradeDate();

	}

	/** message or entry market id must be present */
	public static boolean hasMarketId(final MarketDataOrBuilder message,
			final MarketDataEntryOrBuilder entry) {

		return message.hasMarketId() || entry.hasMarketId();

	}

	/** entry price mantissa must be present */
	public static boolean hasPrice(final MarketDataOrBuilder message,
			final MarketDataEntryOrBuilder entry) {

		return entry.hasPriceMantissa();

	}

	/** message or entry market trade date must be present */
	public static boolean hasSequence(final MarketDataOrBuilder message,
			final MarketDataEntryOrBuilder entry) {

		return message.hasSequence() || entry.hasSequence();

	}

	/** entry size mantissa must be present */
	public static boolean hasSize(final MarketDataOrBuilder message,
			final MarketDataEntryOrBuilder entry) {

		return entry.hasSizeMantissa();

	}

	/** message or entry market time stamp must be present */
	public static boolean hasTimeStamp(final MarketDataOrBuilder message,
			final MarketDataEntryOrBuilder entry) {

		return message.hasTimeStamp() || entry.hasTimeStamp();

	}

	/** message or entry market trade date must be present */
	public static boolean hasTradeDate(final MarketDataOrBuilder message,
			final MarketDataEntryOrBuilder entry) {

		return message.hasTradeDate() || entry.hasTradeDate();

	}

	private MessageRules() {
	}

}
