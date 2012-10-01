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

public enum FixTag {

	// http://fixprotocol.org/FIXimate3.0/

	// NOTE: do not change tag names & numbers;

	// Format: TagName(TagNumber)

	// NOTE: zero is invalid FIX tag value
	UNKNOWN(0), //

	Account(1), //

	BeginString(8), //

	BodyLength(9), //

	CheckSum(10), //

	ClOrdID(11), //

	Currency(15), //

	HandlInst(21), //

	SecurityIDSource(22), //

	MsgSeqNum(34), //

	MsgType(35), //

	NewSeqNo(36), //

	SecurityID(48), //

	SendingTime(52), //

	Side(54), //

	Symbol(55), //

	TransactTime(60), //

	SettlDate(64), //

	HeartBtInt(108), //

	SettlCurrency(120), //

	//

	TradSesStartTime(341), //
	TradSesOpenTime(342), //
	TradSesCloseTime(344), //
	TradSesEndTime(345), //

	//

	SecurityExchange(207), //

	SecurityGroup(1151), //

	SecurityType(167), //

	SecuritySubType(762), //

	SecurityDesc(107), //

	MaturityMonthYear(200), //

	SecurityUpdateAction(980), //

	MDQuoteType(1070), //

	//

	SenderCompID(49), //

	TradeDate(75), //

	RptSeq(83), //

	MDEntryType(269), //

	MDEntryPx(270), //

	MDEntrySize(271), //

	TickDirection(274), //

	QuoteCondition(276), //

	TradeCondition(277), //

	MDUpdateAction(279), //

	OpenCloseSettlFlag(286), //

	UnderlyingSecurityIDSource(305), //

	UnderlyingSecurityID(309), //

	UnderlyingSymbol(311), //

	SecurityTradingStatus(326), //

	NumberOfOrders(346), //

	LastMsgSeqNumProcessed(369), //

	NetChgPrevDay(451), //

	NoSecurityAltID(454), //

	SecurityAltID(455), //

	SecurityAltIDSource(456), //

	CFICode(461), //

	LegCurrency(556), //

	LegPrice(566), //

	LegCFICode(608), //

	LegMaturityMonthYear(610), //

	LegStrikePrice(612), //

	SettlPriceType(731), //

	LegSecuritySubType(764), //

	LegOptionRatio(1017), //

	LegSecurityGroup(5795), //

	TradeVolume(1020), //

	MDPriceLevel(1023), //

	StrikePrice(202), //

	UnderlyingProduct(462), //

	NoLegs(555), //

	LegSymbol(600), //

	LegSecurityID(602), //

	LegSecurityIDSource(603), //

	LegSecurityDesc(620), //

	LegRatioQty(623), //

	LegSide(624), //

	MinTradeVol(562), //

	NoUnderlyings(711), //

	//
	NoInstrAttrib(870), //
	InstrAttribType(871), //
	InstrAttribValue(872), //

	LastUpdateTime(779), //

	TotNumReports(911), //

	StrikeCurrency(947), //

	MinPriceIncrement(969), //

	UnitOfMeasure(996),

	ApplVerID(1128), //

	MaxTradeVol(1140), //

	MDBookType(1021), //

	//
	NoMDFeedTypes(1141), //
	MarketDepth(264), //
	MDFeedType(1022), //

	LotType(1093), //

	MinLotSize(1231), //

	NoLotTypeRules(1234), //

	MatchAlgorithm(1142), //

	MaxPriceVariation(1143), //

	ImpliedMarketIndicator(1144), //

	MinPriceIncrementAmount(1146), //

	EventTime(1145), //

	UnitOfMeasureQty(1147), //

	LowLimitPrice(1148), //

	HighLimitPrice(1149), //

	TradingReferencePrice(1150), //

	SecurityTradingEvent(1174), //

	MDSecurityTradingStatus(1682), //

	ApplID(1180), //

	HighPx(332), //

	LowPx(333), //

	ExpirationCycle(827), //

	NoEvents(864), //
	EventType(865), //
	EventDate(866), //

	NoMDEntries(268), //
	MDEntryDate(272), //
	MDEntryTime(273), //
	TradingSessionID(336), //

	// CME custom exchange-defined fields (registered)
	// http://www.fixprotocol.org/specifications/fields/search
	// http://www.fixprotocol.org/specifications/fields/9000-9999

	FixingBraket(5790), //
	TradingReferenceDate(5796), //
	AggressorSide(5797), //
	MatchEventStartIndicator(5799), //
	DisplayFactor(9787), //
	UserDefinedInstrument(9779), //
	MinCabPrice(9850), //
	MaxCabPrice(9851), //

	// CQG custom
	// overlap reserved range
	// http://www.fixprotocol.org/specifications/fields/5000-5999

	CQG_NoConnections(5001), // error
	CQG_NoConnectionsNEW(20001), // custom

	CQG_ConnectionType(5002), // error
	CQG_ConnectionTypeNEW(20002), // custom

	CQG_ConnectionIPAddress(5003), // error
	CQG_ConnectionIPAddressNEW(20003), // custom

	CQG_ConnectionPortNumber(5004), // error
	CQG_ConnectionPortNumberNEW(20004), // custom

	CQG_NoTradingSessions(5005), // error
	NoTradingSessions(386), // standard

	CQG_TradingDate(5006), // error
	// standard -> TradeDate(75)

	/*
	 * Tag 5007: CQGSecurityName will contain an instrument's CQG symbol that is
	 * being used in CQG Integrated Client.
	 */
	CQG_SecurityNameOne(5007), // error
	CQG_SecurityNameOneNEW(20007), // custom
	/*
	 * Tag 5008: SecurityName will contain a textual description of a financial
	 * instrument.
	 */
	CQG_SecurityNameTwo(5008), // error
	CQG_SecurityNameTwoNEW(20008), // custom
	/*
	 * Tag 5009: MostActiveFlag will indicate the commodity contract with the
	 * greatest volume for the previous day.
	 */
	CQG_MostActiveFlag(5009), // error
	CQG_MostActiveFlagNEW(20009), // custom

	CQG_MDWorkupState(200016) // custom

	;

	final public int code;

	final public String codeString;

	FixTag(final int code) {
		this.code = code;
		this.codeString = Integer.toString(code);
	}

	private final static Logger log = LoggerFactory.getLogger(FixTag.class);

	private final static FixTag[] ENUM_VALUES = values();

	public final static FixTag of(final int code) {
		for (final FixTag known : ENUM_VALUES) {
			if (known.code == code) {
				return known;
			}
		}
		log.error("UNKNOWN : {}", code);
		return UNKNOWN;
	}

	public final static FixTag of(final String name) {
		for (final FixTag known : ENUM_VALUES) {
			if (known.name().equals(name)) {
				return known;
			}
		}
		log.error("UNKNOWN : {}", name);
		return UNKNOWN;
	}

}
