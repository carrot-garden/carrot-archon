package com.barchart.trader.base.api.enums;

import com.carrotgarden.util.values.api.Value;

public enum TradeQualifier {

	;

	private TradeQualifier() {

	}

	public enum Pending implements Value<Pending> {

		NULL_PENDING(""), //

		DAY("In Effect Today"), //

		GTC("Good Till Cancel"), //

		IOC("Immediate or Cancel"), //

		FOK("Fill or Kill"), //

		GTD("Good Till Date"), //

		;

		public final String description;

		Pending(final String description) {
			this.description = description;
		}

		@Override
		public Pending freeze() {
			return this;
		}

		@Override
		public boolean isFrozen() {
			return true;
		}

		@Override
		public boolean isNull() {
			return this == NULL_PENDING;
		}

	}

	public enum Side implements Value<Side> {

		NULL_SIDE(""), //

		BUY("Buy Order"), //

		SELL("Sell Order"), //

		;

		public final String description;

		Side(final String description) {
			this.description = description;
		}

		@Override
		public Side freeze() {
			return this;
		}

		@Override
		public boolean isFrozen() {
			return true;
		}

		@Override
		public boolean isNull() {
			return this == NULL_SIDE;
		}

	}

	public enum Status implements Value<Status> {

		NULL_STATUS(""), //

		NEW("New Order"), //

		NEW_PENDING("Cancel Pending"), //

		PARTIAL("Partial Fill"), //

		FILLED("Complete Fill"), //

		CANCELED("Canceled Order"), //

		CANCEL_PENDING("Cancel Pending"), //

		EXPIRED("Expired Order"), //

		;

		public final String description;

		Status(final String description) {
			this.description = description;
		}

		@Override
		public Status freeze() {
			return this;
		}

		@Override
		public boolean isFrozen() {
			return true;
		}

		@Override
		public boolean isNull() {
			return this == NULL_STATUS;
		}

	}

	public enum Type implements Value<Type> {

		NULL_TYPE(""), //

		MARKET("Market Order"), //

		LIMIT("Limit Order"), //

		STOP("Stop Order"), //

		STOP_LIMIT("Stop Limit Order"), //

		;

		public final String description;

		Type(final String description) {
			this.description = description;
		}

		@Override
		public Type freeze() {
			return this;
		}

		@Override
		public boolean isFrozen() {
			return true;
		}

		@Override
		public boolean isNull() {
			return this == NULL_TYPE;
		}

	}

}
