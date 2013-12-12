/**
 * Copyright (C) 2010-2012 Andrei Pozolotin <Andrei.Pozolotin@gmail.com>
 *
 * All rights reserved. Licensed under the OSI BSD License.
 *
 * http://www.opensource.org/licenses/bsd-license.php
 */
package com.carrotgarden.util.enums.fix;

import java.util.EnumMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class FixAttributeMap extends EnumMap<FixAttributeType, String> {

	private static final Logger log = LoggerFactory
			.getLogger(FixAttributeMap.class);

	public FixAttributeMap() {
		super(FixAttributeType.class);
	}

	public int getInt(FixAttributeType type, int defaultValue) {
		if (type == null) {
			log.debug("type == null");
			return defaultValue;
		}
		String text = get(type);
		if (text == null) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(text);
		} catch (Exception e) {
			return defaultValue;
		}
	}

}
