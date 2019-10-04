package utility;

import java.io.IOException;

import data.UMLEnvironment;

public class Main {

	public static void main(String[] args) throws IOException {
		Run();
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
		//Console.homeScreen(env);
		Console console = new Console(env);
		console.homeScreen();
	}

}
