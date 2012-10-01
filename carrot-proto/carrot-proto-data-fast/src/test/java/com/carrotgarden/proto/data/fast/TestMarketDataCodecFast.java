package com.carrotgarden.proto.data.fast;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openfast.GroupValue;
import org.openfast.Message;
import org.openfast.SequenceValue;
import org.openfast.template.MessageTemplate;
import org.openfast.template.Sequence;
import org.openfast.template.TemplateRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.carrotgarden.proto.data.MarketData;
import com.carrotgarden.proto.data.MarketDataEntry;
import com.carrotgarden.proto.data.MarketDataEntry.Type;
import com.carrotgarden.util.enums.fix.FixEntryType;
import com.carrotgarden.util.enums.fix.FixUpdateAction;

public class TestMarketDataCodecFast {

	private static final Logger log = LoggerFactory
			.getLogger(TestMarketDataCodecFast.class);

	private TemplateRegistry registry;

	@Before
	public void setUp() throws Exception {

		final File file = new File(
				"src/test/resources/venodr/cme/templates.xml");

		final String templateText = UtilFile.readTextFile(file);

		registry = UtilTemplate.makeTemplateRegistry(templateText);

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDecodeSnapshot() throws Exception {

		final MessageTemplate messageTemplate = registry
				.get("MDSnapshotFullRefresh_114");

		final Message source = new Message(messageTemplate);

		// source.setInteger("TradeDate", 20080112);

		final Sequence sequence = messageTemplate.getSequence("MDEntries");
		final SequenceValue sequenceValue = new SequenceValue(sequence);

		{

			final GroupValue group = new GroupValue(sequence.getGroup());
			group.setFieldValue("MDEntryType", FixEntryType.Offer.code + "");
			group.setFieldValue("MDEntryPx", "12.12");
			group.setFieldValue("MDEntrySize", "1001");
			sequenceValue.add(group);

		}

		{

			final GroupValue group = new GroupValue(sequence.getGroup());
			group.setFieldValue("MDEntryType", FixEntryType.Trade.code + "");
			group.setFieldValue("MDEntryPx", "13.13");
			group.setFieldValue("MDEntrySize", "1002");
			sequenceValue.add(group);

		}

		source.setFieldValue("MDEntries", sequenceValue);

		//

		log.debug("source : \n{}", source);

		final MarketMapper mapper = new MarketMapper() {
			@Override
			public Long getTargetId(final String sourceId) {
				return 123L;
			}
		};

		final MarketData.Builder target = MarketDataCodecFast.decode(source,
				mapper);

		log.debug("target : \n{}", target.build());

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

		final MessageTemplate messageTemplate = registry
				.get("MDIncRefresh_109");

		final Message source = new Message(messageTemplate);

		source.setInteger("TradeDate", 20120704);

		final Sequence sequence = messageTemplate.getSequence("MDEntries");
		final SequenceValue sequenceValue = new SequenceValue(sequence);

		{

			final GroupValue group = new GroupValue(sequence.getGroup());
			group.setFieldValue("RptSeq", "12010");
			group.setFieldValue("QuoteCondition", "I");
			group.setFieldValue("MDPriceLevel", "10");
			group.setFieldValue("MDUpdateAction", FixUpdateAction.Change.code
					+ "");
			group.setFieldValue("MDEntryType", FixEntryType.Offer.code + "");
			group.setFieldValue("MDEntryPx", "12.12");
			group.setFieldValue("MDEntrySize", "1001");
			sequenceValue.add(group);

		}

		{

			final GroupValue group = new GroupValue(sequence.getGroup());
			group.setFieldValue("RptSeq", "12011");
			group.setFieldValue("OpenCloseSettleFlag", "0");
			group.setFieldValue("MDUpdateAction", FixUpdateAction.New.code + "");
			group.setFieldValue("MDEntryType", FixEntryType.Trade.code + "");
			group.setFieldValue("MDEntryPx", "13.13");
			group.setFieldValue("MDEntrySize", "1002");
			group.setFieldValue("NetChgPrevDay", "1.25");
			sequenceValue.add(group);

		}

		source.setFieldValue("MDEntries", sequenceValue);

		//

		log.debug("source : \n{}", source);

		final MarketMapper mapper = new MarketMapper() {
			@Override
			public Long getTargetId(final String sourceId) {
				return 123L;
			}
		};

		final MarketData.Builder target = MarketDataCodecFast.decode(source,
				mapper);

		log.debug("target : \n{}", target.build());

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

}
