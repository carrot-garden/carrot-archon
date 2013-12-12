/**
 * Copyright (C) 2010-2012 Andrei Pozolotin <Andrei.Pozolotin@gmail.com>
 *
 * All rights reserved. Licensed under the OSI BSD License.
 *
 * http://www.opensource.org/licenses/bsd-license.php
 */
package com.carrotgarden.util.enums.fix;

public enum FixSettlPriceType {

	// fix tag 731
	// http://www.fixprotocol.org/FIXimate3.0/en/FIX.5.0SP2/tag731.html

	FINAL(1), //

	THEORETICAL(2), //

	;

	public final int code;

	FixSettlPriceType(final int code) {
		this.code = code;
	}

}
