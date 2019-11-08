package utility;

import java.util.logging.Logger;

import org.jline.reader.LineReader;

import config.HelpScreenConfig;
import data.Relationship;
import data.UMLEnvironment;
import data.UMLItem;
import mapper.FieldMapper;
import mapper.FunctionMapper;

/**
 * The Class ConsoleEnum.
 */
public class Console {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(Console.class.getName());

	/** The LineReader. */
	private LineReader reader;

	/** The UMLEnvironment. */
	private UMLEnvironment env;

	public Console() {
		this.env = new UMLEnvironment();
	}

	/**
	 * The run command. This sets up the initial instance of the Console and builds
	 * the console, which creates the tools for tab completion in the terminal,
	 * along with a reader that takes user input.
	 */
	public void run() {
		reader = new ConsoleBuilder().buildConsole();
		homeScreen();
	}

	/**
	 * The Home Screen. Is displayed after initial setup, as well as after improper
	 * command input.
	 */
	private void homeScreen() {
		System.out.println("For a list of commands please type \"help\"  ");
		while (true) {
			String line = reader.readLine("Please input a command: ").replaceAll("\\s+", " ").trim();
			String[] input = line.split(" ");
			checkInput(input);
		}
	}

	/**
	 * Processes commands in single-line format to skip prompts. Commands taken are
	 * add, edit, find, save and load. Check the "help" command to see exact specs
	 * for each.
	 *
	 * @param input The input
	 */
	public void checkInput(String[] input) {
		String command = input[0].toLowerCase();
		if (command.equals("add")) {
			add(input);
		} else if (command.equals("delete")) {
			delete(input);
		} else if (command.equals("edit")) {
			edit(input);
		} else if (command.equals("find")) {
			find(input);
		} else if (command.equals("list")) {
			list();
		} else if (command.equals("save")) {
			save(input);
		} else if (command.equals("load")) {
			load(input);
		} else if (command.equals("add_relationship")) {
			addRelationship(input);
		} else if (command.equals("edit_relationship")) {
			editRelationship(input);
		} else if (command.equals("remove_relationship")) {
			removeRelationship(input);
		} else if (command.equals("list_relationships")) {
			listRelationships();
		} else if (command.equals("edit_field_type")) {
			editFieldType(input);
		} else if (command.equals("edit_function_type")) {
			editFunctionType(input);
		} else if (command.equals("edit_field_var")) {
			editFieldVar(input);
		} else if (command.equals("edit_function_var")) {
			editFunctionVar(input);
		} else if (command.equals("add_field")) {
			addField(input);
		} else if (command.equals("add_function")) {
			addFunction(input);
		} else if (command.equals("list_fields")) {
			listFields(input);
		} else if (command.equals("list_functions")) {
			listFunction(input);
		} else if (command.equals("delete_field")) {
			deleteField(input);
		} else if (command.equals("delete_function")) {
			deleteFunction(input);
		} else if (command.equals("quit")) {
			quit();
		} else if (command.equals("help")) {
			help();
		} else {
			logger.warning("Invalid Command");
		}
	}

	/**
	 * Takes the input adds the new class(es) in environment, then gives a list of
	 * the classes currently in the environment.
	 *
	 * @param input The input
	 */
	private void add(String[] input) {
		if (input.length < 2) {
			logger.warning("Usage: add [className] [optionalAdditionalClass(es)]");
			return;
		}
		for (int i = 1; i < input.length; i++) {
			String newClass = input[i];
			UMLItem item = new UMLItem(newClass);
			env.addItem(item);
		}
		env.listClasses();
	}

	/**
	 * Searches for a class and if it finds it, removes it.
	 * 
	 * @param input The input
	 */
	public void delete(String[] input) {
		if (input.length < 3 || !input[1].equals("-f")) {
			logger.warning("Usage: delete [flag \"-f\" to confirm delete] [className]");
			return;
		}
		for (int i = 2; i < input.length; i++) {
			UMLItem item = env.findItem(input[i]);
			env.removeItem(item);
		}
	}

	/**
	 * Takes the input and edits the name of the UMLEnvironment. Checks if there are
	 * valid parameters and if the class exists and the new name is not currently a
	 * class name.
	 *
	 * @param input The input
	 */
	public void edit(String[] input) {
		if (input.length != 3) {
			logger.warning("Invalid: edit [oldClass] [newClass] - 3 fields required, " + input.length + " found.");
			return;
		}
		String oldClass = input[1];
		String newClass = input[2];
		UMLItem item = env.findItem(oldClass);

		if (canOverwriteEdit(oldClass, newClass)) {
			env.editItem(oldClass, newClass, item);
		}
	}

