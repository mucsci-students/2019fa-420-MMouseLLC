package utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import data.UMLEnvironment;
import data.UMLItem;

public class Console {

  UMLEnvironment env;
  
  public Console(UMLEnvironment env) {
    this.env = env;
  }
  
	/**
	 * Where user begins with all commands
	 * 
	 * @param env
	 *            the UMLEnvironment
	 * @throws IOException
	 *             signals that an I/O exception has occurred.
	 * 
	 */
	public void homeScreen() throws IOException {
		System.out.println("For a list of commands please type \"help\"  ");
		final Scanner console = ReplScanner.getInstance();
		while (true) {
			System.out.flush();
			System.err.flush();
			
			System.out.print("Please input a command: ");
			if (console.hasNextLine()) {
				String input = console.nextLine();
				checkInput(input);
			} else {
				break;
			}
		}
		console.close();
	}

	/**
	 * Processes commands in single-line format to skip prompts. 
	 * Commands taken are add, edit, find, save and load.
	 * Check the help2 command to see exact specs for each.  
	 * @param input
	 * @throws IOException
	 */
	public void multiArgCommand(String[] input) throws IOException {
		input[0] = input[0].toLowerCase();
		if (input[0].equals("add")) {
			// Adds class or promts that it is taken. 
			if (!AddClass.addClass(env, input[1])) {
				System.out.println(input[1] + " is already a class.");
			}
		} else if (input[0].equals("edit")) {
			if (input.length < 3) {
				System.out.println("Invalid: edit [oldClass] [newClass]");
			} else {
				AddClass.editItem(env, input[1], input[2]);
			}
		} else if (input[0].equals("find")) {
			UMLItem i = AddClass.getItem(env, input[1]);
			if (i != null) {
				System.out.println(i + " exists.");
			} else {
				System.out.println(i + " does not exist.");
			}
		} else if (input[0].equals("save")){
			boolean overWrite = false;
			// build up string again to allow spaces in file save
			ArrayList<String> easier = new ArrayList<String>();
			for (String i : input){
				easier.add(i);
			}
			easier.remove(0);
			// Check for -f flag, which allows overwriting same-name file
			if (easier.contains("-f")){
				easier.remove("-f");
				overWrite = true;
			}
			String buildUp = "";
			for (String i : easier){
				buildUp += i + " ";
			}
			buildUp = buildUp.trim();
			LocalFile file = new LocalFile(env, buildUp);
			// Only allow existing files to overwrite if -f flag 
			if (file.hasExistingFileName(buildUp) && !overWrite){
				System.out.println("Filename: " + buildUp + " already exists. Run command with \"-f\" flag to overwrite.");
				return;
			}
			file.saveFile();
		} else if (input[0].equals("load")){
			ArrayList<String> easier = new ArrayList<String>();
			for (String i : input){
				easier.add(i);
			}
			easier.remove(0);
			// Force users to use -f flag to confirm awareness of not saving
			if (easier.contains("-f")){
				easier.remove("-f");
			} else {
				System.out.println("Use flag \"-f\" to confirm awareness that unsaved changes will be lost.");
				return;
			}
			String buildUp = "";
			for (String i : easier){
				buildUp += i + " ";
			}
			buildUp = buildUp.trim();
			LocalFile file = new LocalFile();
			if (!file.hasExistingFileName(buildUp)){
				System.out.println("Filename: " + buildUp + " does not exist.");
				return;
			}
			file.setFileName(buildUp);
			env = file.loadFile();
		} else {
			System.out.println("Invalid Command");
		}
	}

