/**
 * Copyright (C) 2010-2013 Andrei Pozolotin <Andrei.Pozolotin@gmail.com>
 *
 * All rights reserved. Licensed under the OSI BSD License.
 *
 * http://www.opensource.org/licenses/bsd-license.php
 */
package com.carrotgarden.dummy;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestHelloJUnit {

	@Test
	public void testMethod() {

		System.out.println("*** hello junit ***");

		assertTrue(true);

	}

}
