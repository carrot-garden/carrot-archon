/**
 * Copyright (C) 2010-2012 Andrei Pozolotin <Andrei.Pozolotin@gmail.com>
 *
 * All rights reserved. Licensed under the OSI BSD License.
 *
 * http://www.opensource.org/licenses/bsd-license.php
 */
package com.carrotgarden.util.enums.fix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum FixOpenCloseSettleFlag implements PresentInString {

	UNKNOWN("?"), // fix invalid value

	// http://www.fixprotocol.org/FIXimate3.0/en/FIX.5.0SP2/tag286.html

	NONE(""), //

	DailyEntry("0"), //

	SessionEntry("1"), //

	DeliveryEntry("2"), //

	ExpectedEntry("3"), //

	PreviousEntry("4"), //

	TheoreticalEntry("5"), //

	NetChangePreliminary("100"), // CME

	ActualPreliminary("101"), // CME

	SettlementIndexValue("102"), // CME

	SpecialOpeningQuote("103"), // CME

	;

	private static final Logger log = LoggerFactory
			.getLogger(FixOpenCloseSettleFlag.class);

	public final String code;

	FixOpenCloseSettleFlag(final String code) {
		this.code = code;
	}

	@Override
	public boolean isPresentIn(final String value) {

		if (value == null) {
			return false;
		}

		return value.contains(this.code);

	}

	private final static FixOpenCloseSettleFlag[] ENUM_VALUES = values();

	public final static FixOpenCloseSettleFlag of(final String code) {
		if (code == null || code.length() == 0) {
			return NONE;
		}
		for (final FixOpenCloseSettleFlag known : ENUM_VALUES) {
			if (known.code.equals(code)) {
				return known;
			}
		}
		// log.error("UNKNOWN : {}", code);
		return UNKNOWN;
	}

	public char getChar() {
		return code.charAt(0);
	}

}
