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

public enum FixQuoteType {

	// http://www.fixprotocol.org/FIXimate3.0/en/FIX.5.0SP2/tag1070.html

	UNKNOWN(-1), // fix invalid value

	Indicative(0), //

	Tradeable(1), //

	Restricted_Tradeable(2), //

	Counter(3), //

	Indicative_and_Tradeable(4), //

	;

	private static final Logger log = LoggerFactory
			.getLogger(FixQuoteType.class);

	public final int code;

	FixQuoteType(final int code) {
		this.code = code;
	}

	private final static FixQuoteType[] ENUM_VALUES = values();

	public final static FixQuoteType from(final int code) {
		for (final FixQuoteType known : ENUM_VALUES) {
			if (known.code == code) {
				return known;
			}
		}
		log.error("UNKNOWN : {}", code);
		return UNKNOWN;
	}

}
