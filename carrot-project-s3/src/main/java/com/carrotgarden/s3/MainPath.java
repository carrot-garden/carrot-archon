package com.carrotgarden.s3;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.UserDefinedFileAttributeView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainPath {

	static final Logger log = LoggerFactory.getLogger(MainPath.class);

	public static void main(final String[] args) throws Exception {

		log.info("init");

		final Path file = Paths.get("pom.xml");

		final UserDefinedFileAttributeView view = Files.getFileAttributeView(
				file, UserDefinedFileAttributeView.class);

		//

		final String name = "carrot.md5";
		final String value = "1234567";
		view.write(name, Charset.defaultCharset().encode(value));

		//

		final int size = view.size(name);
		// final ByteBuffer buffer = ByteBuffer.allocateDirect(size);
		final ByteBuffer buffer = ByteBuffer.allocate(size);
		view.read(name, buffer);
		buffer.flip();
		log.info(Charset.defaultCharset().decode(buffer).toString());

		log.info("done");

	}

}
