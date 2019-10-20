package utility;

import data.UMLEnvironment;

public class Main {
	public static void main(String[] args) {
		//run();
		GUITest(args);
	}

  public static void run() {
	  ReplScanner.initialize();
		UMLEnvironment env = new UMLEnvironment();
		Console console = new Console(env);
		console.run();
		
	}

  public static void GUITest(String[] args) {
	  GUI.main(args);
  }
}
