
public class LessMerger {
	LessParser original;
	LessParser override;
	
	public LessMerger(LessParser orig, LessParser over){
		original = orig;
		override = over;
	}
	
	public void merge(){
		for(int i = 0; i< override.lessFile.size(); i++){
			if(override.lessFile.get(i).action == "remove" ){
				
			}
		}
	}
	public void removeLessObj(){
		
	}
}
