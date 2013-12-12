package com.ss.less.runners;


import com.ss.less.LessMerger;
import com.ss.less.LessParser;
import com.ss.less.utils.FileUtil;


public class LessRunner implements Runnable {
  private String override, original, file_name, output_dir;

  LessRunner(String orig, String over, String f_name, String o_dir) {
    this.override = over;
    this.original = orig;
    this.output_dir = o_dir;
    this.file_name = f_name;
  }

  @Override
  public void run() {
		LessParser orig = new LessParser(original);
		LessParser over = new LessParser(override);
		
		over.parseLess(false);
		orig.parseLess(true);

		LessMerger lm = new LessMerger(orig, over);
		lm.merge();

		FileUtil f = new FileUtil();
		f.writeFile(output_dir, file_name, orig.toString());
		System.out.println("merged file " + file_name);
  }
} 