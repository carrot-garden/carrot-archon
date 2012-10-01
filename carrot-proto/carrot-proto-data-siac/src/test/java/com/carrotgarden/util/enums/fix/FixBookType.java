/**
 * Copyright (C) 2010-2012 Andrei Pozolotin <Andrei.Pozolotin@gmail.com>
 *
 * All rights reserved. Licensed under the OSI BSD License.
 *
 * http://www.opensource.org/licenses/bsd-license.php
 */
package com.carrotgarden.util.enums.fix;

public enum FixBookType {

	// tag 1021

	// http://www.fixprotocol.org/FIXimate3.0/en/FIX.5.0SP2/tag1021.html

	TopOfBook(1), //

	PriceDepth(2), //

	OrderDepth(3), //

	;

	public final int code;

	FixBookType(final int code) {
		this.code = code;
	}

}