	/**
	 * Takes users command from homescreen and checks if its a valid command
	 * 
	 * @param input
	 *            users request
	 * @param env
	 *            the UMLEvnironment
	 * @throws IOException
	 *             signals that an I/O exception has occurred
	 */
	public void checkInput(String line) throws IOException {
		String[] lineArr = line.split(" ");
		String input = lineArr[0];
		if (lineArr.length > 1) {
			multiArgCommand(lineArr);
			return;
		}
		if (input.toLowerCase().equals("add")) {
			add();
		} else if (input.toLowerCase().equals("list")) {
			list();
		} else if (input.toLowerCase().equals("load")) {
			load();
		} else if (input.toLowerCase().equals("save")) {
			save();
		} else if (input.toLowerCase().equals("edit")) {
			edit();
		} else if (input.toLowerCase().equals("help")) {
			help();
		} else if (input.toLowerCase().equals("help2")) {
			help2();
		} else if (input.toLowerCase().equals("find")) {
			find();
		} else if (input.toLowerCase().equals("quit")) {
			quit();
		}

		else {
			System.out.println("command not found - retry");
			System.out.println("For a list of commands please type \"help\"  ");
		}
	}

	/**
	 * prompts user for input and calls add function to add a new class in
	 * environment then gives a list of the classes currently in the environment
	 * 
	 * @param env
	 *            the UMLEvnironment
	 * @throws IOException
	 *             signals that an I/O exception has occurred
	 */
	public void add() throws IOException {
		System.out.print("Enter new class name: ");
		final Scanner console = ReplScanner.getInstance();
		String newClass = console.next();
		UMLItem isNull = AddClass.getItem(env, newClass);
		if (isNull != null){
			System.out.println("Class " + newClass + " is already added. Use \"edit\" to modify this class.");
			console.nextLine();
			list();
			return;
		}
		System.out.print("Add class " + newClass + "? (y/n): ");
		while (true){
			String answer = console.next();
			if (answer.equals("y")) {
				AddClass.addClass(env, newClass);
				console.nextLine();
				list();
				return;
			} else if (answer.equals("n")){
				System.out.println("Add cancelled.");
				console.nextLine();
				list();
				return;
			}
			System.out.println("Please confirm (y/n).");
		}
	}

	/**
	 * gives a list of the classes currently in the environment
	 * 
	 * @param env
	 *            the UMLEnvironment
	 * @throws IOException
	 *             signals that an I/O exception has occurred
	 */
	public void list() throws IOException {
		System.out.print("List of classes: [ ");
		for (UMLItem i : env.getItems()) {
			System.out.print(i.getName() + " ");
		}
		System.out.print("]\n");
	}

