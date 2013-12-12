package com.carrotgarden.proto.data.fast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.openfast.Message;
import org.openfast.MessageInputStream;
import org.openfast.MessageOutputStream;
import org.openfast.template.TemplateRegistry;
import org.openfast.template.loader.XMLMessageTemplateLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class UtilTemplate {

	private static final Logger log = LoggerFactory
			.getLogger(UtilTemplate.class);

	public static TemplateRegistry makeTemplateRegistry(final String template)
			throws Exception {

		final InputStream templateInput = new ByteArrayInputStream(
				template.getBytes("UTF-8"));

		final XMLMessageTemplateLoader templateLoader = new XMLMessageTemplateLoader();

		templateLoader.setLoadTemplateIdFromAuxId(true);

		templateLoader.load(templateInput);

		final TemplateRegistry registry = templateLoader.getTemplateRegistry();

		return registry;

	}

	public Message fromByteArray(final byte[] byteArray,
			final TemplateRegistry registry) {
		try {

			final ByteArrayInputStream input = new ByteArrayInputStream(
					byteArray);

			final MessageInputStream messageInput = new MessageInputStream(
					input);

			messageInput.setTemplateRegistry(registry);

			final Message fastValue = messageInput.readMessage();

			return fastValue;

		} catch (final Exception e) {
			log.error("can not convert message", e);
			return null;
		}
	}

	public byte[] fromFastMessage(final Message message,
			final TemplateRegistry registry) {
		try {

			final ByteArrayOutputStream output = new ByteArrayOutputStream();

			final MessageOutputStream messageOutput = new MessageOutputStream(
					output);

			messageOutput.setTemplateRegistry(registry);

			messageOutput.writeMessage(message);

			return output.toByteArray();

		} catch (final Exception e) {
			log.error("can not convert message", e);
			return null;
		}
	}

	private UtilTemplate() {
	}

}
