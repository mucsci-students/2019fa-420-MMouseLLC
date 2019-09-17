import java.util.List;
import java.util.Scanner;


public class AddClass {

	public static void main(String[] args) {
		UMLEnvironment uE = new UMLEnvironment();
		// REPL
		// Only takes command add name
		Scanner in = new Scanner (System.in);
		while (in.hasNext()){
			String cmdline = in.nextLine();
			String[] cmd = cmdline.split(" ");
			if (cmd[0].equals("add")){
				if (!addClass(uE, 0, cmd[1], null)){
					System.out.println(cmd[1] + " is already added.");
				}
			} else {
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
		in.close();
	}
	
	/**
	 * Checks UMLEnvironment to see if any UMLItem's have name
	 * @param umlEnv
	 * @param name
	 * @return true if name is unique to the provided environment
	 */
	public static boolean canAddUnique(UMLEnvironment umlEnv, String name){
		for (UMLItem i : umlEnv.Items){
			if (i.getName().equals(name)){
				return false;
			}
		}
		return true;
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
		if (canAddUnique(umlEnv, name)){
			UMLItem uml = new UMLItem(id, name, -1 ,attr);
			umlEnv.addItem(uml);
			return true;
		}
		
		return false;
	}

}
