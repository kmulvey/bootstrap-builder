import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class Block {
	String action;
	Selector sel;
	String selector_str;
	String properties;
	File file;
	
	public Block(){
		selector_str = "";
		sel = new Selector();
	}
	public void setSelector(String selector){
		sel = new Selector(selector);
	}
	public String toString(){
		return action + "\n" + selector_str + "\n" + properties;
	}
	public String getLESS(){
		return  "\n" + selector_str + "{ \n" + properties + "}";
	}
	public void removeBlock(File haystack) {
		try {
			if (!haystack.isFile()) {
				System.out.println("Parameter is not an existing file");
				return;
			}

			File tempFile = new File(haystack.getAbsolutePath() + ".tmp");
			BufferedReader br = new BufferedReader(new FileReader(haystack));
			PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
			String line = null;
			
			// single line selector
			if(sel.getType() == "single"){
				int bracket_count = 0;
				while ((line = br.readLine()) != null) {
					if (line.trim().length() > 0) {
						if (line.trim().contains(sel.getSelectorText())) {
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
//			else if(sel.getSelectorType() == "multiline"){
//				String[] sel_arr = null;
//				if(sel.getSelectorText().contains("\n")){
//					sel_arr = sel.getSelectorText().split("\n");
//				}
//				int bracket_count = 0, i = 0, sel_line = -1;
//				while ((line = br.readLine()) != null) {
//					if (line.trim().length() > 0) {
//						if (line.trim().contains(sel_arr[0])) {
//							if(line.contains("{")) {
//								bracket_count++;
//								sel_line = i;
//								sel_arr=shift(sel_arr);
//							}
//							else if(i == sel_line+1){
//								sel_line = i;
//								sel_arr=shift(sel_arr);
//							}
//							continue;
//						} else if (bracket_count > 0 && line.contains("{")) {
//							bracket_count++;
//							continue;
//						} else if (bracket_count > 0 && line.contains("}")) {
//							bracket_count--;
//							continue;
//						}
//					}
//					if (bracket_count == 0 || sel_arr.length>0) {
//						pw.println(line);
//						pw.flush();
//					}
//					i++;
//				}
//			}
			
			pw.close();
			br.close();

			// Delete the original file
			if (!haystack.delete()) {
				System.out.println("Could not delete file");
				return;
			}

			// Rename the new file to the filename the original file had.
			if (!tempFile.renameTo(haystack))
				System.out.println("Could not rename file");

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
