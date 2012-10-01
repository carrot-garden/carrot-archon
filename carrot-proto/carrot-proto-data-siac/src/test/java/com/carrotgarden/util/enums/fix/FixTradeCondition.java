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

public enum FixTradeCondition implements PresentInString {

	// http://www.fixprotocol.org/FIXimate3.0/en/FIX.5.0SP2/tag277.html

	UNKNOWN("?"), // fix invalid value

	Cash_Market("A"), //

	Average_Price_Trade("B"), //

	Cash_Trade("C"), //

	Next_Day_Market("D"), //

	Opening_Trade("E"), //

	Intraday_Trade_Detail("F"), //

	Rule_127_Trade("G"), //

	Rule_155_Trade("H"), //

	Sold_Last("I"), //

	Next_Day_Trade("J"), //

	Opened("K"), //

	Seller("L"), //

	Sold("M"), //

	Stopped_Stock("N"), //

	Imbalance_More_Buyers("P"), //

	Imbalance_More_Sellers("Q"), //

	Opening_Price("R"), //

	Bargain_Condition("S"), //

	Converted_Price_Indicator("T"), //

	Exchange_Last("U"), //

	Final_Price_of_Session("V"), //

	Ex_pit("W"), //

	Crossed("X"), //

	Trades_resulting_from_manual_slow_quote("Y"), //

	Trades_resulting_from_intermarket_sweep("Z"), //

	Volume_Only("a"), //

	Direct_Plus("b"), //

	Acquisition("c"), //

	Bunched("d"), //

	Distribution("e"), //

	Bunched_Sale("f"), //

	Split_Trade("g"), //

	Cancel_Stopped("h"), //

	Cancel_ETH("i"), //

	Cancel_Stopped_ETH("j"), //

	Out_of_Sequence_ETH("k"), //

	Cancel_Last_ETH("l"), //

	Sold_Last_Sale_ETH("m"), //

	Cancel_Last("n"), //

	Sold_Last_Sale("o"), //

	Cancel_Open("p"), //

	Cancel_Open_ETH("q"), //

	Opened_Sale_ETH("r"), //

	Cancel_Only("s"), //

	Cancel_Only_ETH("t"), //

	Late_Open_ETH("u"), //

	Auto_Execution_ETH("u"), //

	Reopen("w"), //

	Reopen_ETH("x"), //

	Adjusted("y"), //

	Adjusted_ETH("z"), //

	Spread("AA"), //

	Spread_ETH("AB"), //

	Straddle("AC"), //

	Straddle_ETH("AD"), //

	Stopped("AE"), //

	Stopped_ETH("AF"), //

	Regular_ETH("AG"), //

	Combo("AH"), //

	Combo_ETH("AI"), //

	Official_Closing_Price("AJ"), //

	Prior_Reference_Price("AK"), //

	Cancel("0"), //

	Stopped_Sold_Last("AL"), //

	Stopped_Out_of_Sequence("AM"), //

	Offical_Closing_Price("AN"), //

	Crossed_2("AO"), //

	Fast_Market("AP"), //

	Automatic_Execution("AQ"), //

	Form_T("AR"), //

	Basket_Index("AS"), //

	Burst_Basket("AT"), //

	Outside_Spread("AV"), //

	Implied_Trade("1"), //

	Marketplace_entered_trade("2"), //

	Mult_Asset_Class_Multileg_Trade("3"), //

	Multileg_to_Multileg_Trade("4"), //

	;

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory
			.getLogger(FixTradeCondition.class);

	public final String code;
	public final int codeLength;
	public final char charCode0;
	public final char charCode1;

	FixTradeCondition(final String code) {
		this.code = code;
		this.codeLength = code.length();
		this.charCode0 = code.charAt(0);
		if (codeLength > 1) {
			this.charCode1 = code.charAt(0);
		} else {
			this.charCode1 = '?';
		}

	}

	@Override
	public final boolean isPresentIn(final String value) {

		if (value == null || value.length() == 0) {
			return false;
		}

		final int valueLength = value.length();

		// optimization
		if (valueLength == 1 && codeLength == 1) {
			return value.charAt(0) == charCode0;
		}

		// optimization
		if (valueLength == 2 && codeLength == 2) {
			return value.charAt(0) == charCode0 && value.charAt(1) == charCode1;
		}

		final String[] array = value.split(" ");

		for (final String item : array) {
			if (item.equals(code)) {
				return true;
			}
		}

		return false;

	}

}
