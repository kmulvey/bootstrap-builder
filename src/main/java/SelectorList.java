import java.util.ArrayList;


public class SelectorList {
	private ArrayList<Block> blocks = new ArrayList<Block>();
	
	public SelectorList(){
		
	}
	public void addBlock(Block b){
		blocks.add(b);
	}
	public int getBlockCount(){
		return blocks.size();
	}
}