	/**
	 * Finds if a class exists in a given set of arguments.
	 *
	 * @param input The input
	 */
	public void find(String[] input) {
		for (int i = 1; i < input.length; i++) {
			if (env.findItem(input[i]) != null) {
				System.out.println("Class " + input[i] + " found.");
			}
		}
	}

	/**
	 * Lists all classes in the environment
	 */
	public void list() {
		System.out.println(env.listClasses());
	}

	/**
	 * Saves a file. Checks if the file name exists and if there is an overwrite
	 * flag attached.
	 *
	 * @param input The input
	 */
	public void save(String[] input) {
		String builtFileName = buildString(input);
		LocalFile file = new LocalFile(env, builtFileName);
		// Only allow existing files to overwrite if -f flag
		if (file.hasExistingFileName(builtFileName) && !hasOverwriteFlag(input)) {
			logger.warning(
					"Filename: " + builtFileName + " already exists. Run command with \"-f\" flag to overwrite.");
			return;
		}
		file.saveFile();
	}

	/**
	 * Loads a file. Checks if the file name exists and if there is a flag to load
	 * without saving prior.
	 *
	 * @param input The input
	 */
	public void load(String[] input) {
		// Force users to use -f flag to confirm awareness of not saving
		if (!hasOverwriteFlag(input)) {
			System.out.println("Use flag \"-f\" to confirm awareness that unsaved changes will be lost.");
			return;
		}
		String builtFileName = buildString(input);
		LocalFile file = new LocalFile();
		if (!file.hasExistingFileName(builtFileName)) {
			logger.warning("Filename: " + builtFileName + " does not exist.");
		} else {
			file.setFileName(builtFileName);
			env = file.loadFile();
		}
	}

	/**
	 * Adds a relationship between 2 UMLItems to environment
	 * Command: add_relationship [parentClass] [childClass] [optional quantifier]  
	 * @param input The input
	 */
	public void addRelationship(String[] input) {
		if (input.length < 3) {
			logger.warning("Invalid: add_relationship [childClass] [parentClass] - minimum 3 fields required, " + input.length
					+ " found.");
			return;
		}
		
		UMLItem parentItem  = env.findItem(input[1]);
		UMLItem childItem = env.findItem(input[2]);
		if (parentItem == null) {
			logger.warning("Class " + input[1] + " was not found.");
			return;
		} else if (childItem == null) {
			logger.warning("Class " + input[2] + " was not found.");
			return;
		}
		if (input.length > 3) {
			int q = getQuantifier(input[3]);
			if (q < 0) {
				logger.warning("Quantifier needs to be of form: [N] | [1t1] | [1tM] | [Mt1] | [MtM]");
				return;
			}
			Relationship r = new Relationship(parentItem, childItem, q);
			env.addRelationship(r);
		} else {
			Relationship r = new Relationship(parentItem, childItem);
			env.addRelationship(r);
		}

		System.out.println(env.listRelationships());
	}

	/**
	 * Adds a relationship between 2 UMLItems to environment
	 * Command: edit_relationship [parentClass] [childClass] [quantifier]  
	 * @param input The input
	 */
	public void editRelationship(String[] input) {
		if (input.length < 4) {
			logger.warning("Invalid: edit_relationship [childClass] [parentClass] [quantifier] - 4 fields required, " + input.length
					+ " found.");
			return;
		}
		UMLItem parentItem  = env.findItem(input[1]);
		UMLItem childItem = env.findItem(input[2]);
		if (parentItem == null) {
			logger.warning("Class " + input[1] + " was not found.");
			return;
		} else if (childItem == null) {
			logger.warning("Class " + input[2] + " was not found.");
			return;
		}

		Relationship r = env.findRelationship(new Relationship(parentItem, childItem));
		if (r == null) {
			logger.warning("Relationship not found. Add with command add_relationship");
			return;
		}
		int q = getQuantifier(input[3]);
		if (q < 0) {
			logger.warning("Quantifier needs to be of form: [N] | [1t1] | [1tM] | [Mt1] | [MtM]");
			return;
		}
		r.setQuantifier(q);
		System.out.println(env.listRelationships());
	}

	
	/**
	 * Return integer corresponding to command past with arg
	 *   This is further explained in the Relationship Class 
	 * @param arg
	 * @return
	 */
	private int getQuantifier(String arg) {
		switch (arg.toLowerCase()) {
		case ("n"):
			return 0;
		case ("1t1"):
			return 1;
		case ("1tm"):
			return 2;
		case ("mt1"):
			return 3;
		case ("mtm"):
			return 4;
		default:
			return -1;
		}
	}

