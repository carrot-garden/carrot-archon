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

public enum FixUnderlyingProduct {

	UNKNOWN(0), // fix invalid value

	// http://www.fixprotocol.org/FIXimate3.0/en/FIX.5.0SP2/tag462.html

	AGENCY(1),

	COMMODITY(2), // 

	CORPORATE(3), // 

	CURRENCY(4), // 

	EQUITY(5), // 

	GOVERNMENT(6), // 

	INDEX(7), // 

	LOAN(8), // 

	MONEYMARKET(9), // 

	MORTGAGE(10), // 

	MUNICIPAL(11), // 

	OTHER(12), // 

	FINANCING(13), // 

	;

	public final int code;

	FixUnderlyingProduct(final int code) {
		this.code = code;

	}

	private final static Logger log = LoggerFactory
			.getLogger(FixUnderlyingProduct.class);

	private final static FixUnderlyingProduct[] ENUM_VALUES = values();

	public final static FixUnderlyingProduct of(final int code) {
		for (final FixUnderlyingProduct known : ENUM_VALUES) {
			if (known.code == code) {
				return known;
			}
		}
		log.error("UNKNOWN : {}", code);
		return UNKNOWN;
	}

}
