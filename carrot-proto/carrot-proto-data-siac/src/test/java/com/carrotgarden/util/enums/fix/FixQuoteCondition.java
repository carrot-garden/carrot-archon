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

public enum FixQuoteCondition implements PresentInString {

	// http://fixprotocol.org/FIXimate3.0/en/FIX.5.0SP2/tag276.html

	UNKNOWN("?"), // fix invalid value

	OpenActive("A"), //

	ClosedInactive("B"), //

	ExchangeBest("C"), //

	ConsolidatedBest("D"), //

	Locked("E"), //

	Crossed("F"), //

	Depth("G"), //

	FastTrading("H"), //

	NonFirm("I"), //

	ManualSlowQuote("L"), //

	OutrightPrice("J"), //

	ImpliedPrice("K"), //

	DepthOnOffer("M"), //

	DepthOnBid("N"), //

	Closing("O"), //

	NewsDissemination("P"), //

	TradingRange("Q"), //

	OrderInflux("R"), //

	DueToRelated("S"), //

	NewsPending("T"), //

	AdditionalInfo("U"), //

	AdditionalInfoDueToRelated("V"), //

	Resume("W"), //

	ViewOfCommon("X"), //

	VolumeAlert("Y"), //

	OrderImbalance("Z"), //

	EquipmentChangeover("a"), //

	NoOpenNoResume("b"), //

	RegularETH("c"), //

	AutomaticExecution("d"), //

	AutomaticExecutionETH("e"), //

	FastMarketETH("f"), //

	InactiveETH("g"), //

	Rotation("h"), //

	RotationETH("i"), //

	Halt("j"), //

	HaltETH("k"), //

	DueToNewsDissemination("l"), //

	DueToNewsPending("m"), //

	TradingResume("n"), //

	OutOfSequence("o"), //

	BidSpecialist("p"), //

	OfferSpecialist("q"), //

	BidOfferSpecialist("r"), //

	EndOfDaySAM("s"), //

	ForbiddenSAM("t"), //

	FrozenSAM("u"), //

	PreOpeningSAM("v"), //

	OpeningSAM("w"), //

	OpenSAM("x"), //

	SurveillanceSAM("y"), //

	Suspended("z"), // SAM

	Reserved("0"), // SAM

	NoActiveSAM("1"), //

	Restricted("2"), //

	RestOfBookVWAP("3"), //

	BetterPricesInConditionalOrders("4"), //

	MedianPrice("5"), //

	FullCurve("6"), //

	FlatCurve("7"), //
	;

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory
			.getLogger(FixQuoteCondition.class);

	public final String code;
	public final char charCode;

	FixQuoteCondition(final String code) {
		this.code = code;
		this.charCode = code.charAt(0);
	}

	@Override
	public final boolean isPresentIn(final String value) {

		if (value == null || value.length() == 0) {
			return false;
		}

		// optimization
		if (value.length() == 1) {
			return value.charAt(0) == charCode;
		}

		return value.contains(this.code);

	}

}
