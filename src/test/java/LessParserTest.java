import java.io.File;
import java.net.URL;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.ss.less.LessParser;
import com.ss.less.objects.Block;
import com.ss.less.objects.Property;
import com.ss.less.utils.FileUtil;

@RunWith(JUnit4.class)
public class LessParserTests {
	@Test
	public void simpleSourceFileTest() {
		FileUtil f = new FileUtil();
		URL file_path = FileUtilTest.class.getResource("/less/wells.orig.less");
		File test_file = new File(file_path.getPath());
		String file_contents = f.readFile(test_file);
	
		LessParser orig = new LessParser(file_contents);
		orig.parseLess(true);
		
		// how many blocks are in the file?
		Assert.assertEquals(5, orig.getLessFile().size());

		// look more deeply into the first block
		Assert.assertEquals(Block.class, orig.getLessFile().get(0).getClass());

		// count number of children in first block
		Block first_ele = (Block) orig.getLessFile().get(0);
		Assert.assertEquals(8, first_ele.children.size());
		
		// how many of each type?
		int prop = 0, block = 0;
		for(int i = 0; i<first_ele.children.size(); i++){
			if(first_ele.children.get(i) instanceof Block){
				block++;
			}
			else if(first_ele.children.get(i) instanceof Property){
				prop++;
			}
			else{
				Assert.fail("the nuber of props and blocks is wrong");
			}
		}
		Assert.assertEquals(7, prop);
		Assert.assertEquals(1, block);
	}
	@Test
	public void simpleOverrideFileTest() {
		FileUtil f = new FileUtil();
		URL file_path = FileUtilTest.class.getResource("/less/wells.less");
		File test_file = new File(file_path.getPath());
		String file_contents = f.readFile(test_file);
	
		LessParser orig = new LessParser(file_contents);
		orig.parseLess(true);
		
		// how many blocks are in the file?
		Assert.assertEquals(6, orig.getLessFile().size());

		// look more deeply into the first block
		Assert.assertEquals(Block.class, orig.getLessFile().get(0).getClass());

		// count number of children in first block
		Block first_ele = (Block) orig.getLessFile().get(0);
		Assert.assertEquals(5, first_ele.children.size());
		
		// how many of each type?
		int prop = 0, block = 0;
		for(int i = 0; i<first_ele.children.size(); i++){
			if(first_ele.children.get(i) instanceof Block){
				block++;
			}
			else if(first_ele.children.get(i) instanceof Property){
				prop++;
			}
			else{
				Assert.fail("the nuber of props and blocks is wrong");
			}
		}
		Assert.assertEquals(4, prop);
		Assert.assertEquals(1, block);
	}
}
