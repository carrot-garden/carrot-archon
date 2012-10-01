package com.carrotgarden.proto.data.fix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quickfix.FieldMap;
import quickfix.Message;
import quickfix.field.SecurityID;
import quickfix.field.SenderCompID;
import quickfix.field.Symbol;
import quickfix.field.TargetCompID;

public final class UtilIdentity {

	private static final Logger log = LoggerFactory
			.getLogger(UtilIdentity.class);

	public static String getIdentity(final Message.Header header,
			final FieldMap group) {

		final String vendorId = getSenderCompanyId(header);
		final String securityId = getSecurityID(group);
		final String symbol = getSymbol(group);

		return MarketId.getIdentity(vendorId, securityId, symbol);

	}

	public static String getSecurityID(final FieldMap group) {
		return Util.getString(group, SecurityID.FIELD);
	}

	public static String getTargetCompanyId(final FieldMap group) {
		return Util.getString(group, TargetCompID.FIELD);
	}

	public static String getSenderCompanyId(final FieldMap group) {
		return Util.getString(group, SenderCompID.FIELD);
	}

	public static String getSymbol(final FieldMap group) {
		return Util.getString(group, Symbol.FIELD);
	}

	public static Long getTargetId(final Message.Header header,
			final FieldMap group, final MarketMapper mapper) {

		final String sourceId = getIdentity(header, group);

		final Long targetId = mapper.getTargetId(sourceId);

		return targetId;

	}

	private UtilIdentity() {
	}

}
