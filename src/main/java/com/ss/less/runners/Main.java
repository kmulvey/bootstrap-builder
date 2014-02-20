package com.ss.less.runners;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ss.less.utils.FileUtil;

public class Main {

	// there is some logic in here ... and because its the main() its untestable, that should be moved if possible
	public static void main(String[] args) {
		Logger logger = LogManager.getLogger(Main.class.getName());
		String source_dir = "", override_dir = "", output_dir = "";
		CommandLineParser parser = new GnuParser();

		// create the Options
		Options options = new Options();
		options.addOption("s", "source", true, "path to source files");
		options.addOption("o", "override", true, "path to override files");
		options.addOption("w", "workdir", true, "path to override files");
		
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		
		try {
			// parse the command line arguments
			CommandLine line = parser.parse(options, args);

			// source dir
			if (line.hasOption("s")) {
				source_dir = line.getOptionValue("source");
			}
			else {
				logger.fatal("Source directory must be specified.");
				formatter.printHelp( "bootstrap-builder", options );
				return;
			}

			// override dir
			if (line.hasOption("o")) {
				override_dir = line.getOptionValue("override");
			}
			else {
				logger.fatal("Override directory must be specified.");
				formatter.printHelp( "bootstrap-builder", options );
				return;
			}

			// work dir
			if (line.hasOption("w")) {
				output_dir = line.getOptionValue("workdir");
			}
			else {
				logger.fatal("Work directory must be specified.");
				formatter.printHelp( "bootstrap-builder", options );
				return;
			}
		}
		catch (org.apache.commons.cli.ParseException e) {
			logger.fatal(e.getMessage());
			formatter.printHelp( "bootstrap-builder", options );
			return;
		}		

		// trailing slashes
		if (source_dir.charAt(source_dir.length() - 1) != '/')
			source_dir += '/';
		if (override_dir.charAt(override_dir.length() - 1) != '/')
			override_dir += '/';
		if (output_dir.charAt(output_dir.length() - 1) != '/')
			output_dir += '/';

		// We will store the threads so that we can check if they are done
		List<Thread> threads = new ArrayList<Thread>();

		FileUtil f = new FileUtil();
		ArrayList<File> source_files = f.findFiles(source_dir);
		for (int i = 0; i < source_files.size(); i++) {
			File override = new File(override_dir + source_files.get(i).getName());
			if (override.exists()) {
				// Pimp out to a new thread
				Runnable task = new LessRunner(f.readFile(source_files.get(i)), f.readFile(override), source_files.get(i).getName(), output_dir);
				Thread worker = new Thread(task);
				// We can set the name of the thread
				worker.setName(source_files.get(i).getName());
				// Start the thread, never call method run() direct
				worker.start();
				// Remember the thread for later usage
				threads.add(worker);
			}
		}

		// ATC keeps track of what flights are still en route
		// use this for debugging purposes
		/*
    int running = 0;
    do {
      running = 0;
      for (Thread thread : threads) {
        if (thread.isAlive()) {
          running++;
        }
      }
      System.out.println("We have " + running + " running threads. ");
    } while (running > 0);
    */
  }
}