import java.io.IOException;
import java.util.Scanner;
public class Console {

	/**
	 * Where user begins with all commands
	 * 
	 * @param env the UMLEnvironment
	 * @throws IOException signals that an I/O exception has occurred.
	 * 
	 */
	public static void homeScreen (UMLEnvironment env) throws IOException {
		
		final Scanner console = ReplScanner.getInstance();
		while (true) {
			System.out.print("Please input a command: ");
			if (console.hasNextLine()){
				String input = console.nextLine();
				checkInput(input, env);	
			} else {
				break;
			}
		}
		console.close();
	}

	public static void multiArgCommand(UMLEnvironment env, String[] input) throws IOException{
		input[0] = input[0].toLowerCase();
		if (input[0].equals("add")){
			if (!AddClass.addClass(env, input[1])){
				System.out.println(input[1] + " is already a class.");
			}
		} else if (input[0].equals("edit")){
			if (input.length < 3){
				System.out.println("Invalid: edit [oldClass] [newClass]");
			} else {
				AddClass.editItem(env, input[1], input[2]);
			}
		} else if (input[0].equals("find")){
			UMLItem i = AddClass.getItem(env, input[1]);
			if (i != null){
				System.out.println(i + " exists.");
			} else {
				System.out.println(i + " does not exist.");
			}
		} else {
			System.out.println("Invalid Command");
		}
	}
	
	/**
	 * Takes users command from homescreen and checks if its a valid command 
	 * 
	 * @param input users request
	 * @param env the  UMLEvnironment
	 * @throws IOException signals that an I/O exception has occurred
	 */
	public static void checkInput(String line, UMLEnvironment env) throws IOException {
		String[] lineArr = line.split(" ");
		String input = lineArr[0];
		if (lineArr.length > 1){
			multiArgCommand(env, lineArr);
			return;
		}
		if (input.toLowerCase().equals("add")) {
			add(env);
		} else if (input.toLowerCase().equals("list")) {
			list(env);
		} else if (input.toLowerCase().equals("load")) {
			load(env);
		} else if (input.toLowerCase().equals("save")) {
			save(env);
		} else if (input.toLowerCase().equals("edit")) {
			edit(env);
		} else if (input.toLowerCase().equals("help")) {
			help(env);
		} else if (input.toLowerCase().equals("find")) {
			find(env);
		} else if (input.toLowerCase().equals("quit")) {
			quit(env);
		}
		
		else {
			System.out.println("command not found - retry");
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
		final Scanner console = ReplScanner.getInstance();
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
		console.nextLine();
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
		final Scanner console = ReplScanner.getInstance();
		String answer = console.next();
		if (answer.equals("y")) {
			save(env);
		} else {
			System.out.print("Enter file name to load: ");
			String fileName = console.next();
			LocalFile file = new LocalFile(fileName);
			if (!file.hasExistingFileName(fileName)) {
				System.out.print("No such file under name: " + fileName + ". Load canceled.\n");
			} else {
				env = file.loadFile();
				System.out.println("Load complete.");
			}
		}
	}
	
	/**
	 * prompts user for filename and checks if a file already exists under that name
	 * will either cancel or overwrite based on user input, then return to homescreen
	 * @param env the UMLEnvironment 
	 * @throws IOException signals that an I/O exception has occurred
	 */
	public static void save(UMLEnvironment env) throws IOException {
		
		System.out.print("Save current work? (y/n): ");
		final Scanner console = ReplScanner.getInstance();
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
							}
						} else {
							break;
						}
					}	
					file.saveFile();
					System.out.println("File has been saved.");
				} else {	
					System.out.println("Save canceled.");
				}
			} else {
				System.out.println("Save canceled.");
			}
			console.nextLine();
	}		
	
	public static void edit(UMLEnvironment env) throws IOException {
		System.out.print("Enter old class name: ");
		final Scanner console = ReplScanner.getInstance();
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
		console.nextLine();
	}
	
	public static void help (UMLEnvironment env) throws IOException {
		System.out.println("To add a class type \"add\" ");
		System.out.println("To list a class type \"list\" ");
		System.out.println("To edit a class type \"edit\" ");
		System.out.println("To save your project \"save\" ");
		System.out.println("To load your project type \"load\" ");
		System.out.println(" ");
	}
	
	public static void find(UMLEnvironment env) throws IOException {
		System.out.print("Enter class name to find: ");
		final Scanner console = ReplScanner.getInstance();
		String name = console.next();
		for (UMLItem i : env.getItems()){
			if(i.getName().toLowerCase().equals(name.toLowerCase())) {
				System.out.println("Class " + name + " exists.");
				return;
			}
		}
		System.out.println("Class does not exist.");
		console.nextLine();
	}
	
	public static void quit(UMLEnvironment env) {
		System.out.print("Any unsaved work will be lost, do you want to save? (y/n): ");
		final Scanner console = ReplScanner.getInstance();
		String answer = console.next();
		if (answer.equals("y")) {
			saveAndQuit(env);
		} else { 
			System.out.println("Quitting program");
		}
		console.close();
		System.exit(0);
	}
	
	public static void saveAndQuit(UMLEnvironment env) {
		LocalFile file = new LocalFile(env);
		boolean needsValidSaveResp = true;
		final Scanner console = ReplScanner.getInstance();
		String fileName = "";
		while(needsValidSaveResp) {
			System.out.print("Please enter a filename to save: ");
			fileName = console.nextLine();
			if(file.hasExistingFileName(fileName)) {
				System.out.print("Filename currently exists. Overwrite? (Y/N)");
				String response = console.nextLine();
			 	if(response.equalsIgnoreCase("y"))
	          		needsValidSaveResp = false;
	    		else if(!response.equalsIgnoreCase("n"))
	          		System.out.println("Response not valid. Please try again.");
			} else {
				System.out.println("Saving file");
				needsValidSaveResp = false;
			}
		}
		file.saveFile();
		System.out.println("File saved");
	}
	
}
