import java.util.List;


public class AddClass {

	public static void main(String[] args) {
		UMLItem uml = new UMLItem(0, null, 0, null);
		
		UMLEnvironment uE = new UMLEnvironment();
		
	}
	
	public static boolean canAddUnique(UMLEnvironment umlEnv, String name){
		
		for (UMLItem i : umlEnv.Items){
			if (i.getName().equals(name)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 
	 * @param umlEnv
	 * @param id
	 * @param name
	 * @param attr
	 * @return
	 */
	public static boolean addClass(UMLEnvironment umlEnv, int id, String name, List<String> attr){
		if (canAddUnique(umlEnv, name)){
			UMLItem uml = new UMLItem(id, name, -1 ,attr);
			umlEnv.addItem(uml);
			return true;
		}
		
		return false;
	}

}
