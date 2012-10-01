package com.carrotgarden.trader.util.quickfix;

import static com.carrotgarden.trader.util.quickfix.FixSessionSettings.*;
import static quickfix.FileLogFactory.*;
import static quickfix.FileStoreFactory.*;
import static quickfix.Initiator.*;
import static quickfix.Session.*;
import static quickfix.SessionFactory.*;
import static quickfix.SessionSettings.*;
import static quickfix.mina.ssl.SSLSupport.*;

import java.util.Map.Entry;
import java.util.Set;

import quickfix.SessionID;
import quickfix.SessionSettings;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigValue;

public class FixBuilder {

	private static String getValue(final Config config, final String key) {
		if (config.hasPath(key)) {
			return config.getAnyRef(key).toString();
		} else {
			return "";
		}
	}

	private static String getValue(final ConfigValue value, final String key) {
		return value.unwrapped().toString();
	}

	public static SessionID newSessionId(final Config config) {

		return new SessionID(//
				getValue(config, BEGINSTRING), //
				getValue(config, SENDERCOMPID), //
				getValue(config, SENDERSUBID), //
				getValue(config, SENDERLOCID), //
				getValue(config, TARGETCOMPID), //
				getValue(config, TARGETSUBID), //
				getValue(config, TARGETLOCID), //
				getValue(config, SESSION_QUALIFIER));

	}

	public static SessionSettings addSettings(final Config config) {
		return addSettings(null, config);
	}

	public static SessionSettings addSettings(final SessionSettings settings,
			final Config config) {

		final SessionSettings cfg;
		if (settings == null) {
			cfg = new SessionSettings();
		} else {
			cfg = settings;
		}

		final SessionID id = newSessionId(config);

		final Set<Entry<String, ConfigValue>> entrySet = config.entrySet();

		for (final Entry<String, ConfigValue> entry : entrySet) {

			final String key = entry.getKey();

			final String value = getValue(entry.getValue(), key);

			cfg.setString(id, key, value);

		}

		return cfg;

	}

	public static SessionID newSessionId(final FixSessionSettingsBuilder builder) {

		return new SessionID(//
				builder.beginString, //
				builder.senderCompID, //
				builder.senderSubID, //
				builder.senderLocationID, //
				builder.targetCompID, //
				builder.targetSubID, //
				builder.targetLocationID, //
				builder.sessionQualifier);

	}

	public static SessionSettings newSettings(
			final FixSessionSettingsBuilder builder) {
		return addSettings(null, builder);
	}

	public static SessionSettings addSettings(final SessionSettings settings,
			final FixSessionSettingsBuilder builder) {

		final SessionSettings cfg;
		if (settings == null) {
			cfg = new SessionSettings();
		} else {
			cfg = settings;
		}

		final SessionID id = newSessionId(builder);

		// from session id

		cfg.setString(id, BEGINSTRING, id.getBeginString());

		cfg.setString(id, SENDERCOMPID, id.getSenderCompID());
		cfg.setString(id, SENDERSUBID, id.getSenderSubID());
		cfg.setString(id, SENDERLOCID, id.getSenderLocationID());

		cfg.setString(id, TARGETCOMPID, id.getTargetCompID());
		cfg.setString(id, TARGETSUBID, id.getTargetSubID());
		cfg.setString(id, TARGETLOCID, id.getTargetLocationID());

		cfg.setString(id, SESSION_QUALIFIER, id.getSessionQualifier());

		// from standard settings

		cfg.setString(id, SETTING_DATA_DICTIONARY, builder.dataDictionary);

		cfg.setString(id, SETTING_CONNECTION_TYPE, builder.connectType);
		cfg.setString(id, SETTING_RECONNECT_INTERVAL, builder.connectInterval);

		cfg.setString(id, SETTING_SOCKET_CONNECT_HOST, builder.connectHost);
		cfg.setString(id, SETTING_SOCKET_CONNECT_PORT, builder.connectPort);

		cfg.setString(id, SETTING_USE_SSL, builder.socketUseSSL);

		cfg.setString(id, SETTING_HEARTBTINT, builder.heartbeatInterval);

		cfg.setString(id, SETTING_START_TIME, builder.timeStart);
		cfg.setString(id, SETTING_END_TIME, builder.timeEnd);

		cfg.setString(id, SETTING_FILE_LOG_PATH, builder.logPath);
		cfg.setString(id, SETTING_FILE_STORE_PATH, builder.storePath);

		// from custom settings

		cfg.setString(id, SETTING_SENDER_PASSWORD, builder.senderPassword);

		cfg.setString(id, SETTING_CONNECT_USERNAME, builder.connectUsername);
		cfg.setString(id, SETTING_CONNECT_PASSWORD, builder.connectPassword);

		return cfg;

	}

}
