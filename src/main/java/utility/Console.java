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

<<<<<<< HEAD
	UMLEnvironment env;

	public Console(UMLEnvironment env) {
		this.env = env;
	}

	/**
	 * Where user begins with all commands
	 * 
	 * @param env the UMLEnvironment
	 * @throws IOException signals that an I/O exception has occurred.
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
	 * Processes commands in single-line format to skip prompts. Commands taken are
	 * add, edit, find, save and load. Check the help2 command to see exact specs
	 * for each.
	 * 
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
		} else if (input[0].equals("save")) {
			boolean overWrite = false;
			// build up string again to allow spaces in file save
			ArrayList<String> easier = new ArrayList<String>();
			for (String i : input) {
				easier.add(i);
			}
			easier.remove(0);
			// Check for -f flag, which allows overwriting same-name file
			if (easier.contains("-f")) {
				easier.remove("-f");
				overWrite = true;
			}
			String buildUp = "";
			for (String i : easier) {
				buildUp += i + " ";
			}
			buildUp = buildUp.trim();
			LocalFile file = new LocalFile(env, buildUp);
			// Only allow existing files to overwrite if -f flag
			if (file.hasExistingFileName(buildUp) && !overWrite) {
				System.out.println(
						"Filename: " + buildUp + " already exists. Run command with \"-f\" flag to overwrite.");
				return;
			}
			file.saveFile();
		} else if (input[0].equals("load")) {
			ArrayList<String> easier = new ArrayList<String>();
			for (String i : input) {
				easier.add(i);
			}
			easier.remove(0);
			// Force users to use -f flag to confirm awareness of not saving
			if (easier.contains("-f")) {
				easier.remove("-f");
			} else {
				System.out.println("Use flag \"-f\" to confirm awareness that unsaved changes will be lost.");
				return;
			}
			String buildUp = "";
			for (String i : easier) {
				buildUp += i + " ";
			}
			buildUp = buildUp.trim();
			LocalFile file = new LocalFile();
			if (!file.hasExistingFileName(buildUp)) {
				System.out.println("Filename: " + buildUp + " does not exist.");
				return;
			}
			file.setFileName(buildUp);
			env = file.loadFile();
		} else if (input[0].equals("addchild")) {
			if (input.length < 3) {
				System.out.println("Invalid: addchild [child] [parent]");
				return;
			}
			UMLItem c = AddClass.getItem(env, input[1]);
			UMLItem p = AddClass.getItem(env, input[2]);
			if (c == null || p == null) {
				System.out.println("Invalid: addchild [child] [parent], both must be from current class list");
				return;
			} else if (c.getParents().contains(p) && p.getChildren().contains(c)) {
				System.out.print("child already linked to parent.\n");
				return;
			} else if (c == p) {
				System.out.print("child cannot be the same as parent\n");
				return;
			} else {
				p.addChild(c);
				c.addParent(p);
			}

		} else if (input[0].equals("removechild"))

		{
			if (input.length < 3) {
				System.out.println("Invalid: addchild [child] [parent]");
				return;
			}
			UMLItem c = AddClass.getItem(env, input[1]);
			UMLItem p = AddClass.getItem(env, input[2]);
			if (c == null || p == null) {
				System.out.println("Invalid: removechild [child] [parent], both must be from current class list");
				return;
			} else if (!c.getParents().contains(p) && !p.getChildren().contains(c)) {
				System.out.print("child not linked to parent.\n");
				return;
			} else {

				p.removeChild(c);
				c.removeParent(p);

			}
		} else if (input[0].equals("listchildren")) {
			if (input.length < 2) {
				System.out.println("Invalid: listchildren [parent]");
				return;
			} else {
				UMLItem p = AddClass.getItem(env, input[1]);
				if (p == null) {
					System.out.print("parent not in list.\n");
				} else {
					System.out.print("List of chrildren [ ");
					for (UMLItem i : p.getChildren()) {
						System.out.print(i.getName() + " ");
					}
					System.out.print("]\n");
				}
			}
		} else if (input[0].equals("listparents")) {
			if (input.length < 2) {
				System.out.println("Invalid: listparents [child]");
				return;
			} else {
				UMLItem p = AddClass.getItem(env, input[1]);
				if (p == null) {
					System.out.print("child not in list.\n");
				} else {
					System.out.print("List of chrildren [ ");
					for (UMLItem i : p.getChildren()) {
						System.out.print(i.getName() + " ");
					}
					System.out.print("]\n");
				}
			}
		} else

		{

			System.out.println("Invalid Command");
		}

	}

	/**
	 * Takes users command from homescreen and checks if its a valid command
	 * 
	 * @param input users request
	 * @param env   the UMLEvnironment
	 * @throws IOException signals that an I/O exception has occurred
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
		} else if (input.toLowerCase().equals("addchild")) {
			addChild();
		} else if (input.toLowerCase().equals("removechild")) {
			removeChild();
		} else if (input.toLowerCase().equals("listchildren")) {
			multiArgCommand(lineArr);
		} else if (input.toLowerCase().equals("listparents")) {
			multiArgCommand(lineArr);
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
	 * @param env the UMLEvnironment
	 * @throws IOException signals that an I/O exception has occurred
	 */
	public void add() throws IOException {
		System.out.print("Enter new class name: ");
		final Scanner console = ReplScanner.getInstance();
		String newClass = console.next();
		UMLItem isNull = AddClass.getItem(env, newClass);
		if (isNull != null) {
			System.out.println("Class " + newClass + " is already added. Use \"edit\" to modify this class.");
			console.nextLine();
			list();
			return;
		}
		System.out.print("Add class " + newClass + "? (y/n): ");
		while (true) {
			String answer = console.next();
			if (answer.equals("y")) {
				AddClass.addClass(env, newClass);
				console.nextLine();
				list();
				return;
			} else if (answer.equals("n")) {
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
	 * @param env the UMLEnvironment
	 * @throws IOException signals that an I/O exception has occurred
	 */
	public void list() throws IOException {
		System.out.print("List of classes: [ ");
		for (UMLItem i : env.getItems()) {
			System.out.print(i.getName() + " ");
		}
		System.out.print("]\n");
	}

	/**
	 * loads an existing file into the environment if user wishes to save unsaved
	 * work, directed to save otherwise load function is called, then returned to
	 * homescreen
	 * 
	 * @param env the UMLEnvironment
	 * @throws IOException signals that an I/O exception has occurred
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
	 * prompts user for filename and checks if a file already exists under that name
	 * will either cancel or overwrite based on user input, then return to
	 * homescreen
	 * 
	 * @param env the UMLEnvironment
	 * @throws IOException signals that an I/O exception has occurred
	 */
	public void save() throws IOException {
=======
  /** The Constant logger. */
  private static final Logger logger = Logger.getLogger(Console.class.getName());

  /** The LineReader. */
  private LineReader reader;
  
  /** The UMLEnvironment. */
  private UMLEnvironment env;

  public Console(UMLEnvironment env) {
	    this.env = env;
	  }
>>>>>>> origin

  /**
   * The run command. This sets up the initial instance of the
   * Console and builds the TerminalBuilder, which creates the
   * tools for tab completion in the terminal, along with a
   * reader that takes user input.
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

<<<<<<< HEAD
	public void edit() throws IOException {
		System.out.print("Enter old class name: ");
		final Scanner console = ReplScanner.getInstance();
		String oldClass = console.next();
		// Check if oldClass exists
		if (AddClass.getItem(env, oldClass) == null) {
			System.out.println("Class " + oldClass + " does not exist. Please choose an existing class.");
			console.nextLine();
			list();
			return;
		}
		System.out.print("Enter new class name: ");
		String newClass = console.next();
		// check if new class is not already a class
		if (AddClass.getItem(env, newClass) != null) {
			System.out.println("Class " + newClass + " already exists. Please choose another class name.");
			console.nextLine();
			list();
			return;
		}

		System.out.print("Change " + oldClass + " to " + newClass + "? (y/n): ");
		while (true) {
			String answer = console.next();
			if (answer.equals("y")) {
				AddClass.editItem(env, oldClass, newClass);
				System.out.println("Class " + oldClass + " changed to " + newClass + ".");
				list();
				console.nextLine();
				return;
			} else if (answer.equals("n")) {
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
		System.out.println("To add a child to a class type \"addchild\" ");
		System.out.println("To remove a child from a class type \"removechild\" ");
		System.out.println("To view single-lined command syntax type \"help2\" ");
		System.out.println(" ");
	}

	public void help2() {
		System.out.println("Here is a list of the commands executed in one line without prompts.");
		System.out.println("add  [className]");
		System.out.println("edit [originalClass] [newClass] ");
		System.out.println("find [className]");
		System.out.println("save [flag \"-f\" to overwrite] [filename]");
		System.out.println("load [flag \"-f\" confirms unsaved changes lost] [filename] ");
		System.out.println("addchild [child] [parent]");
		System.out.println("removechild [child] [parent]");
		System.out.println("listchildren [parent]");
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
=======
    reader = LineReaderBuilder.builder()
                              .terminal(terminal)
                              .completer(completer)
                              .build();
    
    reader.option(LineReader.Option.COMPLETE_IN_WORD, true);
    reader.option(LineReader.Option.RECOGNIZE_EXACT, true);
    reader.option(LineReader.Option.CASE_INSENSITIVE, true);

    homeScreen();
  }

  /**
   * The Home Screen. Is displayed after initial setup, as well
   * as after improper command input.
   */
  public void homeScreen() {
    System.out.println("For a list of commands please type \"help\"  ");
    while (true) {
      String[] input = reader.readLine("Please input a command: ").split(" ");
      checkInput(input);
    }
  }
  
  /**
   * Checks the user input. Determines if it is a single command
   * or a multiple argument command and acts accordingly.
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
    
    if (isNull != null){
      logger.warning("Class " + newClass + " is already added. Use \"edit\" to modify this class.\n");
      list();
      homeScreen();
    }
    
    String answer = reader.readLine("Add class " + newClass + "? (y/n): ");
    
      if (answer.equals("y")) {
        AddClass.addClass(env, newClass);
        logger.info("Class successfully added.");
      } else if (answer.equals("n")){
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
   * loads an existing file into the environment if user wishes to save
   * unsaved work, directed to save otherwise load function is called, then
   * returned to homescreen.
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
   * prompts user for filename and checks if a file already exists under
   * that name will either cancel or overwrite based on user input.
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
        } else if(file.hasExistingFileName(fileName) && !canOverwriteSave(fileName)) {
          return;    
        }
        else {
          file.saveFile();
        }
      }
    } else {
      logger.info("Save canceled.");
    }
  }
  
  /**
   * Edits the class name. Checks if the class exists, and if so
   * it checks if the new class name is already being used or not.
   * If the new class name does not already exists it overwrites
   * the existing class name for the chosen class.
   */
  public void edit() {
    String oldClass = reader.readLine("Enter class name to edit: ");
    // Check if oldClass exists
    if (!classNameExists(oldClass)){
      logger.warning("Class " + oldClass + " does not exist. Edit cancelled.");
      list();
      return;
    }
    
    String newClass = getNewClassName();
    
    if(canOverwriteEdit(oldClass, newClass)) {
      AddClass.editItem(env, oldClass, newClass);
      logger.info("Class " + oldClass + " changed to " + newClass+ ".");      
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
    System.out.println("To quit your project type \"quit\" ");
    System.out.println("To list commands type \"help\" ");
    System.out.println("To view single-lined command syntax type \"help_multi_arg\" ");
    System.out.println(" ");
  }
>>>>>>> origin

  /**
   * The help with multiple arguments menu.
   */
  public void helpMultipleArgs(){
    System.out.println("Here is a list of the commands executed in one line without prompts.");
    System.out.println("add  [className]");
    System.out.println("edit [originalClass] [newClass] ");
    System.out.println("find [className]");
    System.out.println("save [flag \"-f\" to overwrite] [filename]");
    System.out.println("load [flag \"-f\" confirms unsaved changes lost] [filename] ");
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
   * Quits out of the program. First, it prompts the user if they
   * wish to save prior to quitting. If so, they save and quit, if
   * not it just quits.
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
      } else if(file.hasExistingFileName(fileName) && !canOverwriteSave(fileName)) {
        return;    
      } else {
        file.saveFile();
      }
    } else {
      logger.info("Save canceled. Quitting program.");
    }
  }
  
  /**
   * Processes commands in single-line format to skip prompts. 
   * Commands taken are add, edit, find, save and load.
   * Check the help_multi_args command to see exact specs for each.  
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
    } else if (command.equals("save")){
      multiArgSave(input);
    } else if (command.equals("load")){
      multiArgLoad(input);
    } else {
      logger.warning("Invalid Command");
    }
  }
  
  /**
   * Takes the input adds the new class(es) in environment,
   * then gives a list of the classes currently in the environment.
   *
   * @param input The input
   */
  public void multiArgAdd(String[] input) {
    // Adds class or promts that it is taken.
    for(int i = 1; i < input.length; i++) {
      String newClass = input[i];
      if (!AddClass.addClass(env, newClass)) {
        logger.warning(newClass + " is already a class.");
      } else {
        logger.info("Class successfully added: " + newClass);
      }
    }    
  }
  
  /**
   * Takes the input and edits the name of the UMLEnvironment. Checks
   * if there are valid parameters and if the class exists and the
   * new name is not currently a class name.
   *
   * @param input The input
   */
  public void multiArgEdit(String[] input) {
    if (input.length < 3) {
      logger.warning("Invalid: edit [oldClass] [newClass] - 3 fields required, " + input.length + " found.");
    } else if (!classNameExists(input[1])){
      logger.warning("Class " + input[1] + " does not exist. Edit cancelled.");
      list();
    } else {
      if(canOverwriteEdit(input[1], input[2])) {
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
    for(int i = 1; i < input.length; i++) {
      UMLItem item = AddClass.getItem(env, input[i]);
      if (item != null) {
        logger.info(i + " exists.");
      } else {
        logger.warning(i + " does not exist.");
      }     
    }
  }
  
  /**
   * Saves a file. Checks if the file name exists and if there
   * is an overwrite flag attached.
   *
   * @param input The input
   */
  public void multiArgSave(String[] input) {
    String builtFileName = buildString(input);
    LocalFile file = new LocalFile(env, builtFileName);
    // Only allow existing files to overwrite if -f flag 
    if (file.hasExistingFileName(builtFileName) && !hasOverwriteFlag(input)){
      logger.warning("Filename: " + builtFileName + " already exists. Run command with \"-f\" flag to overwrite.");
      return;
    }
    file.saveFile();
  }
  
  /**
   * Loads a file. Checks if the file name exists and if there is
   * a flag to load without saving prior.
   *
   * @param input The input
   */
  public void multiArgLoad(String[] input) {
    // Force users to use -f flag to confirm awareness of not saving
    if (!hasOverwriteFlag(input)){
      System.out.println("Use flag \"-f\" to confirm awareness that unsaved changes will be lost.");
      return;
    }
    String builtFileName = buildString(input);
    LocalFile file = new LocalFile();
    if (!file.hasExistingFileName(builtFileName)){
      logger.warning("Filename: " + builtFileName + " does not exist.");
      return;
    }
    file.setFileName(builtFileName);
    env = file.loadFile();
  }
  
  
  ////////////////////////////////////////////////
  //////////////// HELPER METHODS ////////////////
  ////////////////////////////////////////////////

<<<<<<< HEAD
	public void addChild() throws IOException {
		final Scanner console = ReplScanner.getInstance();
		System.out.print("Enter child name: ");

		String chName = console.nextLine();
		UMLItem c = AddClass.getItem(env, chName);
		while (c == null) {
			System.out.println("Child name does not exist, enter valid name: ");
			list();
			System.out.print("Enter child name: ");
			chName = console.nextLine();
			c = AddClass.getItem(env, chName);
		}

		System.out.print("Enter parent name: ");

		String parName = console.nextLine();
		UMLItem p = AddClass.getItem(env, parName);

		while (p == null) {
			System.out.println("Parent name does not exist, enter valid name: ");
			list();
			System.out.print("Enter parent name: ");
			parName = console.nextLine();
			p = AddClass.getItem(env, parName);
		}

		if (c.getParents().contains(p) && p.getChildren().contains(c)) {
			System.out.print("Child already linked to parent.\n");
		} else if (c == p) {
			System.out.print("child cannot be the same as parent\n");
		} else {

			p.addChild(c);
			c.addParent(p);
			System.out.print("child added.\n");
		}
	}

	public void removeChild() throws IOException {
		final Scanner console = ReplScanner.getInstance();
		System.out.print("Enter child name: ");

		String chName = console.nextLine();
		UMLItem c = AddClass.getItem(env, chName);
		while (c == null) {
			System.out.println("Child name does not exist, enter valid name: ");
			list();
			System.out.print("Enter child name: ");
			chName = console.nextLine();
			c = AddClass.getItem(env, chName);
		}

		System.out.print("Enter parent name: ");

		String parName = console.nextLine();
		UMLItem p = AddClass.getItem(env, parName);

		while (p == null) {
			System.out.println("Parent name does not exist, enter valid name: ");
			list();
			System.out.print("Enter parent name: ");
			parName = console.nextLine();
			p = AddClass.getItem(env, parName);
		}
		// To see if UMLItem child is a child of UMLItem parent:
		// if (child.getParents().contains(parent) ) //then parent is a parent of child
		// if (parent.getChildren().contains(child) // then child is a child of parent
		//
		if (!c.getParents().contains(p) && !p.getChildren().contains(c)) {
			System.out.print("child not linked to parent.\n");
		} else {

			p.removeChild(c);
			c.removeParent(p);
			System.out.print("child removed.\n");
		}
	}

}
=======
  
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
    while(!newClassNamed) {
      newClass = reader.readLine("Enter a new class name: ");
      if(AddClass.getItem(env, newClass) != null) {
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
    while(!overwriteActionComplete) {
      String answer = reader.readLine("Change " + oldClass + " to " + newClass + "? (y/n): ");
      if (answer.equalsIgnoreCase("y")) {
        return true;
      } else if (answer.equals("n")){
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
    while(!saveActionComplete) {
      String answer = reader.readLine(fileName + " is already a saved file. Do you wish to overwrite? (y/n): ");
      if (answer.equalsIgnoreCase("y")) {
        return true;
      } else if(answer.equalsIgnoreCase("n")){
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
      if(!input[i].equals("-f")) {
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
    for(int i = 1; i < input.length; i++) {
      if(input[i].equals("-f")) {
        return true;
      }
    }
    return false;
  }
  
}
>>>>>>> origin