	/**
	 * loads an existing file into the environment if user wishes to save
	 * unsaved work, directed to save otherwise load function is called, then
	 * returned to homescreen
	 * 
	 * @param env
	 *            the UMLEnvironment
	 * @throws IOException
	 *             signals that an I/O exception has occurred
	 */
	public void load() throws IOException {
		System.out.print("Any unsaved work will be lost, do you want to save? (y/n): ");
		final Scanner console = ReplScanner.getInstance();
		String answer = console.nextLine();
		if (answer.equals("y")) {
			save();
		} else {
			System.out.print("Enter file name to load: ");
			String fileName = console.nextLine();
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
	 * prompts user for filename and checks if a file already exists under that
	 * name will either cancel or overwrite based on user input, then return to
	 * homescreen
	 * 
	 * @param env
	 *            the UMLEnvironment
	 * @throws IOException
	 *             signals that an I/O exception has occurred
	 */
	public void save() throws IOException {

		System.out.print("Save current work? (y/n): ");
		final Scanner console = ReplScanner.getInstance();
		String answer = console.next();
		console.nextLine();
		if (answer.equals("y")) {
			System.out.print("Please enter file name: ");
			String fileName = console.nextLine();
			LocalFile file = new LocalFile(env, fileName);
			System.out.print("Use filename: " + fileName + "? (y/n): ");
			answer = console.next();
			if (answer.equals("y")) {
				while (true) {
					if (file.hasExistingFileName(fileName)) {
						System.out.print(
								fileName + " is already a saved file. Do you wish to overwrite this file? (y/n): ");
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

	public void edit() throws IOException {
		System.out.print("Enter old class name: ");
		final Scanner console = ReplScanner.getInstance();
		String oldClass = console.next();
		// Check if oldClass exists
		if (AddClass.getItem(env, oldClass) == null){
			System.out.println("Class " + oldClass+ " does not exist. Please choose an existing class.");
			console.nextLine();
			list();
			return;
		}
		System.out.print("Enter new class name: ");
		String newClass = console.next();
		// check if new class is not already a class
		if (AddClass.getItem(env, newClass) != null){
			System.out.println("Class " + newClass + " already exists. Please choose another class name.");
			console.nextLine();
			list();
			return;
		}
		
		System.out.print("Change " + oldClass + " to " + newClass + "? (y/n): ");
		while(true){
			String answer = console.next();
			if (answer.equals("y")) {
				AddClass.editItem(env, oldClass, newClass);
				System.out.println("Class " + oldClass + " changed to " + newClass+ ".");
				list();
				console.nextLine();
				return;
			} else if (answer.equals("n")){
				System.out.println("Edit cancelled.");
				list();
				console.nextLine();
				return;
			// Force them to conform to our prompts demands
			} else {
				System.out.println("Please confirm (y/n).");
			}
			
		}
	}

	public void help() {
		System.out.println("To add a class type \"add\" ");
		System.out.println("To list a class type \"list\" ");
		System.out.println("To edit a class type \"edit\" ");
		System.out.println("To find if class exists type \"find\" ");
		System.out.println("To save your project \"save\" ");
		System.out.println("To load your project type \"load\" ");
		System.out.println("To quit your project type \"quit\" ");
		System.out.println("To list commands type \"help\" ");
		System.out.println("To view single-lined command syntax type \"help2\" ");
		System.out.println(" ");
	}

	public void help2(){
		System.out.println("Here is a list of the commands executed in one line without prompts.");
		System.out.println("add  [className]");
		System.out.println("edit [originalClass] [newClass] ");
		System.out.println("find [className]");
		System.out.println("save [flag \"-f\" to overwrite] [filename]");
		System.out.println("load [flag \"-f\" confirms unsaved changes lost] [filename] ");
		System.out.println(" ");
	}
	
	public void find() {
		System.out.print("Enter class name to find: ");
		final Scanner console = ReplScanner.getInstance();
		String name = console.next();
		for (UMLItem i : env.getItems()) {
			if (i.getName().toLowerCase().equals(name.toLowerCase())) {
				System.out.println("Class " + name + " exists.");
				return;
			}
		}
		System.out.println("Class does not exist.");
		console.nextLine();
	}

	public void quit() {
		System.out.print("Any unsaved work will be lost, do you want to save? (y/n): ");
		final Scanner console = ReplScanner.getInstance();
		String answer = console.next();
		if (answer.equals("y")) {
			saveAndQuit();
		} else {
			System.out.println("Quitting program");
		}
		console.close();
		System.exit(0);
	}

	public void saveAndQuit() {
		LocalFile file = new LocalFile(env);
		boolean needsValidSaveResp = true;
		final Scanner console = ReplScanner.getInstance();
		console.nextLine();
		String fileName = "";
		while (needsValidSaveResp) {
			System.out.print("Please enter a filename to save: ");
			fileName = console.nextLine();
			if (file.hasExistingFileName(fileName)) {
				System.out.print("Filename currently exists. Overwrite? (Y/N)");
				String response = console.nextLine();
				if (response.equalsIgnoreCase("y"))
					needsValidSaveResp = false;
				else if (!response.equalsIgnoreCase("n")) {
					System.out.println("Response not valid. Please try again.");
					System.out.println("For a list of commands please type \"help\"  ");
				}
			} else {
				System.out.println("Saving file");
				needsValidSaveResp = false;
			}
		}
		file.setFileName(fileName);
		file.saveFile();
		System.out.println("File saved");
	}

}
