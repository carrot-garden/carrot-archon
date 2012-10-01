package com.carrotgarden.proto.inst;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.carrotgarden.proto.inst.Instrument.Builder;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistry.ExtensionInfo;

public class TestInstrumentCodec {

	private static final Logger log = LoggerFactory
			.getLogger(TestInstrumentCodec.class);

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test0() {

		{

			final FieldDescriptor field = MessageSpec.instForex.getDescriptor();

			log.debug("fullName : {}", field.getFullName());

		}

		{

			final ExtensionRegistry registry = ExtensionRegistry.newInstance();

			MessageSpec.registerAllExtensions(registry);

			final ExtensionInfo info = registry
					.findExtensionByName(MessageSpec.instForex.getDescriptor()
							.getFullName());

			/** extension key used by registry */
			log.debug("info 1 : {}", info.descriptor.getFullName());

			/** extension number used by type */
			log.debug("info 1 : {}", info.descriptor.getNumber());

			log.debug("info 1 : {}", info.descriptor.getMessageType()
					.getFullName());

			log.debug("info 2 : {}", info.defaultInstance
					.getDescriptorForType().getFullName());

			log.debug("info 2 : {}", info.defaultInstance.getClass());

		}

	}

	@Test
	public void testEncode() {

		final Forex forexIn = Forex.newBuilder().build();

		final Builder inst = InstrumentCodec.encode(forexIn);

		assertEquals(InstType.ForexInst, inst.build().getInstType());

	}

}
