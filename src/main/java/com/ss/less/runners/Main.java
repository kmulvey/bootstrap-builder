package com.ss.less.runners;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import utils.FileUtil;

public class Main {

  public static void main(String[] args) {
  	String source_dir = args[0];
  	String override_dir = args[1];
  	String output_dir = args[2];
  	
  	System.out.println(Main.class.getCanonicalName());
  	
    // We will store the threads so that we can check if they are done
    List<Thread> threads = new ArrayList<Thread>();    
    
    
  	FileUtil f = new FileUtil();
		ArrayList<File> etc_files = f.findFiles(source_dir);
		for (int i = 0; i < etc_files.size(); i++) {
			File override = new File(override_dir + etc_files.get(i).getName());
			if (override.exists()) {
				System.out.println("matched file " + etc_files.get(i).getName());
				
				// Pimp out to a new thread
				Runnable task = new LessRunner(f.readFile(etc_files.get(i)), f.readFile(override), etc_files.get(i).getName(), output_dir);
	      Thread worker = new Thread(task);
	      // We can set the name of the thread
	      worker.setName(etc_files.get(i).getName());
	      // Start the thread, never call method run() direct
	      worker.start();
	      // Remember the thread for later usage
	      threads.add(worker);
			}
		}
    
    // ATC keeps track of what flights are still en route
    int running = 0;
    do {
      running = 0;
      for (Thread thread : threads) {
        if (thread.isAlive()) {
          running++;
        }
      }
      //System.out.println("We have " + running + " running threads. ");
    } while (running > 0);

  }
}