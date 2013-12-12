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

public enum FixEntryType {

	// tag 269

	// http://www.fixprotocol.org/FIXimate3.0/en/FIX.5.0SP2/tag269.html

	// non-FIX value
	UNKNOWN('\u0200'), //

	Bid('0'), //

	Offer('1'), //

	Trade('2'), //

	IndexValue('3'), //

	OpeningPrice('4'), //

	ClosingPrice('5'), //

	SettlementPrice('6'), //

	SessionHighPrice('7'), //

	SessionLowPrice('8'), //

	SessionVwapPrice('9'), //

	Imbalance('A'), //

	TradeVolume('B'), //

	OpenInterest('C'), //

	CompositeUnderlyingPrice('D'), //

	SimulatedSellPrice('E'), //

	SimulatedBuyPrice('F'), //

	MarginRate('G'), //

	MidPrice('H'), //

	EmptyBook('J'), // AKA book reset

	SettleHighPrice('K'), //

	SettleLowPrice('L'), //

	PriorSettlePrice('M'), //

	SessionHighBid('N'), //

	SessionLowOffer('O'), //

	EarlyPrices('P'), //

	AuctionClearingPrice('Q'), //

	DailyValueAdjustmentLongPsitions('R'), //

	SwapValueFactor('S'), //

	CumulativeValueAdjustmentLongPositions('T'), //

	DailyValueAdjustmentShortPositions('U'), //

	CumulativeValueAdjustmentShortPositions('V'), //

	FixingPrice('W'), //

	CashRate('X'), //

	RecoveryRate('Y'), //

	RecoveryRateLong('Z'), //

	RecoveryRateShort('a'), //

	CQG_WorkupTrade('w'), //

	;

	FixEntryType(final char code) {
		this.code = code;
	}

	public final char code;

	private final static Logger log = LoggerFactory
			.getLogger(FixEntryType.class);

	private static final FixEntryType[] ENUM_VALUES = values();

	public final static FixEntryType of(final char code) {

		// optimization
		switch (code) {
			case '0' :
				return Bid;
			case '1' :
				return Offer;
			case '2' :
				return Trade;
			case 'B' :
				return TradeVolume;
			default :
				for (final FixEntryType known : ENUM_VALUES) {
					if (known.code == code) {
						return known;
					}
				}
				log.error("UNKNOWN : {}", code);
				return UNKNOWN;
		}

	}

}
