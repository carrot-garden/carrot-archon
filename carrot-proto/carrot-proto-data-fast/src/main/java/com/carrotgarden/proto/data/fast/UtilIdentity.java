package com.carrotgarden.proto.data.fast;

import org.openfast.GroupValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.carrotgarden.util.enums.fix.FixTag;
import com.carrotgarden.util.openfast.OpenFastUtil;

public final class UtilIdentity {

	private static final Logger log = LoggerFactory
			.getLogger(UtilIdentity.class);

	public static String getIdentity(final String vendorId,
			final GroupValue group) {

		final String securityId = getSecurityID(group);
		final String symbol = getSymbol(group);

		return MarketId.getIdentity(vendorId, securityId, symbol);

	}

	public static String getSecurityID(final GroupValue group) {

		return OpenFastUtil.getString(group, FixTag.SecurityID);

	}

	public static String getSymbol(final GroupValue group) {

		return OpenFastUtil.getString(group, FixTag.Symbol);

	}

	public static Long getTargetId(final String vendorId,
			final GroupValue group, final MarketMapper mapper) {

		final String sourceId = getIdentity(vendorId, group);

		final Long targetId = mapper.getTargetId(sourceId);

		return targetId;

	}

	private UtilIdentity() {
	}

}
