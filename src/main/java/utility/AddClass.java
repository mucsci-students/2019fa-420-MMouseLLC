package utility;

import data.UMLEnvironment;
import data.UMLItem;

/**
 * Class containing methods for adding, finding and editing a UMLItem in a given
 * 	UMLEnvironment. 
 * @author Matt Fossett, Grant Hartenstine
 */

public class AddClass {

	/**
	 * Accesses item by name in the UMLEnvironment list
	 * @param umlEnv
	 * @param name
	 * @return UMLItem if exists, else null
	 */
	public static UMLItem getItem(UMLEnvironment umlEnv, String name) {
		for (UMLItem i : umlEnv.getItems()){
			if (i.getName().equals(name)){
				return i;
			}
		}
		return null;
	}
	
	/**
	 * Adds a new UMLItem object to a provided UMLEnvironment object
	 * 
	 * @param umlEnv
	 * @param id
	 * @param name
	 * @param attr
	 * @return true if name is unique to the provided UMLEnvironment
	 */
	public static boolean addClass(UMLEnvironment umlEnv, int id, String name){
		if (getItem(umlEnv, name) == null){
			UMLItem uml = new UMLItem(id, name);
			umlEnv.addItem(uml);
			return true;
		}
		
		return false;
	}
	
	//Adds a Class with only name and uses the size of Env to create a unique ID
	public static boolean addClass(UMLEnvironment umlEnv, String name)
	{
		if (getItem(umlEnv, name) == null){
			UMLItem uml = new UMLItem((umlEnv.getSize() + 1), name);
			umlEnv.addItem(uml);
			return true;
		}
		
		return false;
	}
	
	public static boolean editItem(UMLEnvironment umlEnv, String oldName, String newName){
		// return false if the newName is already found in the list. NO dupes
		if (getItem(umlEnv, newName) != null) {
			System.out.println(newName + " already exists. Pick another name.");
			return false;
		}
		UMLItem i = getItem(umlEnv, oldName); 
		// if item exists
		if ( i != null) {
			i.setName(newName);
			return true;
		}
		System.out.println(oldName + " does not exist. Please create it.");
		return false;
	}
	

}
