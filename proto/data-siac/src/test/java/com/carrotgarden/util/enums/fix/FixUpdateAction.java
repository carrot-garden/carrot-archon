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

public enum FixUpdateAction {

	// http://fixprotocol.org/FIXimate3.0/en/FIX.5.0SP2/tag279.html

	// non-FIX values
	NONE('\u0200'), // 
	UNKNOWN('\u0201'), //

	//

	New('0'), // a.k.a. "Add"

	Change('1'), //

	Delete('2'), //

	DeleteThru('3'), //

	DeleteFrom('4'), //

	Overlay('5'), //

	;

	FixUpdateAction(char code) {
		this.code = code;
	}

	public final char code;

	private final static Logger log = LoggerFactory
			.getLogger(FixUpdateAction.class);

	public static final FixUpdateAction of(final char code) {
		switch (code) {
			case '0' :
				return New;
			case '1' :
				return Change;
			case '2' :
				return Delete;
			case '3' :
				return DeleteThru;
			case '4' :
				return DeleteFrom;
			case '5' :
				return Overlay;
			default :
				log.error("UNKNOWN : {}", code);
				return UNKNOWN;
		}
	}

}
