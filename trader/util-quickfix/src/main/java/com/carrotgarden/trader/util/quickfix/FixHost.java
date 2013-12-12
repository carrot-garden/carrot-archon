package com.carrotgarden.trader.util.quickfix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quickfix.Application;
import quickfix.Connector;
import quickfix.DefaultMessageFactory;
import quickfix.FileLogFactory;
import quickfix.FileStoreFactory;
import quickfix.LogFactory;
import quickfix.MessageFactory;
import quickfix.MessageStoreFactory;
import quickfix.SessionSettings;
import quickfix.SocketAcceptor;
import quickfix.SocketInitiator;

public abstract class FixHost {

	protected final Logger log = LoggerFactory.getLogger(getClass());

	protected final SessionSettings settings;
	protected final Application application;
	protected final boolean isClient;

	protected FixHost(final SessionSettings settings,
			final Application application, final boolean isClient) {

		this.settings = settings;
		this.application = application;
		this.isClient = isClient;

	}

	private volatile Connector connector;

	public boolean isActive() {
		return connector != null;
	}

	public synchronized void activate() {

		if (isActive()) {
			log.error("", new Exception("already active"));
			return;
		}

		final MessageStoreFactory storeFactory = new FileStoreFactory(settings);
		final LogFactory logFactory = new FileLogFactory(settings);
		final MessageFactory messageFactory = new DefaultMessageFactory();

		try {
			if (isClient) {
				connector = new SocketInitiator(application, storeFactory,
						settings, logFactory, messageFactory);
			} else {
				connector = new SocketAcceptor(application, storeFactory,
						settings, logFactory, messageFactory);
			}
		} catch (final Exception e) {
			log.error("can not make connector", e);
			connector = null;
			return;
		}

		try {
			connector.start();
		} catch (final Exception e) {
			log.error("can not start connector", e);
			connector = null;
			return;
		}

	}

	public synchronized void deactivate() {

		if (!isActive()) {
			log.error("", new Exception("already inactive"));
			return;
		}

		connector.stop();

		connector = null;

	}

}
