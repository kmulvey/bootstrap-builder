
import utils.FileUtil;


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
		
		over.parseLess();
		orig.parseLess();

		LessMerger lm = new LessMerger(orig, over);
		lm.merge();

		FileUtil f = new FileUtil();
		f.createWorkDir(output_dir);
		f.writeMergedFile(output_dir, file_name, orig.toString());
		System.out.println(output_dir + file_name);
  }
} 