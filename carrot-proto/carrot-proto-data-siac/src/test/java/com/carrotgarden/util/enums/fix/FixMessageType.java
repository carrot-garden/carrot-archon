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

public enum FixMessageType {

	// NONE("NONE"), //

	UNKNOWN("UNKNOWN"), //

	//

	Heartbeat("0"), //

	//

	Logout("5"), //

	Login("A"), //

	//

	News("B"), //

	Status("f"), //

	//

	RequestQuote("R"), //

	RequestData("V"), //

	RequestReject("Y"), //

	//

	Definition("d"), //

	Snapshot("W"), //

	Update("X"), //

	SequenceReset("4"), //

	//

	SecurityDefinitionRequest("c"), //

	//

	;

	public final String code;

	FixMessageType(String code) {
		this.code = code;
	}

	private final static Logger log = LoggerFactory
			.getLogger(FixMessageType.class);

	public final static FixMessageType formCode(final String codeString) {

		if (codeString == null) {
			return UNKNOWN;
		}

		// optimization
		if (codeString.length() == 1) {
			switch (codeString.charAt(0)) {
				case 'X' :
					return Update;
				case 'W' :
					return Snapshot;
				case 'd' :
					return Definition;
				default :
					break;
			}
		}

		return fromCodeSlow(codeString);

	}

	private final static FixMessageType[] ENUM_VALUES = values();

	public final static FixMessageType fromCodeSlow(final String type) {
		for (final FixMessageType known : ENUM_VALUES) {
			if (known.code.equals(type)) {
				return known;
			}
		}
		log.error("UNKNOWN : {}", type);
		return UNKNOWN;
	}

}
