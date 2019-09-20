import java.io.IOException;
import java.util.*;
public class Console {

	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		homeScreen();
	}	*/
	/**
	 * Where user begins with all commands
	 * 
	 * @param env the UMLEnvironment
	 * @throws IOException signals that an I/O exception has occurred.
	 * 
	 */
	public static void homeScreen (UMLEnvironment env) throws IOException {
		System.out.print("Please input a command: ");
		
		Scanner console = new Scanner (System.in);
		String input = console.next();
		
		while (console.hasNextLine()) {
			checkInput(input, env);
			input = console.next();
		}
		console.close();
	}
	
	/**
	 * Takes users command from homescreen and checks if its a valid command 
	 * 
	 * @param input users request
	 * @param env the  UMLEvnironment
	 * @throws IOException signals that an I/O exception has occurred
	 */
	public static void checkInput(String input, UMLEnvironment env) throws IOException {
			if (input.equals("add")) {
				add(env);
			} else if (input.equals("list")) {
				list(env);
			} else if (input.equals("load")) {
				load(env);
			} else if (input.equals("save")) {
				save(env);
			} else if (input.equals("edit")) {
				edit(env);
			}
			else {
				System.err.println("command not found - retry");
				homeScreen(env);
			}
	}
	
	/**
	 * prompts user for input and calls add function to add a new class in environment
	 * then gives a list of the classes currently in the environment
	 * 
	 * @param env the UMLEvnironment
	 * @throws IOException signals that an I/O exception has occurred 
	 */
	public static void add(UMLEnvironment env) throws IOException {
		System.out.print("Enter new class name: ");
		Scanner console = new Scanner (System.in);
		String newClass = console.next();
		System.out.print("Add class " + newClass + "? (y/n): ");
		String answer = console.next();
		if (answer.equals("y")) {
			boolean added = AddClass.addClass(env, newClass);
			if(added) {
				System.out.println("Class added " + newClass + ".");
				list(env);
			} else {
				System.out.println(newClass + " is already a class.");
				list(env);
			}
		} else {
			System.out.println("Add cancelled.");
		}
		homeScreen(env);
		console.close();
	}
	
	/**
	 * gives a list of the classes currently in the environment
	 * 
	 * @param env the UMLEnvironment
	 * @throws IOException signals that an I/O exception has occurred
	 */
	public static void list(UMLEnvironment env) throws IOException {
		System.out.print("List of classes: [ ");
		for (UMLItem i : env.getItems()){
			System.out.print(i.getName() + " ");
		}
		System.out.print("]\n");
	}
	
	/**
	 * loads an existing file into the environment
	 * if user wishes to save unsaved work, directed to save
	 * otherwise load function is called, then returned to homescreen
	 * 
	 * @param env the UMLEnvironment
	 * @throws IOException signals that an I/O exception has occurred
	 */
	public static void load(UMLEnvironment env) throws IOException {
		System.out.print("Any unsaved work will be lost, do you want to save? (y/n): ");
		Scanner console = new Scanner (System.in);
		String answer = console.next();
		if (answer.equals("y")) {
			save(env);
		} else {
			System.out.print("Enter file name to load: ");
			String fileName = console.next();
			LocalFile file = new LocalFile(fileName);
			if (!file.hasExistingFileName(fileName)) {
				System.out.print("No such file under name: " + fileName + ". Load canceled.\n");
				homeScreen(env);
			} else {
				env = file.loadFile();
			}
			System.out.println("Load complete.");
			homeScreen(env);
		}
		console.close();
	}
	
	/**
	 * prompts user for filename and checks if a file already exists under that name
	 * will either cancel or overwrite based on user input, then return to homescreen
	 * @param env the UMLEnvironment 
	 * @throws IOException signals that an I/O exception has occurred
	 */
	public static void save(UMLEnvironment env) throws IOException {
		
		System.out.print("Save current work? (y/n): ");
		Scanner console = new Scanner (System.in);
		String answer = console.next();
			if (answer.equals("y")) {
				System.out.print("Please enter file name: ");
				String fileName = console.next();
				LocalFile file = new LocalFile(env, fileName);
				System.out.print("Use filename: " + fileName + "? (y/n): ");
				answer = console.next();
				if (answer.equals("y")) {
					while(true) {
						if(file.hasExistingFileName(fileName)) {
							System.out.print(fileName + " is already a saved file. Do you wish to overwrite this file? (y/n): ");
							answer = console.next();
							if (answer.equals("y")) {
								break;
							} else {
								System.out.print("Save canceled.");
								homeScreen(env);
							}
						} else {
							break;
						}
					}	
					file.saveFile();
					System.out.println("File has been saved.");
					homeScreen(env);
				} else {	
					System.out.println("Save canceled.");
					homeScreen(env);
				}
			} else {
				System.out.println("Save canceled.");
				homeScreen(env); 
			}
			console.close();
	}		
	
	public static void edit(UMLEnvironment env) throws IOException {
		System.out.print("Enter old class name: ");
		Scanner console = new Scanner (System.in);
		String oldClass = console.next();
		System.out.print("Enter new class name: ");
		String newClass = console.next();
		System.out.print("Change " + oldClass + " to " + newClass + "? (y/n): ");
		String answer = console.next();
		if (answer.equals("y")) {
			boolean edited = AddClass.editItem(env, oldClass, newClass);
			if(edited) {
				System.out.println("Class " + oldClass + " changed to " + newClass + ".");
				list(env);
			} else {
				list(env);
			}
		} else {
			System.out.println("Add cancelled.");
		}
		homeScreen(env);
		console.close();
	}

}
