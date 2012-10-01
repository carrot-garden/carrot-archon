package com.carrotgarden.proto.data.fix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quickfix.FieldMap;
import quickfix.field.MDUpdateAction;

import com.carrotgarden.proto.data.MarketDataEntry;
import com.carrotgarden.proto.data.MarketDataEntry.Action;

public final class UtilAction {

	private static final Logger log = LoggerFactory.getLogger(UtilAction.class);

	public static void apply(final FieldMap group,
			final MarketDataEntry.Builder entry) {

		final String action = Util.getString(group, MDUpdateAction.FIELD);

		apply(action, entry);

	}

	/** http://fixprotocol.org/FIXimate3.0/en/FIX.5.0SP2/tag279.html */
	public static void apply(final String action,
			final MarketDataEntry.Builder entry) {

		if (isInvalid(action)) {
			entry.setAction(Action.UNKNOWN_ACTION);
			return;
		}

		final MarketDataEntry.Action entryAction;

		switch (action.charAt(0)) {

		case MDUpdateAction.NEW:
			entryAction = Action.ADD;
			break;

		case MDUpdateAction.CHANGE:
			entryAction = Action.EDIT;
			break;

		case MDUpdateAction.DELETE:
		case MDUpdateAction.DELETE_FROM:
		case MDUpdateAction.DELETE_THRU:
			entryAction = Action.REMOVE;
			break;

		case '5': // overlay
			entryAction = Action.EDIT;
			break;

		default:
			entryAction = Action.UNKNOWN_ACTION;
			break;

		}

		entry.setAction(entryAction);

	}

	private static boolean isInvalid(final String text) {
		return text == null || text.length() == 0;
	}

	private UtilAction() {
	}

}