	/**
	 * Remove a class relationship from the environment
	 * Command: remove_relationship [parentClass] [childClass] 
	 * @param input
	 * @return
	 */
	public void removeRelationship(String[] input) {
		if (input.length != 3) {
			logger.warning("Invalid: remove_relationship [parentClass] [childClass]");
			return;
		}
		
		UMLItem parentItem  = env.findItem(input[1]);
		UMLItem childItem = env.findItem(input[2]);
		if (childItem == null) {
			logger.warning("Class " + input[2] + " was not found.");
			return;
		} else if (parentItem == null) {
			logger.warning("Class " + input[1] + " was not found.");
			return;
		}
		
		if (!env.removeRelationship(new Relationship(parentItem, childItem))) {
			logger.warning("Could not find relationship to remove.");
			return;
		}
		
		env.listRelationships();
	}
	
	public void listRelationships() {
		System.out.println(env.listRelationships());
	}
	
	
	/**
	 * Removes a child class from another class given the input of child and parent
	 * to be unlinked
	 * 
	 * @param input The input
	 */
	@Deprecated
	public void removeChild(String[] input) {
		if (input.length != 3) {
			logger.warning(
					"Invalid: removechild [childClass] [parentClass] - 3 fields required, " + input.length + " found.");
		} else {
			String childName = input[1];
			String parentName = input[2];
			UMLItem childItem = env.findItem(childName);
			UMLItem parentItem = env.findItem(parentName);

			env.removeChild(childName, parentName, childItem, parentItem);
			System.out.println(env.listClasses());
		}
	}

	/**
	 * Lists the children of a given parent class
	 * 
	 * @param input
	 */
	@Deprecated
	public void listChildren(String[] input) {
		if (input.length != 2) {
			logger.warning("Invalid: listchildren [parentClass] - 2 fields required, " + input.length + " found.");
		} else {
			String parentName = input[1];
			String childList = env.listChildren(parentName);
			System.out.println(childList);
		}
	}

	/**
	 * Lists the parents of a given child class
	 * 
	 * @param input The input
	 */
	@Deprecated
	public void listParents(String[] input) {
		if (input.length != 2) {
			logger.warning("Invalid: listparents [childClass] - 2 fields required, " + input.length + " found.");
		} else {
			String childName = input[1];
			String parentList = env.listParents(childName);
			System.out.println(parentList);
		}
	}

	/**
	 * edit a field type currently in a class and gives list of fields currently in
	 * the class
	 * 
	 * @param input The input
	 */
	public void editFieldType(String[] input) {
		if (input.length != 5) {
			logger.warning("Invalid: editfieldtype [Class] [Key] [Old Type] [New Type] - 5 fields required, "
					+ input.length + " found");
		} else {
			String className = input[1];
			String key = input[2];
			String oldType = input[3];
			String newType = input[4];

			UMLItem item = env.findItem(className);
			if (item == null) {
				logger.warning("Inavalid Class Name");
			}

			FieldMapper mapper = new FieldMapper(env);
			mapper.editFieldType(className, key, oldType, newType);
			String fields = mapper.listMap(item.getFields());
			System.out.println(fields);
		}
	}

	/**
	 * edit a Function type currently in a class and gives list of Functions
	 * currently in the class
	 * 
	 * @param input The input
	 */
	public void editFunctionType(String[] input) {
		if (input.length != 5) {
			logger.warning("Invalid: editFunctiontype [Class] [Key] [Old Type] [New Type] - 5 fields required, "
					+ input.length + " found");
		} else {
			String className = input[1];
			String key = input[2];
			String oldType = input[3];
			String newType = input[4];

			UMLItem item = env.findItem(className);
			if (item == null) {
				logger.warning("Inavalid Class Name");
			}

			FunctionMapper mapper = new FunctionMapper(env);
			mapper.editFunctionType(className, key, oldType, newType);
			String functions = mapper.listMap(item.getFunctions());
			System.out.println(functions);
		}
	}

	/**
	 * edit a field variable and the type currently in a class and gives list of
	 * fields currently in the class
	 * 
	 * @param input The input
	 */
	public void editFieldVar(String[] input) {
		if (input.length != 6) {
			logger.warning(
					"Invalid: editfieldvar [Class] [Old Key] [New Key] [Old Type] [New Type] - 6 fields required, "
							+ input.length + " found");
		} else {
			String className = input[1];
			String oldKey = input[2];
			String newKey = input[3];
			String oldType = input[4];
			String newType = input[5];

			UMLItem item = env.findItem(className);
			if (item == null) {
				logger.warning("Inavalid Class Name");
			}

			FieldMapper mapper = new FieldMapper(env);
			mapper.editFieldVar(className, oldKey, newKey, oldType, newType);
			String fields = mapper.listMap(item.getFields());
			System.out.println(fields);
		}
	}

