package com.barchart.trader.base.api.enums;

import com.barchart.trader.base.api.values.TradeAccount;
import com.barchart.trader.base.api.values.TradeAccountList;
import com.barchart.trader.base.api.values.TradeOrder;
import com.barchart.trader.base.api.values.TradeOrderList;
import com.barchart.trader.base.api.values.TradeReport;
import com.barchart.trader.base.api.values.TradeReportList;
import com.barchart.trader.base.provider.TradeConst;
import com.carrotgarden.util.values.api.PriceValue;
import com.carrotgarden.util.values.api.SizeValue;
import com.carrotgarden.util.values.api.TextValue;
import com.carrotgarden.util.values.api.TimeValue;
import com.carrotgarden.util.values.api.Value;
import com.carrotgarden.util.values.provider.ValueConst;

public enum TradeField {

	;

	private TradeField() {

	}

	public static final class Order<V extends Value<V>> extends
			ParamEnumBase<V, Order<V>> {

		// keep first
		private static int sequence;
		protected static final int size;
		protected static final Order<?>[] values;
		static {
			sequence = 0;
			size = countEnumFields(Order.class);
			values = new Order<?>[size];
		}

		public static final int size() {
			return size;
		}

		public static final Order<?>[] values() {
			return values.clone();
		}

		protected Order(final V defVal) {
			super(values, sequence++, defVal);
		}

		private static final <X extends Value<X>> Order<X> NEW(
				final X defaultValue) {
			return new Order<X>(defaultValue);
		}

		//

		public static final Order<TextValue> ID = NEW(ValueConst.NULL_TEXT);

		public static final Order<TradeAccount> ACCOUNT = NEW(TradeConst.NULL_ACCOUNT);

		public static final Order<MarketInstrument> INSTRUMENT = NEW(InstrumentConst.NULL_INSTRUMENT);

		public static final Order<TradeQualifier.Pending> PENDING = NEW(TradeQualifier.Pending.NULL_PENDING);

		public static final Order<TradeQualifier.Type> TYPE = NEW(TradeQualifier.Type.NULL_TYPE);

		public static final Order<TradeQualifier.Side> SIDE = NEW(TradeQualifier.Side.NULL_SIDE);

		public static final Order<TradeQualifier.Status> STATUS = NEW(TradeQualifier.Status.NULL_STATUS);

		public static final Order<TimeValue> TIME_PLACED = NEW(ValueConst.NULL_TIME);

		public static final Order<TimeValue> TIME_CANCELED = NEW(ValueConst.NULL_TIME);

		public static final Order<TimeValue> TIME_TO_EXPIRE = NEW(ValueConst.NULL_TIME);

		public static final Order<PriceValue> PRICE_LIMIT = NEW(ValueConst.NULL_PRICE);

		public static final Order<SizeValue> SIZE = NEW(ValueConst.NULL_SIZE);

		public static final Order<PriceValue> PRICE_FILL = NEW(ValueConst.NULL_PRICE);

		public static final Order<TradeReportList> REPORTS = NEW(TradeConst.NULL_REPORTS);

	}

	public static final class Account<V extends Value<V>> extends
			ParamEnumBase<V, Account<V>> {

		// keep first
		private static int sequence;
		protected static final int size;
		protected static final Account<?>[] values;
		static {
			sequence = 0;
			size = countEnumFields(Account.class);
			values = new Account<?>[size];
		}

		public static final int size() {
			return size;
		}

		public static final Account<?>[] values() {
			return values.clone();
		}

		protected Account(final V defVal) {
			super(values, sequence++, defVal);
		}

		private static final <X extends Value<X>> Account<X> NEW(
				final X defaultValue) {
			return new Account<X>(defaultValue);
		}

		//

		public static final Account<TextValue> ID = NEW(ValueConst.NULL_TEXT);

		public static final Account<PriceValue> BALANCE = NEW(ValueConst.NULL_PRICE);

	}

	public static final class Session<V extends Value<V>> extends
			ParamEnumBase<V, Session<V>> {

		// keep first
		private static int sequence;
		protected static final int size;
		protected static final Session<?>[] values;
		static {
			sequence = 0;
			size = countEnumFields(Session.class);
			values = new Session<?>[size];
		}

		public static final int size() {
			return size;
		}

		public static final Session<?>[] values() {
			return values.clone();
		}

		protected Session(final V defVal) {
			super(values, sequence++, defVal);
		}

		private static final <X extends Value<X>> Session<X> NEW(
				final X defaultValue) {
			return new Session<X>(defaultValue);
		}

		//

		public static final Session<TextValue> ID = NEW(ValueConst.NULL_TEXT);

		public static final Session<TradeAccountList> ACCOUNT_LIST = NEW(TradeConst.NULL_ACCOUNT_LIST);

		public static final Session<TradeOrderList> ORDER_LIST = NEW(TradeConst.NULL_ORDER_LIST);

		public static final Session<TradeStatus> STATUS = NEW(TradeStatus.NULL_STATUS);

		// last
		public static final Session<TradeAccount> ACCOUNT = NEW(TradeConst.NULL_ACCOUNT);
		public static final Session<TradeOrder> ORDER = NEW(TradeConst.NULL_ORDER);
		public static final Session<TradeReport> REPORT = NEW(TradeConst.NULL_REPORT);

	}

	public final static class Report<V extends Value<V>> extends
			ParamEnumBase<V, Report<V>> {

		// keep first
		private static int sequence;
		protected static final int size;
		protected static final Report<?>[] values;
		static {
			sequence = 0;
			size = countEnumFields(Report.class);
			values = new Report<?>[size];
		}

		public static final int size() {
			return size;
		}

		public static final Report<?>[] values() {
			return values.clone();
		}

		protected Report(final V defVal) {
			super(values, sequence++, defVal);
		}

		private static final <X extends Value<X>> Report<X> NEW(
				final X defaultValue) {
			return new Report<X>(defaultValue);
		}

		//

		public static final Report<TextValue> ID = NEW(ValueConst.NULL_TEXT);

		public static final Report<TradeAccount> ACCOUNT = NEW(TradeConst.NULL_ACCOUNT);

		public static final Report<TradeOrder> ORDER = NEW(TradeConst.NULL_ORDER);

	}

}
