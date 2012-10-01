package com.carrotgarden.proto.inst;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.carrotgarden.proto.inst.Instrument.Builder;
import com.google.protobuf.ByteString;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistry.ExtensionInfo;
import com.google.protobuf.Message;

public class InstrumentCodec {

	private static final Logger log = LoggerFactory
			.getLogger(InstrumentCodec.class);

	/** message sub type / extension binding meta data */
	private static class MessageMeta {

		final ExtensionInfo info;

		final InstType type;

		MessageMeta(final InstType type, final ExtensionInfo info) {
			this.type = type;
			this.info = info;
		}

		@Override
		public String toString() {
			return " type=" + type + " info=" + info.descriptor.getFullName();
		}

	}

	/** provides management of message sub types / extensions */
	private static final ExtensionRegistry //
	registry = ExtensionRegistry.newInstance();

	/** from specific message type into type/extension descriptor */
	private static final Map<Class<? extends Message>, MessageMeta> //
	messageMetaMap = new ConcurrentHashMap<Class<? extends Message>, MessageMeta>();

	private static final void assertEquals(final Object one, final Object two,
			final String message) throws RuntimeException {
		if (one == two) {
			return;
		}
		if (one == null || two == null || !one.equals(two)) {
			throw new RuntimeException(message);
		}
	}

	private static final void assertNotNull(final Object instance,
			final String message) throws RuntimeException {
		if (instance == null) {
			throw new RuntimeException(message);
		}
	}

	static void prepareExtensions(final ExtensionRegistry registry,
			final Map<Class<? extends Message>, MessageMeta> messageMetaMap) {

		MessageSpec.registerAllExtensions(registry);

		/** all message extensions must have this prefix */
		final String extensionPrefix = Instrument.getDescriptor().getOptions()
				.getExtension(FieldSpec.optionExtensionPrefix);

		/** all message type enums must have this suffix */
		final String enumNameSuffix = Instrument.getDescriptor().getOptions()
				.getExtension(FieldSpec.optionEnumNameSuffix);

		final List<FieldDescriptor> list = MessageSpec.getDescriptor()
				.getExtensions();

		for (final FieldDescriptor field : list) {

			final int number = field.getNumber();
			final String nickName = field.getName();
			final String fullName = field.getFullName();

			final ExtensionInfo info = registry.findExtensionByName(fullName);

			assertNotNull(info, "missing registration : " + fullName);

			final Class<? extends Message> klaz = info.defaultInstance
					.getClass();

			final InstType type = InstType.valueOf(number);

			assertNotNull(type, "missing type enum : " + fullName);

			final String extnName = extensionPrefix + klaz.getSimpleName();

			final String typeName = klaz.getSimpleName() + enumNameSuffix;

			assertEquals(type.name(), typeName,
					"must match : type enum / message name : " + fullName);

			assertEquals(nickName, extnName,
					"must follow a pattern : extension name : " + fullName);

			log.debug("number    : {}", number);
			log.debug("extension : {}", fullName);
			log.debug("inst type    : {}", type);
			log.debug("inst message : {}", klaz);

			final MessageMeta meta = new MessageMeta(type, info);

			messageMetaMap.put(klaz, meta);

		}

	}

	static {
		try {
			prepareExtensions(registry, messageMetaMap);
		} catch (final Throwable e) {
			log.error("fatal : codec is misconfigured", e);
		}
	}

	private InstrumentCodec() {

	}

	public static Instrument decode(final ByteString data) throws Exception {

		return Instrument.newBuilder().mergeFrom(data, registry).build();

	}

	public static <MESSAGE extends Message> Instrument.Builder encode(
			final MESSAGE message) {

		final Builder builder = Instrument.newBuilder();

		if (message == null) {
			log.warn("missing message", new NullPointerException("message"));
			return builder;
		}

		final MessageMeta meta = messageMetaMap.get(message.getClass());

		if (meta == null) {
			log.warn("missing registration", new IllegalStateException("meta"));
			return builder;
		}

		builder.setInstType(meta.type);
		builder.setField(meta.info.descriptor, message);

		return builder;

	}

}
