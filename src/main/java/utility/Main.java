package utility;

import data.UMLEnvironment;

public class Main {

	public static void main(String[] args) throws IOException {
		//Run();
		GUITest(args);
	}
	/*
	 * @author eric
	 * Run is a simple call to the console homescreen
	 * homescreen takes care of the while loop required to keep
	 * the console open during use. Passing the environment around
	 * will ensure the environment stays up to date.
	 * */
  public static void Run() throws IOException  {
	    ReplScanner.initialize();
		UMLEnvironment env = new UMLEnvironment();
		Console console = new Console(env);
		console.homeScreen(); 
	}

  public static void GUITest(String[] args) throws IOException {
	  GUI.main(args);
  }
}
