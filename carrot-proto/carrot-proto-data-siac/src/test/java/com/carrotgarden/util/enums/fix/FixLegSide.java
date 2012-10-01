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

public enum FixLegSide {

	// NOTE: do not change ordinal(), used in persistence

	// http://www.fixprotocol.org/FIXimate3.0/en/FIX.5.0SP2/tag624.html

	UNKNOWN('?'), // non fix value

	Buy('1'), //

	Sell('2'), //

	BuyMinus('3'), //

	SellPlus('4'), //

	SellShort('5'), //

	SellShortExempt('6'), //

	Undisclosed('7'), //

	Cross('8'), //

	CrossShort('9'), //

	CrossShortExempt('A'), //

	AsDefined('B'), //

	Opposite('C'), //

	Subscribe('D'), //

	Redeem('E'), // 

	Lend('F'), // 

	Borrow('G'), // 

	;

	public final char code;

	FixLegSide(char code) {
		this.code = code;
	}

	private final static Logger log = LoggerFactory.getLogger(FixLegSide.class);

	private final static FixLegSide[] ENUM_VALUES = values();

	public final static FixLegSide of(final char code) {
		for (final FixLegSide known : ENUM_VALUES) {
			if (known.code == code) {
				return known;
			}
		}
		log.error("UNKNOWN : {}", code);
		return UNKNOWN;
	}

}
