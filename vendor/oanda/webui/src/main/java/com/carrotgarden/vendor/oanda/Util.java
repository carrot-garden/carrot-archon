package com.carrotgarden.vendor.oanda;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class Util {

	private static final Logger log = LoggerFactory.getLogger(Util.class);

	public static Config reference() {

		/** development-only file to emulate os/env */
		final String name = ".properties";
		final File file = new File(name);

		if (file.exists()) {
			log.info("properties: {}", file.getAbsolutePath());
			final Config props = ConfigFactory.parseFile(file);
			return ConfigFactory.parseResources("reference.conf")
					.withFallback(props).resolve();
		} else {
			return ConfigFactory.defaultReference();
		}

	}

}
