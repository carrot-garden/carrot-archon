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

public enum FixCodeCFI {

	/* keep most specific first; 'X' means 'any' */

	// equity
	EQUITY("EXXXXX"), //

	// future

	FUTURE_SPREAD("FMXXXX"), //
	FUTURE_COMMODITY("FCXXXX"), //
	FUTURE_FINANCIAL("FFXXXX"), //

	FUTURE("FXXXXX"), //

	// option

	OPTION_CALL_EUROPEAN("OCEXXX"), //
	OPTION_PUT_EUROPEAN("OPEXXX"), //
	OPTION_CALL_AMERICAN("OCAXXX"), //
	OPTION_PUT_AMERICAN("OPAXXX"), //

	OPTION_SPREAD("OMXXXX"), //
	OPTION_CALL("OCXXXX"), //
	OPTION_PUT("OPXXXX"), //

	OPTION("OXXXXX"), //

	// other

	INDEX("MRIXXX"), //

	ASSET("MXXXXX"), //

	INDEX_CQG_1("XXXXXC"), //
	INDEX_CQG_2("XXXXCV"), //

	//

	UNKNOWN("??????"), //

	;

	public static final char ANY = 'X';
	public static final char NONE = '?';
	public static final int SIZE = 6;

	private static final Logger log = LoggerFactory.getLogger(FixCodeCFI.class);

	private final String code;

	FixCodeCFI(String code) {

		assert code.length() == SIZE;

		this.code = code;

	}

	private final static FixCodeCFI[] ENUM_VALS = values();

	private static String padAny(String codeCFI) {
		char[] array = new char[SIZE];
		int length = codeCFI.length();
		codeCFI.getChars(0, length, array, 0);
		while (length < SIZE) {
			array[length++] = ANY;
		}
		return new String(array);
	}

	public static final FixCodeCFI fromCode(/* locala */String codeCFI) {
		if (codeCFI == null) {
			return UNKNOWN;
		}
		if (codeCFI.length() < SIZE) {
			codeCFI = padAny(codeCFI);
		}
		for (final FixCodeCFI instance : ENUM_VALS) {
			if (instance.equalsMask(codeCFI)) {
				return instance;
			}
		}
		log.error("unknown codeCFI={}", codeCFI);
		return UNKNOWN;
	}

	private final boolean equalsMask(final String codeCFI) {

		charLoop : for (int k = 0; k < SIZE; k++) {
			char thisChar = this.code.charAt(k);
			if (thisChar == ANY) {
				// ignore
				continue charLoop;
			}
			if (thisChar == codeCFI.charAt(k)) {
				// match
				continue charLoop;
			}
			return false; // first non match
		}

		return true; // total match

	}

	final public String getCode() {
		return code;
	}

}
