package com.carrotgarden.proto.bundle;

import static com.carrotgarden.proto.feed.MessageSpec.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.carrotgarden.proto.feed.Base;
import com.carrotgarden.proto.feed.MessageSpec;
import com.carrotgarden.proto.feed.MessageType;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.GeneratedMessage.GeneratedExtension;
import com.google.protobuf.Message;

/**
 * encode/decode proto.buf messages
 */
public final class MessageCodec {

	private MessageCodec() {
	}

	/** message sub type / extension binding meta data */
	private static class MessageMeta {

		final GeneratedExtension<Base, Message> extension;

		final MessageType type;

		MessageMeta(final MessageType type,
				final GeneratedExtension<Base, Message> extension) {
			this.type = type;
			this.extension = extension;
		}

		@Override
		public String toString() {
			return " type=" + type + " extension="
					+ extension.getDescriptor().getName();
		}

	}

	private static final Logger log = LoggerFactory
			.getLogger(MessageCodec.class);

	/** from specific message type into type/extension descriptor */
	private static final Map<Class<Message>, MessageMeta> //
	messageMetaMap = new ConcurrentHashMap<Class<Message>, MessageMeta>();

	/** provides management of message sub types / extensions */
	private static final ExtensionRegistry //
	registry = ExtensionRegistry.newInstance();

