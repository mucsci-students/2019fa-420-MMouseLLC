package utility;

import data.UMLEnvironment;

public class Main {
	public static void main(String[] args) {
		// If we have a command line arg run the GUI environment
		if (args.length > 0) {
			GUITest(args);
		} else {
			// run CLI
			run();
		}
	}

  public static void run() {
	  	ReplScanner.initialize();
  		UMLEnvironment env = new UMLEnvironment();
		Console console = new Console(env);
		try{
			console.run();
		} catch (org.jline.reader.EndOfFileException e){
			System.exit(0);
		} catch (org.jline.reader.UserInterruptException e){
			System.exit(0);
		}
	}

  public static void GUITest(String[] args) {
	  GUI.main(args);
  }
}
