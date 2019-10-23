package utility;

import java.util.logging.Logger;

import org.jline.reader.LineReader;

import data.UMLEnvironment;
import data.UMLItem;
import deprecated.AddClass;
import mapper.AttributeMapper;

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
	 * the TerminalBuilder, which creates the tools for tab completion in the
	 * terminal, along with a reader that takes user input.
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
			String[] input = reader.readLine("Please input a command: ").split(" ");
			multiArgCommand(input);
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
      add(input);
    } else if (command.equals("delete")) {
      delete(input);
    } else if (command.equals("edit")) {
      edit(input);
    } else if (command.equals("find")) {
      find(input);
    } else if(command.equals("list")) {
      list();
    } else if (command.equals("save")) {
      save(input);
    } else if (command.equals("load")) {
      load(input);
    } else if (command.equals("add_child")) {
      addChild(input);
    } else if (command.equals("remove_child")) {
      removeChild(input);
    } else if (command.equals("list_children")) {
      listChildren(input);
    } else if (command.equals("list_parents")) {
      listParents(input);
    } else if(command.equals("list_attributes")) {
      listAttributes(input);
    } else if (command.equals("edit_attribute")) {
      editAttribute(input);
    } else if (command.equals("add_attribute")) {
      addAttribute(input);
    } else if (command.equals("delete_attribute")) {
      deleteAttribute(input);
    } else if(command.equals("quit")) {
      quit();
    } else if(command.equals("help")) {
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
    for (int i = 1; i < input.length; i++) {
      String newClass = input[i];
      UMLItem item = new UMLItem(newClass);
      env.addItem(item);
    }
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
    for(int i = 2; i < input.length; i++) {
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
    
    if(canOverwriteEdit(oldClass, newClass)) {
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
      env.findItem(input[i]);
    }
  }
  
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
   * Adds a child class to another class given the input of child and parent to be
   * linked
   * 
   * @param input
   */
  public void addChild(String[] input) {
    if (input.length != 3) {
      logger.warning(
          "Invalid: addchild [childClass] [parentClass] - 3 fields required, " + input.length + " found.");
      return;
    }
    
    String childName = input[1];
    String parentName = input[2];
    UMLItem childItem = env.findItem(childName);
    UMLItem parentItem = env.findItem(parentName);
    
    env.addChild(childName, parentName, childItem, parentItem);
    System.out.println(env.listClasses());
  }
  
  /**
   * Removes a child class from another class given the input of child and parent
   * to be unlinked
   * 
   * @param input
   */
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
   * @param input
   */
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
   * Given command list_attributes  [className], log the attributes of given class
   * @param input
   */
  public void listAttributes(String[] input) {
    if (input.length != 2) {
      logger.warning("Invalid: list_attributes [className] - 2 fields required, " + input.length + " found.");
    } else {
      String itemName = input[1];
      AttributeMapper mapper = new AttributeMapper(env);
      String attributes = mapper.listAttributes(itemName);
      System.out.println(attributes);      
    }

  }
  
  /**
   * edit an attribute currently in a class
   * and  gives list of attributes currently in the class
   * 
   * @param args
   */
  public void editAttribute(String[] input) {
    if (input.length != 4) {
      logger.warning("Invalid: editattribute [Class] [Old Attribute] [New Attribute] - 4 fields requierd, " + input.length + " found");
      return;
    }

    String className = input[1];
    String oldAttr = input[2];
    String newAttr = input[3];
    UMLItem editAttr = env.findItem(className);
    if (editAttr == null) {
      logger.warning("Class name " + className + " does not exist.");
      return;
    } else if (editAttr.getAttributes().contains(input[3])) {
      logger.warning("Attribute " + input[3] + " already exists.");
      listAttributes(editAttr);
      return;
      // check to see if oldAttribute exists
    } else if (editAttr.getAttributes().contains(input[2])) {
      editAttr.editAttribute(input[2], input[3]);
      logger.info("Attribute " + input[2] + " changed to " + input[3] + ".");
      listAttributes(editAttr);
      return;
    } else {
      logger.warning("Attribute " + input[2] + " doesn't exist.");
      listAttributes(editAttr);
    }
  }

	/**
	 * add a new attribute in an exisiting class
	 * and gives a list of the attributes currently in the class
	 * 
	 * @param args
	 */
	public void addAttribute(String[] args) {
		// args looks like [add_attribute, [className], [newAttribute]
		// args[1] == [className]

		if (args.length != 3) {
			logger.warning("Invalid: addattribute [Class] [Attribute]");
			return;
		}
		// check that class exists
		UMLItem addAttr = AddClass.getItem(env, args[1]);
		if (addAttr == null) {
			logger.warning("check help for synax");
			return;
		} else if (addAttr.getAttributes().contains(args[2])) {
			logger.warning("Attribute " + args[2] + " already exists.");
			return;
		}
		// class exists - add the attribute to the class
		addAttr.addAttribute(args[2]);
		listAttributes(addAttr);
	}

	/**
	 * delete an attribute currently in a class gives 
	 * and gives list of attributes remaining in the class
	 * 
	 * @param args
	 */
	public void deleteAttribute(String[] args) {
		// args looks like [delete_attribute, [className], [attributeToDelete]
		// args[1] == [className]

		if (args.length != 3) {
			logger.warning("Invalid: addattribute [Class] [AttributeToDelete]");
			return;
		}
		// check that class exists
		UMLItem deleteAttr = AddClass.getItem(env, args[1]);
		if (deleteAttr == null) {
			logger.warning("check help for synax");
			// check to see if attribute exists
		} else if (deleteAttr.getAttributes().contains(args[2])) {
			deleteAttr.removeAttribute(args[2]);
			logger.info("Attribute " + args[2] + " deleted.");
			listAttributes(deleteAttr);
			return;
		} else {
			logger.warning("Attribute " + args[2] + " doesn't exist.");
			listAttributes(deleteAttr);
		}
	}

	/**
	 * The help with multiple arguments menu.
	 */
	public void help() {
		System.out.println("Here is a list of the commands executed in one line without prompts.");
		System.out.println("add  [className]");
		System.out.println("delete [flag \"-f\" to confirm] [className]");
		System.out.println("edit [originalClass] [newClass] ");
		System.out.println("find [className]");
		System.out.println("save [flag \"-f\" to overwrite] [filename]");
		System.out.println("load [flag \"-f\" confirms unsaved changes lost] [filename] ");
		System.out.println("add_child [childClass] [parentClass]");
		System.out.println("remove_child [childClass] [parentClass]");
		System.out.println("list_children [parentClass]");
		System.out.println("list_parents [childClass]");
		System.out.println("add_attribute  [className] [attributeName]");
		System.out.println("edit_attribute  [className] [oldAttributeName] [newAttributeName] ");
		System.out.println("delete_attribute  [className] [attributeName]");
		System.out.println("list_attributes [className]");
		
		System.out.println(" ");
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
			logger.info("Save canceled. Quitting program.");
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
	      env.listClasses();
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