	/**
	 * edit a function variable and the type currently in a class and gives list of
	 * functions currently in the class
	 * 
	 * @param input The input
	 */
	public void editFunctionVar(String[] input) {
		if (input.length != 6) {
			logger.warning(
					"Invalid: editfunctionvar [Class] [Old Key] [New Key] [Old Type] [New Type] - 6 fields required, "
							+ input.length + " found");
		} else {
			String className = input[1];
			String oldKey = input[2];
			String newKey = input[3];
			String oldType = input[4];
			String newType = input[5];

			UMLItem item = env.findItem(className);
			if (item == null) {
				logger.warning("Inavalid Class Name");
			}

			FunctionMapper mapper = new FunctionMapper(env);
			mapper.editFunctionVar(className, oldKey, newKey, oldType, newType);
			String functions = mapper.listMap(item.getFunctions());
			System.out.println(functions);
		}
	}

	/**
	 * add a new field in an exisiting class and gives a list of the field currently
	 * in the class
	 * 
	 * @param input The input
	 */
	public void addField(String[] input) {
		if (input.length != 4) {
			logger.warning("Invalid: addfield [Class] [type] [var] - 4 fields required, " + input.length + " found");
		} else {
			String className = input[1];
			String type = input[2];
			String var = input[3];

			UMLItem item = env.findItem(className);
			if (item == null) {
				logger.warning("Inavalid Class Name");
			}
			UMLItem findType = env.findItem(type);
			if (findType != null) {
				env.addRelationship(new Relationship(findType, item));
			}

			FieldMapper mapper = new FieldMapper(env);
			mapper.addField(className, type, var);
			String fields = mapper.listMap(item.getFields());
			System.out.println(fields);
		}
	}

	/**
	 * add a new Function in an exisiting class and gives a list of the Function
	 * currently in the class
	 * 
	 * @param input The input
	 */
	public void addFunction(String[] input) {
		if (input.length != 4) {
			logger.warning("Invalid: addfunction [Class] [type] [var] - 4 fields required, " + input.length + " found");
		} else {
			String className = input[1];
			String type = input[2];
			String var = input[3];

			UMLItem item = env.findItem(className);
			if (item == null) {
				logger.warning("Inavalid Class Name");
			}

			UMLItem findType = env.findItem(type);
			if (findType != null) {
				env.addRelationship(new Relationship(findType, item));
			}
			
			FunctionMapper mapper = new FunctionMapper(env);
			mapper.addFunction(className, type, var);
			String functions = mapper.listMap(item.getFunctions());
			System.out.println(functions);
		}
	}

	/**
	 * list all the Fields in an existing class
	 * 
	 * @param input The input
	 */
	public void listFields(String[] input) {
		if (input.length != 2) {
			logger.warning("Invalid: listFields [Class] - 2 fields required, " + input.length + " found");
		} else {
			String className = input[1];
			UMLItem item = env.findItem(className);
			if (item == null) {
				logger.warning("Inavalid Class Name");
			}
			FieldMapper mapper = new FieldMapper(env);
			String fields = mapper.listMap(item.getFields());
			System.out.println(fields);
		}
	}

	/**
	 * list all the Functions in an existing class
	 * 
	 * @param input The input
	 */
	public void listFunction(String[] input) {
		if (input.length != 2) {
			logger.warning("Invalid: listFunctions [Class] - 2 fields required, " + input.length + " found");
		} else {
			String className = input[1];
			UMLItem item = env.findItem(className);
			if (item == null) {
				logger.warning("Inavalid Class Name");
			}
			FunctionMapper mapper = new FunctionMapper(env);
			String functions = mapper.listMap(item.getFunctions());
			System.out.println(functions);
		}
	}

	/**
	 * delete a field currently in a class gives and gives list of fields remaining
	 * in the class
	 * 
	 * @param input The input
	 */
	public void deleteField(String[] input) {
		if (input.length != 3) {
			logger.warning("Invalid: deleteField [Class] [Field] - 3 fields required, " + input.length + " found");
		} else {
			String className = input[1];
			String var = input[2];

			UMLItem item = env.findItem(className);
			if (item == null) {
				logger.warning("Inavalid Class Name");
			}

			FieldMapper mapper = new FieldMapper(env);
			mapper.deleteField(className, var);
			String fields = mapper.listMap(item.getFields());
			System.out.println(fields);
		}
	}

