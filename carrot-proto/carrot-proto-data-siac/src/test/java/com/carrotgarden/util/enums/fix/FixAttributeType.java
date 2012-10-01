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

public enum FixAttributeType {

	// FIX tag 871

	// Code to represent the type of instrument attribute
	// http://www.fixprotocol.org/FIXimate3.0/en/FIX.5.0SP2/tag871.html

	UNKNOWN(0), // non FIX value

	//

	Flat(1), //

	ZeroCoupon(2), //

	InterestBearing(3), //

	NoPeriodicPayments(4), //

	VariableRate(5), //

	LessFeeForPut(6), //

	SteppedCoupon(7), //

	CouponPeriod(8), //

	WhenIssued(9), //

	OriginalIssueDiscount(10), //

	CallablePuttable(11), //

	EscrowedToMaturity(12), //

	EscrowedToRedemption(13), //

	PreRefunded(14), //

	InDefault(15), //

	Unrated(16), //

	Taxable(17), //

	Indexed(18), //

	SubjectToAlternativeMinimumTax(19), //

	OriginalIssueDiscountPrice(20), //

	CallableBelowMaturityValue(21), //

	CallableWithoutNotice(22), //

	PriceTickRules(23), //

	TradeTypeEligibility(24), //

	/** used for fractional prices: "main fraction" */
	InstrumentDenominator(25), //

	/** used for fractional prices: "sub fraction" */
	InstrumentNumerator(26), //

	InstrumentPricePrecision(27), //

	InstrumentStrikePrice(28), //

	TradeableIndicator(29), //

	Text(99), //

	CQG_1000(1000), // undocumented
	CQG_1002(1002), // undocumented

	;

	public final int code;

	private FixAttributeType(int code) {
		this.code = code;
	}

	private final static Logger log = LoggerFactory
			.getLogger(FixAttributeType.class);

	private static final FixAttributeType[] ENUM_VALUES = values();

	public final static FixAttributeType fromCode(final int code) {
		for (final FixAttributeType known : ENUM_VALUES) {
			if (known.code == code) {
				return known;
			}
		}
		log.error("UNKNOWN : {}", code);
		return UNKNOWN;
	}

}
