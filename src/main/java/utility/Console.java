package utility;

import java.io.IOException;
import java.util.logging.Logger;

import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.completer.EnumCompleter;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import config.ConsoleCommands;
import data.UMLEnvironment;
import data.UMLItem;

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

	public Console(UMLEnvironment env) {
		this.env = env;
	}

	/**
	 * The run command. This sets up the initial instance of the Console and builds
	 * the TerminalBuilder, which creates the tools for tab completion in the
	 * terminal, along with a reader that takes user input.
	 */
	public void run() {

		env = new UMLEnvironment();
		TerminalBuilder builder = TerminalBuilder.builder();
		Completer completer = new EnumCompleter(ConsoleCommands.class);
		Terminal terminal = null;

		try {
			terminal = builder.build();
		} catch (IOException e) {
			logger.severe("Error. IOException occurred in run() in Console");
		}

		reader = LineReaderBuilder.builder().terminal(terminal).completer(completer).build();

		reader.option(LineReader.Option.COMPLETE_IN_WORD, true);
		reader.option(LineReader.Option.RECOGNIZE_EXACT, true);
		reader.option(LineReader.Option.CASE_INSENSITIVE, true);

		homeScreen();
	}

	/**
	 * The Home Screen. Is displayed after initial setup, as well as after improper
	 * command input.
	 */
	public void homeScreen() {
		System.out.println("For a list of commands please type \"help\"  ");
		while (true) {
			String[] input = reader.readLine("Please input a command: ").split(" ");
			checkInput(input);
		}
	}

	/**
	 * Checks the user input. Determines if it is a single command or a multiple
	 * argument command and acts accordingly.
	 *
	 * @param lineArr The user input
	 */
	public void checkInput(String[] lineArr) {
		String input = lineArr[0];
		if (lineArr.length > 1) {
			multiArgCommand(lineArr);
			return;
		}

		if (input.equalsIgnoreCase("add")) {
			add();
		} else if (input.equalsIgnoreCase("list")) {
			list();
		} else if (input.equalsIgnoreCase("load")) {
			load();
		} else if (input.equalsIgnoreCase("save")) {
			save();
		} else if (input.equalsIgnoreCase("edit")) {
			edit();
		} else if (input.equalsIgnoreCase("help")) {
			help();
		} else if (input.equalsIgnoreCase("help_multi_arg")) {
			helpMultipleArgs();
		} else if (input.equalsIgnoreCase("find")) {
			find();
		} else if (input.equalsIgnoreCase("quit")) {
			quit();
		} else if (input.equalsIgnoreCase("add_child")) {
			addChild();
		} else if (input.equalsIgnoreCase("remove_child")) {
			removeChild();
		} else {
			logger.info("Error. Invalid Command.");
			homeScreen();
		}
	}

	/**
	 * prompts user for input and calls add function to add a new class in
	 * environment then gives a list of the classes currently in the environment.
	 */
	public void add() {
		String newClass = reader.readLine("Enter new class name: ");
		UMLItem isNull = AddClass.getItem(env, newClass);

		if (isNull != null) {
			logger.warning("Class " + newClass + " is already added. Use \"edit\" to modify this class.\n");
			list();
			homeScreen();
		}

		String answer = reader.readLine("Add class " + newClass + "? (y/n): ");

		if (answer.equals("y")) {
			AddClass.addClass(env, newClass);
			logger.info("Class successfully added.");
		} else if (answer.equals("n")) {
			logger.info("Add cancelled.");
		} else {
			logger.warning("Invalid response.");
		}
		list();
	}

	/**
	 * gives a list of the classes currently in the environment.
	 */
	public void list() {
		StringBuilder builder = new StringBuilder();
		builder.append("List of classes: [ ");
		for (UMLItem i : env.getItems()) {
			builder.append("{" + i.getName() + "} ");
		}
		builder.append("]\n");
		logger.info(builder.toString());
	}

	/**
	 * loads an existing file into the environment if user wishes to save unsaved
	 * work, directed to save otherwise load function is called, then returned to
	 * homescreen.
	 */
	public void load() {
		String answer = reader.readLine("Any unsaved work will be lost, do you want to save? (y/n): ");
		if (answer.equalsIgnoreCase("y")) {
			save();
		} else {
			String fileName = reader.readLine("Enter file name to load: ");
			LocalFile file = new LocalFile(fileName);
			if (!file.hasExistingFileName(fileName)) {
				logger.warning("No such file under name: " + fileName + ". Load canceled.\n");
			} else {
				env = file.loadFile();
				logger.info("File loaded successfully.");
			}
		}
	}

	/**
	 * prompts user for filename and checks if a file already exists under that name
	 * will either cancel or overwrite based on user input.
	 */
	public void save() {
		String answer = reader.readLine("Save current work? (y/n): ");

		if (answer.equalsIgnoreCase("y")) {
			String fileName = reader.readLine("Please enter file name: ");
			LocalFile file = new LocalFile(env, fileName);
			answer = reader.readLine("Use filename " + fileName + "? (y/n): ");
			if (answer.equalsIgnoreCase("y")) {
				if (file.hasExistingFileName(fileName) && canOverwriteSave(fileName)) {
					file.saveFile();
				} else if (file.hasExistingFileName(fileName) && !canOverwriteSave(fileName)) {
					return;
				} else {
					file.saveFile();
				}
			}
		} else {
			logger.info("Save canceled.");
		}
	}

	/**
	 * Edits the class name. Checks if the class exists, and if so it checks if the
	 * new class name is already being used or not. If the new class name does not
	 * already exists it overwrites the existing class name for the chosen class.
	 */
	public void edit() {
		String oldClass = reader.readLine("Enter class name to edit: ");
		// Check if oldClass exists
		if (!classNameExists(oldClass)) {
			logger.warning("Class " + oldClass + " does not exist. Edit cancelled.");
			list();
			return;
		}

		String newClass = getNewClassName();

		if (canOverwriteEdit(oldClass, newClass)) {
			AddClass.editItem(env, oldClass, newClass);
			logger.info("Class " + oldClass + " changed to " + newClass + ".");
		} else {
			logger.info("Edit cancelled.");
		}

		list();
	}

	/**
	 * The help menu.
	 */
	public void help() {
		System.out.println("To add a class type \"add\" ");
		System.out.println("To list a class type \"list\" ");
		System.out.println("To edit a class type \"edit\" ");
		System.out.println("To find if class exists type \"find\" ");
		System.out.println("To save your project \"save\" ");
		System.out.println("To load your project type \"load\" ");
		System.out.println("To add a child to a class type \"add_child\"");
		System.out.println("To remove a child from a class type \"remove_child\"");
		System.out.println("To quit your project type \"quit\" ");
		System.out.println("To list commands type \"help\" ");
		System.out.println("To view single-lined command syntax type \"help_multi_arg\" ");
		System.out.println(" ");
	}

	/**
	 * The help with multiple arguments menu.
	 */
	public void helpMultipleArgs() {
		System.out.println("Here is a list of the commands executed in one line without prompts.");
		System.out.println("add  [className]");
		System.out.println("edit [originalClass] [newClass] ");
		System.out.println("find [className]");
		System.out.println("save [flag \"-f\" to overwrite] [filename]");
		System.out.println("load [flag \"-f\" confirms unsaved changes lost] [filename] ");
		System.out.println("add_child [childClass] [parentClass]");
		System.out.println("remove_child [childClass] [parentClass]");
		System.out.println("list_children [parentClass]");
		System.out.println("list_parents [childClass]");
		System.out.println(" ");
	}

	/**
	 * Checks to see if a class name exists.
	 */
	public void find() {
		String name = reader.readLine("Enter class name to find: ");
		for (UMLItem i : env.getItems()) {
			if (i.getName().equalsIgnoreCase(name)) {
				logger.warning("Class " + name + " exists.");
				return;
			}
		}
		logger.warning("Class does not exist.");
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
			logger.info("Quitting program");
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
			logger.info("Save canceled. Quitting program.");
		}
	}

	/**
	 * Links a class to another via child/parent relationship
	 */
	public void addChild() {
		String childClass = reader.readLine("Enter child class name: ");
		UMLItem c = AddClass.getItem(env, childClass);

		while (c == null) {
			logger.warning("Child name " + childClass + " does not exist, enter valid name: ");
			list();
			childClass = reader.readLine("Enter child name: ");
			c = AddClass.getItem(env, childClass);
		}

		String parentClass = reader.readLine("Enter parent class name: ");
		UMLItem p = AddClass.getItem(env, parentClass);

		while (p == null) {
			logger.warning("parent name " + parentClass + " does not exist, enter valid name: ");
			list();
			parentClass = reader.readLine("Enter parent name: ");
			p = AddClass.getItem(env, parentClass);
		}

		if (c.getParents().contains(p) && p.getChildren().contains(c)) {
			logger.warning("Child " + childClass + "already linked to parent " + parentClass + ".");
		} else if (c == p) {
			logger.warning("Child " + childClass + "cannot be the same as parent " + parentClass + ".");
		} else {
			String answer = reader.readLine("Add child {" + childClass + "} to parent {" + parentClass + "}? {y/n}");
			if (answer.equals("y")) {
				p.addChild(c);
				c.addParent(p);
				logger.info("Child successfully added.");
			} else if (answer.equals("n")) {
				logger.info("Add child cancelled.");
			} else {
				logger.warning("Invalid response.");
			}

		}
	}

	/**
	 * Removes child/parent relationship link between two classes
	 */
	public void removeChild() {
		String childClass = reader.readLine("Enter child class name: ");
		UMLItem c = AddClass.getItem(env, childClass);

		while (c == null) {
			logger.warning("Child name " + childClass + " does not exist, enter valid name: ");
			list();
			childClass = reader.readLine("Enter child name: ");
			c = AddClass.getItem(env, childClass);
		}

		String parentClass = reader.readLine("Enter parent class name: ");
		UMLItem p = AddClass.getItem(env, parentClass);

		while (p == null) {
			logger.warning("parent name " + parentClass + " does not exist, enter valid name: ");
			list();
			parentClass = reader.readLine("Enter parent name: ");
			p = AddClass.getItem(env, parentClass);
		}

		if (!c.getParents().contains(p) && !p.getChildren().contains(c)) {
			logger.warning("Child " + childClass + "not linked to parent " + parentClass + ".");
		} else if (c == p) {
			logger.warning("Child " + childClass + "cannot be the same as parent " + parentClass + ".");
		} else {
			String answer = reader
					.readLine("Remove child {" + childClass + "} from parent {" + parentClass + "}? {y/n}");
			if (answer.equals("y")) {
				p.removeChild(c);
				c.removeParent(p);
				logger.info("Child successfully removed.");
			} else if (answer.equals("n")) {
				logger.info("Remove child cancelled.");
			} else {
				logger.warning("Invalid response.");
			}

		}
	}

	/**
	 * Processes commands in single-line format to skip prompts. Commands taken are
	 * add, edit, find, save and load. Check the help_multi_args command to see
	 * exact specs for each.
	 *
	 * @param input The input
	 */
	public void multiArgCommand(String[] input) {
		String command = input[0].toLowerCase();
		if (command.equals("add")) {
			multiArgAdd(input);
		} else if (command.equals("edit")) {
			multiArgEdit(input);
		} else if (command.equals("find")) {
			multiArgFind(input);
		} else if (command.equals("save")) {
			multiArgSave(input);
		} else if (command.equals("load")) {
			multiArgLoad(input);
		} else if (command.equals("add_child")) {
			multiArgAddChild(input);
		} else if (command.equals("remove_child")) {
			multiArgRemoveChild(input);
		} else if (command.equals("list_children")) {
			multiArgListChildren(input);
		} else if (command.equals("list_parents")) {
			multiArgListParents(input);
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
	public void multiArgAdd(String[] input) {
		// Adds class or promts that it is taken.
		for (int i = 1; i < input.length; i++) {
			String newClass = input[i];
			if (!AddClass.addClass(env, newClass)) {
				logger.warning(newClass + " is already a class.");
			} else {
				logger.info("Class successfully added: " + newClass);
			}
		}
	}

	/**
	 * Takes the input and edits the name of the UMLEnvironment. Checks if there are
	 * valid parameters and if the class exists and the new name is not currently a
	 * class name.
	 *
	 * @param input The input
	 */
	public void multiArgEdit(String[] input) {
		if (input.length < 3) {
			logger.warning("Invalid: edit [oldClass] [newClass] - 3 fields required, " + input.length + " found.");
		} else if (!classNameExists(input[1])) {
			logger.warning("Class " + input[1] + " does not exist. Edit cancelled.");
			list();
		} else {
			if (canOverwriteEdit(input[1], input[2])) {
				AddClass.editItem(env, input[1], input[2]);
				logger.info("Class " + input[1] + " changed to " + input[2] + ".");
			} else {
				logger.info("Edit cancelled.");
			}
		}
	}

	/**
	 * Finds if a class exists in a given set of arguments.
	 *
	 * @param input The input
	 */
	public void multiArgFind(String[] input) {
		for (int i = 1; i < input.length; i++) {
			UMLItem item = AddClass.getItem(env, input[i]);
			if (item != null) {
				logger.info(i + " exists.");
			} else {
				logger.warning(i + " does not exist.");
			}
		}
	}

	/**
	 * Saves a file. Checks if the file name exists and if there is an overwrite
	 * flag attached.
	 *
	 * @param input The input
	 */
	public void multiArgSave(String[] input) {
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
	public void multiArgLoad(String[] input) {
		// Force users to use -f flag to confirm awareness of not saving
		if (!hasOverwriteFlag(input)) {
			System.out.println("Use flag \"-f\" to confirm awareness that unsaved changes will be lost.");
			return;
		}
		String builtFileName = buildString(input);
		LocalFile file = new LocalFile();
		if (!file.hasExistingFileName(builtFileName)) {
			logger.warning("Filename: " + builtFileName + " does not exist.");
			return;
		}
		file.setFileName(builtFileName);
		env = file.loadFile();
	}

	/**
	 * Adds a child class to another class given the input of child and parent to be
	 * linked
	 * 
	 * @param input
	 */
	public void multiArgAddChild(String[] input) {
		if (input.length < 3) {
			logger.warning(
					"Invalid: addchild [childClass] [parentClass] - 3 fields required, " + input.length + " found.");
			return;
		} else if (!classNameExists(input[1])) {
			logger.warning("Child class " + input[1] + " does not exist. Add child cancelled.");
			list();
			return;
		} else if (!classNameExists(input[2])) {
			logger.warning("Parent class " + input[2] + " does not exist. Add child cancelled.");
			list();
			return;
		} else if (input[1] == input[2]) {
			logger.warning(
					"Child " + input[1] + " cannot be the same as parent " + input[2] + ". Add child cancelled.");
			list();
		}

		UMLItem c = AddClass.getItem(env, input[1]);
		UMLItem p = AddClass.getItem(env, input[2]);

		if (c.getParents().contains(p) && p.getChildren().contains(c)) {
			logger.warning("Child " + input[1] + " already linked to parent " + input[2] + ". Add child cancelled.");
		} else {
			p.addChild(c);
			c.addParent(p);
			logger.info("Child successfully added.");
		}

	}

	/**
	 * Removes a child class from another class given the input of child and parent
	 * to be unlinked
	 * 
	 * @param input
	 */
	public void multiArgRemoveChild(String[] input) {
		if (input.length < 3) {
			logger.warning(
					"Invalid: removechild [childClass] [parentClass] - 3 fields required, " + input.length + " found.");
			return;
		} else if (!classNameExists(input[1])) {
			logger.warning("Child class " + input[1] + " does not exist. Remove child cancelled.");
			list();
			return;
		} else if (!classNameExists(input[2])) {
			logger.warning("Parent class " + input[2] + " does not exist. Remove child cancelled.");
			list();
			return;
		}

		UMLItem c = AddClass.getItem(env, input[1]);
		UMLItem p = AddClass.getItem(env, input[2]);

		if (!c.getParents().contains(p) && !p.getChildren().contains(c)) {
			logger.warning("Child " + input[1] + " not linked to parent " + input[2] + ". Remove child cancelled.");
		} else {
			p.removeChild(c);
			c.removeParent(p);
			logger.info("Child successfully removed.");
		}
	}

	/**
	 * Lists the children of a given parent class
	 * 
	 * @param input
	 */
	public void multiArgListChildren(String[] input) {
		if (input.length < 2) {
			logger.warning("Invalid: listchildren [parentClass] - 2 fields required, " + input.length + " found.");
			return;
		} else if (!classNameExists(input[1])) {
			logger.warning("Parent class " + input[1] + " does not exist. List children cancelled.");
			list();
			return;
		} else {
			UMLItem p = AddClass.getItem(env, input[1]);
			StringBuilder builder = new StringBuilder();
			builder.append("List of children [ ");
			for (UMLItem i : p.getChildren()) {
				builder.append("{" + i.getName() + "} ");
			}
			builder.append("]");
			logger.info(builder.toString());
		}
	}

	/**
	 * Lists the parents of a given child class
	 * 
	 * @param input
	 */
	public void multiArgListParents(String[] input) {
		if (input.length < 2) {
			logger.warning("Invalid: listparents [childClass] - 2 fields required, " + input.length + " found.");
			return;
		} else if (!classNameExists(input[1])) {
			logger.warning("Child class " + input[1] + " does not exist. List parents cancelled.");
			list();
			return;
		} else {
			UMLItem c = AddClass.getItem(env, input[1]);
			StringBuilder builder = new StringBuilder();
			builder.append("List of parents [ ");
			for (UMLItem i : c.getParents()) {
				builder.append("{" + i.getName() + "} ");
			}
			builder.append("]");
			logger.info(builder.toString());
		}
	}

	////////////////////////////////////////////////
	//////////////// HELPER METHODS ////////////////
	////////////////////////////////////////////////

	/**
	 * Checks if a class name exists.
	 * 
	 * @return boolean If the class name exists
	 */
	public boolean classNameExists(String className) {
		return (AddClass.getItem(env, className) != null);
	}

	/**
	 * Gets a new class name.
	 * 
	 * @return newClass The new class name
	 */
	public String getNewClassName() {
		boolean newClassNamed = false;
		String newClass = "";
		while (!newClassNamed) {
			newClass = reader.readLine("Enter a new class name: ");
			if (AddClass.getItem(env, newClass) != null) {
				logger.warning("Class " + newClass + " already exists. Please choose another class name.");
				list();
			} else {
				newClassNamed = true;
			}
		}
		return newClass;
	}

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
				logger.info("Save canceled.");
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

}
