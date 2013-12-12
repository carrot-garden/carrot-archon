package com.carrotgarden.s3;

import static java.nio.file.LinkOption.*;
import static java.nio.file.StandardWatchEventKinds.*;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

/**
 * Example to watch a directory (or tree) for changes to files.
 */
public class WatchDir {

	private final WatchService watcher;
	private final Map<WatchKey, Path> keys;
	private final boolean recursive;
	private boolean trace = true;

	@SuppressWarnings("unchecked")
	static <T> WatchEvent<T> cast(final WatchEvent<?> event) {
		return (WatchEvent<T>) event;
	}

	/**
	 * Register the given directory with the WatchService
	 */
	private void register(final Path dir) throws IOException {

		final WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE,
				ENTRY_MODIFY);

		if (trace) {

			final Path prev = keys.get(key);

			if (prev == null) {
				System.out.format("register: %s\n", dir);
			} else {
				if (!dir.equals(prev)) {
					System.out.format("update: %s -> %s\n", prev, dir);
				}
			}

		}

		keys.put(key, dir);

	}

	/**
	 * Register the given directory, and all its sub-directories, with the
	 * WatchService.
	 */
	private void registerAll(final Path start) throws IOException {

		// register directory and sub-directories
		Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(final Path dir,
					final BasicFileAttributes attrs) throws IOException {

				register(dir);

				return FileVisitResult.CONTINUE;

			}
		});

	}

	/**
	 * Creates a WatchService and registers the given directory
	 */
	WatchDir(final Path dir, final boolean recursive) throws IOException {

		this.watcher = FileSystems.getDefault().newWatchService();
		this.keys = new HashMap<WatchKey, Path>();
		this.recursive = recursive;

		if (recursive) {
			System.out.format("Scanning %s ...\n", dir);
			registerAll(dir);
			System.out.println("Done.");
		} else {
			register(dir);
		}

		// enable trace after initial registration
		this.trace = true;
	}

	/**
	 * Process all events for keys queued to the watcher
	 */
	void processEvents() {

		for (;;) {

			// wait for key to be signalled
			WatchKey key;
			try {
				key = watcher.take();
			} catch (final InterruptedException x) {
				return;
			}

			final Path dir = keys.get(key);
			if (dir == null) {
				System.err.println("WatchKey not recognized!!");
				continue;
			}

			for (final WatchEvent<?> event : key.pollEvents()) {

				final WatchEvent.Kind<?> kind = event.kind();

				// TBD - provide example of how OVERFLOW event is handled
				if (kind == OVERFLOW) {
					continue;
				}

				// Context for directory entry event is the file name of entry
				final WatchEvent<Path> ev = cast(event);
				final Path name = ev.context();
				final Path child = dir.resolve(name);

				// print out event
				System.out.format("%s: %s\n", event.kind().name(), child);

				// if directory is created, and watching recursively, then
				// register it and its sub-directories
				if (recursive && (kind == ENTRY_CREATE)) {
					try {
						if (Files.isDirectory(child, NOFOLLOW_LINKS)) {
							registerAll(child);
						}
					} catch (final IOException x) {
						// ignore to keep sample readbale
					}
				}
			}

			// reset key and remove from set if directory no longer accessible
			final boolean isValid = key.reset();

			if (!isValid) {

				keys.remove(key);

				// all directories are inaccessible
				if (keys.isEmpty()) {
					break;
				}

			}

		}

	}

	static void usage() {
		System.err.println("usage: java WatchDir [-r] dir");
		System.exit(-1);
	}

	public static void main(final String[] args) throws IOException {

		// parse arguments
		// if (args.length == 0 || args.length > 2)
		// usage();

		final boolean recursive = true;

		final int dirArg = 0;

		// if (args[0].equals("-r")) {
		// if (args.length < 2)
		// usage();
		// recursive = true;
		// dirArg++;
		// }

		// register directory and process its events
		final Path dir = Paths.get(".");

		new WatchDir(dir, recursive).processEvents();

	}

}
