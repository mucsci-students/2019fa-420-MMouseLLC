
public class Main {

	public static void main(String[] args) {
		Run();
	}
	/*
	 * @author eric
	 * Run is a simple call to the console homescreen
	 * homescreen takes care of the while loop required to keep
	 * the console open during use. Passing the environment around
	 * will ensure the environment stays up to date.
	 * */
	public static void Run() {
		UMLEnvironment env = new UMLEnvironment();
		Console.homeScreen(env);
	}

}
