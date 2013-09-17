
public class Property extends LessObject{
	String name;
	String value;
	
	public Property(String prop){
		String[] property = prop.split(":");
		name = property[0];
		value = property[1];
	}
}