	/**
	 * delete a Function currently in a class gives and gives list of Function s
	 * remaining in the class
	 * 
	 * @param input The input
	 */
	public void deleteFunction(String[] input) {
		if (input.length != 3) {
			logger.warning(
					"Invalid:  deleteFunction [Class] [Function] - 3 fields required, " + input.length + " found");
		} else {
			String className = input[1];
			String var = input[2];

			UMLItem item = env.findItem(className);
			if (item == null) {
				logger.warning("Inavalid Class Name");
			}

			FunctionMapper mapper = new FunctionMapper(env);
			mapper.deleteFunction(className, var);
			String functions = mapper.listMap(item.getFunctions());
			System.out.println(functions);
		}
	}

	/**
	 * The help menu,
	 */
	public void help() {
		HelpScreenConfig.printHelpScreen();
	}

	/**
	 * Quits out of the program. First, it prompts the user if they wish to save
	 * prior to quitting. If so, they save and quit, if not it just quits.
	 */
	public void quit() {
		String answer = reader.readLine("Any unsaved work will be lost, do you want to save? (y/n): ");
		if (answer.equalsIgnoreCase("y")) {
			saveAndQuit();
		} else {
			System.out.println("Quitting program.");
		}
		System.exit(0);
	}

	/**
	 * Saves the file and then quits out of the program.
	 */
	public void saveAndQuit() {
		String fileName = reader.readLine("Please enter file name: ");
		LocalFile file = new LocalFile(env, fileName);
		String answer = reader.readLine("Use filename " + fileName + "? (y/n): ");
		if (answer.equalsIgnoreCase("y")) {
			if (file.hasExistingFileName(fileName) && canOverwriteSave(fileName)) {
				file.saveFile();
			} else if (file.hasExistingFileName(fileName) && !canOverwriteSave(fileName)) {
				return;
			} else {
				file.saveFile();
			}
		} else {
			System.out.println("Save canceled. Quitting program.");
		}
	}

	////////////////////////////////////////////////
	//////////////// HELPER METHODS ////////////////
	////////////////////////////////////////////////

	/**
	 * Checks if the user has confirmed that a class can be overwritten.
	 * 
	 * @param oldClass The old class name
	 * @param newClass The new class name
	 * @return boolean If the class name can be overwritten
	 */
	public boolean canOverwriteEdit(String oldClass, String newClass) {
		boolean overwriteActionComplete = false;
		while (!overwriteActionComplete) {
			String answer = reader.readLine("Change " + oldClass + " to " + newClass + "? (y/n): ");
			if (answer.equalsIgnoreCase("y")) {
				return true;
			} else if (answer.equals("n")) {
				System.out.println("Edit cancelled by user.");
				overwriteActionComplete = true;
			} else {
				logger.warning("Invalid response.");
			}
		}
		return false;
	}

	/**
	 * Can overwrite.
	 *
	 * @param fileName the file name
	 * @return true, if successful
	 */
	public boolean canOverwriteSave(String fileName) {
		boolean saveActionComplete = false;
		while (!saveActionComplete) {
			String answer = reader.readLine(fileName + " is already a saved file. Do you wish to overwrite? (y/n): ");
			if (answer.equalsIgnoreCase("y")) {
				return true;
			} else if (answer.equalsIgnoreCase("n")) {
				System.out.println("Save canceled.");
				saveActionComplete = true;
			} else {
				logger.warning("Invalid response.");
				System.out.println("Input not valid. Please try again.");
			}
		}
		return false;
	}

	/**
	 * Builds a String from a multiple arg command.
	 *
	 * @param input the input
	 * @return String The String
	 */
	public String buildString(String[] input) {
		// build up string again to allow spaces in file save
		StringBuilder buildUp = new StringBuilder();
		for (int i = 1; i < input.length; i++) {
			// Check for -f flag, which allows overwriting same-name file
			if (!input[i].equals("-f")) {
				buildUp.append(input[i]);
				buildUp.append(" ");
			}
		}
		return buildUp.toString().trim();
	}

	/**
	 * Checks if the overwrite flag is present in an arg list.
	 *
	 * @param input the input
	 * @return boolean If the arg list has the overwrite flag.
	 */
	public boolean hasOverwriteFlag(String[] input) {
		for (int i = 1; i < input.length; i++) {
			if (input[i].equals("-f")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the UMLEnvironment
	 * 
	 * @return env The UMLEnvironment
	 */
	public UMLEnvironment getUMLEnvironment() {
		return env;
	}

}
