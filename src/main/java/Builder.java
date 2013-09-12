import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Builder {
	static String overrides = "/var/www/bootstrap-twbs/less/shutterstock";
	static String originals = "/var/www/bootstrap-kmulvey/less";

	public static void main(String[] args) {
		findFiles(overrides);
	}

	private static void findFiles(String path) {
//		File dir = new File(path);
//		for (File child : dir.listFiles()) {
//			readFile(child);
//		}
		readFile(new File("/var/www/bootstrap-kmulvey/less/shutterstock/dropdowns.less"));
		}

	private static void readFile(File file) {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			SelectorList of = new SelectorList();
			Block curr_block = new Block();
			Variable var = new Variable();
			String line, curr_block_contents = "";
			int bracket_count = 0;

			while ((line = br.readLine()) != null) {
				if (line.trim().length() > 0) {
					if (line.trim().length() > 0 && line.charAt(0) == '@') {
						var = new Variable();
						var.file = file;
						var.name = line.substring(1, line.indexOf("=")).trim();
						continue;
					} else if (line.charAt(0) == '!') {
						curr_block = new Block();
						curr_block.file = file;
						curr_block.action = line.substring(1).trim();
						continue;
					} 
					// opening brackets mean its a selector of some kind
					else if (line.contains("{")) {
						// if we found an opening bracket and selector already populated then we probably have a nested selector
						if(curr_block.selector_str.length() > 0 && bracket_count > 0) {
							curr_block.selector_str += "\n" + line;
							curr_block_contents += line;
							curr_block.sel.addSelector(curr_block_contents);
							curr_block_contents = "";
						}
						// close the multi
						else if(curr_block.selector_str.length() > 0){
							curr_block_contents += line;
							curr_block.sel.addSelector(curr_block_contents);
							curr_block_contents = "";
						}
						// otherwise its probably a single
						else {
							curr_block.selector_str = line;
							curr_block.sel.addSelector(line);
						}
						bracket_count++;
						continue;
					}
					// multiline is trying to capture everything between the action and the {
					else if (curr_block.action != null && bracket_count == 0) {
						curr_block.selector_str += line + "\n";
						curr_block_contents += line;
						continue;
					}else if (line.contains("}")) {
						bracket_count--;
						if (bracket_count == 0) {
							curr_block.properties = curr_block_contents;
							curr_block_contents = "";
							of.addBlock(curr_block);
//							actionRouter(curr_block);
						}
					} else if (bracket_count != 0) {
						curr_block_contents += line += "\n";
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void actionRouter(Block b) {
		switch (b.action) {
			case "add":
				addBlock(b);
				break;
			case "remove":
				removeBlock(b);
				break;
			case "change":
				changeBlock(b);
				break;
		}

	}

	private static void addBlock(Block b) {
		String motd = "\n /* SS Override */";
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(originals + "/" + b.file.getName(), true)));
			out.println(motd + b.getLESS());
		} catch (IOException e) {
			// oh noes!
		} finally {
			if (out != null) {
				out.close();
			}
		}

	}

	private static void removeBlock(Block b) {
		try {
			File f = new File(originals + "/" + b.file.getName());
			if (!f.isFile()) {
				System.out.println("Parameter is not an existing file");
				return;
			}

			File tempFile = new File(f.getAbsolutePath() + ".tmp");
			BufferedReader br = new BufferedReader(new FileReader(f));
			PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
			String line = null;
/*			
			// single line selector
			if(b.selector_type == "single"){
				int bracket_count = 0;
				while ((line = br.readLine()) != null) {
					if (line.trim().length() > 0) {
						if (line.trim().contains(b.sel)) {
							if(line.contains("{")) bracket_count++;
							continue;
						} else if (bracket_count > 0 && line.contains("{")) {
							bracket_count++;
							continue;
						} else if (bracket_count > 0 && line.contains("}")) {
							bracket_count--;
							continue;
						}
					}
					if (bracket_count == 0) {
						pw.println(line);
						pw.flush();
					}
				}
			}
			// multiline line selector
			else if(b.selector_type == "multiline"){
				String[] sel_arr = null;
				if(b.sel.contains("\n")){
					sel_arr = b.sel.split("\n");
				}
				int bracket_count = 0, i = 0, sel_line = -1;
				while ((line = br.readLine()) != null) {
					if (line.trim().length() > 0) {
						if (line.trim().contains(sel_arr[0])) {
							if(line.contains("{")) {
								bracket_count++;
								sel_line = i;
								sel_arr=shift(sel_arr);
							}
							else if(i == sel_line+1){
								sel_line = i;
								sel_arr=shift(sel_arr);
							}
							continue;
						} else if (bracket_count > 0 && line.contains("{")) {
							bracket_count++;
							continue;
						} else if (bracket_count > 0 && line.contains("}")) {
							bracket_count--;
							continue;
						}
					}
					if (bracket_count == 0 || sel_arr.length>0) {
						pw.println(line);
						pw.flush();
					}
					i++;
				}
			}
*/			
			pw.close();
			br.close();

			// Delete the original file
			if (!f.delete()) {
				System.out.println("Could not delete file");
				return;
			}

			// Rename the new file to the filename the original file had.
			if (!tempFile.renameTo(f))
				System.out.println("Could not rename file");

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private static void changeBlock(Block b) {

	}
	public static String[] shift(String[] arr )
  {
			String[] result = new String[arr.length-1];
      for( int i = 1; i <= arr.length-1 ; i++ ){
          result[i-1] = arr[i];
      }
      return result;
  }
}
