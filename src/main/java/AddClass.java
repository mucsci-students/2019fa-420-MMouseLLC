import java.util.List;
import java.util.Scanner;


public class AddClass {

	public static void main(String[] args) {
		UMLEnvironment uE = new UMLEnvironment();
		// REPL
		// Only takes command add name
		final Scanner in = ReplScanner.getInstance();
		while (in.hasNext()){
			String cmdline = in.nextLine();
			String[] cmd = cmdline.split(" ");
			if (cmd[0].equals("add")){
				if (!addClass(uE, 0, cmd[1], null)){
					System.out.println(cmd[1] + " is already added.");
				}
			} else if (cmd[0].equals("edit")) {
				// cmd syntax: edit oldClassName newClassName
				//editItem(uE, cmd[1], cmd[2]);
				if (!editItem(uE, cmd[1], cmd[2])){
					System.out.println(cmd[2] + " is already a class.");
				}
			}
			
			else {
				System.out.println("Invalid command");
				continue;
			}
			// PRINT
			System.out.print("[ ");
			for (UMLItem i : uE.getItems()){
				System.out.print(i.getName() + " ");
			}
			System.out.print("]\n");	
		}
	}
	
	/**
	 * Accesses item by name in the UMLEnvironment list
	 * @param umlEnv
	 * @param name
	 * @return UMLItem if exists, else null
	 */
	public static UMLItem getItem(UMLEnvironment umlEnv, String name) {
		for (UMLItem i : umlEnv.Items){
			if (i.getName().toLowerCase().equals(name.toLowerCase())){
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
	public static boolean addClass(UMLEnvironment umlEnv, int id, String name, List<String> attr){
		if (getItem(umlEnv, name) == null){
			UMLItem uml = new UMLItem(id, name, -1 ,attr);
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
		// return false if the newName is already found in the list. NO dups
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
