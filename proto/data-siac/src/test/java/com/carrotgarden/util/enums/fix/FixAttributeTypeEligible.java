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

public enum FixAttributeTypeEligible {

	// FIX tag 871=24; eligibility details

	// Code to represent the type of instrument attribute
	// http://www.fixprotocol.org/FIXimate3.0/en/FIX.5.0SP2/tag871.html

	UNKNOWN(0), // non FIX value

	//

	Electronic_Match(1), //  

	Order_Cross(2), //

	Block_Trade(3), //

	EFP(4), // 

	EBF(5), // 

	EFS(6), // 

	EFR(7), // 

	OTC(8), // 

	Indicative_Mass_Quoting(9), //   

	Negative_Strike(10), // 

	Negative_Price_Outright(11), // 

	Fractional_Display_Price(12), // 

	Volatility_Quoted_Option(13), // 

	RFQ_Cross(14), // 

	Zero_Price_Outright(16), // 

	CME_18(18), // 

	;

	public final int code;

	private FixAttributeTypeEligible(int code) {
		this.code = code;
	}

	private final static Logger log = LoggerFactory
			.getLogger(FixAttributeTypeEligible.class);

	private final static FixAttributeTypeEligible[] ENUM_VALUES = values();

	public final static FixAttributeTypeEligible of(final int code) {
		for (final FixAttributeTypeEligible known : ENUM_VALUES) {
			if (known.code == code) {
				return known;
			}
		}
		log.error("UNKNOWN : {}", code);
		return UNKNOWN;
	}

}
