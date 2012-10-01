package com.carrotgarden.proto.data.fix;

public final class MarketId {

	public static String SEPARATOR = "-";
	public static String UNKNOWN_SENDER_ID = "NO_SENDER";
	public static String UNKNOWN_INSTRUMENT_ID = "NO_IDENTITY";

	public static String getIdentity( //
			final String vendorId, //
			final String securityId, //
			final String symbol //
	) {

		final StringBuilder text = new StringBuilder(128);

		if (isValid(vendorId)) {
			text.append(vendorId);
		} else {
			text.append(UNKNOWN_SENDER_ID);
		}

		text.append(SEPARATOR);

		if (isValid(securityId)) {
			text.append(securityId);
		} else if (isValid(symbol)) {
			text.append(symbol);
		} else {
			text.append(UNKNOWN_INSTRUMENT_ID);
		}

		return text.toString();

	}

	private static boolean isValid(final String text) {
		return text != null && text.length() > 0;
	}

	private MarketId() {
	}

}