	static {
		prepareExtensions(registry);
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private static <MESSAGE extends Message> MESSAGE castType(final Base base,
			final Class<MESSAGE> klaz) throws Exception {

		final GeneratedExtension<Base, Message> extension = messageMetaMap
				.get(klaz).extension;

		return (MESSAGE) base.getExtension(extension);

	}

	/**
	 * 
	 * decode sub type messages with help of message visitor and message
	 * consumer target
	 * 
	 */
	public static <TARGET> void decode(final Base base,
			final MessageVisitor<TARGET> visitor, final TARGET target)
			throws Exception {

		if (!base.hasType()) {
			log.warn("missing base type",
					new IllegalArgumentException(base.toString()));
			return;
		}

		final MessageType type = base.getType();

		// log.debug("type : {}", type);

		/** keep high frequency types first */
		switch (type) {

		case MarketDataType:
			visitor.apply(//
					base.getExtension(messageMarketData), target);
			break;
		case MarketNewsType:
			visitor.apply(//
					base.getExtension(messageMarketNews), target);
			break;

		//

		case DataSubscribeRequestType:
			visitor.apply(//
					base.getExtension(messageDataSubscribeRequest), target);
			break;
		case DataSubscribeResponseType:
			visitor.apply(//
					base.getExtension(messageDataSubscribeResponse), target);
			break;

		//

		case NewsSubscribeRequestType:
			visitor.apply(//
					base.getExtension(messageNewsSubscribeRequest), target);
			break;
		case NewsSubscribeResponseType:
			visitor.apply(//
					base.getExtension(messageNewsSubscribeResponse), target);
			break;

		//

		case InstrumentRequestType:
			visitor.apply(//
					base.getExtension(messageInstrumentRequest), target);
			break;
		case InstrumentResponseType:
			visitor.apply(//
					base.getExtension(messageInstrumentResponse), target);
			break;

		//

		case LoginRequestType:
			visitor.apply(//
					base.getExtension(messageLoginRequest), target);
			break;
		case LoginResponseType:
			visitor.apply(//
					base.getExtension(messageLoginResponse), target);
			break;

		//

		case HeartBeatType:
			visitor.apply(//
					base.getExtension(messageHeartBeat), target);
			break;

		//

		default:
			log.warn("unsupported message type",
					new UnsupportedOperationException(type.name()));
			break;
		}

	}

	/** decode form raw array */
	public static Base decode(final byte[] array) throws Exception {

		final Base.Builder builder = Base.newBuilder();

		builder.mergeFrom(array, registry);

		return builder.build();

	}

	public static <TARGET> void decode(final byte[] array,
			final MessageVisitor<TARGET> visitor, final TARGET target)
			throws Exception {

		final Base base = decode(array);

		decode(base, visitor, target);

	}

	/** decode from length-prefixed raw array */
	public static Base decodeDelimited(final byte[] array) throws Exception {

		final ByteArrayInputStream input = new ByteArrayInputStream(array);

		final Base.Builder builder = Base.newBuilder();

		builder.mergeDelimitedFrom(input, registry);

		return builder.build();

	}

	public static <TARGET> void decodeDelimited(final byte[] array,
			final MessageVisitor<TARGET> visitor, final TARGET market)
			throws Exception {

		final Base base = decodeDelimited(array);

		decode(base, visitor, market);

	}

	public static byte[] encode(final Base base) throws Exception {

		// final ByteArrayOutputStream output = new ByteArrayOutputStream(128);
		// base.writeTo(output);
		// final byte[] array = output.toByteArray();
		// return array;

		return base.toByteArray();

	}

	/** embed sub type message into a base */
	public static <MESSAGE extends Message> Base.Builder encode(
			final MESSAGE message) {

		if (message == null) {
			log.warn("missing message", new NullPointerException());
			return Base.newBuilder();
		}

		final Class<? extends Message> klaz = message.getClass();

		final MessageMeta meta = messageMetaMap.get(klaz);

		if (meta == null) {
			log.warn("unsupported message type",
					new UnsupportedOperationException(klaz.getName()));
			return Base.newBuilder();
		}

		final Base.Builder builder = Base.newBuilder();

		builder.setType(meta.type);
		builder.setExtension(meta.extension, message);

		return builder;

	}

	/** encode with length-prefix before raw array */
	public static void encodeDelimited(final Base base, final ByteBuffer buffer)
			throws Exception {

		final ByteArrayOutputStream output = new ByteArrayOutputStream(128);

		base.writeDelimitedTo(output);

		final byte[] array = output.toByteArray();

		buffer.put(array);

	}

	/**
	 * builds {@link #messageMetaMap}
	 * 
	 * note: 1) extension name contract; 2) enum name contract; 3) enum +
	 * extension number contract
	 */
	private static void prepareExtensions(final ExtensionRegistry registry) {

		MessageSpec.registerAllExtensions(registry);

		/** all message extensions must have this prefix */
		final String extensionPrefix = Base.getDescriptor().getOptions()
				.getExtension(MessageSpec.optionExtensionPrefix);

		/** all message type enums must have this suffix */
		final String enumNameSuffix = Base.getDescriptor().getOptions()
				.getExtension(MessageSpec.optionEnumNameSuffix);

		for (final Field field : MessageSpec.class.getDeclaredFields()) {

			final String fieldName = field.getName();
			final Class<?> fieldType = field.getType();

			final boolean isNameMatch = fieldName.startsWith(extensionPrefix);

			final boolean isTypeMatch = //
			GeneratedExtension.class.isAssignableFrom(fieldType);

			final boolean isMessageExtension = isNameMatch && isTypeMatch;

			if (isMessageExtension) {

				try {

					@SuppressWarnings("unchecked")
					final GeneratedExtension<Base, Message> extension = //
					(GeneratedExtension<Base, Message>) field.get(null);

					@SuppressWarnings("unchecked")
					final Class<Message> messageClass = (Class<Message>) extension
							.getMessageDefaultInstance().getClass();

					final String messageName = fieldName.replaceAll(
							extensionPrefix, "");

					final String enumName = messageName + enumNameSuffix;

					final MessageType type = MessageType.valueOf(enumName);

					final int enumNumber = type.getNumber();

					final int messageNumber = extension.getDescriptor()
							.getNumber();

					/**
					 * numbers must match
					 */
					if (enumNumber != messageNumber) {
						throw new IllegalArgumentException(
								"message enum/extension field number mismatch");
					}

					final MessageMeta messageMeta = new MessageMeta(type,
							extension);

					messageMetaMap.put(messageClass, messageMeta);

					// log.debug("extension ready : {}", meta);

				} catch (final Exception e) {

					log.error("can not prepare message extenstion : "
							+ fieldName, e);

				}

			}

		}

	}

}
