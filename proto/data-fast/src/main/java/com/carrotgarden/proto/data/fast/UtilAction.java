package com.carrotgarden.proto.data.fast;

import org.openfast.GroupValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.carrotgarden.proto.data.MarketDataEntry;
import com.carrotgarden.proto.data.MarketDataEntry.Action;
import com.carrotgarden.util.enums.fix.FixUpdateAction;
import com.carrotgarden.util.openfast.OpenFastUtil;

public final class UtilAction {

	private static final Logger log = LoggerFactory.getLogger(UtilAction.class);

	public static void apply(final GroupValue group,
			final MarketDataEntry.Builder entry) {

		final FixUpdateAction action = OpenFastUtil.getUdateAction(group);

		apply(action, entry);

	}

	/** http://fixprotocol.org/FIXimate3.0/en/FIX.5.0SP2/tag279.html */
	public static void apply(final FixUpdateAction action,
			final MarketDataEntry.Builder entry) {

		final Action entryAction;

		switch (action) {

		case New:
			entryAction = Action.ADD;
			break;

		case Change:
			entryAction = Action.EDIT;
			break;

		case Delete:
		case DeleteFrom:
		case DeleteThru:
			entryAction = Action.REMOVE;
			break;

		case Overlay:
			entryAction = Action.EDIT;
			break;

		default:
			entryAction = Action.UNKNOWN_ACTION;
			break;

		}

		entry.setAction(entryAction);

	}

	private UtilAction() {
	}

}
