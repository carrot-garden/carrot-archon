package com.carrotgarden.proto.data.fix;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quickfix.field.MDEntryPx;
import quickfix.field.MDEntrySize;
import quickfix.field.MDEntryType;
import quickfix.field.OrderID;
import quickfix.fix44.MarketDataSnapshotFullRefresh;
import quickfix.fix44.MarketDataSnapshotFullRefresh.NoMDEntries;

import com.carrotgarden.proto.data.MarketData;
import com.carrotgarden.proto.data.MarketDataEntry;
import com.carrotgarden.proto.data.MarketDataEntry.Type;

public class TestMarketDataCodecFix {

	private static final Logger log = LoggerFactory
			.getLogger(TestMarketDataCodecFix.class);

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDecodeSnapshot() throws Exception {

		final MarketDataSnapshotFullRefresh source = new MarketDataSnapshotFullRefresh();

		{

			final NoMDEntries group = new NoMDEntries();

			group.set(new MDEntryType('1'));
			group.set(new MDEntryPx(12.12));
			group.set(new MDEntrySize(1001));
			group.set(new OrderID("1230"));

			source.addGroup(group);

		}

		{

			final NoMDEntries group = new NoMDEntries();

			group.set(new MDEntryType('2'));
			group.set(new MDEntryPx(13.13));
			group.set(new MDEntrySize(1002));
			group.set(new OrderID("1231"));

			source.addGroup(group);

		}

		//

		log.debug("source : {}", source);

		final MarketMapper mapper = new MarketMapper() {
			@Override
			public Long getTargetId(final String sourceId) {
				return 123L;
			}
		};

		final MarketData.Builder target = MarketDataCodecFix.decode(source,
				mapper);

		log.debug("target : {}", target.build());

		{

			final MarketDataEntry entry = target.getEntry(0);

			assertEquals(Type.ASK, entry.getType());

			assertEquals(1212, entry.getPriceMantissa());
			assertEquals(-2, entry.getPriceExponent());

			assertEquals(1001, entry.getSizeMantissa());
			assertEquals(0, entry.getSizeExponent());

		}

		{

			final MarketDataEntry entry = target.getEntry(1);

			assertEquals(Type.TRADE, entry.getType());

			assertEquals(1313, entry.getPriceMantissa());
			assertEquals(-2, entry.getPriceExponent());

			assertEquals(1002, entry.getSizeMantissa());
			assertEquals(0, entry.getSizeExponent());

		}

	}

	@Test
	public void testDecodeUpdate() throws Exception {

	}

}
