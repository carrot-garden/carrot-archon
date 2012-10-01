package com.barchart.trader.vendor.dukascopy;

class DukasSession {
	// class CqgSession extends VarSession {

	final DukasClientHost client;

	DukasSession(final DukasClientHost client) {
		this.client = client;
	}

	// @Override
	public void login() {
		try {
			client.activate();
		} catch (final Exception e) {
			// TODO
			e.printStackTrace();
		}
	}

	// @Override
	public void logout() {
		try {
			client.deactivate();
		} catch (final Exception e) {
			// TODO
			e.printStackTrace();
		}
	}

}
