package com.barchart.trader.vendor.cqg;

class CqgSession {
	// class CqgSession extends VarSession {

	final CqgClientHost client;

	CqgSession(final CqgClientHost client) {
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
