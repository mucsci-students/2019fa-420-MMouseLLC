package utility;

/**
 * Beginning of program
 */
public class Main {
  
  private static Console console = new Console();

	/**
	 * Depending on cmd line args start different instance of UML Editor Project
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		//If we have a command line arg run the GUI environment
		if (args.length > 0) {
			runGUI(args);
		} else {
			 //run CLI
			run();
		}
	}
	
	/**
	 * Run the Console
	 */
	public static void run() {
		try {
			console.run();
		} catch (org.jline.reader.EndOfFileException e) {
			System.exit(0);
		} catch (org.jline.reader.UserInterruptException e) {
			System.exit(0);
		}
	}

	/**
	 * Run Graphical User Interface
	 * @param args
	 */
	public static void runGUI(String[] args) {
		GUI.main(args);
	}
	
	/**
	 * Gets the Console
	 * 
	 * @return console the Console
	 */
	public Console getConsole() {
	  return console;
	}
}

