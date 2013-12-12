package com.carrotgarden.proto.data.fast;

import org.openfast.GroupValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.carrotgarden.proto.data.MarketDataEntry;
import com.carrotgarden.proto.data.MarketDataEntry.Descriptor;
import com.carrotgarden.util.enums.fix.FixTag;
import com.carrotgarden.util.openfast.OpenFastUtil;

public final class UtilDescriptor {

	private static final Logger log = LoggerFactory
			.getLogger(UtilDescriptor.class);

	public static void applyDescriptor(final GroupValue group,
			final MarketDataEntry.Builder entry) {

		final String tradeCondition = OpenFastUtil.getString(group,
				FixTag.TradeCondition);

		if (tradeCondition != null) {
			entry.addDescriptor(Descriptor.TRADE_CANCELED);
		}

		final String quoteCondition = OpenFastUtil.getString(group,
				FixTag.QuoteCondition);

		if (quoteCondition != null) {
			entry.addDescriptor(Descriptor.BOOK_IMPLIED);
		}

		final String flagOpenCloseSettle = OpenFastUtil.getString(group,
				FixTag.OpenCloseSettlFlag);

		if (flagOpenCloseSettle != null) {
			entry.addDescriptor(Descriptor.SETTLE_UNOFFICIAL);
		}

	}

	private UtilDescriptor() {
	}

}
