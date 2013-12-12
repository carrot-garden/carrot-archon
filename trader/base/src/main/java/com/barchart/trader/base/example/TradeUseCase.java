package com.barchart.trader.base.example;

import static com.barchart.trader.base.api.enums.TradeEvent.*;
import static com.barchart.trader.base.api.enums.TradeField.Account.*;
import static com.barchart.trader.base.api.enums.TradeField.Order.*;

import com.barchart.trader.base.api.TradeListener;
import com.barchart.trader.base.api.TradeSession;
import com.barchart.trader.base.api.enums.TradeEvent;
import com.barchart.trader.base.api.enums.TradeField.Session;
import com.barchart.trader.base.api.enums.TradeQualifier.Pending;
import com.barchart.trader.base.api.enums.TradeQualifier.Side;
import com.barchart.trader.base.api.enums.TradeQualifier.Type;
import com.barchart.trader.base.api.values.TradeAccount;
import com.barchart.trader.base.api.values.TradeOrder;
import com.barchart.trader.base.api.values.TradeOrderBuilder;
import com.carrotgarden.util.values.api.PriceValue;
import com.carrotgarden.util.values.api.SizeValue;

public class TradeUseCase {

	// get provider-specific session from factory
	TradeSession session = null;

	// make order listener
	TradeListener<TradeOrder> orderListener = new TradeListener<TradeOrder>() {

		@Override
		public Session<TradeOrder> bindField() {
			return Session.ORDER;
		}

		@Override
		public TradeEvent[] bindEvents() {
			return new TradeEvent[] { ORDER_FILL };
		}

		@Override
		public void onTradeEvent(final TradeEvent event, final TradeOrder order) {

			final PriceValue priceFill = order.get(PRICE_FILL);

		}

	};

	// register listener
	{

		session.register(orderListener);

	}

	// make account listener
	TradeListener<TradeAccount> accountListener = new TradeListener<TradeAccount>() {

		@Override
		public Session<TradeAccount> bindField() {
			return Session.ACCOUNT;
		}

		@Override
		public TradeEvent[] bindEvents() {
			return new TradeEvent[] { ORDER_FILL, ORDER_CANCEL };
		}

		@Override
		public void onTradeEvent(final TradeEvent event,
				final TradeAccount account) {

			final PriceValue balance = account.get(BALANCE);

		}

	};

	// register listener
	{

		session.register(accountListener);

	}

	// do order setup
	{

		// get from lookup service
		final MarketInstrument instrument = null;

		// get from builder
		final PriceValue price = null;
		final SizeValue size = null;

		// get from session
		final TradeOrderBuilder orderTemp = session.newOrderBuilder();

		// set user configurable fields
		orderTemp. //
				init(INSTRUMENT, instrument). //
				init(PRICE_LIMIT, price). //
				init(SIZE, size). //
				init(TYPE, Type.MARKET). //
				init(SIDE, Side.BUY). //
				init(PENDING, Pending.DAY) //
		;

		/*
		 * get real order made by provider; will throw IllegalArgumentException
		 * if builder setup was incomplete/inconsistent
		 */
		final TradeOrder order = session.place(orderTemp);

		/*
		 * now wait; listeners will receive events when the order is filed /
		 * canceled by underlying provider
		 */
		try {
			Thread.sleep(1000);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}

		/* will throw IllegalStateException if impossible to cancel */
		session.cancel(order);

	}

}
